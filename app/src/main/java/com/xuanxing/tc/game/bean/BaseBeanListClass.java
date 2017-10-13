package com.xuanxing.tc.game.bean;

import java.util.List;

/**
 * Created by tianchao on 2017/10/13.
 */

public class BaseBeanListClass<T> extends BaseBean {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
