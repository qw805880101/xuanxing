package com.xuanxing.tc.game.bean;

/**
 * 搜索历史
 *
 * Created by admin on 2017/9/15.
 */

public class SearchHistory {

    public static final int HOT = 0;
    public static final int HISTORY = 1;

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

    private int type;

    public SearchHistory() {
    }

    public SearchHistory(int num, String historySearchContent) {
        this.num = num;
        this.historySearchContent = historySearchContent;
    }

    public SearchHistory(String historySearchContent, int num, String hotSearchContent) {
        this.historySearchContent = historySearchContent;
        this.num = num;
        this.hotSearchContent = hotSearchContent;
    }

    public SearchHistory(String historySearchContent, int num, int type) {
        this.historySearchContent = historySearchContent;
        this.num = num;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
