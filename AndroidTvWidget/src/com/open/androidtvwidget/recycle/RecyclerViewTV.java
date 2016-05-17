package com.open.androidtvwidget.recycle;

import com.open.androidtvwidget.R;
import com.open.androidtvwidget.utils.DisplayUtil;
import com.open.androidtvwidget.view.WidgetTvViewBring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewTV extends RecyclerView {

	public RecyclerViewTV(Context context) {
		super(context);
		init(context);
	}

	public RecyclerViewTV(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RecyclerViewTV(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	WidgetTvViewBring mWidgetTvViewBring;
	Drawable mDrawable;

	private void init(Context context) {
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setHasFixedSize(true);
		setWillNotDraw(true);
		setOverScrollMode(View.OVER_SCROLL_NEVER);
		setChildrenDrawingOrderEnabled(true);
		mWidgetTvViewBring = new WidgetTvViewBring(this);
		//
		mDrawable = getResources().getDrawable(R.drawable.white_light_10);
	}

	@Override
	public void bringChildToFront(View child) {
		mWidgetTvViewBring.bringChildToFront(this, child);
	}

	// @Override
	// protected int getChildDrawingOrder(int childCount, int i) {
	// return mWidgetTvViewBring.getChildDrawingOrder(childCount, i);
	// }

//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		try {
//			super.dispatchDraw(canvas);
//			//
//			Log.d("hailongqiu", "hailongqiu dispatchDraw");
//			View v = getFocusedChild();
//			Rect rect = new Rect();
//			v.getGlobalVisibleRect(rect);
//			Rect gridRect = new Rect();
//			getGlobalVisibleRect(gridRect);
//			rect.offset(-gridRect.left, -gridRect.top);
//			mDrawable.setBounds(new Rect(DisplayUtil.dip2px(getContext(), rect.left),
//					DisplayUtil.dip2px(getContext(), rect.top), DisplayUtil.dip2px(getContext(), rect.right),
//					DisplayUtil.dip2px(getContext(), rect.bottom)));
////			mDrawable.setBounds(gridRect);
//			mDrawable.draw(canvas);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public Rect findLocationWithView(View view) {
		ViewGroup root = (ViewGroup) getParent();
		Rect rect = new Rect();
		root.offsetDescendantRectToMyCoords(view, rect);
		return rect;
	}

}
