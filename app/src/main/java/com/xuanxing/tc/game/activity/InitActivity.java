package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/9/14.
 */

public class InitActivity extends BaseActivity {

    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.bt_skip)
    Button btSkip;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;

    int a = 5;

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucent(this, 1);
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btSkip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0;
                Intent intent = new Intent(InitActivity.this, HomeActivity.class);
                InitActivity.this.startActivity(intent);
                InitActivity.this.finish();
            }
        });
    }

    @Override
    public void initdata() {
        new Thread() {
            @Override
            public void run() {
                while (a > 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a--;
                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                btSkip.setText(a + "  跳过");
                if (a == 0){
                    Intent intent = new Intent(InitActivity.this, HomeActivity.class);
                    InitActivity.this.startActivity(intent);
                    InitActivity.this.finish();
                }
            } catch (Exception e){

            }
        }
    };
}
