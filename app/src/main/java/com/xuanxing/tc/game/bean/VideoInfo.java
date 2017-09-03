package com.xuanxing.tc.game.bean;

/**
 * Created by admin on 2017/8/31.
 */

public class VideoInfo {

    private int headImage;
    private String name;
    private String time;
    private int image;
    private String heading;
    private String total;

    public VideoInfo(int headImage, String name, String time, int image, String heading, String total) {
        this.headImage = headImage;
        this.name = name;
        this.time = time;
        this.image = image;
        this.heading = heading;
        this.total = total;
    }

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
