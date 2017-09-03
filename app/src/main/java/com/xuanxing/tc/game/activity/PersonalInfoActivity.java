package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

/**
 * Created by admin on 2017/9/3.
 */

public class PersonalInfoActivity extends BaseActivity {

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("个人信息")
                .setTitleTextColor(this,R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalInfoActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {

    }
}
