package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/10/17.
 */

public class SearchList {

    private List<SearchHistory> mSearchHistories;
    private NewsList newsList;
    private VedioList videoList;

    public NewsList getNewsList() {
        return newsList;
    }

    public void setNewsList(NewsList newsList) {
        this.newsList = newsList;
    }

    public VedioList getVideoList() {
        return videoList;
    }

    public void setVideoList(VedioList videoList) {
        this.videoList = videoList;
    }

    public List<SearchHistory> getSearchHistories() {
        return mSearchHistories;
    }

    public void setSearchHistories(List<SearchHistory> searchHistories) {
        mSearchHistories = searchHistories;
    }
}
