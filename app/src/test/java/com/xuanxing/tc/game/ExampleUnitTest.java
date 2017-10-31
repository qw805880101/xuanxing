package com.xuanxing.tc.game;

import com.alibaba.fastjson.JSON;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.MemberInfo;

import org.junit.Test;

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
}