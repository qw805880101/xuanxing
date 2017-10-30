package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorMoreActivity;
import com.xuanxing.tc.game.bean.GameAnchorList;

import java.util.List;

/**
 * 主播列表
 *
 * Created by admin on 2017/8/31.
 */

public class AnchorAdapter extends BaseQuickAdapter<GameAnchorList, BaseViewHolder> {

    private Context context;

    private List<GameAnchorList> data;

    public AnchorAdapter(Context context, @Nullable List<GameAnchorList> data) {
        super(R.layout.item_anchor, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GameAnchorList item) {
        helper.setText(R.id.txt_game_name, item.getGameInfo().getGameCategoryName());
        RecyclerView recyclerView = helper.getView(R.id.rv_anchor_info);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new FindAnchorAdapter(context, data.get(helper.getLayoutPosition()).getAnchorList()));

        helper.getView(R.id.txt_anchor_game_more).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnchorMoreActivity.class);
                intent.putExtra("gameName", item.getGameInfo().getGameCategoryName());
                intent.putExtra("gameId", item.getGameInfo().getGameCategoryCode());
                context.startActivity(intent);
            }
        });

    }
}
