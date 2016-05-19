package com.open.demo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;

/**
 * 菜单的显示窗口.
 * 
 * @author hailongqiu
 *
 */
public class OpenMenuView {

	private Context mContext;

	// 定义浮动窗口布局
	RelativeLayout mFloatLayout;
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
		mFloatLayout = (RelativeLayout) mInflater.inflate(R.layout.open_menu_view, null);
		// 添加mFloatLayout
		mWindowManager.addView(mFloatLayout, mWmParams);
		mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		mFloatLayout.setFocusable(true);
		mFloatLayout.setFocusableInTouchMode(true);
		mFloatLayout.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.d("hailongqiu", "hailongqiu onKey keyCode:" + keyCode);
				int action = event.getAction();
				if (action == KeyEvent.ACTION_UP) {
					if (keyCode == KeyEvent.KEYCODE_BACK)
						onDestroy(); 
				} 
				return false;
			}
		});
		mFloatLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
	}
	
	/**
	 * 移除悬浮窗口
	 */
	public void onDestroy() {
		if (mFloatLayout != null) {
			mWindowManager.removeView(mFloatLayout);
		}
	}
}
