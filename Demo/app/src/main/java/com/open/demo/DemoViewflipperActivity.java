package com.open.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;
import com.open.androidtvwidget.view.SmoothVorizontalScrollView;

/**
 */
public class DemoViewflipperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper_demo);
        SmoothVorizontalScrollView vf = (SmoothVorizontalScrollView) findViewById(R.id.vscrollview);
        vf.setFadingEdge(200);
        OPENLOG.initTag("hailongqiu", true);
        //
        final MainUpView mainUpView = (MainUpView) findViewById(R.id.mainUpView1);
        mainUpView.setUpRectResource(R.drawable.test_rectangle);
        vf.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                mainUpView.setFocusView(newFocus, oldFocus, 1.0f);
            }
        });
    }

}
