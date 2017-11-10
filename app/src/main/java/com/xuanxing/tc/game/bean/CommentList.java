package com.xuanxing.tc.game.bean;

/**
 * Created by Administrator on 2017/10/31.
 */

public class CommentList {

    /**
     *  {
     "id": 4,
     "topicId": 58,
     "topicType": 1,
     "content": "xnnnn",
     "fromMid": 8,
     "fromNickName": "我现在",
     "fromHeadIcon": "http://ouhxa0s50.bkt.clouddn.com/dev/headIcon/iEBpBeNNtG1509031085237.png\"",
     "toMid": 0,
     "toNickName": null,
     "toHeadIcon": null,
     "parentId": 0,
     "status": 1,
     "commentTime": "2017-11-02 15:59:15"
     }
     */

    private int id;
    private int topicId;
    private int topicType;
    private String content;
    private int fromMid;
    private String fromNickName;
    private String fromHeadIcon;
    private int toMid;
    private int toNickName;
    private int toHeadIcon;
    private int parentId;
    private int status;
    private String commentTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFromMid() {
        return fromMid;
    }

    public void setFromMid(int fromMid) {
        this.fromMid = fromMid;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getFromHeadIcon() {
        return fromHeadIcon;
    }

    public void setFromHeadIcon(String fromHeadIcon) {
        this.fromHeadIcon = fromHeadIcon;
    }

    public int getToMid() {
        return toMid;
    }

    public void setToMid(int toMid) {
        this.toMid = toMid;
    }

    public int getToNickName() {
        return toNickName;
    }

    public void setToNickName(int toNickName) {
        this.toNickName = toNickName;
    }

    public int getToHeadIcon() {
        return toHeadIcon;
    }

    public void setToHeadIcon(int toHeadIcon) {
        this.toHeadIcon = toHeadIcon;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
