package com.open.androidtvwidget.view;

import java.util.ArrayList;

import com.open.androidtvwidget.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * 标题栏控件.
 * 
 * @author hailongqiu
 *
 */
public class OpenTabHost extends TabHost {

	public OpenTabHost(Context context) {
		super(context);
		init(context, null);
	}

	public OpenTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private Context mContext;
	private TabWidget mTabWidget; // 标题栏.

	private void init(Context context, AttributeSet attrs) {
		this.setBackgroundColor(Color.TRANSPARENT);
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.tabhost_title_head, this, true);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		setup();
		initViewEvents();
	}

	private void initViewEvents() {
		setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int position = getCurrentTab();
				switchTab(position);
			}
		});
	}

	public void initChildWidgetTests() {
		for (int i = 0; i < 4; i++) {
			String tabName = "测试标题A" + i;
			View tabView = newTabIndicator(tabName, false);
			TabSpec tabSpec = this.newTabSpec(tabName).setIndicator(tabView);
			//
			this.addTabWidget(tabSpec);
		}
		requestLayout();
	}

	private void switchTabView(int index) {
		switchTab(index);
	}

	public void switchTab(int index) {
		TabWidget tw = getTabWidget();
		for (int i = 0; i < tw.getChildCount(); i++) {
			View viewC = tw.getChildTabViewAt(i);
			TextViewWithTTF view = (TextViewWithTTF) viewC.findViewById(R.id.tv_tab_indicator);
			if (view != null) {
				Resources res = view.getResources();
				if (res != null) {
					if (i == index) {
						view.setTextColor(res.getColor(android.R.color.white));
						view.setTypeface(null, Typeface.BOLD);
					} else {
						view.setTextColor(res.getColor(R.color.white_50));
						view.setTypeface(null, Typeface.NORMAL);
					}
				}
			}
		}
	}

	private View newTabIndicator(String tabName, boolean focused) {
		final String name = tabName;
		View viewC = View.inflate(this.mContext, R.layout.tab_view_indicator_item, null);

		TextViewWithTTF view = (TextViewWithTTF) viewC.findViewById(R.id.tv_tab_indicator);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);

//		mTabWidget.setPadding(getResources().getDimensionPixelSize(R.dimen.tab_left_offset), 0, 0, 0);

		view.setText(name);

		if (focused == true) {
			Resources res = getResources();
			view.setTextColor(res.getColor(android.R.color.white));
			view.setTypeface(null, Typeface.BOLD);
			view.requestFocus();
		}
		return viewC;
	}

	public void addTabWidget(String title) {
	}

	public void addTabWidget(TabHost.TabSpec tabSpec) {
		tabSpec.setContent(new DummyTabFactory(mContext));
		this.addTab(tabSpec);
	}
	
	class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context context) {
			this.mContext = context;
		}
		
		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

}
