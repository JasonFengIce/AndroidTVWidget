package com.open.androidtvwidget.adapter;

import java.util.ArrayList;
import java.util.List;

import com.open.androidtvwidget.R;
import com.open.androidtvwidget.view.OpenTabHost;
import com.open.androidtvwidget.view.TextViewWithTTF;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabWidget;

public class OpenTabTitleAdapter extends BaseTabTitleAdapter {
	
	private List<String> titleList = new ArrayList<String>();
	
	public OpenTabTitleAdapter() {
		for (int i = 0; i < 4; i++) {
			titleList.add("标题栏" + i);
		}
	}
	
	@Override
	public int getCount() {
		return titleList.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		parent.getContext();
		String title = titleList.get(position);
		if (convertView == null) {
			convertView = newTabIndicator(parent.getContext(), title, false);
		} else {
			// ... ..
		}
		return convertView;
	}

	private View newTabIndicator(Context context, String tabName, boolean focused) {
		final String name = tabName;
		View viewC = View.inflate(context, R.layout.tab_view_indicator_item, null);
		TextViewWithTTF view = (TextViewWithTTF) viewC.findViewById(R.id.tv_tab_indicator);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 0, 20, 0);
		view.setLayoutParams(lp);

		// mTabWidget.setPadding(getResources().getDimensionPixelSize(R.dimen.tab_left_offset),
		// 0, 0, 0);

		view.setText(name);

		if (focused == true) {
			Resources res = context.getResources();
			view.setTextColor(res.getColor(android.R.color.white));
			view.setTypeface(null, Typeface.BOLD);
			view.requestFocus();
		}
		return viewC;
	}

}
