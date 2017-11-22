package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.FansAdapter;
import com.xuanxing.tc.game.adapter.MoreAnchorAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.FansInfo;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 粉丝
 * <p>
 * Created by admin on 2017/9/3.
 */

public class FansActivity extends BaseActivity implements MoreAnchorAdapter.FollowOnclick {

    @BindView(R.id.swipe_refresh_fans)
    SwipeRefreshLayout mSwipeRefreshFans;
    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;
    @BindView(R.id.rv_fans)
    RecyclerView rvFans;

    private int page = 1;
    private int totalPage;
    private int total;

    private boolean isRef = false;

    private FansAdapter mFansAdapter;

    private List<FansInfo> data = new ArrayList<>();

    private boolean isOther = false;
    private String newsMemberId;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("粉丝")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FansActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fans;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFansAdapter = new FansAdapter(data);
        mFansAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    getLoadData();
                } else {
                    mFansAdapter.loadMoreEnd();//加载结束
                }
            }
        }, rvFans);

        mFansAdapter.setFollowOnclick(this);

        mSwipeRefreshFans.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                mFansAdapter.setEnableLoadMore(false);
                getLoadData();
            }
        });
        rvFans.setLayoutManager(new LinearLayoutManager(this));
        rvFans.setAdapter(mFansAdapter);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        if (intent != null) {
            isOther = intent.getBooleanExtra("isOther", false);
            newsMemberId = intent.getStringExtra("newsMemberId");
        }
        getLoadData();
    }

    private void getLoadData() {
        Observable<BaseBeanClass<BaseList>> anchorMore = null;
        if (isOther) {
            anchorMore = mXuanXingApi.getOtherFansList(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                    MyApplication.loginInfo.getP_token(), page, 10, newsMemberId).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        } else {
            anchorMore = mXuanXingApi.getFansList(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                    MyApplication.loginInfo.getP_token(), page, 10).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        }

        mRxManager.add(anchorMore.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {
                mFansAdapter.setEnableLoadMore(true);
                mSwipeRefreshFans.setRefreshing(false); //刷新完成
//                moreAnchorAdapter.loadMoreComplete(); //加载完成
                if (baseBean.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = baseBean.getData().getFansList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }

                    if (baseBean.getData().getFansList().getItems().size() <= 0) {
                        rvFans.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        rvFans.setVisibility(View.VISIBLE);
                        if (isRef) { //刷新
                            isRef = false;
                            data = baseBean.getData().getFansList().getItems();
                        } else { //加载
                            data.addAll(baseBean.getData().getFansList().getItems());
                        }
                        mFansAdapter.setNewData(data);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }

            }
        }, this));
    }

    @Override
    public void follow(View view, int position) {
        if (data.get(position).getIsAttention() == 1) {
            follow(0, position);
        } else {
            follow(1, position);
        }
    }

    /**
     * 关注
     *
     * @param isAttention
     * @param pos
     */
    private void follow(final int isAttention, final int pos) {
        Observable<BaseBean> follow = mXuanXingApi.follow(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), isAttention, "" + data.get(pos).getMemberId()).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(follow.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    Map map = new LinkedHashMap();
                    String num = "";
                    if (isAttention == 1) {
                        num = "" + (Integer.parseInt(MyApplication.loginInfo.getAttentionNum()) + 1);
                        ToastUtils.showToast(FansActivity.this, "关注成功");
                        data.get(pos).setIsAttention(1);
                    } else {
                        num = "" + (Integer.parseInt(MyApplication.loginInfo.getAttentionNum()) - 1);
                        ToastUtils.showToast(FansActivity.this, "取消关注");
                        data.get(pos).setIsAttention(0);
                    }

                    map.put("followNum", num);
                    //事件发送
                    EventBus.getDefault().post(new SendEvent("followNum", num));
                    XUtils.modUserInfo(FansActivity.this, map);
                    mFansAdapter.setNewData(data);

                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

}
