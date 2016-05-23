package com.open.demo;

import java.util.ArrayList;

import com.open.androidtvwidget.menu.IOpenMenu;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 菜单回调事件.
 * 
 * @author hailongqiu
 *
 */
interface OnMenuListener {
	public boolean onMenuItemClick(AdapterView<?> parent, View view, int position, long id);

	public boolean onMenuItemSelected(AdapterView<?> parent, View view, int position, long id);
}

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

	/*
	 * 
	 * <ListView android:id="@+id/menu_listview"
	 * android:layout_width="wrap_content" android:layout_height="wrap_content"
	 * android:divider="#00000000" android:scrollbars="none" > </ListView>
	 */
	public void setMenuData(OpenMenu openMenu) {
		setMenuDataInternal(null, openMenu);
	}

	private AbsListView getMenuView(OpenMenu openMenu, ArrayList<IOpenMenuItem> items) {
		AbsListView absListView = openMenu.getMenuView();
		if (absListView == null)
			absListView = new ListView(mContext);
		// 设置ID.
		int id = openMenu.getId();
		absListView.setId(id != 0 ? id : GenerateViewId.getSingleton().generateViewId());
		// 设置 adpater.
		absListView.setAdapter(new MenuAdpater(openMenu, items));
		// 设置属性.
		absListView.setFocusable(true);
		absListView.setFocusableInTouchMode(true);
		absListView.requestFocus();
		// 设置事件
		absListView.setOnKeyListener(this);
		absListView.setOnItemSelectedListener(this);
		absListView.setOnItemClickListener(this);
		return absListView;
	}

	private void setMenuDataInternal(View parentView, OpenMenu openMenu) {
		ArrayList<IOpenMenuItem> items = openMenu.getMenuDatas();
		if (items != null) {
			// 获取自定义的absListView.
			AbsListView absListView = getMenuView(openMenu, items);
			// 设置菜单宽度.
			LayoutParams parm = new LayoutParams();
			int width = openMenu.getMenuWidth();
			int height = openMenu.getMenuHeight();
			parm.width = ((width == 0) ? DEFUALT_MENU_WIDTH : width);
			parm.height = ((height == 0) ? LayoutParams.WRAP_CONTENT : height);
			// 添加菜单View到FloatLayout.
			mFloatLayout.addView(absListView, parm);
			mFloatLayout.requestLayout();
		}
	}

	/**
	 * 测试.
	 */
	private void testAddBtn() {
		Button btn = new Button(mContext);
		btn.setText("测试ABC");
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
		private IOpenMenu mOpenMenu;

		public MenuAdpater(IOpenMenu openMenu, ArrayList<IOpenMenuItem> items) {
			this.mOpenMenu = openMenu;
			this.mItems = items;
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
				convertView = mInflater.inflate(mOpenMenu.getLayoutID(), parent, false);
			}
			IOpenMenuView.ItemView itemView = (IOpenMenuView.ItemView) convertView;
			itemView.initialize(getItem(position), 0);
			return convertView;
		}

	}

	/**
	 * 删除菜单.
	 */
	public boolean removeMenu(View v) {
		if (mFloatLayout.getChildCount() > 1) {
			mFloatLayout.removeView(v);
			mFloatLayout.requestLayout();
			mFloatLayout.getChildAt(mFloatLayout.getChildCount() - 1).setFocusable(true);
			mFloatLayout.getChildAt(mFloatLayout.getChildCount() - 1).requestFocus();
		} else {
			onDestroy();
		}
		return true;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.d("hailongqiu", "hailongqiu onKey keyCode:" + keyCode);
		int action = event.getAction();
		if (action == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			// case KeyEvent.KEYCODE_DPAD_LEFT:
			case KeyEvent.KEYCODE_BACK:
				removeMenu(v);
				return true;
			case KeyEvent.KEYCODE_DPAD_LEFT:// 防止菜单往左边跑到其它地方.
				if ((v instanceof ListView)) {
					removeMenu(v);
					return true;
				}
			case KeyEvent.KEYCODE_DPAD_RIGHT: // 防止菜单往右边跑到其它地方.
			case KeyEvent.KEYCODE_DPAD_UP: // 防止菜单往上面跑到其它地方.
			case KeyEvent.KEYCODE_DPAD_DOWN: // 防止菜单往下面跑到其它地方.
				v.onKeyDown(keyCode, event);
				return true;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// 菜单item选中事件触发.
		if (mOnMenuListener != null) {
			if (mOnMenuListener.onMenuItemSelected(parent, view, position, id))
				return;
		}
		MenuAdpater menuAdapter = (MenuAdpater) parent.getAdapter();
		IOpenMenuItem menuItem = menuAdapter.getDatas().get(position);
		if (menuItem.hasSubMenu()) {
			// 选择显示子菜单(暂时先不支持).
			OpenSubMenu subMenu = menuItem.getSubMenu();
			if (subMenu != null) {
				// setMenuDataInternal(view, subMenu);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * 菜单子菜单，要么就是菜单事件单击.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 菜单item单击事件触发.
		if (mOnMenuListener != null) {
			if (mOnMenuListener.onMenuItemClick(parent, view, position, id))
				return;
		}
		//
		MenuAdpater menuAdapter = (MenuAdpater) parent.getAdapter();
		IOpenMenuItem menuItem = menuAdapter.getDatas().get(position);
		if (menuItem.hasSubMenu()) {
			OpenSubMenu subMenu = menuItem.getSubMenu();
			if (subMenu != null) {
				setMenuDataInternal(view, subMenu);
			}
		}
	}

	OnMenuListener mOnMenuListener;

	public void setOnMenuListener(OnMenuListener cb) {
		this.mOnMenuListener = cb;
	}

}
