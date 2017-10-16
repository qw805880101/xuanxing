package com.xuanxing.tc.game.bean;

/**
 * 主播列表
 *
 * Created by tianchao on 2017/10/16.
 */

public class AnchorList {

    /**
     * "anchorId": 2,
     "anchorName": "150****0914_gh",
     "anchorPic": "http://ouhxa0s50.bkt.clouddn.com/dev/character/info/PEdSZXYfAk1504188680882.png"
     */

    private int anchorId;
    private String anchorName;
    private String anchorPic;

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getAnchorPic() {
        return anchorPic;
    }

    public void setAnchorPic(String anchorPic) {
        this.anchorPic = anchorPic;
    }
}
