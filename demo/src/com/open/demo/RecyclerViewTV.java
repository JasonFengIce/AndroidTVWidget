package com.open.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class RecyclerViewTV extends RecyclerView {
	
	public RecyclerViewTV(Context context) {
		super(context);
		init(context);
	}
	
	public RecyclerViewTV(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}
	
	public RecyclerViewTV(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		init(context);
	}

	private void init(Context context) {
	}
	
//	@Override
//	public void bringChildToFront(View child) {
//	}
	
	
}
