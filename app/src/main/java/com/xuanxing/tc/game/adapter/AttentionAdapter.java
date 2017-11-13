package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.AnchorInfo;
import com.xuanxing.tc.game.bean.AttentionInfo;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 关注
 * <p>
 * Created by admin on 2017/8/31.
 */

public class AttentionAdapter extends BaseQuickAdapter<AttentionInfo, BaseViewHolder> {

    public AttentionAdapter(@Nullable List<AttentionInfo> data) {
        super(R.layout.item_fans, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, AttentionInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getNickName());

        Button bt = helper.getView(R.id.bt_anchor_follow);
        if (item.getIsAttention().equals("0")) { //未关注
            bt.setText("关注");
            bt.setSelected(false);
            bt.setEnabled(true);
        } else if (item.getIsAttention().equals("1")) { //已关注
            bt.setText("已关注");
            bt.setSelected(true);
            bt.setEnabled(false);
        }

        helper.setText(R.id.txt_content, "" + item.getIntro());

        CircleImageView circleImageView = helper.getView(R.id.iv_head);
        XUtils.loadHeadIcon(mContext, item.getHeadIcon(), circleImageView);
    }
}
