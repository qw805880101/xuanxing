package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.CollectionAdapter;
import com.xuanxing.tc.game.adapter.MessageAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import butterknife.BindView;

/**
 * 收藏
 *
 * Created by admin on 2017/9/3.
 */

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.rv_collection)
    RecyclerView rvCollection;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("收藏")
                .setTitleTextColor(this,R.color.white)
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
        rvCollection.setLayoutManager(new LinearLayoutManager(this));
        rvCollection.setAdapter(new CollectionAdapter(RecommendItemUtil.getRecommendItem()));
    }

    @Override
    public void initdata() {

    }
}
