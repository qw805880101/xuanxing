package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * 资讯详情
 * Created by Administrator on 2017/10/31.
 */

public class NewsDetailInfo {

    private List<CommentList> commentList;
    private int isAttention;
    private int isCollect;
    private int detailInfo;
    private int collectNum;
    private int commentNum;
    private List<NewsInfo> relateGameNewsList;

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public List<CommentList> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentList> commentList) {
        this.commentList = commentList;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(int detailInfo) {
        this.detailInfo = detailInfo;
    }

    public List<NewsInfo> getRelateGameNewsList() {
        return relateGameNewsList;
    }

    public void setRelateGameNewsList(List<NewsInfo> relateGameNewsList) {
        this.relateGameNewsList = relateGameNewsList;
    }
}
