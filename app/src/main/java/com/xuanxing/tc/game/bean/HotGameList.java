package com.xuanxing.tc.game.bean;

import java.io.Serializable;

/**
 * 热门游戏列表
 *
 * Created by tianchao on 2017/10/16.
 */

public class HotGameList implements Serializable {

    private static final long serialVersionUID = 3220847594181341319L;
    /**
     * "id": 1,
     "categoryName": "王者荣耀",
     "categoryCode": "CT1000010",
     "categoryPic": null,
     "categorySort": 1
     */
    private int id;
    private String categoryName;
    private String categoryCode;
    private String categoryPic;
    private String categorySort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }

    public String getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(String categorySort) {
        this.categorySort = categorySort;
    }
}
