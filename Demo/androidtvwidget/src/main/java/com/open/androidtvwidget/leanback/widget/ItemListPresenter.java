package com.open.androidtvwidget.leanback.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.leanback.adapter.GeneralAdapter;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.utils.OPENLOG;

import java.util.List;

/**
 * Leanback item 标题头下面的 横向布局.
 * Created by hailongqiu on 2016/8/24.
 */
public class ItemListPresenter extends OpenPresenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListContentView listContentView = new ListContentView(parent.getContext());
        return new ItemListViewHolder(listContentView, listContentView.getRecyclerViewTV());
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ItemListViewHolder itemListViewHolder = (ItemListViewHolder) viewHolder;
        GeneralAdapter generalAdapter = new GeneralAdapter(new TestHedadPresenter(item));
        itemListViewHolder.mRecyclerViewTV.setLayoutManager(new LinearLayoutManager(viewHolder.view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        itemListViewHolder.mRecyclerViewTV.setAdapter(generalAdapter);
        itemListViewHolder.mRecyclerViewTV.getLayoutParams().height = 120;
    }

    static class ItemListViewHolder extends OpenPresenter.ViewHolder {
        private RecyclerViewTV mRecyclerViewTV;

        public ItemListViewHolder(View view, RecyclerViewTV rv) {
            super(view);
            this.mRecyclerViewTV = rv;
        }
    }

}
