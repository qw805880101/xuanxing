package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorActivity;
import com.xuanxing.tc.game.bean.FansInfo;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;


/**
 * 粉丝adapter
 *
 * Created by admin on 2017/8/31.
 */

public class FansAdapter extends BaseQuickAdapter<FansInfo, BaseViewHolder> {

    private Context context;

    private MoreAnchorAdapter.FollowOnclick mFollowOnclick;

    private boolean isAnchor = false;

    public FansAdapter(@Nullable List<FansInfo> data) {
        super(R.layout.item_fans, data);
        this.context = context;
    }

    public void isAnchor(boolean isAnchor) {
        this.isAnchor = isAnchor;
    }

    public void setFollowOnclick(MoreAnchorAdapter.FollowOnclick mFollowOnclick) {
        this.mFollowOnclick = mFollowOnclick;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FansInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getNickName());

        Button bt = helper.getView(R.id.bt_anchor_follow);

        if (MyApplication.loginInfo.getMemberInfo().getMemberId().equals("" + item.getMemberId())) {
            bt.setVisibility(View.GONE);
        }

        if (item.getIsAttention() == 0) { //未关注
            bt.setText("关注");
            bt.setSelected(false);
//            bt.setEnabled(true);
        } else if (item.getIsAttention() == 1) { //已关注
            bt.setText("已关注");
            bt.setSelected(true);
//            bt.setEnabled(false);
        }
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowOnclick.follow(v, helper.getLayoutPosition());
            }
        });

        helper.setText(R.id.txt_content, "" + item.getIntro());

        RoundedImageView circleImageView = helper.getView(R.id.iv_head);
        XUtils.loadHeadIcon(mContext, item.getHeadIcon(), circleImageView);

        helper.getView(R.id.content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnchor) {
                    Intent intent = new Intent(mContext, AnchorActivity.class);
                    intent.putExtra("anchorId", item.getMemberId());
                    mContext.startActivity(intent);
                }
            }
        });

    }
}
