package com.xuanxing.tc.game.bean;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NoticeInfo {

    /**
     * "id": 2,
     * "fromMemberId": null,
     * "headIcon": null,
     * "nickName": null,
     * "newsId": null,
     * "noticeType": 0,
     * "userType": 0,
     * "status": 1,
     * "noticeContent": "欢迎您加入游戏帮",
     * "createTimeStr": 1509280567000
     *
     *
     * noticeType 0:系统 1:评论 2 关注 userType 0：系统 1：用户
     */

    private int id;
    private int fromMemberId;
    private int headIcon;
    private int nickName;
    private int newsId;
    private int noticeType;
    private int userType;
    private int status;
    private String noticeContent;
    private String createTimeStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(int fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public int getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(int headIcon) {
        this.headIcon = headIcon;
    }

    public int getNickName() {
        return nickName;
    }

    public void setNickName(int nickName) {
        this.nickName = nickName;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
