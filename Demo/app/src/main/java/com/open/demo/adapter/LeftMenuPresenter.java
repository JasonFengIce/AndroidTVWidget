package com.open.demo.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

import com.open.androidtvwidget.leanback.widget.OpenPresenter;
import com.open.androidtvwidget.utils.OPENLOG;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView demo 的左侧菜单.
 * Created by hailongqiu on 2016/8/24.
 */
public class LeftMenuPresenter extends OpenPresenter {

    private List<String> strList = new ArrayList<String>() {
        {
            add("横向LinerLayout");
            add("纵向LinerLayout");
            add("横向GridLayout");
            add("纵向GridLayout");
            add("带标题头GridLayout");
            add("Leanback demo");
        }
    };

    @Override
    public int getItemCount() {
        return strList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button btn = new Button(parent.getContext());
        return new ViewHolder(btn);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Button btn = (Button) viewHolder.view;
        String str = strList.get(position);
        btn.setText(str);
    }

}
