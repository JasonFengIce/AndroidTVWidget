package com.open.demo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

/**
 * 
 * @author hailongqiu
 *
 */
public class OpenMenuView {

	private Context mContext;

	// 定义浮动窗口布局
	FrameLayout mFloatLayout;
	WindowManager.LayoutParams wmParams;
	// 创建浮动窗口设置布局参数的对象
	WindowManager mWindowManager;

	public OpenMenuView(Context context) {
		mContext = context;
		//
		initMenuWindow();
	}

	private void initMenuWindow() {
		wmParams = new WindowManager.LayoutParams();
		// 获取的是WindowManagerImpl.CompatModeWrapper
		mWindowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
		// 设置window type
		wmParams.type = LayoutParams.TYPE_PHONE;
		// 设置图片格式，效果为背景透明
		wmParams.format = PixelFormat.RGBA_8888;
		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
		// FLAG_NOT_TOUCH_MODAL 不阻塞事件传递到后面的窗口
		wmParams.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM; // |
																			// WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
																			// //LayoutParams.FLAG_NOT_FOCUSABLE;
		// 调整悬浮窗显示的停靠位置为左侧置顶
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
		wmParams.x = 0;
		wmParams.y = 0;
		// 设置悬浮窗口长宽数据
		// wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		// wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;

		// 设置悬浮窗口长宽数据
		// wmParams.width = 200;
		// wmParams.height = 200;

		LayoutInflater inflater = LayoutInflater.from(mContext);
		// 获取浮动窗口视图所在布局
		// mFloatLayout = (FrameLayout)
		// inflater.inflate(R.layout.split_screen_menu, null);
	}
}
