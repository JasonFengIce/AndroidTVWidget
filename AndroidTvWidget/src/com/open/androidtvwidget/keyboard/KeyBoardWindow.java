package com.open.androidtvwidget.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class KeyBoardWindow extends RelativeLayout {

	public KeyBoardWindow(Context context) {
		super(context);
		init(context, null);
	}
	
	public KeyBoardWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public KeyBoardWindow(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		
	}
	
}
