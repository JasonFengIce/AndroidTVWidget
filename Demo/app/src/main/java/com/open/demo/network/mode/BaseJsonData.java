package com.open.demo.network.mode;

/**
 * Json数据.
 * Created by hailongqiu on 2016/9/5.
 */
public class BaseJsonData {
    private static final int VIEW_PAGER_TYPE = 1; // ViewPager类型.
    private int type;

    public class Item {
        int width;
        int height;
        int row;
        int colum;
        String imgUrl;
    }
}
