package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.NewsInfo;

import java.text.DecimalFormat;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2017/8/31.
 */

public class AnchorVideoAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    public static final int SHARE = 2;

    private MyOnClickListener mOnClickListener;

    public AnchorVideoAdapter(@Nullable List<NewsInfo> data) {
        super(R.layout.item_anchor_video, data);
    }

    public void setOnClickListener(MyOnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public BaseViewHolder getBaseViewHolder() {
        return this.getBaseViewHolder();
    }

    @Override
    protected void convert(final BaseViewHolder helper, NewsInfo item) {
        helper.setText(R.id.txt_video_heading, item.getTitle());
        if (item.getPlayNum() > 1000) {
            DecimalFormat df = new DecimalFormat("######0.00");
            double num = item.getPlayNum() / 1000;
            helper.setText(R.id.txt_video_total, df.format(num) + "K");
        } else {
            helper.setText(R.id.txt_video_total, "" + item.getPlayNum());
        }

        /**
         * 分享
         */
        helper.setOnClickListener(R.id.txt_video_forward, new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.setOnClick(view, SHARE, helper.getLayoutPosition());
            }
        });

        JCVideoPlayerStandard jcVideoPlayerStandard = helper.getView(R.id.video_player);
        jcVideoPlayerStandard.setUp(item.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//        jcVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    public interface MyOnClickListener {
        void setOnClick(View view, int type, int pos);
    }

}
