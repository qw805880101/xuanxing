package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.HotGameAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 更多热门游戏
 * Created by tianchao on 2017/10/16.
 */

public class HotGameMoreActivity extends BaseActivity{

    @BindView(R.id.rv_hot_game)
    RecyclerView rvHotGame;

    private HotGameAdapter mHotGameAdapter;

    private List<HotGameList> hotGameList = new ArrayList<>();

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
        mHotGameAdapter = new HotGameAdapter(this, hotGameList);
        rvHotGame.setLayoutManager(new GridLayoutManager(this, 3));
        rvHotGame.addItemDecoration(new SpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_10)));
        rvHotGame.setAdapter(mHotGameAdapter);
    }

    @Override
    public void initdata() {
        //TODO 添加查看更多热门游戏数据

    }
}
