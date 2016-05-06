package com.open.demo;

import com.open.androidtvwidget.menu.OpenListMenuView;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuBuilder;
import com.open.androidtvwidget.menu.OpenSubMenuBuilder;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.SmoothHorizontalScrollView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

/**
 * 菜单DEMO测试.
 * 
 * @author hailongqiu
 *
 */
public class DemoMenuActivity extends Activity {

	private Context mContext;

//	private SmoothHorizontalScrollView test_hscroll;
	
	public DemoMenuActivity() {
		OPENLOG.initTag("hailongqiu", true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_menu_activity);
//		test_hscroll = (SmoothHorizontalScrollView) findViewById(R.id.test_hscroll);
		findViewById(R.id.content11).setBackgroundResource(R.drawable.mainbackground);
		mContext = DemoMenuActivity.this;
		initAllMenu();
	}
	
	OpenMenuBuilder openMenu;
	
	private void initAllMenu() {
		// 主菜单.
		openMenu = new OpenMenuBuilder(mContext, 250).setGravity(1);
		openMenu.setBackgroundResource(R.drawable.ic_bg_setting); // 设置背景.
		openMenu.add("菜单1").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单2").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单3").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单4").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单5").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单6").setIcon(R.drawable.ic_launcher);
		openMenu.add("菜单7").setIcon(R.drawable.ic_launcher);
		// 菜单1的子菜单.
		OpenSubMenuBuilder subMenu1 = new OpenSubMenuBuilder(mContext);
		subMenu1.add("菜单1-1");
		subMenu1.add("菜单1-2").setIcon(R.drawable.ic_launcher);;
		subMenu1.add("菜单1-3");
		// 菜单2的子菜单.
		OpenSubMenuBuilder subMenu2 = new OpenSubMenuBuilder(mContext);
		subMenu2.add("菜单2-1");
		subMenu2.add("菜单2-2");
		subMenu2.add("菜单2-3");
		// 添加子菜单. 
		openMenu.addSubMenu(0, subMenu1);
		openMenu.addSubMenu(1, subMenu2);
		// 菜单1添加子菜单.
		OpenSubMenuBuilder subMenu1_1 = new OpenSubMenuBuilder(mContext);
		subMenu1_1.add("菜单1-2-1");
		subMenu1_1.add("菜单1-2-2");
		subMenu1_1.add("菜单1-2-3");
		subMenu1.addSubMenu(1, subMenu1_1);
		//
		openMenu.toString();
		// 添加菜单动画.
		Animation animation = new AlphaAnimation(0f, 1f);
		// Animation animation = new RotateAnimation(0f, 360f);
		// Animation animation = new TranslateAnimation(-100f, 0f, 0f, 0f);
		animation.setDuration(500);
		// 1f为延时
		LayoutAnimationController controller = new LayoutAnimationController(animation, 0.5f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		openMenu.setLayoutAnimation(controller); // 设置菜单item显示出来的动画.
		subMenu1.setLayoutAnimation(controller);
		subMenu1_1.setLayoutAnimation(controller);
		subMenu2.setLayoutAnimation(controller);
		//
		openMenu.showMenu();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		OPENLOG.D("fsdfsdfdsf%s-%s", "AAAAAAAA", "BBBBBBB");
		if (openMenu.isShowMenu()) {
//			test_hscroll.setEnabled(false);
		} else {
//			test_hscroll.setEnabled(true);
		}
		return super.dispatchKeyEvent(event);
	}
}
