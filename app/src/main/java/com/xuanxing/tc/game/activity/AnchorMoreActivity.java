package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.MoreAnchorAdapter;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;
import com.xuanxing.tc.game.bean.AnchorList;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.GameAnchorList;
import com.xuanxing.tc.game.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 更多主播
 * Created by tianchao on 2017/10/16.
 */

public class AnchorMoreActivity extends BaseActivity implements MoreAnchorAdapter.FollowOnclick{

    @BindView(R.id.swipe_refresh_more_anchor)
    SwipeRefreshLayout mSwipeRefreshRecommend;
    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;
    @BindView(R.id.rv__more_anchor)
    RecyclerView rvMoreAnchor;

    private TitleBuilder mTitleBuilder;

    private String gameId;

    private int page;
    private int totalPage;
    private int total;

    private boolean isRef = false;

    private MoreAnchorAdapter moreAnchorAdapter;

    private List<AnchorInfo> data = new ArrayList<>();


    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        mTitleBuilder = new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("主播")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnchorMoreActivity.this.finish();
                    }
                });
        return mTitleBuilder.build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_anchor_more;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        moreAnchorAdapter = new MoreAnchorAdapter(this, data);
        moreAnchorAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    getLoadData();
                } else {
//                    moreAnchorAdapter.loadMoreEnd();//加载结束
                }
            }
        });

        moreAnchorAdapter.setFollowOnclick(this);

        mSwipeRefreshRecommend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                getLoadData();
            }
        });
        rvMoreAnchor.setLayoutManager(new LinearLayoutManager(this));
        rvMoreAnchor.setAdapter(moreAnchorAdapter);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        String gameName = intent.getStringExtra("gameName");
        gameId = intent.getStringExtra("gameId");
        mTitleBuilder.setTitleText(gameName + "主播");
        getLoadData();
    }

    private void getLoadData() {
        Observable<BaseBeanClass<BaseList>> anchorMore = mXuanXingApi.anchorMore(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), gameId, page, 10).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());

        mRxManager.add(anchorMore.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {

                mSwipeRefreshRecommend.setRefreshing(false); //刷新完成
                moreAnchorAdapter.loadMoreComplete(); //加载完成
                if (baseBean.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = baseBean.getData().getAnchorList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }

                    if (baseBean.getData().getAnchorList().getItems().size() <= 0) {
                        rvMoreAnchor.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        rvMoreAnchor.setVisibility(View.VISIBLE);
                        if (isRef) { //刷新
                            isRef = false;
                            data = baseBean.getData().getAnchorList().getItems();
                        } else { //加载
                            data.addAll(baseBean.getData().getAnchorList().getItems());
                        }
                        moreAnchorAdapter.setNewData(data);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }

            }
        }, this));
    }

    @Override
    public void follow(View view, int position) {
        follow(1, position);
    }

    /**
     * 关注
     * @param isAttention
     * @param pos
     */
    private void follow(final int isAttention, final int pos){
        Observable<BaseBean> follow = mXuanXingApi.follow(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), isAttention, "" + data.get(pos).getAnchorId()).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(follow.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    ToastUtils.showToast(AnchorMoreActivity.this, "关注成功");
                    data.get(pos).setIsAttention(1);
                    moreAnchorAdapter.setNewData(data);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }
}
