package com.xuanxing.tc.game.bean;

import java.io.Serializable;

/**
 * 登录信息
 * <p>
 * Created by tianchao on 2017/10/12.
 */

public class LoginInfo implements Serializable {

    private static final long serialVersionUID = -4722897279115242284L;

    private String p_token; //App请求凭证
    private MemberInfo memberInfo; //用户详情
    private String attentionNum; //关注数量
    private String fansNum; //粉丝数量
    private String collectNum; //收藏数量
    private String phoneNum;
    private int isFirstLogin; // 1是首次登陆，0不是

    public int getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getP_token() {
        return p_token;
    }

    public void setP_token(String p_token) {
        this.p_token = p_token;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(String attentionNum) {
        this.attentionNum = attentionNum;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }
}
