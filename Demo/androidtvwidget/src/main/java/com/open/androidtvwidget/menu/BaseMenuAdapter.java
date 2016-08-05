package com.open.androidtvwidget.menu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hailongqiu on 2016/8/5.
 */
public abstract class BaseMenuAdapter {

    private MenuDataObservable mMenuSetObserver = new MenuDataObservable(); // 观察者.

    public abstract View getView(int position, View convertView, ViewGroup parent);
    public abstract long getItemId(int position);
    public abstract Object getItem(int position);
    public abstract  int getCount();
    public abstract int getItemViewType(int position);

    public void notifyDataSetChanged() {
        if (mMenuSetObserver != null) {
            mMenuSetObserver.notifyChanged();
        }
    }

    public void notifyDataSetInvalidated() {
        if (mMenuSetObserver != null) {
            mMenuSetObserver.notifyInvalidated();
        }
    }

    public void registerDataSetObserver(MenuSetObserver observer) {
        mMenuSetObserver.unregisterObserver(observer);
    }

    public void unregisterDataSetObserver(MenuSetObserver observer) {
        mMenuSetObserver.unregisterObserver(observer);
    }

}
