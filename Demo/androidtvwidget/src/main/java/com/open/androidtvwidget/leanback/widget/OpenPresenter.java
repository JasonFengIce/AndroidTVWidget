package com.open.androidtvwidget.leanback.widget;

import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.menu.OpenMenu;

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

    public abstract ViewHolder onCreateViewHolder(ViewGroup parent);

    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    public abstract int getItemCount();

}
