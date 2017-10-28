package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/10/17.
 */

public class News {

    private NewsList newsList;

    private NewsList newestNewsList;

    public NewsList getNewestNewsList() {
        return newestNewsList;
    }

    public void setNewestNewsList(NewsList newestNewsList) {
        this.newestNewsList = newestNewsList;
    }

    public NewsList getNewsList() {
        return newsList;
    }

    public void setNewsList(NewsList newsList) {
        this.newsList = newsList;
    }
}
