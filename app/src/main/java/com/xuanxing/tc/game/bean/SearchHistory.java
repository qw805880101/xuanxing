package com.xuanxing.tc.game.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 搜索历史
 *
 * Created by admin on 2017/9/15.
 */

public class SearchHistory {
    /**
     * 搜索历史内容
     */
    private String historySearchContent;

    /**
     * 条数
     */
    private int num;

    /**
     * 搜索热门内容
     */
    private String hotSearchContent;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getHistorySearchContent() {
        return historySearchContent;
    }

    public void setHistorySearchContent(String historySearchContent) {
        this.historySearchContent = historySearchContent;
    }

    public String getHotSearchContent() {
        return hotSearchContent;
    }

    public void setHotSearchContent(String hotSearchContent) {
        this.hotSearchContent = hotSearchContent;
    }
}
