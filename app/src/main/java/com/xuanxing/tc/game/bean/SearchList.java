package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/10/17.
 */

public class SearchList {

    private List<SearchHistory> mSearchHistories;
    private NewsList newsList;
    private VideoList videoList;

    public NewsList getNewsList() {
        return newsList;
    }

    public void setNewsList(NewsList newsList) {
        this.newsList = newsList;
    }

    public VideoList getVideoList() {
        return videoList;
    }

    public void setVideoList(VideoList videoList) {
        this.videoList = videoList;
    }

    public List<SearchHistory> getSearchHistories() {
        return mSearchHistories;
    }

    public void setSearchHistories(List<SearchHistory> searchHistories) {
        mSearchHistories = searchHistories;
    }
}
