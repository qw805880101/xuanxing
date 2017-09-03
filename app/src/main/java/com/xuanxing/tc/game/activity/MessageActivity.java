package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.MessageAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.RecommendItemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/9/3.
 */

public class MessageActivity extends BaseActivity {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("消息通知")
                .setTitleTextColor(this,R.color.white)
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
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        rvMessage.setAdapter(new MessageAdapter(this, RecommendItemUtil.getRecommendItem()));
    }

    @Override
    public void initdata() {

    }
}
