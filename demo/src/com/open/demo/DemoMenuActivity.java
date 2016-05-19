package com.open.demo;

import com.open.androidtvwidget.bridge.EffectNoDrawBridge;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenSubMenu;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 菜单DEMO测试.
 * 
 * @author hailongqiu
 *
 */
public class DemoMenuActivity extends Activity {

	private Context mContext;
	OpenMenu openMenu;
	View oldView;
	// private SmoothHorizontalScrollView test_hscroll;

	public DemoMenuActivity() {
		OPENLOG.initTag("hailongqiu", true); // 测试LOG输出.
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_menu_activity);
		// test_hscroll = (SmoothHorizontalScrollView)
		// findViewById(R.id.test_hscroll);
		findViewById(R.id.content11).setBackgroundResource(R.drawable.mainbackground);
		mContext = DemoMenuActivity.this;
		initAllMenu();
	}

	private void initAllMenu() {
		// 主菜单.
		openMenu = new OpenMenu();
		openMenu.add("菜单1").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单2").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单3").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单4").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单5").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单6").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		openMenu.add("菜单7").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		// 菜单1的子菜单.
		OpenSubMenu subMenu1 = new OpenSubMenu();
		subMenu1.add("菜单1-1");
		subMenu1.add("菜单1-2").setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		subMenu1.add("菜单1-3");
		// 菜单2的子菜单.
		OpenSubMenu subMenu2 = new OpenSubMenu();
		subMenu2.add("菜单2-1");
		subMenu2.add("菜单2-2");
		subMenu2.add("菜单2-3");
		// 添加子菜单.
		openMenu.addSubMenu(0, subMenu1);
		openMenu.addSubMenu(1, subMenu2);
		// 菜单1添加子菜单.
		OpenSubMenu subMenu1_1 = new OpenSubMenu();
		subMenu1_1.add("菜单1-2-1");
		subMenu1_1.add("菜单1-2-2");
		subMenu1_1.add("菜单1-2-3");
		subMenu1.addSubMenu(1, subMenu1_1);
		//
		openMenu.toString();
		//
		final MainUpView mainUpView = new MainUpView(mContext);
		mainUpView.setEffectBridge(new EffectNoDrawBridge());
		mainUpView.setUpRectResource(R.drawable.white_light_10);
		// 菜单VIEW测试.
		OpenMenuView openMenuView = new OpenMenuView(mContext);
		openMenuView.setMenuData(openMenu);
	}

}
