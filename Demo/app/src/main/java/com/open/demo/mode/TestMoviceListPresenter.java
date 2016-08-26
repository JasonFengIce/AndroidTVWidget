package com.open.demo.mode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.open.androidtvwidget.leanback.mode.DefualtListPresenter;
import com.open.demo.R;

/**
 * Leanback 横向item demo.
 * Created by hailongqiu on 2016/8/25.
 */
public class TestMoviceListPresenter extends DefualtListPresenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Movie movie = ((Movie) getItem(position));
        TextView textview = (TextView) viewHolder.view.findViewById(R.id.textView);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.view.getContext(), "teim:" + position, Toast.LENGTH_LONG).show();
            }
        });
        textview.setText(movie.getTitle());
    }

}
