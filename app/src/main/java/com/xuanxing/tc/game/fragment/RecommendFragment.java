package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBeanListClass;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendFragment extends BaseFragment {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;

    private RecommendAdapter mRecommendAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        mRecommendAdapter = new RecommendAdapter(mNewsInfos);

        rvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecommend.setAdapter(mRecommendAdapter);
    }

    @Override
    public void initData() {
        Observable<BaseBeanListClass<NewsInfo>> newsList = mXuanXingApi.getNewsList("1", "10")
                .compose(RxUtil.<BaseBeanListClass<NewsInfo>>rxSchedulerHelper());
        mRxManager.add(newsList.subscribe(new Action1<BaseBeanListClass<NewsInfo>>() {
            @Override
            public void call(BaseBeanListClass<NewsInfo> newsListBaseBeanListClass) {
                if (newsListBaseBeanListClass.getCode().equals("0000")){
                    mRecommendAdapter.setNewData(newsListBaseBeanListClass.getData());
                    System.out.println("获取信息列表成功" + newsListBaseBeanListClass.getData().size());
                } else {
                    toastMessage(newsListBaseBeanListClass.getCode(), newsListBaseBeanListClass.getMsg());
                }
            }
        }, this));

    }
}
