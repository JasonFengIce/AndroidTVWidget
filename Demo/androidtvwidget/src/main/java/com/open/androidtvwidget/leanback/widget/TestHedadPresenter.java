package com.open.androidtvwidget.leanback.widget;

import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by hailongqiu on 2016/8/24.
 */
public class TestHedadPresenter extends OpenPresenter {

    List<String> mItems;

    public TestHedadPresenter(Object items) {
        mItems = (List<String>) items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button btn = new Button(parent.getContext());
        return new ViewHolder(btn);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String str = ((String)mItems.get(position));
        ((Button) viewHolder.view).setText(str);

    }
}
