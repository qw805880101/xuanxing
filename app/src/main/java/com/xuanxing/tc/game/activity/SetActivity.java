package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

/**
 * 设置
 *
 * Created by admin on 2017/9/3.
 */

public class SetActivity extends BaseActivity {

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("设置")
                .setTitleTextColor(this,R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {

    }
}
