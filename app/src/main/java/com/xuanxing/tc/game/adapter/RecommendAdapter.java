package com.xuanxing.tc.game.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.activity.NewsDetailsActivity;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {
    public RecommendAdapter(@Nullable List<NewsInfo> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewsInfo item) {

        helper.setText(R.id.txt_heading, item.getTitle())
                .setText(R.id.txt_author_time, item.getMemberName() + "  " + item.getCreateTime());

        if (item.getPlayNum() > 1000) {
            DecimalFormat df = new DecimalFormat("######0.00");
            double num = item.getPlayNum() / 1000;
            helper.setText(R.id.txt_total, df.format(num) + "K");
        } else {
            helper.setText(R.id.txt_total, "" + item.getPlayNum());
        }

        if (item.getTopicPic() != null && !item.getTopicPic().equals("")) { //纯文本
            helper.setVisible(R.id.iv_recommend, false).
                    setVisible(R.id.iv_video_icon, false).
                    setVisible(R.id.item_lin, false).
                    setVisible(R.id.txt_content, true).
                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
                    setText(R.id.txt_content, item.getShortContent());
        } else {
            ImageView imageView = helper.getView(R.id.iv_recommend);
            //TODO 添加照片
            Glide.with(mContext).load(item.getTopicPic()).into(imageView);
            helper.setVisible(R.id.iv_recommend, true).
                    setVisible(R.id.iv_video_icon, false).
                    setVisible(R.id.item_lin, true).
                    setVisible(R.id.txt_content, false).
                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
                    setText(R.id.txt_content, item.getShortContent());
        }

        if (item.getTopicType() == 1) { //带图片
            ImageView imageView = helper.getView(R.id.iv_recommend);
            //TODO 添加照片
            Glide.with(mContext).load(item.getTopicPic()).into(imageView);
            helper.setVisible(R.id.iv_recommend, true).
                    setVisible(R.id.iv_video_icon, false).
                    setVisible(R.id.item_lin, true).
                    setVisible(R.id.txt_content, false).
                    setImageResource(R.id.iv_total, R.mipmap.guankanshu).
                    setText(R.id.txt_content, item.getShortContent());
        }

        if (item.getTopicType() == 2) { //视频
            ImageView imageView = helper.getView(R.id.iv_recommend);
            //TODO 添加照片
            Glide.with(mContext).load(item.getTopicPic()).into(imageView);
            helper.setVisible(R.id.iv_recommend, true).
                    setVisible(R.id.iv_video_icon, true).
                    setVisible(R.id.item_lin, true).
                    setVisible(R.id.txt_content, false).
                    setImageResource(R.id.iv_total, R.mipmap.bofangshu).
                    setText(R.id.txt_content, item.getShortContent());
        }

        helper.getView(R.id.lin_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.loginInfo == null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("newsId", "" + item.getId());
                intent.putExtra("categoryCode", item.getCategoryCode());
                intent.putExtra("newsType", item.getTopicType());
                mContext.startActivity(intent);
            }
        });

    }
}
