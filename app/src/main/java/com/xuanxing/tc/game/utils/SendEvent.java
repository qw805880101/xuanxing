package com.xuanxing.tc.game.utils;

/**
 * Created by tianchao on 2017/10/14.
 */

public class SendEvent {

    protected int code;
    protected int position;
    protected String key;
    protected String value;

    public SendEvent() {
    }

    public SendEvent(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public SendEvent(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
