package com.xuanxing.tc.game.fragment.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseFragment;

import butterknife.BindView;

/**
 * 热门游戏-攻略
 * Created by tianchao on 2017/10/28.
 */

public class RaidersFragment extends BaseFragment{
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.swipe_refresh_recommend)
    SwipeRefreshLayout mSwipeRefreshRecommend;

    @Override
    protected void initLazyView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
