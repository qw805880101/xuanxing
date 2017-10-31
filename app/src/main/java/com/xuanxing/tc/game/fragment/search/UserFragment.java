package com.xuanxing.tc.game.fragment.search;

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
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.SearchList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 搜索-用户
 * Created by tianchao on 2017/10/17.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;

    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;

    @BindView(R.id.swipe_refresh_search)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecommendAdapter mRecommendAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    private String keyWord;
    private int keyType;
    private int page;
    private int total;
    private int totalPage;

    private boolean isLoadData = false; //是否加载数据
    private boolean isRefresh = false; //是否刷新

    public void setSearch(String keyWord, int keyType, int page, boolean isLoadData) {
        this.keyWord = keyWord;
        this.keyType = keyType;
        this.page = page;
        this.isLoadData = isLoadData;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mRecommendAdapter = new RecommendAdapter(mNewsInfos);
//        mRecommendAdapter.disableLoadMoreIfNotFullPage();
        mRecommendAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    search(keyWord, keyType, page);
                } else {
                    mRecommendAdapter.loadMoreEnd();//加载结束
                }
            }
        }, rvRecommend);

        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                search(keyWord, keyType, page);
            }
        });

        rvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecommend.setAdapter(mRecommendAdapter);
    }

    /**
     * 搜索
     *
     * @param keyWord
     * @param keyType
     * @param page
     */
    private void search(String keyWord, int keyType, int page) {
        isLoadData = true;
        startProgressDialog(this.getContext());
        Observable<BaseBeanClass<SearchList>> search = mXuanXingApi.search(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), keyWord,
                keyType, page, 10).compose(RxUtil.<BaseBeanClass<SearchList>>rxSchedulerHelper());
        mRxManager.add(search.subscribe(new Action1<BaseBeanClass<SearchList>>() {
            @Override
            public void call(BaseBeanClass<SearchList> baseBean) {
                stopProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false); //刷新完成
                mRecommendAdapter.loadMoreComplete(); //加载完成
                if (baseBean.getCode().equals("0000")) {
                    total = baseBean.getData().getNewsList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }
                    if (baseBean.getData().getNewsList().getItems().size() <= 0) {
                        rvRecommend.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        rvRecommend.setVisibility(View.VISIBLE);
                        if (isRefresh) {
                            isRefresh = false;
                            mNewsInfos = baseBean.getData().getNewsList().getItems();
                        } else
                            mNewsInfos.addAll(baseBean.getData().getNewsList().getItems());
                        mRecommendAdapter.setNewData(mNewsInfos);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void call(Throwable throwable) {
        super.call(throwable);
        mRecommendAdapter.loadMoreFail(); //加载失败
        mSwipeRefreshLayout.setRefreshing(false); //刷新失败
    }

    @Override
    protected void initLazyView() {
        if (!isLoadData)
            search(keyWord, keyType, page);
    }
}
