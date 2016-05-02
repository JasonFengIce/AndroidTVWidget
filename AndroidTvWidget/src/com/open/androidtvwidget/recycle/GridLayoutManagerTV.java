package com.open.androidtvwidget.recycle;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 修复 GridLayoutManager 焦点错乱问题.
 */
public class GridLayoutManagerTV extends GridLayoutManager {

	public GridLayoutManagerTV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public GridLayoutManagerTV(Context context, int spanCount) {
		super(context, spanCount);
	}

	@Override
	public boolean requestChildRectangleOnScreen(RecyclerView arg0, View arg1, Rect arg2, boolean arg3) {
		Log.d("hailongqiu", "hailongqiu requestChildRectangleOnScreen");
		return false;
	}

	/**
	 * 焦点搜索失败处理.
	 */
	@Override
	public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler,
			RecyclerView.State state) {
		View nextFocus = super.onFocusSearchFailed(focused, focusDirection, recycler, state);
		Log.d("hailongqiu", "hailongqiu onFocusSearchFailed");
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onRequestChildFocus(RecyclerView parent, View child, View focused) {
		Log.d("hailongqiu", "hailongqiu onRequestChildFocus child:" + child + " focused:" + focused);
		if (getPositionByView(child) == NO_POSITION) {
			// This shouldn't happen, but in case it does be sure not to attempt
			// a
			// scroll to a view whose item has been removed.
			return true;
		}
		// if (!mInLayout && !mInSelection && !mInScroll) {
		scrollToView(child, focused, true);
		// }
		return true; // super.onRequestChildFocus(parent, child, focused);
	}

	@SuppressWarnings("deprecation")
	private int getPositionByView(View view) {
		if (view == null) {
			return NO_POSITION;
		}
		LayoutParams params = (LayoutParams) view.getLayoutParams();
		if (params == null || params.isItemRemoved()) {
			// when item is removed, the position value can be any value.
			return NO_POSITION;
		}
		return params.getViewPosition();
	}

	private void scrollToView(View view, View childView, boolean smooth) {
		int newFocusPosition = getPositionByView(view);
		view.requestFocus();
	}

}
