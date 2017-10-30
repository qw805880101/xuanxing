package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/10/16.
 */

public class GameAnchorList {

    private GameInfo gameInfo;
    private List<AnchorInfo> anchorList;

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public List<AnchorInfo> getAnchorList() {
        return anchorList;
    }

    public void setAnchorList(List<AnchorInfo> anchorList) {
        this.anchorList = anchorList;
    }
}
