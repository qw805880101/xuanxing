package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorActivity;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * 主播详细列表
 *
 * Created by admin on 2017/8/31.
 */

public class AnchorInfoAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {

    private Context context;

    public AnchorInfoAdapter(Context context, @Nullable List<RecommendInfo> data) {
        super(R.layout.item_anchor_info, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {
        helper.getView(R.id.lin_anchor).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnchorActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
