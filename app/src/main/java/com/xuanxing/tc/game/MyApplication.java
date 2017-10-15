package com.xuanxing.tc.game;

import com.psylife.wrmvplibrary.WRCoreApp;
import com.xuanxing.tc.game.bean.LoginInfo;

/**
 * Created by admin on 2017/8/30.
 */

public class MyApplication extends WRCoreApp {

    public static final String USER_INFO = "userInfo";

    public static LoginInfo loginInfo;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public String setBaseUrl() {
        return "http://120.27.18.127:10086/gamehelp_admin/";
    }
}
