package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * 粉丝adapter
 *
 * Created by admin on 2017/8/31.
 */

public class FansAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {
    public FansAdapter(@Nullable List<RecommendInfo> data) {
        super(R.layout.item_fans, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {

    }
}
