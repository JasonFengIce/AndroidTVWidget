package com.open.androidtvwidget.menu;

/**
 * 菜单item接口.
 *
 * @author hailongqiu
 */
public interface OpenMenuItem {
    static final int NO_ICON = 0; // 默认icon id.
    static final int DEFAULT_TEXT_SIZE = 24; // 默认字体

    /**
     * 设置菜单item-ID.
     */
    public OpenMenuItem setId(int id);

    public int getId();

    /**
     * 设置菜单资源.
     */
    public OpenMenuItem setIconRes(int iconID);
    public int getIconRes();

    /**
     * 设置菜单文本内容
     */
    public OpenMenuItem setTitle(CharSequence title);

    public CharSequence getTitle();

    /**
     * 设置字体大小.
     */
    public OpenMenuItem setTextSize(int size);

    public int getTextSize();

    /**
     * 设置数据.
     */
    public OpenMenuItem setObjectData(Object data);

    public Object getObjectData();

    /**
     * 保存子菜单.
     */
    public OpenMenuItem setSubMenu(OpenMenu subMenu);

    public OpenMenu getSubMenu();

    /**
     * 判断子菜单是否存在.
     */
    public boolean hasSubMenu();

    /**
     * 设置checked标志位.
     */
    public OpenMenuItem setChecked(boolean checked);

    public boolean isChecked();

}
