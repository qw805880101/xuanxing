package com.xuanxing.tc.game.utils;

import android.support.annotation.DrawableRes;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.xuanxing.tc.game.utils.VideoListItem;

/**
 * Created by Administrator on 2016/7/28.
 */
public class OnlineVideoListItem extends VideoListItem {

    private final String mOnlineUrl; // 资源文件描述

    public OnlineVideoListItem(
            VideoPlayerManager<MetaData> videoPlayerManager,
            String title,
            String imageResource,
            String onlineUrl
    ) {
        super(videoPlayerManager, title, imageResource);

        mOnlineUrl = onlineUrl;
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mOnlineUrl);
    }
}