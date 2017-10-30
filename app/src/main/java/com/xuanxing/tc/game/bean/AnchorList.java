package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by sandlovechao on 2017/10/30.
 */

public class AnchorList {

    private int totalCount;
    private List<AnchorInfo> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AnchorInfo> getItems() {
        return items;
    }

    public void setItems(List<AnchorInfo> items) {
        this.items = items;
    }
}
