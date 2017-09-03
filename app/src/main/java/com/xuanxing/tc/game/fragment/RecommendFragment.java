package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendFragment extends BaseFragment {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        rvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecommend.setAdapter(new RecommendAdapter(RecommendItemUtil.getRecommendItem()));
    }
}
