package com.xuanxing.tc.game;

import com.psylife.wrmvplibrary.WRCoreApp;
import com.xuanxing.tc.game.bean.HomeFragmentInfo;

/**
 * Created by admin on 2017/8/30.
 */

public class MyApplication extends WRCoreApp {

    public static HomeFragmentInfo homeFragmentInfo;

    @Override
    public String setBaseUrl() {
        return "http://www.baidu.com";
    }
}
