package com.open.demo.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * JSON网络加载布局DEMO.(高危DEMO，请勿模仿)
 * Created by hailongqiu on 2016/9/4.
 */
public class BaseNetWorkActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Object> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(0, null, this);
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
