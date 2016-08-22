package com.open.demo.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.recycle.RecyclerViewTV;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public class TreeMenuPresenter extends MenuPresenter {

    public TreeMenuPresenter(Context context) {
        super(context);
    }

    /**
     * 创建子菜单的 ViewHolder.
     */
    @Override
    protected MenuPresenter.MernuViewHolder createMenuViewHolder(ViewGroup parent) {
        MenuContentView menuContentView = new MenuContentView(parent.getContext());
        menuContentView.getRecyclerViewTV().setLayoutManager(getLayoutManger());
        return new ViewHolder(parent, menuContentView.getRecyclerViewTV());
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
    }

    public static class ViewHolder extends MenuPresenter.MernuViewHolder {

        final RecyclerViewTV mRecyclerViewTV;
        MenuAdapter mMenuAdapter;

        public ViewHolder(View view, RecyclerViewTV recyclerViewTV) {
            super(view);
            this.mRecyclerViewTV = recyclerViewTV;
        }

        public final RecyclerViewTV getRecyclerViewTV() {
            return this.mRecyclerViewTV;
        }

    }

}
