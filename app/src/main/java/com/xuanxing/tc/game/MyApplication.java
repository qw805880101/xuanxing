package com.xuanxing.tc.game;

import android.os.Build;
import android.os.StrictMode;

import com.psylife.wrmvplibrary.WRCoreApp;
import com.xuanxing.tc.game.bean.LoginInfo;

/**
 * Created by admin on 2017/8/30.
 */

public class MyApplication extends WRCoreApp {

    public static final String USER_INFO = "userInfo";
    public static final String SEARCH_HISTORY = "searchHistory";

    public static LoginInfo loginInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    @Override
    public String setBaseUrl() {
        return "http://120.27.18.127:10086/gamehelp_admin/";
    }
}
