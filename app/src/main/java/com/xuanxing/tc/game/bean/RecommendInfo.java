package com.xuanxing.tc.game.bean;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendInfo {

    private int thumbnail; //缩略图
    private int type; //0 纯文本， 1 图片， 2 视频
    private String heading;
    private String content;
    private String author;
    private String time;
    private String total;

    public RecommendInfo() {
    }

    public RecommendInfo(int thumbnail, int type, String heading, String content, String author, String time, String total) {
        this.thumbnail = thumbnail;
        this.type = type;
        this.heading = heading;
        this.content = content;
        this.author = author;
        this.time = time;
        this.total = total;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
