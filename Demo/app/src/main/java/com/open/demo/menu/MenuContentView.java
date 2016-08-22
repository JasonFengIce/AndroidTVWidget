package com.open.demo.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.open.androidtvwidget.recycle.RecyclerViewTV;
import com.open.demo.R;

/**
 *  MenuContentView 是子菜单的内容.
 * Created by hailongqiu on 2016/8/22.
 */
public class MenuContentView extends LinearLayout {

    private RecyclerViewTV mRecyclerViewTV;

    public MenuContentView(Context context) {
        this(context, null);
    }

    public MenuContentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.lb_content_menu, this);
        mRecyclerViewTV = (RecyclerViewTV) findViewById(R.id.menu_content);
        setOrientation(LinearLayout.VERTICAL);
        // 先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    public RecyclerViewTV getRecyclerViewTV() {
        return this.mRecyclerViewTV;
    }

}
