package com.xuanxing.tc.game.bean;

/**
 * Created by tianchao on 2017/10/12.
 */

public class BaseBeanClass<T> extends BaseBean{

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
