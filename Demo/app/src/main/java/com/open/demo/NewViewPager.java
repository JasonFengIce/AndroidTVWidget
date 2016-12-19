package com.open.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.SmoothVorizontalScrollView;

import java.util.ArrayList;

/**
 * Created by hailongqiu on 2016/11/29.
 */

public class NewViewPager extends SmoothVorizontalScrollView {
    public NewViewPager(Context context) {
        super(context);
    }

    public NewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        OPENLOG.E("direction:" + direction);
        if (direction == View.FOCUS_DOWN) {
            OPENLOG.E("direction FOCUS_DOWN:" + direction);
        } else if (direction == View.FOCUS_UP) {
            OPENLOG.E("direction FOCUS_UP:" + direction);
        }
        return super.dispatchUnhandledMove(focused, direction);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        OPENLOG.E("direction:" + direction + " focusableMode:" + focusableMode);
        super.addFocusables(views, direction, focusableMode);
    }
}
