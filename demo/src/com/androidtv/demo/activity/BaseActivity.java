package com.androidtv.demo.activity;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity implements CallBaseible {
	
	@Override
	protected void onStart() {
		super.onStart();
		initAllDatas();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initAllViews();
	}

	@Override
	public void initAllViews() {
	}

	@Override
	public void initAllDatas() {
	}
}
