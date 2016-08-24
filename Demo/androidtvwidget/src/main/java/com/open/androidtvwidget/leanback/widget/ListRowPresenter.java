package com.open.androidtvwidget.leanback.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.open.androidtvwidget.leanback.adapter.GeneralAdapter;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Leanback ListRowPresenter 控制层.
 * Created by hailongqiu on 2016/8/24.
 */
public class ListRowPresenter extends OpenPresenter {

    OpenPresenter mItemHeaderPresenter = new ItemHeaderPresenter(); // item 标题头的 Presenter.
    OpenPresenter mItemListPresenter = new ItemListPresenter(); // item 标题头下面的 横向 items.

    List<ListRow> mItems;

    public ListRowPresenter(List<ListRow> items) {
        this.mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * 设置标题头的Presenter.
     */
    public void setHeadPresenter(OpenPresenter presenter) {
        this.mItemHeaderPresenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContainerView itemContainerView = new ItemContainerView(parent.getContext());
        // 添加标题头.
        ViewHolder headVH = mItemHeaderPresenter.onCreateViewHolder(parent, viewType);
        itemContainerView.addHeaderView(headVH.view);
        // 添加横向控件.
        ViewHolder listVH = mItemListPresenter.onCreateViewHolder(parent, viewType);
        itemContainerView.addRowView(listVH.view);
        //
        return new ListRowViewHolder(itemContainerView, headVH, listVH);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ListRowViewHolder listRowViewHolder = (ListRowViewHolder) viewHolder;
        ListRow listRow = mItems.get(position);

        if (listRowViewHolder.mHeadViewHolder != null) {
            mItemHeaderPresenter.onBindViewHolder(listRowViewHolder.mHeadViewHolder, listRow.getHeaderItem());
        }

        if (listRowViewHolder.mListViewHolder != null) {
            mItemListPresenter.onBindViewHolder(listRowViewHolder.mListViewHolder, listRow.getItems());
        }

    }

    static class ListRowViewHolder extends OpenPresenter.ViewHolder {

        ViewHolder mHeadViewHolder;
        ViewHolder mListViewHolder;

        public ListRowViewHolder(View view, ViewHolder headVH, ViewHolder lilstVH) {
            super(view);
            this.mHeadViewHolder = headVH;
            this.mListViewHolder = lilstVH;
        }

    }

}
