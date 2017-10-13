package com.xuanxing.tc.game.base;


import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.base.WRBaseActivity;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.api.Api;

import java.util.Observable;

import rx.functions.Action1;

/**
 * Created by admin on 2017/8/23.
 */

public abstract class BaseActivity extends WRBaseActivity implements Action1<Throwable> {

    public Api mXuanXingApi = RxService.createApi(Api.class);

    public RxManager mRxManager = new RxManager();

    /**
     * 显示错误日志
     * @param code
     * @param msg
     */
    public void toastMessage(String code, String msg){
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void call(Throwable throwable) {
        System.out.println(throwable.getMessage());
        ToastUtils.showToast(this, "网络错误");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }
}
