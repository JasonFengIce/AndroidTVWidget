package com.open.demo.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuItem;

import java.util.ArrayList;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public class MenuAdapter extends RecyclerView.Adapter {

    private OpenMenu mOpenMenu;
    private OpenPresenter mPresenter;

    public MenuAdapter(Context context, OpenMenu openMenu) {
        this.mOpenMenu = openMenu;
        mPresenter = new TreeMenuPresenter();
    }

    public void setOpenMenu(OpenMenu openMenu) {
        this.mOpenMenu = openMenu;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        OpenPresenter presenter = this.mPresenter;
        OpenPresenter.ViewHolder presenterVh;
        presenterVh = presenter.onCreateViewHolder(parent);
        view = presenterVh.view;
        ViewHolder viewHolder = new ViewHolder(view, presenter, presenterVh);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArrayList<OpenMenuItem> items = mOpenMenu.getMenuDatas();
        OpenMenuItem menuItem = items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        this.mPresenter.onBindViewHolder(viewHolder.getViewHolder(), menuItem);
    }

    @Override
    public int getItemCount() {
        return mOpenMenu != null ? mOpenMenu.getMenuDatas().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final OpenPresenter.ViewHolder mHolder;
        final OpenPresenter mPresenter;

        public ViewHolder(View itemView, OpenPresenter presenter, OpenPresenter.ViewHolder holder) {
            super(itemView);
            this.mPresenter = presenter;
            this.mHolder = holder;
        }

        public OpenPresenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public OpenPresenter getOpenPresenter() {
            return this.mPresenter;
        }

    }
}
