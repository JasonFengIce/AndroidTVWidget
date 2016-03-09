package com.open.androidtvwidget.keyboard;

import android.content.Context;

/**
 * 软键盘服务.
 * @author hailong.qiu 356752238@qq.com
 *
 */
public class SoftKeyService {

	private SkbContainer mSkbContainer;
	private InputModeSwitcher mInputModeSwitcher;
	
	private Context mContext;
	
	public SoftKeyService(Context context) {
		this.mContext = context;
		mInputModeSwitcher = new InputModeSwitcher(this); // 输入法切换.
		//
		mSkbContainer = new SkbContainer(mContext);
		mSkbContainer.setInputModeSwitcher(mInputModeSwitcher);
	}
	
	public void setInputMode(int inputMode) {
		mInputModeSwitcher.setInputMode(inputMode);
	}
	
}
