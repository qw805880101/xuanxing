package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.CollectionAdapter;
import com.xuanxing.tc.game.adapter.MessageAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.NoticeInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * 消息通知
 * Created by admin on 2017/9/3.
 */

public class MessageActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh_notice)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;

    private int page = 1;
    private int totalPage;
    private int total;

    private boolean isRef = false;

    private MessageAdapter messageAdapter;

    private List<NoticeInfo> data = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("消息通知")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        messageAdapter = new MessageAdapter(this, data);
        messageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    loadData();
                } else {
                    messageAdapter.loadMoreEnd();//加载结束
                }
            }
        }, rvMessage);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                messageAdapter.setEnableLoadMore(false);
                loadData();
            }
        });
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        rvMessage.setAdapter(messageAdapter);
    }

    @Override
    public void initdata() {
        loadData();
    }

    private void loadData() {
        Observable<BaseBeanClass<BaseList>> getNoticeList = mXuanXingApi.getNoticeList(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), page, 10).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(getNoticeList.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {
                messageAdapter.setEnableLoadMore(true);
                swipeRefreshLayout.setRefreshing(false); //刷新完成
                messageAdapter.loadMoreComplete(); //加载完成
                if (baseBean.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = baseBean.getData().getNoticeList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }
                    if (baseBean.getData().getNoticeList().getItems().size() <= 0) {
                        rvMessage.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        rvMessage.setVisibility(View.VISIBLE);
                        if (isRef) { //刷新
                            isRef = false;
                            data = baseBean.getData().getNoticeList().getItems();
                        } else { //加载
                            data.addAll(baseBean.getData().getNoticeList().getItems());
                        }
                        messageAdapter.setNewData(data);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

}
