package com.xuanxing.tc.game.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改感兴趣游戏
 * Created by tianchao on 2017/10/16.
 */

public class ModInterestList {

    private List<HotGameList> addedData;
    private List<HotGameList> noAddedData;

    public List<HotGameList> getAddedData() {
        return addedData;
    }

    public void setAddedData(List<HotGameList> addedData) {
        this.addedData = addedData;
    }

    public List<HotGameList> getNoAddedData() {
        return noAddedData;
    }

    public void setNoAddedData(List<HotGameList> noAddedData) {
        this.noAddedData = noAddedData;
    }
}
