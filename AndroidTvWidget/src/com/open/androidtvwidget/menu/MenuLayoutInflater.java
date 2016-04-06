package com.open.androidtvwidget.menu;

import org.xmlpull.v1.XmlPullParser;

import com.open.androidtvwidget.R;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;

public class MenuLayoutInflater {

	private Context mContext;
	private static MenuLayoutInflater sInstance = null;

	public static void testLoadLayout(Context context) {
		MenuLayoutInflater.from(context).inflate(R.layout.item_listview);
	}

	public static MenuLayoutInflater from(Context context) {
		if (sInstance == null)
			sInstance = new MenuLayoutInflater(context);
		return sInstance;
	}

	public Context getContext() {
		return mContext;
	}

	protected MenuLayoutInflater(Context context) {
		mContext = context;
	}

	public View inflate(int reSource) {
		XmlResourceParser parser = getContext().getResources().getLayout(reSource);
		try {
			return inflate(parser);
		} finally {
			parser.close();
		}
	}

	public View inflate(XmlPullParser parser) {
		return null;
	}

	public final View createView(String name, String prefix, AttributeSet attrs) {
		return null;
	}

}
