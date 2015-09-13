package com.androidtv.demo.activity;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.widget.LinearLayout;

import com.androidtv.demo.R;
import com.open.androidtvwidget.view.MainLayout;
import com.open.androidtvwidget.view.MainUpView;

/**
 * Ä£·ÂVSTµÄDEMO.
 * 
 * @author hailongqiu 356752238@qq.com
 *
 */
public class MainLauncherActivity extends BaseActivity {

	LinearLayout mMainLay;
	MainUpView mMainUpView;

	@Override
	public void initAllViews() {
		super.initAllViews();
		setContentView(R.layout.activity_main);
		findViews();
		mMainLay.getViewTreeObserver().addOnGlobalFocusChangeListener(
				new OnGlobalFocusChangeListener() {
					@Override
					public void onGlobalFocusChanged(View oldFocus,
							View newFocus) {
						mMainUpView.setFocusView(newFocus, 1.1f);
						if (oldFocus != null)
							mMainUpView.setUnFocusView(oldFocus);
					}
				});
	}

	private void findViews() {
		mMainUpView = (MainUpView) findViewById(R.id.main_up_view);
		mMainLay = (LinearLayout) findViewById(R.id.main_lay);
	}

	@Override
	public void initAllDatas() {
		super.initAllDatas();
	}
}
