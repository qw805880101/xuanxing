package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.FindList;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;
import com.xuanxing.tc.game.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class FindAdapter extends BaseQuickAdapter<FindList, BaseViewHolder> {
    private Context context;
    private RecommendInfo recommendInfo;
    private FindOnclick mFindOnclick;

    public FindAdapter(Context context, List<FindList> list) {
        super(R.layout.item_find, list);
        this.context = context;
    }

    public void setFindOnClick(FindOnclick mFindOnclick){
        this.mFindOnclick = mFindOnclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindList item) {
        RecyclerView rvHotGame = helper.getView(R.id.rv_hot_game);
        rvHotGame.setLayoutManager(new GridLayoutManager(context, 3));
        rvHotGame.addItemDecoration(new SpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.bottom_10)));
        rvHotGame.setAdapter(new HotGameAdapter(context, item.getHotGameList()));

        RecyclerView rvAnchor = helper.getView(R.id.rv_anchor);
        rvAnchor.setLayoutManager(new LinearLayoutManager(context));
        rvAnchor.setAdapter(new AnchorAdapter(context, item.getGameAnchorList()));

        helper.getView(R.id.txt_game_more).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mFindOnclick.onClickView(view);
            }
        });


    }

    public interface FindOnclick{
        void onClickView(View view);
    }

}
