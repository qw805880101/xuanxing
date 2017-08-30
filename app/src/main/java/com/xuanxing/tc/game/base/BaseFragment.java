package com.xuanxing.tc.game.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.base.WRBaseFragment;

/**
 * Created by admin on 2017/8/30.
 */

public abstract class BaseFragment extends WRBaseFragment {
    public RxManager mRxManager = new RxManager();

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }
}
