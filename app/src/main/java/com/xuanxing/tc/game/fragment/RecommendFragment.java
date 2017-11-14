package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseBeanListClass;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.bean.News;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.NewsList;
import com.xuanxing.tc.game.bean.RecommendItemUtil;
import com.xuanxing.tc.game.fragment.game.NewFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by admin on 2017/8/31.
 */

public class RecommendFragment extends BaseFragment {

    @BindView(R.id.lin_search_null)
    LinearLayout linNull;
    @BindView(R.id.txt_null_hint)
    TextView txtNull;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.swipe_refresh_recommend)
    SwipeRefreshLayout mRefreshLayout;

    private RecommendAdapter mRecommendAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    private int total;
    private int totalPage;
    private int page;

    private boolean isRef = false;

    private int type = 0; // 1:AnchorActivity 主播界面 视频 文章

    private String newsMemberId;

    public static RecommendFragment getRecommendFragment(String newsMemberId, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("newsMemberId", newsMemberId);
        bundle.putInt("type", type);
        RecommendFragment fragment = new RecommendFragment();
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
        }, rvRecommend);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                loadData(page);
            }
        });
        rvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecommend.setAdapter(mRecommendAdapter);
    }

    private void loadData(int page) {
        Observable<BaseBeanClass<News>> newsList = null;
        if (type == 1) { //主播界面获取他人文章视频列表
            newsList = mXuanXingApi.getOtherMemberNewsList(page, 10,
                    newsMemberId, 1).compose(RxUtil.<BaseBeanClass<News>>rxSchedulerHelper());
        } else {
            newsList = mXuanXingApi.getNewsList(page, 10)
                    .compose(RxUtil.<BaseBeanClass<News>>rxSchedulerHelper());
        }
        mRxManager.add(newsList.subscribe(new Action1<BaseBeanClass<News>>() {
            @Override
            public void call(BaseBeanClass<News> newsListBaseBeanListClass) {
                mRefreshLayout.setRefreshing(false); //刷新完成
                mRecommendAdapter.loadMoreComplete(); //加载完成
                if (newsListBaseBeanListClass.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = newsListBaseBeanListClass.getData().getNewsList().getTotalCount();
                    if (total <= 0) {
                        linNull.setVisibility(View.VISIBLE);
                        rvRecommend.setVisibility(View.GONE);
                    } else {
                        linNull.setVisibility(View.GONE);
                        rvRecommend.setVisibility(View.VISIBLE);
                    }
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }
                    if (isRef) { //刷新
                        isRef = false;
                        mNewsInfos = newsListBaseBeanListClass.getData().getNewsList().getItems();
                    } else { //加载
                        mNewsInfos.addAll(newsListBaseBeanListClass.getData().getNewsList().getItems());
                    }
                    mRecommendAdapter.setNewData(mNewsInfos);
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
        mRefreshLayout.setRefreshing(false); //刷新失败
    }

    @Override
    public void initData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newsMemberId = bundle.getString("newsMemberId");
            type = bundle.getInt("type");
        }
        loadData(1);
    }

    @Override
    protected void initLazyView() {

    }
}
