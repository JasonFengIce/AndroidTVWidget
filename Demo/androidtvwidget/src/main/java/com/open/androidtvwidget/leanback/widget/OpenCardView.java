package com.open.androidtvwidget.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Leanback CardView 控件.
 * Created by hailongqiu on 2016/8/26.
 */
public class OpenCardView extends FrameLayout {

    private final Rect mShadowBounds = new Rect();

    public OpenCardView(Context context) {
        this(context, null);
    }

    public OpenCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OpenCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
