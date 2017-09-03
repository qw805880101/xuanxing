package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.AnchorAdapter;
import com.xuanxing.tc.game.adapter.FindAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tc on 2017/8/24.
 */

public class FindFragment extends BaseFragment {

    @BindView(R.id.rv_find)
    RecyclerView rvFind;

    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        list.add("");
        rvFind.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFind.setAdapter(new FindAdapter(this.getContext(), list));
    }
}