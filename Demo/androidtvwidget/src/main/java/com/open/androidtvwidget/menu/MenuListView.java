package com.open.androidtvwidget.menu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;

import com.open.androidtvwidget.utils.OPENLOG;

import java.util.ArrayList;

/**
 * 菜单控件.
 * Created by hailongqiu on 2016/8/5.
 */
public class MenuListView extends LinearLayout {

    public static final int INVALID_POSITION = -1;
    public static final long INVALID_ROW_ID = Long.MIN_VALUE;
    /**
     * Regular layout - usually an unsolicited layout from the view system
     */
    static final int LAYOUT_NORMAL = 0;

    /**
     * Show the first item
     */
    static final int LAYOUT_FORCE_TOP = 1;
    private FlingRunnable mFlingRunnable;
    private MenuRecycleBin mRecycler;
    private BaseMenuAdapter mAdapter;
    private MenuSetObserver mDataSetObserver;
    private int mOldItemCount;
    private int mItemCount;
    int mSelectedPosition = INVALID_POSITION;
    private boolean mDataChanged;
    boolean mBlockLayoutRequests;
    boolean mInLayout = false;
    int mLayoutMode = LAYOUT_NORMAL;
    int mOldSelectedPosition = INVALID_POSITION;
    /**
     * The id of the last selected position we used when notifying
     */
    long mOldSelectedRowId = INVALID_ROW_ID;

    public MenuListView(Context context) {
        this(context, null);
    }

    public MenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRecycler = new MenuRecycleBin();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //
        layoutChildren();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void layoutChildren() {
        OPENLOG.D("layoutChildren");
        final boolean blockLayoutRequests = mBlockLayoutRequests;
        if (blockLayoutRequests) {
            return;
        }
        mBlockLayoutRequests = true;

        if (mAdapter == null) {
            resetList();
        }

        try {

            invalidate();

            // 加载模式.
            switch (mLayoutMode) {
                case LAYOUT_FORCE_TOP:
                    break;
            }

            // mDataChanged = true，有数据改变时;
            boolean dataChanged = mDataChanged;
            if (dataChanged) {
                handleDataChanged();
            }
        } finally {
            if (!blockLayoutRequests) {
                mBlockLayoutRequests = false;
            }
        }
    }

    protected void handleDataChanged() {

    }

    protected void resetList() {
        removeAllViewsInLayout();
        mDataChanged = false; // 数据改变.
        mLayoutMode = LAYOUT_NORMAL; // 加载模式.
        mOldSelectedPosition = INVALID_POSITION;
        mOldSelectedRowId = INVALID_ROW_ID;
        invalidate();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Dispatch in the normal way
        boolean handled = super.dispatchKeyEvent(event);
        if (!handled) {
            // If we didn't handle it...
            View focused = getFocusedChild();
            if (focused != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                // ... and our focused child didn't handle it
                // ... give it to ourselves so we can scroll if necessary
                handled = onKeyDown(event.getKeyCode(), event);
            }
        }
        return handled;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    private boolean commonKey(int keyCode, int count, KeyEvent event) {
        boolean handled = false;
        int action = event.getAction();

        if (handled) {
            return true;
        }

        if (action != KeyEvent.ACTION_UP) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded();
                        if (!handled) {
                            while (count-- > 0) {
                                if (arrowScroll(FOCUS_UP)) {
                                    handled = true;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded();
                        if (!handled) {
                            while (count-- > 0) {
                                if (arrowScroll(FOCUS_DOWN)) {
                                    handled = true;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    break;
                case KeyEvent.KEYCODE_PAGE_UP:
                    break;
                case KeyEvent.KEYCODE_PAGE_DOWN:
                    break;
            }
        }

        switch (action) {
            case KeyEvent.ACTION_DOWN:
                return super.onKeyDown(keyCode, event);
            case KeyEvent.ACTION_UP:
                return super.onKeyUp(keyCode, event);
            case KeyEvent.ACTION_MULTIPLE:
                return super.onKeyMultiple(keyCode, count, event);
            default: // shouldn't happen
                return false;
        }
    }

    boolean resurrectSelectionIfNeeded() {
        if (mSelectedPosition < 0 && resurrectSelection()) {
            updateSelectorState();
            return true;
        }
        return false;
    }

    boolean resurrectSelection() {
        return false;
    }

    void updateSelectorState() {
    }

    /**
     * 用于响应按键重复点击.
     *
     * @param keyCode
     * @param repeatCount
     * @param event
     * @return
     */
    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public void addView(View child) {
        throw new UnsupportedOperationException("addView 在MenuListView中无法使用... ...，请使用setAdapter");
    }

    public void setAdapter(BaseMenuAdapter baseMenuAdapter) {
        OPENLOG.D("setAdapter");
        ListView ls;
        BaseAdapter ad;

        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        resetList();
        mRecycler.clear();

        this.mAdapter = baseMenuAdapter;

        if (this.mAdapter != null) {
            mOldItemCount = mItemCount;
            mItemCount = mAdapter.getCount();
            mDataSetObserver = new MenuListViewSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
            mRecycler.setViewTypeCount(mAdapter.getViewTypeCount());
        } else {
            //
        }
        // 重新调用 onMeasure onLayout.
        requestLayout();
    }

    public BaseMenuAdapter getAdapter() {
        return this.mAdapter;
    }

    /**
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    private View makeAndAddView(int position) {
        View child;
        if (!mDataChanged) {
            child = mRecycler.getActiveView(position);
            if (child != null) {
                setupChild(child, position);
                return child;
            }
        }
        return null;
    }

    private void obtainView(int position) {
        final View transientView = mRecycler.getTransientStateView(position);
        final View updatedView = this.mAdapter.getView(position, transientView, this);
        if (transientView != null) {
            final LayoutParams params = (LayoutParams) transientView.getLayoutParams();
            if (transientView != updatedView) {
                mRecycler.addScrapView(updatedView, position);
            }
        }
    }

    private void setupChild(View child, int position) {
        addViewInLayout(child, position, null);
    }

    private View fillFromTop(int nextTop) {
        return null;
    }

    private View fillDown(int pos, int nextTop) {
        return null;
    }

    // 滚动.
    boolean arrowScroll(int direction) {
        mInLayout = true;
        try {
            final boolean handled = arrowScrollImpl(direction);
            // 按键声音.
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            return handled;
        } finally {
            mInLayout = false;
        }
    }

    private boolean arrowScrollImpl(int direction) {
        if (getChildCount() < 0) {
            return false;
        }
        return false;
    }

    public View getSelectedView() {
        return null;
    }

    public void smoothScrollBy(int distance, int duration) {
        smoothScrollBy(distance, duration, false);
    }

    public void smoothScrollBy(int distance, int duration, boolean linear) {
        if (mFlingRunnable == null) {
            mFlingRunnable = new FlingRunnable();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mRecycler.clear();

        if (mFlingRunnable != null) {
            removeCallbacks(mFlingRunnable);
        }
    }

    protected class MenuListViewSetObserver extends MenuSetObserver {
        private SparseArray<View> mTransientStateViews;

        @Override
        public void onChanged() {
        }

        @Override
        public void onInvalidated() {
        }
    }

    protected class MenuRecycleBin {

        private ArrayList<View>[] mScrapViews;
        private ArrayList<View> mCurrentScrap;
        private ArrayList<View> mSkippedScrap;
        private SparseArray<View> mTransientStateViews;

        View getTransientStateView(int position) {
            return null;
        }

        void addScrapView(View scrap, int position) {

        }

        View getActiveView(int position) {
            return null;
        }

        void clear() {
            clearTransientStateViews();
        }

        void clearTransientStateViews() {

        }

        public void setViewTypeCount(int viewTypeCount) {
        }
    }

    private class FlingRunnable implements Runnable {
        private final OverScroller mScroller;

        FlingRunnable() {
            mScroller = new OverScroller(getContext());
        }

        void start(int initialVelocity) {

        }

        void startScroll(int distance, int duration, boolean linear) {

        }

        @Override
        public void run() {

        }
    }
}
