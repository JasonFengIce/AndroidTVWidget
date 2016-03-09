package com.open.androidtvwidget.keyboard;

import com.open.androidtvwidget.R;

import android.content.Context;

/**
 * 软键盘.
 * @author hailong.qiu 356752238@qq.com
 *
 */
public class SkbContainer {

	private InputModeSwitcher mInputModeSwitcher;
	private SoftKeyboardView mSoftKeyboardView; // 主要的子软键盘.
	private int mSkbLayout;
	private Context mContext;

	public SkbContainer(Context context) {
		this.mContext = context;
	}

	public void setInputModeSwitcher(InputModeSwitcher inputModeSwitcher) {
		mInputModeSwitcher = inputModeSwitcher;
	}

	public void updateInputMode() {
		int skbLayout = mInputModeSwitcher.getSkbLayout(); // 输入类型转换出布局XML id.
		if (mSkbLayout != skbLayout) {
			mSkbLayout = skbLayout;
			updateSkbLayout(); // 更新软键盘布局.
		}
	}

	private void updateSkbLayout() {
		SkbPool skbPool = SkbPool.getInstance();
		SoftKeyboard majorSkb = null; // XML中读取保存的键值.
		switch (mSkbLayout) {
		case R.xml.sbd_qwerty: // 全英文键盘.
			majorSkb = skbPool.getSoftKeyboard(mContext, R.xml.sbd_qwerty);
			break;
		default:
			break;
		}
		// 重新绘制 软键盘.
		mSoftKeyboardView.setSoftKeyboard(majorSkb);
	}

}
