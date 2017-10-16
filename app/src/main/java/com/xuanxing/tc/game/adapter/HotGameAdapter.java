package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.List;

/**
 * Created by tianchao on 2017/10/16.
 */

public class HotGameAdapter extends BaseQuickAdapter<HotGameList, BaseViewHolder> {
    private Context context;

    public HotGameAdapter(Context context, List<HotGameList> list) {
        super(R.layout.item_hot_game, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotGameList item) {
        helper.setText(R.id.txt_game_name_01, item.getCategoryName());

        Glide.with(mContext).load(item.getCategoryPic()).into((ImageView) helper.getView(R.id.iv_game_photo_01));
    }
}