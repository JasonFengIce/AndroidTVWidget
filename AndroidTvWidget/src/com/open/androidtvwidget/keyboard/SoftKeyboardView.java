package com.open.androidtvwidget.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 键盘控件.
 * 
 * @author hailong.qiu
 *
 */
public class SoftKeyboardView extends View {

	public SoftKeyboardView(Context context) {
		super(context);
		init(context, null);
	}

	public SoftKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SoftKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

	}

	private SoftKeyboard mSoftKeyboard;

	/*
	 * 传入需要绘制的键盘(从XML读取出来的).
	 */
	public void setSoftKeyboard(SoftKeyboard softSkb) {
		this.mSoftKeyboard = softSkb;
	}

	@Override
	protected void onDraw(Canvas canvas) {
	}

}
