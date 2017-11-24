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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AnchorMoreActivity;
import com.xuanxing.tc.game.bean.GameAnchorList;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * 感兴趣的游戏列表
 * <p>
 * Created by admin on 2017/8/31.
 */

public class InterestAdapter extends BaseQuickAdapter<HotGameList, BaseViewHolder> {

    private int height;

    private ChannelState mChannelState;

    public InterestAdapter(@Nullable List<HotGameList> data) {
        super(R.layout.item_interest, data);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setChannelState(ChannelState mChannelState) {
        this.mChannelState = mChannelState;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HotGameList item) {

        RoundedImageView roundedImageView = helper.getView(R.id.iv_game_logo);
        XUtils.loadHeadIcon(mContext, item.getCategoryPic(), roundedImageView);

        TextView textView = helper.getView(R.id.txt_game_name);
        textView.setText(item.getCategoryName());

        if (height != 0){
            LinearLayout linearLayout = helper.getView(R.id.lin_interest);
            ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
            lp.width = LayoutParams.MATCH_PARENT;
            if (height / 4 > 320) {
                lp.height = height / 4;
            } else {
                lp.height = height / 3;
            }
            LogUtil.d("height:" + height);

            linearLayout.setLayoutParams(lp);
        }

        ImageView imageView = helper.getView(R.id.iv_add);

        if (item.getType() == 1) {
            imageView.setImageResource(R.mipmap.jia);
        }
        if (item.getType() == 0) {
            imageView.setImageResource(R.mipmap.jian);
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mChannelState.channelState(view, helper.getLayoutPosition(), item.getType());
            }
        });
    }

    public interface ChannelState {
        void channelState(View view, int position, int type);
    }

}
