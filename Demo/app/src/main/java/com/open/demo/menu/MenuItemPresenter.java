package com.open.demo.menu;

import android.view.ViewGroup;

import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuItem;

/**
 * 菜单item Presenter.
 */
public class MenuItemPresenter extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        OpenMenuItem openMenuItem = (OpenMenuItem) item;
        openMenuItem.getTitle();
    }

}
