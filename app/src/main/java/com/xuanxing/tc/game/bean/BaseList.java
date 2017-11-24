package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by sandlovechao on 2017/10/30.
 */

public class BaseList {

    private AnchorList anchorList;
    private FansList fansList;
    private CollectList collectList;
    private NoticeList noticeList;
    private AttentionList attentionList;
    private MemberInfo otherMemberInfo;
    private LikeGameList selectLikeGameList;
    private List<HotGameList> likeGameList;

    public List<HotGameList> getLikeGameList() {
        return likeGameList;
    }

    public void setLikeGameList(List<HotGameList> likeGameList) {
        this.likeGameList = likeGameList;
    }

    public LikeGameList getSelectLikeGameList() {
        return selectLikeGameList;
    }

    public void setSelectLikeGameList(LikeGameList selectLikeGameList) {
        this.selectLikeGameList = selectLikeGameList;
    }

    public MemberInfo getOtherMemberInfo() {
        return otherMemberInfo;
    }

    public void setOtherMemberInfo(MemberInfo otherMemberInfo) {
        this.otherMemberInfo = otherMemberInfo;
    }

    public AttentionList getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(AttentionList attentionList) {
        this.attentionList = attentionList;
    }

    public NoticeList getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(NoticeList noticeList) {
        this.noticeList = noticeList;
    }

    public CollectList getCollectList() {
        return collectList;
    }

    public void setCollectList(CollectList collectList) {
        this.collectList = collectList;
    }

    public FansList getFansList() {
        return fansList;
    }

    public void setFansList(FansList fansList) {
        this.fansList = fansList;
    }

    public AnchorList getAnchorList() {
        return anchorList;
    }

    public void setAnchorList(AnchorList anchorList) {
        this.anchorList = anchorList;
    }
}
