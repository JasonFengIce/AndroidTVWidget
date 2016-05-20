package com.open.demo;

import java.util.ArrayList;

import com.open.androidtvwidget.menu.IOpenMenuItem;
import com.open.androidtvwidget.menu.IOpenMenuView;
import com.open.androidtvwidget.menu.OpenMenu;
import com.open.androidtvwidget.menu.OpenSubMenu;
import com.open.androidtvwidget.utils.GenerateViewId;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 菜单的显示窗口.
 * 
 * @author hailongqiu
 *
 */
public class OpenMenuView implements IOpenMenuView, OnKeyListener, OnItemSelectedListener, OnItemClickListener {

	private static final int DEFUALT_MENU_WIDTH = 200;

	private Context mContext;

	// 定义浮动窗口布局
	LinearLayout mFloatLayout;
	WindowManager.LayoutParams mWmParams;
	// 创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;
	LayoutInflater mInflater;

	public OpenMenuView(Context context) {
		mContext = context;
		if (mContext == null)
			throw new AssertionError("context not found.");
		mInflater = LayoutInflater.from(mContext);
		//
		initMenuWindow();
	}

	private void initMenuWindow() {
		mWmParams = new WindowManager.LayoutParams();
		// 获取的是WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
		// 设置window type
		mWmParams.type = LayoutParams.TYPE_PHONE;
		// 设置图片格式，效果为背景透明
		mWmParams.format = PixelFormat.RGBA_8888;
		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
		// FLAG_NOT_TOUCH_MODAL 不阻塞事件传递到后面的窗口
		mWmParams.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM; // |
																			// WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
																			// //LayoutParams.FLAG_NOT_FOCUSABLE;
		// 调整悬浮窗显示的停靠位置为左侧置顶
		// mWmParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
		mWmParams.x = 0;
		mWmParams.y = 0;
		// 设置悬浮窗口长宽数据
		// wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		// wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		mWmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		mWmParams.height = WindowManager.LayoutParams.MATCH_PARENT;

		// 获取浮动窗口视图所在布局
		mFloatLayout = (LinearLayout) mInflater.inflate(R.layout.open_menu_view, null);
		// 添加mFloatLayout
		mWindowManager.addView(mFloatLayout, mWmParams);
		// mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
		// View.MeasureSpec.UNSPECIFIED),
		// View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		mFloatLayout.setFocusable(true);
		mFloatLayout.setFocusableInTouchMode(true);
		mFloatLayout.setOnKeyListener(this);
	}

	private int mMenuItemLayoutID = R.layout.list_menu_item_layout;

	/*
	 * 
	 * <ListView android:id="@+id/menu_listview"
	 * android:layout_width="wrap_content" android:layout_height="wrap_content"
	 * android:divider="#00000000" android:scrollbars="none" > </ListView>
	 */
	public void setMenuData(OpenMenu openMenu) {
		setMenuDataInternal(null, openMenu);
	}

	private void setMenuDataInternal(View parentView, OpenMenu openMenu) {
		ArrayList<IOpenMenuItem> items = openMenu.getMenuDatas();
		if (items != null) {
			//
			ListView listview = new ListView(mContext);
			listview.setId(GenerateViewId.getSingleton().generateViewId());
			LayoutParams parm = new LayoutParams();
			parm.width = DEFUALT_MENU_WIDTH;
			parm.height = LayoutParams.WRAP_CONTENT;
			listview.setAdapter(new MenuAdpater(null, items));
			listview.setFocusable(true);
			listview.setFocusableInTouchMode(true);
			listview.requestFocus();
			listview.setOnKeyListener(this);
			// mMenuListview.setOnItemSelectedListener(this);
			listview.setOnItemClickListener(this);
			//
			mFloatLayout.addView(listview, parm);
			mFloatLayout.requestLayout();
			if (parentView != null) {
				Log.d("hailongqiu", "hailongqiu setMenuDataInternal listview id:" + listview.getId() + " parent id:"
						+ parentView.getId());
//				 parentView.setNextFocusRightId(listview.getId());
//				 listview.setNextFocusLeftId(parentView.getId());
			}
		}
	}

	private void testAddBtn() {
		Button btn = new Button(mContext);
		btn.setText("fjdkslfjdslkfjsdklfj");
		mFloatLayout.addView(btn);
		mFloatLayout.requestLayout();
	}

	/**
	 * 移除悬浮窗口
	 */
	public void onDestroy() {
		if (mFloatLayout != null) {
			mWindowManager.removeView(mFloatLayout);
		}
	}

	/**
	 * 菜单Menu item adpater.
	 * 
	 * @author hailongqiu
	 *
	 */
	class MenuAdpater extends BaseAdapter {

		private ArrayList<IOpenMenuItem> mItems;
		private View mParentView;

		public MenuAdpater(ArrayList<IOpenMenuItem> items) {
			this(null, items);
		}
		
		public MenuAdpater(View parentView, ArrayList<IOpenMenuItem> items) {
			this.mItems = items;
			this.mParentView = parentView;
		}

		public void setDatas(ArrayList<IOpenMenuItem> items) {
			mItems = items;
			notifyDataSetChanged();
		}

		public ArrayList<IOpenMenuItem> getDatas() {
			return mItems;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public IOpenMenuItem getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(mMenuItemLayoutID, parent, false);
			}
			IOpenMenuView.ItemView itemView = (IOpenMenuView.ItemView) convertView;
			itemView.initialize(getItem(position), 0);
			if (mParentView != null) {
				int id = mParentView.getId();
				Log.d("hailongqiu", "hailongqiu getView id:" + id); 
//				convertView.setNextFocusLeftId(id);
			}
			return convertView;
		}

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.d("hailongqiu", "hailongqiu onKey keyCode:" + keyCode);
		int action = event.getAction();
		if (action == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_BACK:
				if (mFloatLayout.getChildCount() > 1) {
					mFloatLayout.removeView(v);
					mFloatLayout.requestLayout();
//					mFloatLayout.getChildAt(mFloatLayout.getChildCount() - 1).setFocusable(true);
					mFloatLayout.getChildAt(mFloatLayout.getChildCount() - 1).requestFocus();
					return true;
				} else {
					onDestroy();
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		MenuAdpater menuAdapter = (MenuAdpater) parent.getAdapter();
		IOpenMenuItem menuItem = menuAdapter.getDatas().get(position);
		OpenSubMenu subMenu = menuItem.getSubMenu();
		ListView listview = new ListView(mContext);
		subMenu.getMenuDatas();
		mFloatLayout.addView(listview);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d("hailongqiu", "hailongqiu onItemClick");
		MenuAdpater menuAdapter = (MenuAdpater) parent.getAdapter();
		IOpenMenuItem menuItem = menuAdapter.getDatas().get(position);
		OpenSubMenu subMenu = menuItem.getSubMenu();
		//
		if (subMenu != null) {
			Log.d("hailongqiu", "hailongqiu onItemClick subMenu:" + subMenu);
			setMenuDataInternal(view, subMenu);
		}
	}

}
