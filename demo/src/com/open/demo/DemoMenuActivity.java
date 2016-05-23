package com.open.demo;

import com.open.androidtvwidget.bridge.EffectNoDrawBridge;
import com.open.androidtvwidget.menu.IOpenMenuView.OnMenuListener;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenSubMenu;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

	private Drawable getResources(int id) {
		return getResources().getDrawable(id);
	}

	private GridView getGridView(Context context) {
		GridView gridView = new GridView(context);
		gridView.setColumnWidth(200);
		gridView.setNumColumns(4);
		return gridView;
	}

	private void initAllMenu() {
		// 主菜单.
		openMenu = new OpenMenu();
		// openMenu.setMenuView(getGridView(mContext));
		 openMenu.setMenuLoadAnimation(loadAnimation2()); // 设置菜单动画.
		openMenu.setMenuShowAnimation(showAnimation()); // 设置菜单显示动画.
		// openMenu.setMenuMargins(100, 100, 0, 0); // 增加菜单的边距.
		openMenu.add("菜单1").setIcon(getResources(R.drawable.ic_launcher)).setId(R.id.button1);
		openMenu.add("菜单2").setIcon(getResources(R.drawable.ic_launcher));
		openMenu.add("菜单3").setIcon(getResources(R.drawable.ic_launcher));
		openMenu.add("菜单4").setIcon(getResources(R.drawable.ic_launcher));
		openMenu.add("菜单5").setIcon(getResources(R.drawable.ic_launcher));
		openMenu.add("菜单6").setIcon(getResources(R.drawable.ic_launcher));
		openMenu.add("菜单7").setIcon(getResources(R.drawable.ic_launcher));
		// 菜单1的子菜单.
		OpenSubMenu subMenu1 = new OpenSubMenu();
		subMenu1.add("菜单1-1");
		subMenu1.add("菜单1-2").setIcon(getResources(R.drawable.ic_launcher));
		subMenu1.add("菜单1-3");
		// subMenu1.setMenuAnimation(loadAnimation()); // 设置菜单动画.
		// 菜单2的子菜单.
		OpenSubMenu subMenu2 = new OpenSubMenu();
		subMenu2.add("菜单2-1");
		subMenu2.add("菜单2-2");
		subMenu2.add("菜单2-3");
		// 添加子菜单.
		openMenu.addSubMenu(2, subMenu1);
		openMenu.addSubMenu(4, subMenu2);
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
		openMenuView.setOnMenuListener(new OnMenuListener() {
			@Override
			public boolean onMenuItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (view.getId() == R.id.button1) {
					Toast.makeText(mContext, "button", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext, "测试菜单 position:" + view.getId(), Toast.LENGTH_LONG).show();
				}
				return false;
			}

			@Override
			public boolean onMenuItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// mainUpView.setFocusView(view, 1.0f);
				return false;
			}

		});
		// 设置菜单数据.
		openMenuView.setMenuData(openMenu);
	}

	private LayoutAnimationController loadAnimation() {
		/*
		 * 创建动画的集合
		 */
		AnimationSet set = new AnimationSet(false);
		Animation animation;
		/*
		 * 创建旋转动画
		 */
		animation = new RotateAnimation(180, 10);
		animation.setDuration(1000);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 1);
		controller.setInterpolator(this, android.R.anim.accelerate_interpolator);
		controller.setAnimation(set);
		return controller;
	}

	private LayoutAnimationController loadAnimation2() {
		int duration = 300;
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}
	
	/**
	 * 从左到右显示菜单.
	 */
	private Animation showAnimation() {
		Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(800);
		return animation;
	}

}
