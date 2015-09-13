package com.androidtv.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.androidtv.demo.R;
import com.open.androidtvwidget.view.ReflectItemView;

public class MainClassWidget extends ReflectItemView {

	public MainClassWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWidgetView(context, attrs);
	}

	public MainClassWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWidgetView(context, attrs);
	}

	public MainClassWidget(Context context) {
		super(context);
		initWidgetView(context, null);
	}
	
	private int mSrcId = R.drawable.ic_cl_class;
	private ImageView bgIv;
	
	private void initWidgetView(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray tArray = context.obtainStyledAttributes(attrs,
					R.styleable.classMainWidget);// ªÒ»°≈‰÷√ Ù–‘
			mSrcId = tArray.getResourceId(R.styleable.classMainWidget_src, R.drawable.ic_cl_class);
		}
		setClickable(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		//
		View rootView = View.inflate(context, R.layout.main_class_widget, this);
		//
		bgIv = (ImageView) findViewById(R.id.bg_iv);
		bgIv.setImageResource(mSrcId);
	}
}
