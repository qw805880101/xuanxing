package com.xuanxing.tc.game.base;


import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.base.WRBaseActivity;

/**
 * Created by admin on 2017/8/23.
 */

public abstract class BaseActivity extends WRBaseActivity {

    public RxManager mRxManager = new RxManager();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }
}
