package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/10/17.
 */

public class News {

    private NewsList newsList;

    private NewsList newestNewsList;

    private VideoList videoList;

    public VideoList getVideoList() {
        return videoList;
    }

    public void setVideoList(VideoList videoList) {
        this.videoList = videoList;
    }

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
