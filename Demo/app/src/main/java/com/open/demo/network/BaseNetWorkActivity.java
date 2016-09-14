package com.open.demo.network;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.FrameLayout;

import com.open.androidtvwidget.bridge.EffectNoDrawBridge;
import com.open.androidtvwidget.bridge.OpenEffectBridge;
import com.open.androidtvwidget.utils.IdentifierUtuils;
import com.open.androidtvwidget.utils.OPENLOG;
import com.open.androidtvwidget.view.MainUpView;
import com.open.demo.R;
import com.open.demo.network.mode.BaseJsonData;
import com.open.demo.network.view.JsonItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON网络加载布局DEMO.(高危DEMO，请勿模仿)
 * Created by hailongqiu on 2016/9/4.
 */
public class BaseNetWorkActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Object> {

    FrameLayout mFrameLayout;
    MainUpView mMainUpView;
    Context mContext;
    BaseJsonData mBaseJsonData = new BaseJsonData();
    View mOldView;
    View root_lay;

    private float mScaleX = 1.2f;
    private float mScaleY = 1.2f;

    public BaseNetWorkActivity() {
        mBaseJsonData.setBgRes("main_bg");
        int x = 200;
        int y = 200;
        int width = 200;
        int height = 300;
        List<BaseJsonData.Item> items = new ArrayList<BaseJsonData.Item>();
        for (int i = 0; i < 6; i++) {
            BaseJsonData.Item item = mBaseJsonData.new Item();
            item.setX("" + x);
            item.setY("" + y);
            item.setWidth("" + width);
            item.setHeight("" + height);
            item.setText("item" + i);
            item.setFocus(true);
            item.setMouse(true);
            item.setRadius("10");
            x += width + 20;
            items.add(item);
        }
        mBaseJsonData.setItems(items);
        //
        BaseJsonData.UpRectItem upRectItem = mBaseJsonData.new UpRectItem();
        upRectItem.setType(0);
        upRectItem.setTranAnimTime(300);
        upRectItem.setScaleX(1.0f);
        upRectItem.setScaleY(1.0f);
        upRectItem.setUpRes("white_light_10");
        upRectItem.setRectLeftPadding("10");
        upRectItem.setRectRightPadding("10");
        upRectItem.setRectTopPadding("10");
        upRectItem.setRectBottomPadding("10");
        mBaseJsonData.setUpRectItem(upRectItem);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OPENLOG.initTag("hailongqiu", true);
        setContentView(R.layout.demo_json_load_activity);
        mContext = getApplicationContext();
        getSupportLoaderManager().initLoader(0, null, this);
        // 设置背景
        View root_lay = findViewById(R.id.root_lay);
        int upRes = IdentifierUtuils.getIdentifierDrawable(mContext, mBaseJsonData.getBgRes());
        root_lay.setBackgroundResource(upRes);
        //
        initAllViews();
        initJsonLoadViews();
    }

    private void initJsonLoadViews() {
        float density = getResources().getDisplayMetrics().density;
        BaseJsonData.UpRectItem upRectItem = mBaseJsonData.getUpRectItem();
        OpenEffectBridge openEffectBridge = new OpenEffectBridge();
        if (upRectItem.getType() == 0) {
            openEffectBridge = new EffectNoDrawBridge();
            mMainUpView.setEffectBridge(openEffectBridge);
        } else {
            mMainUpView.setEffectBridge(openEffectBridge);
        }
        /*  设置边框图片 */
        int upRes = IdentifierUtuils.getIdentifierDrawable(mContext, upRectItem.getUpRes());
        openEffectBridge.setUpRectResource(upRes);
        /* 设置边框移动时间 */
        openEffectBridge.setTranDurAnimTime(upRectItem.getTranAnimTime());
        /* 设置边框间距 */
        float leftPadding = IdentifierUtuils.getIdentifierWidthDimen(mContext, upRectItem.getRectLeftPadding());
        float rightPadding = IdentifierUtuils.getIdentifierWidthDimen(mContext, upRectItem.getRectRightPadding());
        float topPadding = IdentifierUtuils.getIdentifierWidthDimen(mContext, upRectItem.getRectTopPadding());
        float bottomPadding = IdentifierUtuils.getIdentifierWidthDimen(mContext, upRectItem.getRectBottomPadding());
        RectF rectf = new RectF(leftPadding * density, topPadding * density, rightPadding * density, bottomPadding * density);
        openEffectBridge.setDrawUpRectPadding(rectf); // 间距.
        /* 获取放大Scale */
        mScaleX = upRectItem.getScaleX();
        mScaleY = upRectItem.getScaleY();
        /* item view 处理 */
        List<BaseJsonData.Item> items = mBaseJsonData.getItems();
        for (BaseJsonData.Item item : items) {

            int x = (int) IdentifierUtuils.getIdentifierWidthDimen(mContext, item.getX());
            int y = (int) IdentifierUtuils.getIdentifierHeightDimen(mContext, item.getY());
            int width = (int) IdentifierUtuils.getIdentifierWidthDimen(mContext, item.getWidth());
            int height = (int) IdentifierUtuils.getIdentifierHeightDimen(mContext, item.getHeight());
            float radius = IdentifierUtuils.getIdentifierWidthDimen(mContext, item.getRadius());

            JsonItemView child = new JsonItemView(mContext);
            child.setBackgroundResource(R.drawable.cd_bg_1);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
            lp.leftMargin = x;
            lp.topMargin = y;
            mFrameLayout.addView(child, lp);
            /* 焦点 */
            if (item.isFocus()) {
                child.setFocusable(true);
                child.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        v.bringToFront();
                        mMainUpView.setFocusView(v, mScaleX, mScaleY);
                        mMainUpView.setUnFocusView(v);
                        mOldView = v;
                    }
                });
            }
            /* 单击事件 */
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OPENLOG.D("v id: " + v.getId());
                }
            });
            /* 鼠标 */
            if (item.isMouse()) {
                child.setClickable(true);
                child.setFocusableInTouchMode(true);
            }
            /* 文本 */
            child.setText(item.getText());
            /*  背景图片 */
            /* 圆角 */
            child.setDrawShape(true);
            child.setRadius(radius);
        }
    }

    private void initAllViews() {
        mFrameLayout = (FrameLayout) findViewById(R.id.main_lay);
        mMainUpView = (MainUpView) findViewById(R.id.mainup_view);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }

}
