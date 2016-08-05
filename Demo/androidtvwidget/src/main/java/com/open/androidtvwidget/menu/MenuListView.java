package com.open.androidtvwidget.menu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 菜单控件.
 * Created by hailongqiu on 2016/8/5.
 */
public class MenuListView extends LinearLayout {

    private MenuRecycleBin mRecycler;
    private BaseMenuAdapter mAdapter;
    private MenuSetObserver mDataSetObserver;
    private int mOldItemCount;
    private int mItemCount;
    private boolean mDataChanged;

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

    public void setAdapter(BaseMenuAdapter baseMenuAdapter) {
        ListView ls;
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        this.mAdapter = baseMenuAdapter;

        if (this.mAdapter != null) {
            mOldItemCount = mItemCount;
            mItemCount = mAdapter.getCount();
            mDataSetObserver = new MenuListViewSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }

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
        View getTransientStateView(int position) {
            return null;
        }

        void addScrapView(View scrap, int position) {

        }

        View getActiveView(int position) {
            return null;
        }
    }
}
