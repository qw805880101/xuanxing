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
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.CollectionAdapter;
import com.xuanxing.tc.game.adapter.MessageAdapter;
import com.xuanxing.tc.game.adapter.MoreAnchorAdapter;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.AnchorInfo;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.RecommendItemUtil;
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
 * 收藏
 * <p>
 * Created by admin on 2017/9/3.
 */

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh_collection)
    SwipeRefreshLayout mSwipeRefreshFans;
    @BindView(R.id.lin_search_null)
    LinearLayout mLinSearchNull;
    @BindView(R.id.rv_collection)
    RecyclerView rvCollection;

    private int page = 1;
    private int totalPage;
    private int total;

    private boolean isRef = false;

    private CollectionAdapter mCollectionAdapter;

    private List<NewsInfo> data = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("收藏")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CollectionActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mCollectionAdapter = new CollectionAdapter(this, data);
        mCollectionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    getLoadData();
                } else {
                    mCollectionAdapter.loadMoreEnd();//加载结束
                }
            }
        }, rvCollection);

        mSwipeRefreshFans.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                mCollectionAdapter.setEnableLoadMore(false);
                getLoadData();
            }
        });
        rvCollection.setLayoutManager(new LinearLayoutManager(this));
        rvCollection.setAdapter(mCollectionAdapter);
    }

    @Override
    public void initdata() {
        getLoadData();
    }

    private void getLoadData() {
        Observable<BaseBeanClass<BaseList>> anchorMore = mXuanXingApi.getCollectionList(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), page, 10).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(anchorMore.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {
                mCollectionAdapter.setEnableLoadMore(true);
                mSwipeRefreshFans.setRefreshing(false); //刷新完成
                mCollectionAdapter.loadMoreComplete(); //加载完成
                if (baseBean.getCode().equals("0000")) {
                    /* 总数-总页数 */
                    total = baseBean.getData().getCollectList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }
                    if (baseBean.getData().getCollectList().getItems().size() <= 0) {
                        rvCollection.setVisibility(View.GONE);
                        mLinSearchNull.setVisibility(View.VISIBLE);
                    } else {
                        mLinSearchNull.setVisibility(View.GONE);
                        rvCollection.setVisibility(View.VISIBLE);
                        if (isRef) { //刷新
                            isRef = false;
                            data = baseBean.getData().getCollectList().getItems();
                        } else { //加载
                            data.addAll(baseBean.getData().getCollectList().getItems());
                        }
                        mCollectionAdapter.setNewData(data);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    /**
     * 删除收藏
     */
    public void delCollection(String newsId, final int position) {
        Observable<BaseBean> collection = mXuanXingApi.mIsCollect(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), 0, newsId).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(collection.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    data.remove(position);
                    final Map<String, String> map = new LinkedHashMap();
                    ToastUtils.showToast(CollectionActivity.this, "取消收藏");
                    map.put("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1));
                    //事件发送 通知我的界面更新收藏
                    EventBus.getDefault().post(new SendEvent("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1)));
                    XUtils.modUserInfo(CollectionActivity.this, map);
                    mCollectionAdapter.setNewData(data);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

}
