package com.open.demo.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.menu.MenuSetObserver;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuItem;
import com.open.androidtvwidget.recycle.RecyclerViewTV;
import com.open.androidtvwidget.utils.OPENLOG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public class MenuAdapter extends RecyclerView.Adapter {

    private OpenMenu mOpenMenu;
    private OpenPresenter mPresenter;

    public MenuAdapter(RecyclerViewTV recyclerView, OpenMenu openMenu) {
        this.mOpenMenu = openMenu;
        this.mPresenter = new TreeMenuPresenter(recyclerView);
        //
        this.mOpenMenu.registerDataSetObserver(new MenuSetObserver() {
            @Override
            public void onShow(OpenMenu openMenu) {
                super.onShow(openMenu);
                OPENLOG.D("onShow123456789");
            }

            @Override
            public void onHide(OpenMenu openMenu) {
                super.onHide(openMenu);
                OPENLOG.D("onHide123456789");
            }
        });
    }

    public void setOpenMenu(OpenMenu openMenu) {
        this.mOpenMenu = openMenu;
    }

    public Object getItemPosition(int position) {
        return mOpenMenu.getMenuDatas().get(position);
    }

    public void addAll(List<OpenMenuItem> list, int pos) {
        mOpenMenu.getMenuDatas().addAll(pos, list);
        notifyItemRangeInserted(pos, list.size());
    }

    public void removeAll(List<OpenMenuItem> list, int pos) {
        mOpenMenu.getMenuDatas().removeAll(list);
        notifyItemRangeRemoved(pos, list.size());
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
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
