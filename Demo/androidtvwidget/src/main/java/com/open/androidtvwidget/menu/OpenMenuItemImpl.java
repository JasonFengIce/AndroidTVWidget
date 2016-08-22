package com.open.androidtvwidget.menu;

import android.graphics.drawable.Drawable;

public class OpenMenuItemImpl implements OpenMenuItem {

    private int mId;
    private int mTextSize = DEFAULT_TEXT_SIZE;
    private boolean mChecked = false;
    private Object mData;
    private CharSequence mTitle;

    private OpenMenu mMenu;
    private OpenMenu mSubMenu;
    private int mIconID;

    OpenMenuItemImpl(OpenMenu menu, int id, CharSequence title) {
        this.mMenu = menu;
        this.mId = id;
        this.mTitle = title;
    }

    @Override
    public OpenMenuItem setIconRes(int iconID) {
        this.mIconID = iconID;
        notifyChanged();
        return this;
    }

    @Override
    public int getIconRes() {
        return this.mIconID;
    }

    @Override
    public OpenMenuItem setTitle(CharSequence title) {
        this.mTitle = title;
        notifyChanged();
        return this;
    }

    @Override
    public CharSequence getTitle() {
        return mTitle;
    }

    @Override
    public OpenMenu getSubMenu() {
        return mSubMenu;
    }

    @Override
    public OpenMenuItem setSubMenu(OpenMenu subMenu) {
        mSubMenu = subMenu;
        return this;
    }

    @Override
    public boolean hasSubMenu() {
        return (mSubMenu != null);
    }

    @Override
    public OpenMenuItem setChecked(boolean checked) {
        this.mChecked = checked;
        notifyChanged();
        return this;
    }

    @Override
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override
    public OpenMenuItem setTextSize(int size) {
        mTextSize = size;
        notifyChanged();
        return this;
    }

    @Override
    public int getTextSize() {
        return mTextSize;
    }

    @Override
    public OpenMenuItem setObjectData(Object data) {
        this.mData = data;
        notifyChanged();
        return this;
    }

    @Override
    public Object getObjectData() {
        return this.mData;
    }

    @Override
    public OpenMenuItem setId(int id) {
        this.mId = id;
        return this;
    }

    @Override
    public int getId() {
        return this.mId;
    }

    private void notifyChanged() {
        if (mMenu != null) {
            mMenu.notifyChanged();
        }
    }

}
