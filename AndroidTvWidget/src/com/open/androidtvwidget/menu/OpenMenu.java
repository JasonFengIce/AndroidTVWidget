/*
Copyright 2016 The Open Source Project

Author: hailongqiu <356752238@qq.com>
Maintainer: hailongqiu <356752238@qq.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.open.androidtvwidget.menu;

import java.util.ArrayList;

import com.open.androidtvwidget.utils.OPENLOG;

/**
 * 菜单.
 * 
 * @author hailongqiu
 *
 */
public class OpenMenu implements IOpenMenu {

	private static final String TAG = "OpenMenu";

	private ArrayList<IOpenMenuItem> mItems;
	private OpenMenu mParent;

	private int mTextSize = IOpenMenuItem.DEFAULT_TEXT_SIZE;

	public OpenMenu() {
		init();
	}

	public void setParentMenu(OpenMenu openMenu) {
		mParent = openMenu;
	}

	public OpenMenu getParentMenu() {
		return this.mParent;
	}

	private void init() {
		this.mItems = new ArrayList<IOpenMenuItem>();
	}

	@Override
	public OpenMenu setTextSize(int size) {
		this.mTextSize = size;
		return this;
	}

	public IOpenMenuItem addInternal(int itemId, CharSequence title) {
		final IOpenMenuItem item = new OpenMenuItem(this, itemId, title);
		item.setTextSize(mTextSize);
		mItems.add(item);
		return item;
	}

	@Override
	public IOpenMenuItem add(CharSequence title) {
		return addInternal(0, title);
	}

	/**
	 * 添加子菜单.
	 */
	@Override
	public OpenSubMenu addSubMenu(int pos, OpenSubMenu openSubMenu) {
		mItems.get(pos).setSubMenu(openSubMenu);
		// 添加父菜单.
		if (openSubMenu != null) {
			openSubMenu.setParentMenu(OpenMenu.this);
		}
		return openSubMenu;
	}

	/**
	 * 设置数据.
	 */
	@Override
	public String toString() {
		for (IOpenMenuItem item : mItems) {
			String title = item.getTitle().toString();
			OPENLOG.E("menu item:" + title);
			OpenSubMenu submenu = item.getSubMenu();
			if (submenu != null) {
				OPENLOG.E("=======sub menu======start start start start");
				submenu.toString();
				OPENLOG.E("=======sub menu======end end end end");
			}
		}
		return super.toString();
	}

}
