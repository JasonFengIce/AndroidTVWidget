package com.open.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.adapter.GeneralAdapter;
import com.open.androidtvwidget.leanback.mode.DefualtListPresenter;
import com.open.androidtvwidget.leanback.mode.ItemHeaderPresenter;
import com.open.androidtvwidget.leanback.mode.ItemListPresenter;
import com.open.androidtvwidget.leanback.mode.ListRow;
import com.open.androidtvwidget.leanback.mode.ListRowPresenter;
import com.open.androidtvwidget.leanback.mode.OpenPresenter;
import com.open.androidtvwidget.leanback.recycle.GridLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.leanback.widget.ItemContainerView;
import com.open.androidtvwidget.leanback.widget.ListContentView;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;
import com.open.demo.adapter.HeaderGridPresenter;
import com.open.demo.adapter.LeftMenuPresenter;
import com.open.demo.adapter.RecyclerViewPresenter;
import com.open.demo.mode.Movie;
import com.open.demo.mode.TestMoviceListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerview Demo.
 * setSelectedItemAtCentered 设置一直在中间. (如果设置 false，那么请使用setSelectedItemOffset来设置相差的边距)
 *
 * @author hailongqiu
 */
public class DemoRecyclerviewActivity extends Activity implements RecyclerViewTV.OnItemListener {

    private Context mContext;
    private RecyclerViewTV left_menu_rv; // 左侧菜单.
    private RecyclerViewTV mRecyclerView;
    private MainUpView mainUpView1;
    private RecyclerViewBridge mRecyclerViewBridge;
    private View oldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_recyclerview_activity);
        OPENLOG.initTag("hailongqiu", true); // 打开debug信息.
        mContext = DemoRecyclerviewActivity.this;
        left_menu_rv = (RecyclerViewTV) findViewById(R.id.left_menu_rv);
        mRecyclerView = (RecyclerViewTV) findViewById(R.id.recyclerView);
        mainUpView1 = (MainUpView) findViewById(R.id.mainUpView1);
        mainUpView1.setEffectBridge(new RecyclerViewBridge());
        // 注意这里，需要使用 RecyclerViewBridge 的移动边框 Bridge.
        mRecyclerViewBridge = (RecyclerViewBridge) mainUpView1.getEffectBridge();
        mRecyclerViewBridge.setUpRectResource(R.drawable.video_cover_cursor);
        RectF receF = new RectF(getDimension(R.dimen.w_130), getDimension(R.dimen.h_130),
                getDimension(R.dimen.w_130), getDimension(R.dimen.h_130));
        mRecyclerViewBridge.setDrawShadowRectPadding(receF);
        // 初始化左侧菜单.
        initLeftMenu();
        //  初始化带标题头的demo.
//        testHeaderGridLayout();
        testLeanbackDemo();
        //
        mRecyclerView.setOnItemListener(this);
        // item 单击事件处理.
        mRecyclerView.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
            }
        });
    }

    private void initLeftMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        left_menu_rv.setLayoutManager(layoutManager);
        left_menu_rv.setFocusable(false);
        GeneralAdapter generalAdapter = new GeneralAdapter(new LeftMenuPresenter());
        left_menu_rv.setAdapter(generalAdapter);
        left_menu_rv.setOnItemListener(new RecyclerViewTV.OnItemListener() {
            @Override
            public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
                // 传入 itemView也可以, 自己保存的 oldView也可以.
                mRecyclerViewBridge.setUnFocusView(itemView);
            }

            @Override
            public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
                mRecyclerViewBridge.setFocusView(itemView, 1.0f);
                oldView = itemView;
            }

            /**
             * 这里是调整开头和结尾的移动边框.
             */
            @Override
            public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
                mRecyclerViewBridge.setFocusView(itemView, 1.0f);
                oldView = itemView;
            }
        });
        left_menu_rv.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                // 测试.
                mRecyclerViewBridge.setFocusView(itemView, oldView, 1.0f);
                oldView = itemView;
                //
                onViewItemClick(itemView, position);
            }
        });
    }

    /**
     * 测试LinerLayout.
     */
    private void testRecyclerViewLinerLayout(int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setFocusable(false);
        GeneralAdapter generalAdapter = new GeneralAdapter(new RecyclerViewPresenter(100));
        mRecyclerView.setAdapter(generalAdapter);
    }

    /**
     * 测试GridLayout.
     */
    private void testRecyclerViewGridLayout(int orientation) {
        GridLayoutManagerTV gridlayoutManager = new GridLayoutManagerTV(this, 4); // 解决快速长按焦点丢失问题.
        gridlayoutManager.setOrientation(orientation);
        mRecyclerView.setLayoutManager(gridlayoutManager);
        mRecyclerView.setFocusable(false);
        GeneralAdapter generalAdapter = new GeneralAdapter(new RecyclerViewPresenter(100));
        mRecyclerView.setAdapter(generalAdapter);
    }

    /**
     * 测试带标题栏的grid.
     */
    private void testHeaderGridLayout() {
        final GridLayoutManagerTV gridlayoutManager = new GridLayoutManagerTV(this, 5); // 解决快速长按焦点丢失问题.
        gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        // recyclerView.setHasFixedSize(true); // 保持固定的大小
        mRecyclerView.setLayoutManager(gridlayoutManager);
        mRecyclerView.setFocusable(false);
        final HeaderGridPresenter headerGridAdapter = new HeaderGridPresenter(100);
        GeneralAdapter generalAdapter = new GeneralAdapter(headerGridAdapter);
        mRecyclerView.setAdapter(generalAdapter);
        gridlayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return headerGridAdapter.isHeader(position) ? gridlayoutManager.getSpanCount() : 1;
            }
        });
    }

    ///////////////////////////////////////////////////////////////
    ////   Leanback 测试 Demo start ---->
    ///////////////////////////////////////////////////////////////

    /**
     * Leanback 标题头.
     */
    private static final String MOVIE_CATEGORY[] = {
            "全部软件",
            "聊天工具",
            "浏览器",
            "游戏娱乐",
            "网络游戏",
            "杀毒安全",
    };

    /**
     * Leanback 横向 数据测试.
     */
    private static final List<Movie> MOVIE_ITEMS = new ArrayList<Movie>() {
        {
            add(new Movie(0, "天天2模拟器"));
            add(new Movie(0, "陌陌222"));
            add(new Movie(0, "爱奇艺222"));
            add(new Movie(0, "英雄2联盟2"));
            add(new Movie(0, "腾讯22视频"));
            add(new Movie(0, "QQ22音乐"));
            add(new Movie(0, "无敌22讯飞"));
            add(new Movie(0, "360浏览器"));
            add(new Movie(0, "美图秀秀"));
            add(new Movie(0, "YY语音"));
            add(new Movie(0, "迅雷"));
            add(new Movie(0, "腾讯视频"));
            add(new Movie(0, "酷狗阴影"));
            add(new Movie(0, "优酷"));
            add(new Movie(0, "篮球"));
            add(new Movie(0, "足球"));
        }
    };
    private static final List<Movie> MOVIE_ITEMS2 = new ArrayList<Movie>() {
        {
            add(new Movie(0, "天天模拟器AAA"));
            add(new Movie(0, "陌陌AAA"));
            add(new Movie(0, "爱奇艺222AAA"));
            add(new Movie(0, "英雄2联盟2AA"));
            add(new Movie(0, "腾讯视频AA"));
            add(new Movie(0, "酷狗阴影AA"));
            add(new Movie(0, "优酷AA"));
            add(new Movie(0, "篮球AA"));
            add(new Movie(0, "足球AAA1"));
            add(new Movie(0, "足球AAA15"));
            add(new Movie(0, "足球AAA16"));
        }
    };

    private final RecyclerViewTV.OnChildViewHolderSelectedListener mRowSelectedListener =
            new RecyclerViewTV.OnChildViewHolderSelectedListener() {
                @Override
                public void onChildViewHolderSelected(RecyclerView parent,
                                                      RecyclerView.ViewHolder viewHolder, int position) {
                    onRowSelected(parent, viewHolder, position, -1);
                }
            };

    private GeneralAdapter.ViewHolder mSelectedViewHolder;
    List<ListRow> mListRows = new ArrayList<ListRow>();
    ListRowPresenter mListRowPresenter;

    /**
     * 一行选中.
     */
    private void onRowSelected(RecyclerView parent, RecyclerView.ViewHolder viewHolder,
                               int position, int subposition) {
        if (mSelectedViewHolder != viewHolder) {
            OPENLOG.D("pos:" + position + " vh:" + viewHolder);
            // 先清除 MselectedViewHolder 的一行选中颜色.
            if (mSelectedViewHolder != null) {
                setRowViewSelected(mSelectedViewHolder, false);
            }
            // 设置当前选中的 一行的选中颜色.
            mSelectedViewHolder = (GeneralAdapter.ViewHolder) viewHolder;
            if (mSelectedViewHolder != null) {
                setRowViewSelected(mSelectedViewHolder, true);
            }
        }
    }

    /**
     * 改变一行的颜色.这里只是DEMO，你可以改变一行的图片哈，或者背景颜色哈.
     * 具体可以看Leanback的android demo.
     */
    private void setRowViewSelected(GeneralAdapter.ViewHolder viewHolder, boolean selected) {
        if (isListRowPresenter()) {
            try {
                ListRowPresenter.ListRowViewHolder listRowPresenter = (ListRowPresenter.ListRowViewHolder) viewHolder.getViewHolder();
                ItemListPresenter.ItemListViewHolder itemListViewHolder = (ItemListPresenter.ItemListViewHolder) listRowPresenter.getListViewHolder();
                DefualtListPresenter defualtListPresenter = itemListViewHolder.getDefualtListPresenter();
                if (defualtListPresenter instanceof TestMoviceListPresenter) {
                    TestMoviceListPresenter testMoviceListPresenter = (TestMoviceListPresenter) defualtListPresenter;
                    testMoviceListPresenter.setSelect(selected);
                    //
                    RecyclerViewTV recyclerViewTV = itemListViewHolder.getRecyclerViewTV();
                    int count = recyclerViewTV.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View view = recyclerViewTV.getChildAt(i);
                        if (selected) {
                            view.setAlpha(0.5f);
                        } else {
                            view.setAlpha(1.0f);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Leanback Demo.
     */
    private void testLeanbackDemo() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加测试数据。
        for (int i = 0; i < MOVIE_CATEGORY.length; i++) {
            String txt = MOVIE_CATEGORY[i];
            // 添加一行的数据.
            ListRow listRow = new ListRow(txt); // 标题头.
            List<Movie> movies = MOVIE_ITEMS;
            if (i % 2 == 0)
                movies = MOVIE_ITEMS2;
            listRow.addAll(movies); // 添加列的数据.
            listRow.setOpenPresenter(new TestMoviceListPresenter()); // 设置列的item样式.
            // 添加一行的数据（标题头，列的数据)
            mListRows.add(listRow);
        }
        // 添加最后一行数据.
        ListRow lastListRow = new ListRow("设置");
        lastListRow.add("网络wifi");
        lastListRow.add("声音");
        lastListRow.add("声音");
        lastListRow.setOpenPresenter(new DefualtListPresenter());
        mListRows.add(lastListRow);
        // 测试demo, 一般你想要自己的效果，
        // 继承 Header 和 List 可以继承 OpenPresente来重写.
        //  而横向中的item 继承 DefualtListPresenter 来重写.
        mListRowPresenter = new ListRowPresenter(mListRows,
                new ItemHeaderPresenter(),
                new ItemListPresenter());
        GeneralAdapter generalAdapter = new GeneralAdapter(mListRowPresenter);
        mRecyclerView.setAdapter(generalAdapter);
        // 行选中的事件.
        mRecyclerView.setOnChildViewHolderSelectedListener(mRowSelectedListener);
        // 更新数据测试 并且重写请求焦点.(这里只是demo)
        final Handler handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 我只是DEMO，不要每次都来按照我的写的，好么.
                ItemContainerView itemContainerView = (ItemContainerView) mRecyclerView.getChildAt(saveRowPos);
                if (itemContainerView != null) {
                    ListContentView listContentView = (ListContentView) itemContainerView.getChildAt(1);
                    if (listContentView != null) {
                        RecyclerViewTV columnRecyclerView = listContentView.getRecyclerViewTV();
                        View view = columnRecyclerView.getChildAt(saveColumnPos);
                        if (view != null)
                            view.requestFocus();
                    }
                }
            }
        };
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                View view = mRecyclerView.getSelectView();
                // 我这里DEMO比较多，不要照抄我的，谢谢.
                if (view != null && view instanceof  ItemContainerView) {
                    // 保存原来的焦点位置.
                    ItemContainerView itemContainerView = (ItemContainerView) mRecyclerView.getSelectView();
                    if (itemContainerView != null) {
                        saveRowPos = mRecyclerView.getSelectPostion(); // 保存第几列的焦点.
                        ListContentView listContentView = (ListContentView) itemContainerView.getChildAt(1);
                        if (listContentView != null) {
                            RecyclerViewTV recyclerViewTV = listContentView.getRecyclerViewTV();
                            saveColumnPos = recyclerViewTV.getSelectPostion();
                        }
                    }
                }
                // 请求更新数据.
                ListRow listRow = mListRows.get(0);
                listRow.setHeaderItem("改变标题头数据");
                mListRowPresenter.setItems(mListRows, 0);
                // 更新数据，焦点会丢失，SB你会如何做
                // 只有保存原来的焦点view的位置, 然后 延时请求焦点.
                handler1.sendEmptyMessageDelayed(10, 500);
            }
        };
        handler.sendEmptyMessageDelayed(10, 6666);
    }

    int saveColumnPos = -1;
    int saveRowPos = -1;

    ///////////////////////////////////////////////////////////////
    ////   Leanback 测试 Demo end ---->
    ///////////////////////////////////////////////////////////////

    public float getDimension(int id) {
        return getResources().getDimension(id);
    }

    // 左边侧边栏的单击事件.
    private void onViewItemClick(View v, int pos) {
        switch (pos) {
            case 0: // 横向 liner layout.
                //
                testRecyclerViewLinerLayout(LinearLayoutManager.HORIZONTAL);
                break;
            case 1: // 纵向 liner layout.
                testRecyclerViewLinerLayout(LinearLayoutManager.VERTICAL);
                break;
            case 2: // 横向 grid layout.
                testRecyclerViewGridLayout(GridLayoutManager.HORIZONTAL);
                break;
            case 3: // 纵向 grid layout.
                testRecyclerViewGridLayout(GridLayoutManager.VERTICAL);
                break;
            case 4: // 带header的grid.
                testHeaderGridLayout();
                break;
            case 5: // Leanback demo.
                testLeanbackDemo();
                break;
            default:
                break;
        }
    }

    /**
     * 排除 Leanback demo的RecyclerView.
     */
    private boolean isListRowPresenter() {
        GeneralAdapter generalAdapter = (GeneralAdapter) mRecyclerView.getAdapter();
        OpenPresenter openPresenter = generalAdapter.getPresenter();
        return (openPresenter instanceof ListRowPresenter);
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        if (!isListRowPresenter()) {
            mRecyclerViewBridge.setUnFocusView(oldView);
        }
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        if (!isListRowPresenter()) {
            mRecyclerViewBridge.setFocusView(itemView, 1.2f);
            oldView = itemView;
        }
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        if (!isListRowPresenter()) {
            mRecyclerViewBridge.setFocusView(itemView, 1.2f);
            oldView = itemView;
        }
    }

}
