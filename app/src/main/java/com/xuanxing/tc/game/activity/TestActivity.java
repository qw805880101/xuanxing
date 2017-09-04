package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by admin on 2017/9/3.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.test_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        webView.loadData();


    }

    @Override
    public void initdata() {

    }
}
