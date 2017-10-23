package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.HotGameAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.GameMoreList;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 更多热门游戏
 * Created by tianchao on 2017/10/16.
 */

public class HotGameMoreActivity extends BaseActivity{

    @BindView(R.id.rv_hot_game)
    RecyclerView rvHotGame;

    @BindView(R.id.sw_hot_game)
    SwipeRefreshLayout refreshLayout;

    private HotGameAdapter mHotGameAdapter;

    private List<HotGameList> hotGameList = new ArrayList<>();

    private int page;

    private int total;

    private int totalPage;

    private boolean isRefresh = false; //是否刷新

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("热门游戏")
                .setTitleTextColor(this,R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HotGameMoreActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_game;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

       refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                initdata();
            }
        });

        mHotGameAdapter = new HotGameAdapter(this, hotGameList);
        mHotGameAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (totalPage > page) {
                    page += 1;
                    initdata();
                } else {
                    mHotGameAdapter.loadMoreEnd();//加载结束
                }
            }
        });
        rvHotGame.setLayoutManager(new GridLayoutManager(this, 3));
        rvHotGame.addItemDecoration(new SpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_10)));
        rvHotGame.setAdapter(mHotGameAdapter);
    }

    @Override
    public void initdata() {
        //TODO 添加查看更多热门游戏数据
        startProgressDialog(this);
        Observable<BaseBeanClass<GameMoreList>> game = mXuanXingApi.gameMore(page, 20)
                .compose(RxUtil.<BaseBeanClass<GameMoreList>>rxSchedulerHelper());
        mRxManager.add(game.subscribe(new Action1<BaseBeanClass<GameMoreList>>() {
            @Override
            public void call(BaseBeanClass<GameMoreList> gameMoreListBaseBeanClass) {
                stopProgressDialog();
                refreshLayout.setRefreshing(false); //刷新完成
                mHotGameAdapter.loadMoreComplete(); //加载完成
                if (gameMoreListBaseBeanClass.getCode().equals("0000")){
                    total = gameMoreListBaseBeanClass.getData().getGameList().getTotalCount();
                    if (total % 21 > 0){
                        totalPage = total/21+1;
                    } else {
                        totalPage = total/21;
                    }

                    if (isRefresh){
                        isRefresh = false;
                        hotGameList = gameMoreListBaseBeanClass.getData().getGameList().getItems();
                    } else {
                        hotGameList.addAll(gameMoreListBaseBeanClass.getData().getGameList().getItems());
                    }
                    mHotGameAdapter.setNewData(hotGameList);
//                    mHotGameAdapter.disableLoadMoreIfNotFullPage();
                }
            }
        },this));
    }

    @Override
    public void call(Throwable throwable) {
        super.call(throwable);
        mHotGameAdapter.loadMoreFail(); //加载失败
        refreshLayout.setRefreshing(false); //刷新失败
    }
}
