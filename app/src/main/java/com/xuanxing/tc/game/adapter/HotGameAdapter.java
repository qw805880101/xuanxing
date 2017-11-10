package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HotGameNewActivity;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * 热门游戏
 *
 * Created by tianchao on 2017/10/16.
 */

public class HotGameAdapter extends BaseQuickAdapter<HotGameList, BaseViewHolder> {
    private Context context;

    public HotGameAdapter(Context context, List<HotGameList> list) {
        super(R.layout.item_hot_game, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotGameList item) {
        helper.setText(R.id.txt_game_name_01, item.getCategoryName());
        XUtils.loadHeadIcon(mContext, item.getCategoryPic(), (ImageView) helper.getView(R.id.iv_game_photo_01));
        helper.getView(R.id.rl_hot_game).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.loginInfo == null){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(context, HotGameNewActivity.class);
                intent.putExtra("gameInfo", item);
                context.startActivity(intent);
            }
        });
    }
}
