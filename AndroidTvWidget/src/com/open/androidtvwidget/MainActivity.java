package com.open.androidtvwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.RelativeLayout;

import com.open.androidtvwidget.view.MainUpView;
import com.open.androidtvwidget.view.ReflectItemView;

public class MainActivity extends Activity {

	MainUpView mainUpView1;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RelativeLayout content11 = (RelativeLayout) findViewById(R.id.content11);

		mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
		final ReflectItemView relayout1 = (ReflectItemView) findViewById(R.id.relayout11);
		final ReflectItemView relayout2 = (ReflectItemView) findViewById(R.id.relayout2);
		final ReflectItemView relayout3 = (ReflectItemView) findViewById(R.id.relayout3);
		
		final View test_top_iv = findViewById(R.id.test_top_iv);
		relayout1.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				testtest(v, hasFocus);
				if (hasFocus) {
					test_top_iv.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
				} else {
					test_top_iv.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
				}
			}
		});
		relayout2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				testtest(v, hasFocus);
			}
		});
		relayout3.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				testtest(v, hasFocus);
			}
		});
	}

	private void testtest(View v, boolean hasFocus) {
		if (hasFocus) {
			mainUpView1.setFocusView(v, 1.2f);
		} else {
			mainUpView1.setUnFocusView(v);
		}
	}

}
