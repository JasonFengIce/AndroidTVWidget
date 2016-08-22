package com.open.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenMenuImpl;
import com.open.androidtvwidget.menu.OpenMenuItem;
import com.open.androidtvwidget.utils.OPENLOG;

/**
 * 菜单DEMO测试.
 * 
 * @author hailongqiu
 *
 */
public class DemoMenuActivity extends Activity implements OnClickListener {

	private Context mContext;
	OpenMenu openMenu;
	View oldView;

	public DemoMenuActivity() {
		OPENLOG.initTag("hailongqiu", true); // 测试LOG输出.
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_menu_activity);
//		findViewById(R.id.content11).setBackgroundResource(R.drawable.main_bg);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
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
		openMenu = new OpenMenuImpl();
//		openMenu.setMenuView(getGridView(mContext)); // 设置自己的 菜单view（默认是listview).
//		openMenu.setMenuLoadAnimation(MenuAnimationUtils.loadAnimation2()); // 设置菜单动画.
//		openMenu.setMenuShowAnimation(MenuAnimationUtils.showAnimation()); // 设置菜单显示动画.
//		openMenu.setMenuHideAnimation(MenuAnimationUtils.hideAnimation()); // 设置菜单隐藏动画.
		// openMenu.setMenuMargins(100, 100, 0, 0); // 增加菜单的边距.
		// openMenu.setGravity(Gravity.CENTER); // 设置菜单位置(中间，默认 TOP).
		final OpenMenuItem menuItem1 = openMenu.add("菜单1");
		menuItem1.setIconRes(R.drawable.ic_launcher).setId(R.id.menu_1_1).setChecked(true);
		openMenu.add("菜单2").setIconRes(R.drawable.ic_launcher).setId(R.id.menu_1_2);
		openMenu.add("菜单3").setIconRes(R.drawable.ic_launcher).setId(R.id.menu_1_3);
		openMenu.add("菜单4").setIconRes(R.drawable.ic_launcher).setId(R.id.menu_1_4);
		openMenu.add("菜单5").setIconRes(R.drawable.ic_launcher).setId(R.id.menu_1_5);
		openMenu.add("菜单6").setIconRes(R.drawable.ic_launcher);
		openMenu.add("菜单7").setIconRes(R.drawable.ic_launcher);
		// 菜单1的子菜单.
		OpenMenu subMenu1 = new OpenMenuImpl();
		subMenu1.add("菜单1-1");
		subMenu1.add("菜单1-2").setIconRes(R.drawable.ic_launcher);
		subMenu1.add("菜单1-3");
//		subMenu1.setMenuLoadAnimation(MenuAnimationUtils.loadAnimation2()); // 设置菜单动画.
		// 菜单2的子菜单.
		OpenMenu subMenu2 =  new OpenMenuImpl();
		subMenu2.add("菜单2-1");
		subMenu2.add("菜单2-2");
		subMenu2.add("菜单2-3");
		// 菜单1添加子菜单.
		OpenMenu subMenu1_1 = new OpenMenuImpl();
		subMenu1_1.add("菜单1-2-1");
		subMenu1_1.add("菜单1-2-2");
		subMenu1_1.add("菜单1-2-3");
		// 添加子菜单.
		openMenu.addSubMenu(menuItem1, subMenu1);
		openMenu.addSubMenu(4, subMenu2);
		subMenu1.addSubMenu(1, subMenu1_1);
		// 输出菜单数据.
		openMenu.toString();
		// 菜单VIEW测试.
//		openMenuView = new OpenMenuViewImpl(mContext);
//		// 设置移动边框.
//		final MainUpView mainUpView = new MainUpView(mContext);
//		EffectNoDrawBridge noDrawBridge = new EffectNoDrawBridge();
//		mainUpView.setEffectBridge(noDrawBridge);
//		noDrawBridge.setUpRectResource(R.drawable.white_light_10);
//		openMenuView.setMoveView(mainUpView);
		//
//		openMenuView.setOnMenuListener(new OnMenuListener() {
//			@Override
//			public boolean onMenuItemClick(AdapterView<?> parent, OpenMenuItem menuItem, View view, int position, long id) {
//				String title = "测试菜单 position:" + position + " id:" + view.getId();
//				switch (view.getId()) {
//				case R.id.menu_1_1:
//					title = "菜单1-1-测试";
//					break;
//				case R.id.menu_1_2:
//					title = "菜单1-2-打开";
//					openMenu.hideMenu();
//					break;
//				case R.id.menu_1_3:
//					if (menuItem != null)
//						menuItem.setTitle("设置菜单测试");
//					title = "菜单1-3-关闭";
//					break;
//				case R.id.menu_1_4:
//					title = "菜单1-4-操作";
//					break;
//				case R.id.menu_1_5:
//					title = "菜单1-5-创新";
//					break;
//				default:
//					break;
//				}
//				Toast.makeText(mContext, title, Toast.LENGTH_LONG).show();
//				return false;
//			}
//
//			@Override
//			public boolean onMenuItemSelected(AdapterView<?> parent, OpenMenuItem menuItem, View view, int position, long id) {
//				mainUpView.setFocusView(view, oldView, 1.0f);
//				oldView = view;
//				return true;
//			}
//
//			@Override
//			public boolean onMenuItemFocusChange(AdapterView<?> parent, View view) {
//				mainUpView.setFocusView(view, oldView, 1.0f);
//				oldView = view;
//				return false;
//			}
//
//		});
		// 设置菜单数据.
//		openMenuView.setMenuData(openMenu);
		openMenu.showMenu();
//		openMenu.getMenuView().setBackgroundResource(R.drawable.ic_bg_menu); // test
//		subMenu1.showMenu();
//		subMenu1_1.showMenu();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.button1:
				openMenu.showMenu();
				break;
			case R.id.button2:
				openMenu.hideMenu();
				break;
		}
	}
}
