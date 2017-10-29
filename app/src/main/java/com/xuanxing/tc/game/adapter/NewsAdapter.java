package com.xuanxing.tc.game.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;

import java.util.List;

/**
 * 评论列表
 *
 * Created by tianchao on 2017/10/16.
 */

public class NewsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<String> list;
    public NewsAdapter(Context context, List<String> list) {
        super(R.layout.item_recommend, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {

    }
}
