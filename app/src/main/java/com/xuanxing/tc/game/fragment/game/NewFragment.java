package com.xuanxing.tc.game.fragment.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.bean.News;
import com.xuanxing.tc.game.bean.NewsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 热门游戏-最新
 * Created by tianchao on 2017/10/28.
 */

public class NewFragment extends BaseFragment {
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.swipe_refresh_recommend)
    SwipeRefreshLayout mSwipeRefreshRecommend;
    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;

    private RecommendAdapter mRecommendAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    private int total;
    private int totalPage;
    private int page;

    private HotGameList gameInfo;
    private int newsType; //新闻分类

    private boolean isRef = false;

    public static NewFragment getInstance(HotGameList gameInfo, int newsType){
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameInfo", gameInfo);
        bundle.putInt("newsType", newsType);
        NewFragment fragment = new NewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mRecommendAdapter = new RecommendAdapter(mNewsInfos);
        mRecommendAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    loadData(page);
                } else {
                    mRecommendAdapter.loadMoreEnd();//加载结束
                }
            }
        }, mRvRecommend);

        mSwipeRefreshRecommend.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                loadData(page);
            }
        });
        mRvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRvRecommend.setAdapter(mRecommendAdapter);
    }

    private void loadData(int page) {
        Observable<BaseBeanClass<News>> newsList = mXuanXingApi.gameNews(page, 10, gameInfo.getCategoryCode(), newsType)
                .compose(RxUtil.<BaseBeanClass<News>>rxSchedulerHelper());
        mRxManager.add(newsList.subscribe(new Action1<BaseBeanClass<News>>() {
            @Override
            public void call(BaseBeanClass<News> newsListBaseBeanListClass) {
                mSwipeRefreshRecommend.setRefreshing(false); //刷新完成
                mRecommendAdapter.loadMoreComplete(); //加载完成
                if (newsListBaseBeanListClass.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = newsListBaseBeanListClass.getData().getNewestNewsList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }

                    if (newsListBaseBeanListClass.getData().getNewestNewsList().getItems().size() <= 0) {
                        mRvRecommend.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        mRvRecommend.setVisibility(View.VISIBLE);
                        if (isRef) { //刷新
                            isRef = false;
                            mNewsInfos = newsListBaseBeanListClass.getData().getNewestNewsList().getItems();
                        } else { //加载
                            mNewsInfos.addAll(newsListBaseBeanListClass.getData().getNewestNewsList().getItems());
                        }
                        mRecommendAdapter.setNewData(mNewsInfos);
                    }
                } else {
                    toastMessage(newsListBaseBeanListClass.getCode(), newsListBaseBeanListClass.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void call(Throwable throwable) {
        super.call(throwable);
        mRecommendAdapter.loadMoreFail(); //加载失败
        mSwipeRefreshRecommend.setRefreshing(false); //刷新失败
    }

    @Override
    public void initData() {
        Bundle bundle = this.getArguments();
        gameInfo = (HotGameList) bundle.getSerializable("gameInfo");
        newsType = bundle.getInt("newsType");
    }

    @Override
    protected void initLazyView() {
        loadData(1);
    }
}
