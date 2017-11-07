package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NoticeList {

    private int totalCount;
    private List<NoticeInfo> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<NoticeInfo> getItems() {
        return items;
    }

    public void setItems(List<NoticeInfo> items) {
        this.items = items;
    }
}
