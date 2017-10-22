package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/22.
 */

public class GameMore {

    private int totalCount;
    private List<HotGameList> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<HotGameList> getItems() {
        return items;
    }

    public void setItems(List<HotGameList> items) {
        this.items = items;
    }
}
