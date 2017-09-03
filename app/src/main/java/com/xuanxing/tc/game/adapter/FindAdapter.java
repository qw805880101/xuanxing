package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class FindAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private RecommendInfo recommendInfo;

    public FindAdapter(Context context, List<String> list) {
        super(R.layout.item_find, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_anchor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new AnchorAdapter(context, RecommendItemUtil.getRecommendItem()));
    }
}
