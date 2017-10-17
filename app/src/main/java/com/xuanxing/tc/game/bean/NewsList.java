package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/10/17.
 */

public class NewsList {
    private int totalCount;
    private List<NewsInfo> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<NewsInfo> getItems() {
        return items;
    }

    public void setItems(List<NewsInfo> items) {
        this.items = items;
    }
}
