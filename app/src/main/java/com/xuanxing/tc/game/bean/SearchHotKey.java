package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/10/16.
 */

public class SearchHotKey {

    /**
     *  "id": 1,
     "keyWord": "测试搜词",
     "orderNum": 1
     */

    private int id;
    private String keyWord;
    private String orderNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
