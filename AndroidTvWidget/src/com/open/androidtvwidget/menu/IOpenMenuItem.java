package com.open.androidtvwidget.menu;

import android.graphics.drawable.Drawable;

/**
 * 菜单接口.
 * @author hailongqiu
 */
public interface IOpenMenuItem {
	static final int NO_ICON = 0;
	static final int DEFAULT_TEXT_SIZE = 24;
	
	//
	public Drawable getIcon();
	/**
	 * 设置菜单图标.
	 */
	public IOpenMenuItem setIcon(Drawable icon);
	public IOpenMenuItem setIcon(int iconResId);
	public IOpenMenuItem setTitle(CharSequence title);
	public IOpenMenuItem setTitle(int title);
	public IOpenMenuItem setTextSize(int size);
	public int getTextSize();
	/**
	 * 获取菜单文字内容.
	 */
	public CharSequence getTitle();
	public OpenSubMenu getSubMenu();
	/**
	 * 保存子菜单.
	 */
	public void setSubMenu(OpenSubMenu subMenu);
	/**
	 * 判断子菜单是否存在.
	 */
	public boolean hasSubMenu(); 
}
