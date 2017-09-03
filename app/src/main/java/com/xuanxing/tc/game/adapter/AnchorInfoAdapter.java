package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * 主播详细列表
 *
 * Created by admin on 2017/8/31.
 */

public class AnchorInfoAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {
    public AnchorInfoAdapter(@Nullable List<RecommendInfo> data) {
        super(R.layout.item_anchor_info, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {

    }
}
