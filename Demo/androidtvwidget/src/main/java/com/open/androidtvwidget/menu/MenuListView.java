package com.open.androidtvwidget.menu;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.open.androidtvwidget.menu.BaseMenuAdapter;
import com.open.androidtvwidget.menu.IOpenMenu;
import com.open.androidtvwidget.menu.MenuSetObserver;

/**
 * Created by hailongqiu on 2016/8/5.
 */
public class MenuListView extends LinearLayout {

    private BaseMenuAdapter mBaseMenuAdapter;
    private MenuSetObserver mDataSetObserver;

    public MenuListView(Context context) {
        this(context, null);
    }

    public MenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setAdapter(BaseMenuAdapter baseMenuAdapter) {

        if (mBaseMenuAdapter != null && mDataSetObserver != null) {
            mBaseMenuAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        this.mBaseMenuAdapter = baseMenuAdapter;

        if (this.mBaseMenuAdapter != null) {
            mDataSetObserver = new MenuListViewSetObserver();
            mBaseMenuAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    public BaseMenuAdapter getAdapter() {
        return this.mBaseMenuAdapter;
    }

    protected class MenuListViewSetObserver extends MenuSetObserver {
        @Override
        public void onChanged(IOpenMenu openMenu) {
            super.onChanged(openMenu);
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}
