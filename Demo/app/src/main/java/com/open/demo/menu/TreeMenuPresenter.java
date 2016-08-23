package com.open.demo.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.open.androidtvwidget.menu.MenuSetObserver;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuItem;
import com.open.androidtvwidget.menu.OpenMenuItemView;
import com.open.androidtvwidget.recycle.RecyclerViewTV;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.demo.R;

/**
 * 树级菜单的 Presenter. (mvp)
 * Created by hailongqiu on 2016/8/23.
 */
public class TreeMenuPresenter extends OpenPresenter {

    private RecyclerViewTV mRecyclerViewTV;

    public TreeMenuPresenter(RecyclerViewTV recyclerViewTV) {
        this.mRecyclerViewTV = recyclerViewTV;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item_layout, parent, false);
        //
        OpenPresenter.ViewHolder result = new ContainerViewHolder(rootView);
        return result;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        OpenMenuItem menuItem = (OpenMenuItem) item;
        ContainerViewHolder holder = (ContainerViewHolder) viewHolder;
        OpenMenuItemView openMenuItemView = (OpenMenuItemView) holder.view;
        openMenuItemView.initialize(menuItem);
        // 子控件.
        if (menuItem.getMenu().getParentMenu() != null) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) openMenuItemView.getLayoutParams();
            lp.leftMargin = menuItem.getMenu().getTreeDepth() * 45;
        }
        // item 单击处理.
        mRecyclerViewTV.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                MenuAdapter menuAdapter = (MenuAdapter) mRecyclerViewTV.getAdapter();
                OpenMenuItem menuItem = (OpenMenuItem) menuAdapter.getItemPosition(position);
                if (menuItem.hasSubMenu()) {
                    OPENLOG.D("展开子菜单");
                    if (!menuItem.isShowSubMenu()) {
                        // 显示菜单.
                        menuAdapter.addAll(menuItem.getSubMenu().getMenuDatas(), position + 1);
                    } else {
                        // 隐藏菜单.
                        menuAdapter.removeAll(menuItem.getSubMenu().getMenuDatas(), position + 1);
                    }
                    menuItem.setShowSubMenu(!menuItem.isShowSubMenu());
                }
            }
        });
    }

    static class ContainerViewHolder extends OpenPresenter.ViewHolder {
        public ContainerViewHolder(View view) {
            super(view);
        }
    }

}
