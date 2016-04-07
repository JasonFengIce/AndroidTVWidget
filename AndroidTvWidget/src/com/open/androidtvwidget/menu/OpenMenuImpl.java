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

import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 */
public class OpenMenuImpl implements OpenMenu {

	public void onCreateOptionsMenu() {
		Menu menu;
		MenuItem menui;
	}

	/**
	 * 
	 * @param reSource
	 *            Menu的layout的ID
	 */
	public void inflate(int reSource) {
	}

	@Override
	public OpenMenuItem add(int groupId, int itemId, int order, CharSequence title) {
		return null;
	}

	@Override
	public OpenSubMenu addSubMenu(CharSequence title) {
		return null;
	}

	public ArrayList<OpenMenuItem> menuItems = new ArrayList<OpenMenuItem>();

	public OpenMenuItem add(int title) {
		OpenMenuItem item = null; //new MenuItem();
		return item;
	}

	@Override
	public OpenMenuItem add(int groupId, int itemId, int order, int title) {
		OpenMenuItem item = null;// = new OpenMenuItem();
//		item.setGroupId(groupId);
//		item.setItemId(itemId);
//		item.setOrder(order);
//		item.setTitle(title);
//		menuItems.add(item);
		return item;
	}

	void add(OpenMenuItem item) {
		menuItems.add(item);
	}
	
}
