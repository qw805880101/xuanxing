package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

/**
 * 更多主播
 * Created by tianchao on 2017/10/16.
 */

public class AnchorMoreActivity extends BaseActivity {

    private TitleBuilder mTitleBuilder;

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

    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        String gameName = intent.getStringExtra("gameName");
        mTitleBuilder.setTitleText(gameName + "主播");
    }
}
