package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorActivity;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * 主播详细列表
 * <p>
 * Created by admin on 2017/8/31.
 */

public class FindAnchorAdapter extends BaseQuickAdapter<AnchorInfo, BaseViewHolder> {

    private Context context;

    public FindAnchorAdapter(Context context, @Nullable List<AnchorInfo> data) {
        super(R.layout.item_anchor_info, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final AnchorInfo item) {

        helper.setText(R.id.txt_anchor_name, item.getAnchorName());
        XUtils.loadHeadIcon(mContext, item.getAnchorPic(), (RoundedImageView) helper.getView(R.id.iv_anchor_head));

        helper.getView(R.id.lin_anchor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.loginInfo == null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(context, AnchorActivity.class);
                intent.putExtra("anchorId", item.getAnchorId());
                context.startActivity(intent);
            }
        });
    }
}
