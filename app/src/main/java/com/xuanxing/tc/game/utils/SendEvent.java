package com.xuanxing.tc.game.utils;

/**
 * Created by tianchao on 2017/10/14.
 */

public class SendEvent {

    protected String key;
    protected String value;

    public SendEvent(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
