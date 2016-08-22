package com.open.demo.menu;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.androidtvwidget.menu.OpenMenuItem;
import com.open.androidtvwidget.recycle.RecyclerViewTV;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.demo.R;
import com.open.demo.adapter.HeaderGridAdapter;
import com.open.demo.adapter.RecyclerViewAdapter;

/**
 * 树级菜单的 Presenter. (mvp)
 * Created by hailongqiu on 2016/8/23.
 */
public class TreeMenuPresenter extends OpenPresenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
//        View subMenuView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, parent, false);
        MenuContentView subMenuView = new MenuContentView(parent.getContext());
//        RecyclerViewTV subMenuView = new RecyclerViewTV(parent.getContext());
        View headview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_view, parent, false);

        TreeContainerView treeContainerView = new TreeContainerView(parent.getContext());
        treeContainerView.addItemView(headview);
        treeContainerView.addSubMenuView(subMenuView);
        //
        OpenPresenter.ViewHolder result;
        result = new ContainerViewHolder(treeContainerView, subMenuView);
        //
        return result;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        OpenMenuItem menuItem = (OpenMenuItem) item;
        ContainerViewHolder holder = (ContainerViewHolder) viewHolder;
        // 设置item 属性.
        TreeContainerView treeContainerView = (TreeContainerView) holder.view;
        View headview = treeContainerView.getItemView();
        TextView head_tv= (TextView) headview.findViewById(R.id.head_tv);
        head_tv.setText("" + menuItem.getTitle());
        head_tv.setTextColor(Color.BLACK);
        //
        if (menuItem.hasSubMenu()) {
            MenuContentView menuContentView = (MenuContentView) treeContainerView.getSubMenuView();
            RecyclerViewTV recyclerViewTV = (RecyclerViewTV)menuContentView.getRecyclerViewTV();
            recyclerViewTV.setLayoutManager(new LinearLayoutManager(recyclerViewTV.getContext(), LinearLayoutManager.HORIZONTAL, false));
            ViewGroup.LayoutParams layoutParams = recyclerViewTV.getLayoutParams();
            layoutParams.height = 150;
            recyclerViewTV.setLayoutParams(layoutParams);
            MenuAdapter menuAdapter = new MenuAdapter(recyclerViewTV.getContext(), menuItem.getSubMenu());
            recyclerViewTV.setAdapter(menuAdapter);
            OPENLOG.D("111"  + recyclerViewTV);
        }
    }

    static class ContainerViewHolder extends OpenPresenter.ViewHolder {
        public View mSubMenuView;

        public ContainerViewHolder(View view, View subMenuView) {
            super(view);
            this.mSubMenuView = subMenuView;
        }
    }

}
