package com.open.androidtvwidget;

import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuBuilder;
import com.open.androidtvwidget.menu.OpenMenuItem;
import com.open.androidtvwidget.menu.OpenSubMenu;
import com.open.androidtvwidget.menu.OpenSubMenuBuilder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.LayoutAnimationController;

/**
 * 菜单DEMO测试.
 * @author hailongqiu
 *
 */
public class DemoMenuActivity extends Activity {
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = DemoMenuActivity.this;
		initAllMenu();
	}
	
	private void initAllMenu() {
		// 主菜单.
		OpenMenu openMenu = new OpenMenuBuilder(mContext);
		openMenu.add("菜单1").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单2");
		openMenu.add("菜单3");
		openMenu.add("菜单4");
		openMenu.add("菜单5");
//		openMenu.setLayoutAnimation(new LayoutAnimationController(animation));
		// 菜单1的子菜单.
		OpenSubMenuBuilder subMenu1 = new OpenSubMenuBuilder(mContext);
		subMenu1.add("菜单1-1");
		subMenu1.add("菜单1-2");
		subMenu1.add("菜单1-3");
		// 菜单2的子菜单.
		OpenSubMenuBuilder subMenu2 = new OpenSubMenuBuilder(mContext);
		subMenu2.add("菜单2-1");
		subMenu2.add("菜单2-2");
		subMenu2.add("菜单2-3");
		// 添加子菜单.
		openMenu.addSubMenu(0, subMenu1);
		openMenu.addSubMenu(1, subMenu2);
	}
	
}
