package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/11/13.
 */

public class AttentionInfo {

    /**
     *  {
     "memberId": 2,
     "nickName": "150****0914_gh",
     "headIcon": "http://ouhxa0s50.bkt.clouddn.com/dev/headIcon/GX6Tm3pHQm1509198320388.png\"",
     "intro": "我最厉害，哈哈",
     "isAttention": "1",
     "attentionTimeStr": 1509541052000
     }
     */

    private int memberId;
    private String nickName;
    private String headIcon;
    private String intro;
    private String isAttention;
    private String attentionTimeStr;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public String getAttentionTimeStr() {
        return attentionTimeStr;
    }

    public void setAttentionTimeStr(String attentionTimeStr) {
        this.attentionTimeStr = attentionTimeStr;
    }
}
