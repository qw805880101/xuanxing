package com.xuanxing.tc.game.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 搜索分组头部
 *
 * Created by admin on 2017/9/15.
 */

public class SearchHead extends SectionEntity<SearchHistory> {


    private String headName;
    private int headImage;
    private String headType;

    public SearchHead(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SearchHead(SearchHistory searchHistory) {
        super(searchHistory);
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }

    public String getHeadType() {
        return headType;
    }

    public void setHeadType(String headType) {
        this.headType = headType;
    }
}
