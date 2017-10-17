package com.xuanxing.tc.game.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HotGameMoreActivity;
import com.xuanxing.tc.game.adapter.AnchorAdapter;
import com.xuanxing.tc.game.adapter.FindAdapter;
import com.xuanxing.tc.game.adapter.FindAdapter.FindOnclick;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.FindList;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tc on 2017/8/24.
 */

public class FindFragment extends BaseFragment implements FindOnclick {

    @BindView(R.id.rv_find)
    RecyclerView rvFind;

    private FindAdapter mFindAdapter;
    private List<FindList> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mFindAdapter = new FindAdapter(this.getContext(), list);
        mFindAdapter.setFindOnClick(this);
        rvFind.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFind.setAdapter(mFindAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.txt_game_more:
                Intent intent = new Intent(this.getContext(), HotGameMoreActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initLazyView() {
        Observable<BaseBeanClass<FindList>> findData = mXuanXingApi.findData().compose(RxUtil.<BaseBeanClass<FindList>>rxSchedulerHelper());
        mRxManager.add(findData.subscribe(new Action1<BaseBeanClass<FindList>>() {
            @Override
            public void call(BaseBeanClass<FindList> findListBaseBeanClass) {
                if (findListBaseBeanClass.getCode().equals("0000")){
                    list.add(findListBaseBeanClass.getData());
                    mFindAdapter.setNewData(list);
                } else {
                    toastMessage(findListBaseBeanClass.getCode(), findListBaseBeanClass.getMsg());
                }
            }
        }, this));
    }
}