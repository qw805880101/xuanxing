package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.MoreAnchorAdapter.FollowOnclick;
import com.xuanxing.tc.game.bean.AttentionInfo;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * 关注
 * <p>
 * Created by admin on 2017/8/31.
 */

public class AttentionAdapter extends BaseQuickAdapter<AttentionInfo, BaseViewHolder> {

    private FollowOnclick mFollowOnclick;

    public AttentionAdapter(@Nullable List<AttentionInfo> data) {
        super(R.layout.item_fans, data);
    }

    public void setFollowOnclick(FollowOnclick mFollowOnclick) {
        this.mFollowOnclick = mFollowOnclick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AttentionInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getNickName());

        Button bt = helper.getView(R.id.bt_anchor_follow);

        if (MyApplication.loginInfo.getMemberInfo().getMemberId().equals("" + item.getMemberId())) {
            bt.setVisibility(View.GONE);
        }

        if (item.getIsAttention().equals("0")) { //未关注
            bt.setText("关注");
            bt.setSelected(false);
//            bt.setEnabled(true);
        } else if (item.getIsAttention().equals("1")) { //已关注
            bt.setText("已关注");
            bt.setSelected(true);
//            bt.setEnabled(false);
        }

        helper.setText(R.id.txt_content, "" + item.getIntro());

        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mFollowOnclick.follow(view, helper.getLayoutPosition());
            }
        });

        RoundedImageView circleImageView = helper.getView(R.id.iv_head);
        XUtils.loadHeadIcon(mContext, item.getHeadIcon(), circleImageView);
    }
}
