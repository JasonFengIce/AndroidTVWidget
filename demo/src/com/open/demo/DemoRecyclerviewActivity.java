package com.open.demo;

import com.open.androidtvwidget.recycle.GridLayoutManagerTV;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * recyclerview Demo.
 * 
 * @author hailongqiu
 *
 */
public class DemoRecyclerviewActivity extends Activity implements OnClickListener {

	Context mContext;
	RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = DemoRecyclerviewActivity.this;
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		//
//		testHeaderGridLayout();
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
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		GridLayoutManager gridlayoutManager = new GridLayoutManagerTV(this, 4);
		gridlayoutManager.setOrientation(orientation);
		recyclerView.setLayoutManager(gridlayoutManager);
		recyclerView.setFocusable(false);
	}

	private void testHeaderGridLayout() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		GridLayoutManager gridlayoutManager = new GridLayoutManagerTV(this, 4);
		gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(gridlayoutManager);
		recyclerView.setFocusable(false);
	}

}
