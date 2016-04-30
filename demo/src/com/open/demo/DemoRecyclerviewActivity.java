package com.open.demo;

import com.open.androidtvwidget.bridge.OpenEffectBridge;
import com.open.androidtvwidget.recycle.GridLayoutManagerTV;
import com.open.androidtvwidget.view.MainUpView;
import com.open.demo.adapter.HeaderGridAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;

/**
 * recyclerview Demo.
 * 
 * @author hailongqiu
 *
 */
public class DemoRecyclerviewActivity extends Activity implements OnClickListener {

	Context mContext;
	RecyclerView recyclerView;
	MainUpView mainUpView1;
	OpenEffectBridge openEffectBridge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_recyclerview_activity);
		mContext = DemoRecyclerviewActivity.this;
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
		//
		openEffectBridge = (OpenEffectBridge) mainUpView1.getEffectBridge();
		openEffectBridge.setUpRectResource(R.drawable.test_rectangle);
		//
		testHeaderGridLayout();
		initAllViewEvents();
	}

	private View oldView;

	private void initAllViewEvents() {
		findViewById(R.id.h_liner_btn).setOnClickListener(this);
		findViewById(R.id.v_liner_btn).setOnClickListener(this);
		findViewById(R.id.h_grid_btn).setOnClickListener(this);
		findViewById(R.id.v_grid_btn).setOnClickListener(this);
		findViewById(R.id.head_grid_btn).setOnClickListener(this);
		//
		recyclerView.getViewTreeObserver().addOnGlobalFocusChangeListener(new OnGlobalFocusChangeListener() {
			@Override
			public void onGlobalFocusChanged(View oldFocus, View focusview) {
				mainUpView1.setFocusView(focusview, oldFocus, 1.2f);
				oldView = focusview;
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.h_liner_btn: // 横向 liner layout.
			testRecyclerViewLinerLayout(LinearLayoutManager.HORIZONTAL);
			break;
		case R.id.v_liner_btn:
			testRecyclerViewLinerLayout(LinearLayoutManager.VERTICAL);
			break;
		case R.id.h_grid_btn: // 横向 grid layout.
			testRecyclerViewGridLayout(GridLayoutManager.HORIZONTAL);
			break;
		case R.id.v_grid_btn:
			testRecyclerViewGridLayout(GridLayoutManager.VERTICAL);
			break;
		case R.id.head_grid_btn: // 带header的grid.
			testHeaderGridLayout();
			break;
		default:
			break;
		}
	}

	/**
	 * 测试LinerLayout.
	 */
	public void testRecyclerViewLinerLayout(int orientation) {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(orientation);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setFocusable(false);
	}

	/**
	 * 测试GridLayout.
	 */
	private void testRecyclerViewGridLayout(int orientation) {
		GridLayoutManager gridlayoutManager = new GridLayoutManagerTV(this, 4);
		gridlayoutManager.setOrientation(orientation);
		recyclerView.setLayoutManager(gridlayoutManager);
		recyclerView.setFocusable(false);
	}

	/**
	 * 测试带标题栏的grid.
	 */
	private void testHeaderGridLayout() {
		final GridLayoutManager gridlayoutManager = new GridLayoutManagerTV(this, 5);
		//
		gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//		 recyclerView.setHasFixedSize(true); // 保持固定的大小
		recyclerView.setLayoutManager(gridlayoutManager);
		recyclerView.setFocusable(false);
		final HeaderGridAdapter mHeaderGridAdapter = new HeaderGridAdapter(100);
		recyclerView.setAdapter(mHeaderGridAdapter);
		gridlayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				return mHeaderGridAdapter.isHeader(position) ? gridlayoutManager.getSpanCount() : 1;
			}
		});
	}

}
