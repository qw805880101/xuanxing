package com.xuanxing.tc.game.bean;

/**
 * 游戏信息
 *
 * Created by tianchao on 2017/10/16.
 */

public class GameInfo {

    /**"gameCategoryId": 10,
     "gameCategoryCode": "CT1000010",
     "gameCategoryName": "王者荣耀",
     "gameCategoryPic": null*/

    private int gameCategoryId;
    private String gameCategoryCode;
    private String gameCategoryName;
    private String gameCategoryPic;

    public int getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(int gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public String getGameCategoryCode() {
        return gameCategoryCode;
    }

    public void setGameCategoryCode(String gameCategoryCode) {
        this.gameCategoryCode = gameCategoryCode;
    }

    public String getGameCategoryName() {
        return gameCategoryName;
    }

    public void setGameCategoryName(String gameCategoryName) {
        this.gameCategoryName = gameCategoryName;
    }

    public String getGameCategoryPic() {
        return gameCategoryPic;
    }

    public void setGameCategoryPic(String gameCategoryPic) {
        this.gameCategoryPic = gameCategoryPic;
    }
}
