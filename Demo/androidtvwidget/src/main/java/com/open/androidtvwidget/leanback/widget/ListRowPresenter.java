package com.open.androidtvwidget.leanback.widget;

import android.view.ViewGroup;

/**
 * Created by hailongqiu on 2016/8/24.
 */
public class ListRowPresenter extends OpenPresenter {

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }

}
