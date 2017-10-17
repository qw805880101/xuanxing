package com.xuanxing.tc.game.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.alibaba.fastjson.JSON;
import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.utils.SpUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.api.Api;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.SearchHistory;
import com.xuanxing.tc.game.bean.SearchList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import rx.Observable;
import rx.functions.Action1;

import static com.xuanxing.tc.game.MyApplication.SEARCH_HISTORY;
import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * Created by admin on 2017/9/15.
 */

public class XUtils {

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void modUserInfo(Context context, LoginInfo loginInfo) {
        SpUtils.putString(context, USER_INFO, JSON.toJSONString(loginInfo));
    }

    public static void modUserInfo(Context context, Map<String, String> map) {
        for (Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("nickname") && !entry.getValue().equals("")) {
                MyApplication.loginInfo.getMemberInfo().setNickName(entry.getValue());
            }
            if (entry.getKey().equals("birthday") && !entry.getValue().equals("")) {
                MyApplication.loginInfo.getMemberInfo().setBirthdayStr(entry.getValue());
            }
            if (entry.getKey().equals("alipay") && !entry.getValue().equals("")) {
//                MyApplication.loginInfo.getMemberInfo().setNickName(entry.getValue());
            }
            if (entry.getKey().equals("sex") && !entry.getValue().equals("")) {
                MyApplication.loginInfo.getMemberInfo().setSex(entry.getValue());
            }
            if (entry.getKey().equals("headicon") && !entry.getValue().equals("")) {
                MyApplication.loginInfo.getMemberInfo().setHeadIcon(entry.getValue());
            }
            if (entry.getKey().equals("intro")) {
                MyApplication.loginInfo.getMemberInfo().setIntro(entry.getValue());
            }
            if (entry.getKey().equals("followNum") && !entry.getValue().equals("")) {
                MyApplication.loginInfo.setAttentionNum(entry.getValue());
            }
        }
        SpUtils.putString(context, USER_INFO, JSON.toJSONString(MyApplication.loginInfo));
    }

    public static void modName(final String nickname, String birthday, String alipay, String sex, String headicon,
                               String intro, Api api, RxManager manager, Action1<BaseBean> action1, Action1<Throwable> action2) {
        Observable<BaseBean> modUserInfo = api.modUserInfo(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(), nickname,
                birthday, alipay, sex, headicon, intro).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        manager.add(modUserInfo.subscribe(action1, action2));
    }

    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 存储搜索历史
     *
     * @param content
     */
    public static boolean setHistorySearch(Context context, String content) {
        try {
            String historyStr = SpUtils.getString(context, SEARCH_HISTORY);
            SearchList historys = new SearchList();
            List<SearchHistory> list = new ArrayList<>();
            if (!historyStr.equals("")) {
                historys = JSON.parseObject(historyStr, SearchList.class);
                list = historys.getSearchHistories();
            }
            for (SearchHistory s : list) {
                if (s.getHistorySearchContent().equals(content)) {
                    return false;
                }
            }
            int num = list.size() + 1;
            list.add(new SearchHistory(num, content));
            historys.setSearchHistories(list);
            SpUtils.putString(context, SEARCH_HISTORY, JSON.toJSONString(historys));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static SearchList getHistorySearch(Context context) {
        String historyStr = SpUtils.getString(context, SEARCH_HISTORY);
        SearchList historys = new SearchList();
        if (!historyStr.equals("")) {
            historys = JSON.parseObject(historyStr, SearchList.class);
        }
        return historys;
    }

    public static void clearHistoryList(Context context) {
        SpUtils.remove(context, SEARCH_HISTORY);
    }
}
