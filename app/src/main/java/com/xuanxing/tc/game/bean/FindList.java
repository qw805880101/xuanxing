package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * 发现分类列表
 * Created by tianchao on 2017/10/16.
 */

public class FindList {

    private List<GameAnchorList> gameAnchorList;
    private List<HotGameList> hotGameList;

    public List<HotGameList> getHotGameList() {
        return hotGameList;
    }

    public void setHotGameList(List<HotGameList> hotGameList) {
        this.hotGameList = hotGameList;
    }

    public List<GameAnchorList> getGameAnchorList() {
        return gameAnchorList;
    }

    public void setGameAnchorList(List<GameAnchorList> gameAnchorList) {
        this.gameAnchorList = gameAnchorList;
    }
}
