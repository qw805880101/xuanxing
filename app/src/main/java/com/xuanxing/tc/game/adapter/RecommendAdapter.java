package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {
    public RecommendAdapter(@Nullable List<RecommendInfo> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {

        helper.setText(R.id.txt_heading, item.getHeading())
        .setText(R.id.txt_author_time, item.getAuthor() + "    " + item.getTime());

        if (item.getType() == 0){ //纯文本
            helper.setVisible(R.id.iv_recommend, false).
                    setVisible(R.id.iv_video_icon, false).
                    setVisible(R.id.item_lin, false).
                    setVisible(R.id.txt_content, true).
                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
                    setText(R.id.txt_content, item.getContent());

        }

        if (item.getType() == 1){ //带图片
            helper.setVisible(R.id.iv_recommend, true).
                    setVisible(R.id.iv_video_icon, false).
                    setVisible(R.id.item_lin, true).
                    setVisible(R.id.txt_content, false).
                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
                    setText(R.id.txt_content, item.getContent());
        }

        if (item.getType() == 2){ //带视频
            helper.setVisible(R.id.iv_recommend, true).
                    setVisible(R.id.iv_video_icon, true).
                    setVisible(R.id.item_lin, true).
                    setVisible(R.id.txt_content, false).
                    setImageResource(R.id.iv_total, R.mipmap.bofangshu).
                    setText(R.id.txt_content, item.getContent());
        }

    }
}
