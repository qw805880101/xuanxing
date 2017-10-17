package com.xuanxing.tc.game.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.psylife.wrmvplibrary.base.WRBaseLazyFragment;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.api.Api;

import rx.functions.Action1;

/**
 * Created by admin on 2017/8/30.
 */

public abstract class BaseFragment extends WRBaseLazyFragment implements Action1<Throwable> {

    public Api mXuanXingApi = RxService.createApi(Api.class);

    public RxManager mRxManager = new RxManager();

    @Override
    public View getTitleView() {
        return null;
    }

    /**
     * 显示错误日志
     * @param code
     * @param msg
     */
    public void toastMessage(String code, String msg){
        ToastUtils.showToast(this.getContext(), msg);
    }

    @Override
    public void call(Throwable throwable) {
        System.out.println(throwable.getMessage());
        ToastUtils.showToast(this.getContext(), "网络错误");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }
}
