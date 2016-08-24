package com.open.androidtvwidget.leanback.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public abstract class OpenPresenter {

    /**
     * 基本数据类型(ViewHolder)
     */
    public static class ViewHolder {

        public final View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }

    public abstract int getItemCount();
    public abstract int getItemViewType(int position);
    public abstract ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onViewAttachedToWindow(ViewHolder viewHolder);
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);
    public abstract void onViewDetachedFromWindow (ViewHolder viewHolder);
    public abstract void onUnbindViewHolder(ViewHolder viewHolder);

}
