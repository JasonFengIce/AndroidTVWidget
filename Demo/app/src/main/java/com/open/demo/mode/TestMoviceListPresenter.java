package com.open.demo.mode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.open.androidtvwidget.leanback.mode.DefualtListPresenter;

/**
 * Created by hailongqiu on 2016/8/25.
 */
public class TestMoviceListPresenter extends DefualtListPresenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headview = LayoutInflater.from(parent.getContext()).inflate(com.open.androidtvwidget.R.layout.list_menu_item_layout, parent, false);
        Button btn = new Button(parent.getContext());
        return new ViewHolder(btn);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Movie movie = ((Movie) getItem(position));
        Button btn = ((Button) viewHolder.view);
        btn.setText(movie.getTitle());
    }
}
