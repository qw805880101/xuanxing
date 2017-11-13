package com.xuanxing.tc.game;

import com.alibaba.fastjson.JSON;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.MemberInfo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = "{\n" +
                "    \"attentionNum\": \"0\",\n" +
                "    \"collectNum\": \"0\",\n" +
                "    \"fansNum\": \"0\",\n" +
                "    \"p_token\": \"b145b1ca99b35515580473b75743f72d\"\n" +
                "}";

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAttentionNum("123");
        loginInfo.setCollectNum("13");
        loginInfo.setFansNum("123");
        loginInfo.setP_token("123123");
        loginInfo.setMemberInfo(new MemberInfo());

        String ss = JSON.toJSONString(loginInfo);
        System.out.println(ss);
        LoginInfo loginInfos = (LoginInfo) JSON.parseObject(ss, LoginInfo.class);
    }

    @Test
    public void testDate(){
        String s = "";
        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        java.util.Date dt = new Date(Long.parseLong("1504345756000"));
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        System.out.println(sDateTime);
    }
}