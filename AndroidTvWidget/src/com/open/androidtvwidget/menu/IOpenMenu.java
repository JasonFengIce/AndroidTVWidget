package com.open.androidtvwidget.menu;

import java.util.ArrayList;

import com.open.androidtvwidget.R;

import android.widget.AbsListView;

public interface IOpenMenu {
	static final int NONE = 0;
	static final int DEFAULT_LAYOUT_ID = R.layout.list_menu_item_layout;
	
	public IOpenMenuItem add(CharSequence title); // 添加菜单项.
	public OpenSubMenu addSubMenu(int pos, OpenSubMenu openSubMenu); // 添加子菜单到某个位置的菜单上.
	public OpenMenu setTextSize(int size); // 全局设置菜单字体.
	public ArrayList<IOpenMenuItem> getMenuDatas(); // 获取菜单数据.
	/**
	 * 可以设置ListView, GridView.
	 * 可以不设置，默认是ListView.
	 */
	public IOpenMenu setMenuView(AbsListView absListView);
	public AbsListView getMenuView();
	public IOpenMenu setMenuWidth(int width);
	public int getMenuWidth();
	/**
	 * 如果你想要写自己的布局. 可以参考 OpenMenuViewItem.java
	 */
	public IOpenMenu setLayoutID(int id);
	public int getLayoutID();
	/**
	 * 设置ID. 
	 */
	public IOpenMenu setId(int id);
	public int getId();
	
}
