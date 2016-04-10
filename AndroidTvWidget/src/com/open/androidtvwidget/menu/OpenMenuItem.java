package com.open.androidtvwidget.menu;

import android.graphics.drawable.Drawable;

public interface OpenMenuItem {

	public Drawable getIcon();

	public OpenMenuItem setIcon(Drawable icon);

	public OpenMenuItem setIcon(int iconResId);

	public OpenMenuItem setTitle(CharSequence title);

	public OpenMenuItem setTitle(int title);

	public CharSequence getTitle();

	public OpenSubMenu getSubMenu();

	public void setSubMenu(OpenSubMenu subMenu);
}
