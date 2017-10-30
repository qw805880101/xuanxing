package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorActivity;
import com.xuanxing.tc.game.activity.PersonalInfoActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 游戏主播更多
 * <p>
 * Created by admin on 2017/8/31.
 */

public class MoreAnchorAdapter extends BaseQuickAdapter<AnchorInfo, BaseViewHolder> {

    private Context context;

    private FollowOnclick mFollowOnclick;

    public MoreAnchorAdapter(Context context, @Nullable List<AnchorInfo> data) {
        super(R.layout.item_fans, data);
        this.context = context;
    }

    public void setFollowOnclick(FollowOnclick mFollowOnclick){
        this.mFollowOnclick = mFollowOnclick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, AnchorInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getAnchorName());

        Button bt = helper.getView(R.id.bt_anchor_follow);
        if (item.getIsAttention() == 0) { //未关注
            bt.setText("关注");
            bt.setSelected(false);
            bt.setEnabled(true);
        } else if (item.getIsAttention() == 1) { //已关注
            bt.setText("已关注");
            bt.setSelected(true);
            bt.setEnabled(false);
        }
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowOnclick.follow(v, helper.getLayoutPosition());
            }
        });

        helper.setText(R.id.txt_content, ""+item.getAnchorId());

        CircleImageView circleImageView = helper.getView(R.id.iv_head);
        Glide.with(context).load(item.getAnchorPic()).crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(circleImageView);
    }

    public interface FollowOnclick{
        void follow(View view, int position);
    }

}
