package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.RecommendInfo;

import java.util.List;

/**
 * 消息列表
 *
 * Created by admin on 2017/8/31.
 */

public class MessageAdapter extends BaseQuickAdapter<RecommendInfo, BaseViewHolder> {

    private Context context;

    private List<RecommendInfo> data;

    public MessageAdapter(Context context, @Nullable List<RecommendInfo> data) {
        super(R.layout.item_message, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo item) {
    }
}