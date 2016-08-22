package com.open.demo.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public class MenuAdapter extends RecyclerView.Adapter {

    TreeMenuPresenter mTreeMenuPresenter;
    private OpenMenu mOpenMenu;

    public MenuAdapter(Context context) {
        mTreeMenuPresenter = new TreeMenuPresenter(context); //
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MenuPresenter.ViewHolder presenterVh = mTreeMenuPresenter.onCreateViewHolder(parent);
        ViewHolder viewHolder = new ViewHolder(presenterVh.view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArrayList<OpenMenuItem> items = mOpenMenu.getMenuDatas();
        OpenMenuItem menuItem = items.get(position);
    }

    @Override
    public int getItemCount() {
        return mOpenMenu != null ? mOpenMenu.getMenuDatas().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
