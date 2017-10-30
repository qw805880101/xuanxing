package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主播详细列表
 *
 * Created by admin on 2017/8/31.
 */

public class FindAnchorAdapter extends BaseQuickAdapter<AnchorInfo, BaseViewHolder> {

    private Context context;

    public FindAnchorAdapter(Context context, @Nullable List<AnchorInfo> data) {
        super(R.layout.item_anchor_info, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getAnchorName());
        Glide.with(mContext).load(item.getAnchorPic()).into((CircleImageView) helper.getView(R.id.iv_anchor_head));

        helper.getView(R.id.lin_anchor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnchorActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
