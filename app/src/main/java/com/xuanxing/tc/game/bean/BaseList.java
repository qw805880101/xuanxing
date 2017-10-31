package com.xuanxing.tc.game.bean;

/**
 * Created by sandlovechao on 2017/10/30.
 */

public class BaseList {

    private AnchorList anchorList;
    private FansList fansList;

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
