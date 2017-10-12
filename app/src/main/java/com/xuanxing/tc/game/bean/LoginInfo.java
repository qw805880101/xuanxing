package com.xuanxing.tc.game.bean;

import java.io.Serializable;

/**
 * 登录信息
 *
 * Created by tianchao on 2017/10/12.
 */

public class LoginInfo implements Serializable {

    private String p_token; //App请求凭证
    private MemberInfo mMemberInfo; //用户详情
    private String attentionNum; //关注数量
    private String fansNum; //粉丝数量
    private String collectNum; //收藏数量

    public String getP_token() {
        return p_token;
    }

    public void setP_token(String p_token) {
        this.p_token = p_token;
    }

    public MemberInfo getMemberInfo() {
        return mMemberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        mMemberInfo = memberInfo;
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
