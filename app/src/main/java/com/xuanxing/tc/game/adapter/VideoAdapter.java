package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.utils.DateUtils;
import com.xuanxing.tc.game.utils.XUtils;

import java.text.DecimalFormat;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2017/8/31.
 */

public class VideoAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    public static final int CANCER_FOLLOW = 0;

    public static final int FOLLOW = 1;

    public static final int SHARE = 2;

    private Context context;

    private MyOnClickListener mOnClickListener;

    public VideoAdapter(Context context, @Nullable List<NewsInfo> data) {
        super(R.layout.item_video, data);
        this.context = context;
    }

    public void setOnClickListener(MyOnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public BaseViewHolder getBaseViewHolder() {
        return this.getBaseViewHolder();
    }

    @Override
    protected void convert(final BaseViewHolder helper, NewsInfo item) {
        RoundedImageView circleImageView = helper.getView(R.id.iv_video_head);
        //TODO 添加照片
        XUtils.loadHeadIcon(mContext, item.getTopicPic(), circleImageView);
        helper.setText(R.id.bt_video_follow, item.getIsAttention() == 0 ? "关注" : "取消关注");
        helper.setText(R.id.txt_video_name, item.getMemberName());
        try {
            String dateStr = DateUtils.getDate(item.getCreateTimeStr());
            if (DateUtils.IsToday(dateStr)) {
                helper.setText(R.id.txt_video_time, "今天" + dateStr.substring(11, 12) + "点");
            } else if (DateUtils.IsYesterday(dateStr)) {
                helper.setText(R.id.txt_video_time, "昨天" + dateStr.substring(11, 12) + "点");
            } else {
                helper.setText(R.id.txt_video_time, dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.setText(R.id.txt_video_heading, item.getTitle());
        if (item.getPlayNum() > 1000) {
            DecimalFormat df = new DecimalFormat("######0.00");
            double num = item.getPlayNum() / 1000;
            helper.setText(R.id.txt_video_total, df.format(num) + "K");
        } else {
            helper.setText(R.id.txt_video_total, "" + item.getPlayNum());
        }

        /**
         * 关注
         */
        final Button bt = helper.getView(R.id.bt_video_follow);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bt.getText().toString().equals("关注"))
                    mOnClickListener.setOnClick(view, FOLLOW, helper.getLayoutPosition());
                if (bt.getText().toString().equals("取消关注"))
                    mOnClickListener.setOnClick(view, CANCER_FOLLOW, helper.getLayoutPosition());
            }
        });

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
