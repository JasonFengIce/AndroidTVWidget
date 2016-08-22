package com.open.demo.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.open.demo.R;

/**
 *  树级菜单控件. 上面是 item text, 下面是子菜单.
 * Created by hailongqiu on 2016/8/22.
 */
public class TreeContainerView extends LinearLayout {

    private ViewGroup mMenuDock;

    public TreeContainerView(Context context) {
        this(context, null, 0);
    }

    public TreeContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.lb_tree_container, this);
        mMenuDock = (ViewGroup) findViewById(R.id.lb_tree_container_menu_dock);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * 添加菜单的item
     */
    public void addItemView(View itemView) {
        if (indexOfChild(itemView) < 0) {
            addView(itemView, 0);
        }
    }

    /**
     * 删除菜单的item.
     */
    public void removeItemView(View itemView) {
        if (indexOfChild(itemView) >= 0) {
            removeView(itemView);
        }
    }

    /**
     * 添加子菜单(item下的子菜单).
     */
    public void addSubMenuView(View subMenuView) {
        if (subMenuView != null && mMenuDock.indexOfChild(subMenuView) < 0) {
            mMenuDock.addView(subMenuView);
        }
    }

    /**
     * 删除子菜单。
     */
    public void removeSubMenuView(View subMenuView) {
        if (subMenuView != null && mMenuDock.indexOfChild(subMenuView) >= 0) {
            mMenuDock.removeView(subMenuView);
        }
    }

    public View getItemView() {
        return getChildAt(0);
    }

    public View getSubMenuView() {
        return mMenuDock.getChildAt(0);
    }

    /**
     * 显示/隐藏 ---> 子菜单.
     */
    public void showSubMenu(boolean show) {
        mMenuDock.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
