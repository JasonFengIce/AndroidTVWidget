package com.open.androidtvwidget.leanback.mode;

import android.view.View;
import android.view.ViewGroup;

import com.open.androidtvwidget.leanback.adapter.GeneralAdapter;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.leanback.widget.ListContentView;

/**
 * Leanback item 标题头下面的 横向布局.（要弄自己的样式，继承这个，重写)，这里只是规定了横向的.
 * Created by hailongqiu on 2016/8/24.
 */
public class ItemListPresenter extends OpenPresenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListContentView listContentView = new ListContentView(parent.getContext());
        return new ItemListViewHolder(listContentView, listContentView.getRecyclerViewTV());
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final Object item) {
        final ItemListViewHolder itemListViewHolder = (ItemListViewHolder) viewHolder;
        ListRow listRow = (ListRow) item;
        if (itemListViewHolder.mDefualtListPresenter == null) {
            DefualtListPresenter openPresenter = (DefualtListPresenter) listRow.getOpenPresenter();
            itemListViewHolder.setDefualtListPresenter(openPresenter);
        }
        itemListViewHolder.mDefualtListPresenter.setItems(listRow.getItems());
        GeneralAdapter generalAdapter = new GeneralAdapter(itemListViewHolder.mDefualtListPresenter);
        itemListViewHolder.mRecyclerViewTV.setLayoutManager(itemListViewHolder.mDefualtListPresenter.getLayoutManger(viewHolder.view.getContext()));
        itemListViewHolder.mRecyclerViewTV.setAdapter(generalAdapter);
        itemListViewHolder.mRecyclerViewTV.setOnItemListener(new RecyclerViewTV.OnItemListener() {
            @Override
            public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
                if (itemListViewHolder.mDefualtListPresenter.getOnItemListener() != null) {
                    itemListViewHolder.mDefualtListPresenter.getOnItemListener().onItemPreSelected(parent, itemView, position);
                }
            }

            @Override
            public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
                if (itemListViewHolder.mDefualtListPresenter.getOnItemListener() != null) {
                    itemListViewHolder.mDefualtListPresenter.getOnItemListener().onItemSelected(parent, itemView, position);
                }
            }

            @Override
            public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
                if (itemListViewHolder.mDefualtListPresenter.getOnItemListener() != null) {
                    itemListViewHolder.mDefualtListPresenter.getOnItemListener().onReviseFocusFollow(parent, itemView, position);
                }
            }
        });
        itemListViewHolder.mRecyclerViewTV.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                if (itemListViewHolder.mDefualtListPresenter.getOnItemClickListener() != null) {
                    itemListViewHolder.mDefualtListPresenter.getOnItemClickListener().onItemClick(parent, itemView, position);
                }
            }
        });
    }

    static class ItemListViewHolder extends OpenPresenter.ViewHolder {
        private RecyclerViewTV mRecyclerViewTV;
        private DefualtListPresenter mDefualtListPresenter;

        public ItemListViewHolder(View view, RecyclerViewTV rv) {
            super(view);
            this.mRecyclerViewTV = rv;
        }

        public void setDefualtListPresenter(DefualtListPresenter presenter) {
            this.mDefualtListPresenter = presenter;
        }
    }

}
