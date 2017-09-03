package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.bean.VideoInfo;
import com.xuanxing.tc.game.utils.VideoListItem;

import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class VideoAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    private Context context;

    private final List<VideoListItem> mList; // 视频项列表

    public VideoAdapter(Context context, @Nullable List<VideoInfo> data, List<VideoListItem> mList) {
        super(R.layout.item_video, data);
        this.context = context;
        this.mList = mList;
    }

    public BaseViewHolder getBaseViewHolder(){
        return this.getBaseViewHolder();
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        try {


        } catch (Exception e){
            System.out.println("出错啦");
            e.printStackTrace();
        }

//        helper.setText(R.id.txt_heading, item.getHeading())
//        .setText(R.id.txt_author_time, item.getAuthor() + "    " + item.getTime());
//
//        if (item.getType() == 0){ //纯文本
//            helper.setVisible(R.id.iv_recommend, false).
//                    setVisible(R.id.iv_video_icon, false).
//                    setVisible(R.id.item_lin, false).
//                    setVisible(R.id.txt_content, true).
//                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
//                    setText(R.id.txt_content, item.getContent());
//
//        }
//
//        if (item.getType() == 1){ //带图片
//            helper.setVisible(R.id.iv_recommend, true).
//                    setVisible(R.id.iv_video_icon, false).
//                    setVisible(R.id.item_lin, true).
//                    setVisible(R.id.txt_content, false).
//                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
//                    setText(R.id.txt_content, item.getContent());
//        }
//
//        if (item.getType() == 2){ //带视频
//            helper.setVisible(R.id.iv_recommend, true).
//                    setVisible(R.id.iv_video_icon, true).
//                    setVisible(R.id.item_lin, true).
//                    setVisible(R.id.txt_content, false).
//                    setImageResource(R.id.iv_total, R.mipmap.bofangshu).
//                    setText(R.id.txt_content, item.getContent());
//        }

    }

    // 返回播放器
    public VideoPlayerView getVpvPlayer() {
        return this.getBaseViewHolder().getView(R.id.item_video_vpv_player);
    }

}
