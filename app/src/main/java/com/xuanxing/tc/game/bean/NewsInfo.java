package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/10/13.
 */

public class NewsInfo {

    /**
     id (integer, optional),
     title (string, optional),
     topicPic (string, optional),
     topicType (integer, optional),
     memberName (string, optional),
     memberId (integer, optional),
     categoryCode (string, optional),
     videoUrl (string, optional),
     shortContent (string, optional),
     playNum (integer, optional),
     status (integer, optional),
     createTime (string, optional)
     isAttention
     */

    private int id; //id
    private String title; //标题
    private String topicPic; //图片
    private int topicType; //类型
    private String memberName; //发布人
    private int memberId; //
    private String categoryCode; //
    private String videoUrl; //视频地址
    private String shortContent; //内容简介
    private int playNum; //
    private int status; //
    private String createTimeStr; //创建时间
    private int isAttention; //是否关注

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicPic() {
        return topicPic;
    }

    public void setTopicPic(String topicPic) {
        this.topicPic = topicPic;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
