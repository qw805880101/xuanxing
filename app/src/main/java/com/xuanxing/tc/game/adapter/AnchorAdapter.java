package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * 主播列表
 *
 * Created by admin on 2017/8/31.
 */

public class AnchorAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {

    private Context context;

    private List<RecommendInfo> data;

    public AnchorAdapter(Context context, @Nullable List<RecommendInfo> data) {
        super(R.layout.item_anchor, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_anchor_info);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AnchorInfoAdapter(context, data));
    }
}
