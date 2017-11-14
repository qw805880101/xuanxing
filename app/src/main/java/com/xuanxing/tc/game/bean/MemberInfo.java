package com.xuanxing.tc.game.bean;

import java.io.Serializable;

/**
 * 用户信息
 * Created by tianchao on 2017/10/12.
 */

public class MemberInfo implements Serializable {

    private static final long serialVersionUID = 3888947993155795928L;

    private String nickName; // 用户名称
    private String memberId; // 用户ID
    private String birthdayStr; // 用户生日
    private String sourceType; // 会员来源类型 1是注册，2是爬来的会员
    private String mobile; // 手机号
    private String sex; // 用户性别
    private String headIcon; // 用户头像
    private String status; // 用户级别
    private String intro; // 用户简介
    private String createTimeStr; // 用户创建时间
    private int attentionNum; //关注数
    private int fansNum; //粉丝数

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
