package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorMoreActivity;
import com.xuanxing.tc.game.bean.GameAnchorList;

import java.util.List;

/**
 * 感兴趣的游戏列表
 * <p>
 * Created by admin on 2017/8/31.
 */

public class InterestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int height;

    public InterestAdapter(@Nullable List<String> data) {
        super(R.layout.item_interest, data);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        LinearLayout linearLayout = helper.getView(R.id.lin_interest);
        ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = height / 3;
        linearLayout.setLayoutParams(lp);
    }
}
