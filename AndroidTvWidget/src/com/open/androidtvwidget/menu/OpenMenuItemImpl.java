package com.open.androidtvwidget.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class OpenMenuItemImpl implements OpenMenuItem {
	
	static final int NO_ICON = 0;
	private final int mId;
	private final int mGroup;
	private final int mCategoryOrder;
	private final int mOrdering;
	private CharSequence mTitle;
	private CharSequence mTitleCondensed;
	private Intent mIntent;
	private char mShortcutNumericChar;
	private char mShortcutAlphabeticChar;
	private OpenMenuBuilder mMenu;
	/** The icon's drawable which is only created as needed */
	private Drawable mIconDrawable;
	private int mShowAsAction;

	OpenMenuItemImpl(OpenMenuBuilder menu, int group, int id, int categoryOrder, int ordering, CharSequence title,
			int showAsAction) {
		mMenu = menu;
		mId = id;
		mGroup = group;
		mCategoryOrder = categoryOrder;
		mOrdering = ordering;
		mTitle = title;
		mShowAsAction = showAsAction;
	}
}
