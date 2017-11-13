package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/11/13.
 */

public class AttentionList {

    private int totalCount;
    private List<AttentionInfo> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AttentionInfo> getItems() {
        return items;
    }

    public void setItems(List<AttentionInfo> items) {
        this.items = items;
    }
}
