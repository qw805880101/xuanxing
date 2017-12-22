package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.InterestAdapter.ChannelState;
import com.xuanxing.tc.game.bean.FindList;
import com.xuanxing.tc.game.bean.ModInterestList;
import com.xuanxing.tc.game.bean.RecommendInfo;
import com.xuanxing.tc.game.utils.InterestSpaceItemDecoration;
import com.xuanxing.tc.game.utils.SpaceItemDecoration;

import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class ModInterestAdapter extends BaseQuickAdapter<ModInterestList, BaseViewHolder> {

    private InterestAdapter addedAdapter;
    private InterestAdapter noAddedAdapter;

    private ChannelState mChannelState;

    private boolean isFirstUi = true;

    public ModInterestAdapter(List<ModInterestList> list, ChannelState mChannelState) {
        super(R.layout.item_mod_interest, list);
        this.mChannelState = mChannelState;
    }

    @Override
    protected void convert(BaseViewHolder helper, ModInterestList item) {

        RecyclerView mRvAdded = helper.getView(R.id.rv_added);
        RecyclerView mRvNoAdded = helper.getView(R.id.rv_no_added);

        if (isFirstUi) {
            mRvAdded.addItemDecoration(new InterestSpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.bottom_1)));
            mRvNoAdded.addItemDecoration(new InterestSpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.bottom_1)));
            isFirstUi = false;
        }

        addedAdapter = new InterestAdapter(item.getAddedData());
        addedAdapter.setChannelState(mChannelState);
        noAddedAdapter = new InterestAdapter(item.getNoAddedData());
        noAddedAdapter.setChannelState(mChannelState);

        mRvAdded.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvAdded.setAdapter(addedAdapter);

        mRvNoAdded.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvNoAdded.setAdapter(noAddedAdapter);
    }
}
