package com.open.androidtvwidget.menu;

public interface OpenMenu {
	public OpenMenuItem add(int groupId, int itemId, int order, CharSequence title);
    public OpenMenuItem add(int groupId, int itemId, int order, int titleRes);
    OpenSubMenu addSubMenu(final CharSequence title);
}
