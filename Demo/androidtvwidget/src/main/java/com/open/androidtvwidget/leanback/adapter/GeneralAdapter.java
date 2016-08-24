package com.open.androidtvwidget.leanback.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.leanback.widget.OpenPresenter;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public class GeneralAdapter extends RecyclerView.Adapter {

    private OpenPresenter mPresenter;

    public GeneralAdapter(OpenPresenter presenter) {
        this.mPresenter = presenter;
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
        ViewHolder viewHolder = (ViewHolder) holder;
        this.mPresenter.onBindViewHolder(viewHolder.getViewHolder(), position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getItemCount();
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
