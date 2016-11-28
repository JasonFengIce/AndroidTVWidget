package com.open.demo;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;
import com.open.androidtvwidget.view.SmoothVorizontalScrollView;

/**
 */
public class DemoViewflipperActivity extends Activity {

    View view1;
    View view2;
    View view3;

    Button switch1_btn;
    SmoothVorizontalScrollView vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper_demo);
        vf = (SmoothVorizontalScrollView) findViewById(R.id.vscrollview);
        vf.setFadingEdge(200);
        OPENLOG.initTag("hailongqiu", true);
        //
        switch1_btn = (Button) findViewById(R.id.switch1_btn);

        view1 = findViewById(R.id.content11);
        view2 = findViewById(R.id.content12);
        view3 = findViewById(R.id.content13);

        final MainUpView mainUpView = (MainUpView) findViewById(R.id.mainUpView1);
        mainUpView.setUpRectResource(R.drawable.test_rectangle);
        vf.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                mainUpView.setFocusView(newFocus, oldFocus, 1.0f);
            }
        });
        // 翻页.
        switch1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = (ViewGroup) vf.getChildAt(0);
                View view = viewGroup.getChildAt(1);
                OPENLOG.D("view:" + view);
                if (view != null) {
                    Rect rect = findLocationWithView(view);
                    int scrollY = vf.getScrollY();
                    int offset = rect.top + scrollY;
                    if (offset < 0) {
                        offset = 0;
                    }
                    vf.scrollTo(0, offset);
                }
            }
        });
    }

    public Rect findLocationWithView(View view) {
        ViewGroup root = (ViewGroup) vf.getParent();
        Rect rect = new Rect();
        root.offsetDescendantRectToMyCoords(view, rect);
        return rect;
    }

}
