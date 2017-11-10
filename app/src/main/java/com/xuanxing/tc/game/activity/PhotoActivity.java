package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;

import static com.xuanxing.tc.game.activity.FeedbackActivity.DEL;

/**
 * Created by tianchao on 2017/10/26.
 */

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.iv_photo)
    ImageView mPhoto;

    private TitleBuilder mTitleBuilder;

    private int position; //图片坐标

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        mTitleBuilder = new TitleBuilder(this);
        mTitleBuilder.setTitleBgRes(R.color.title_bg_e83646)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setRightImage(R.mipmap.shanchu)
                .setRightOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //事件发送
                        SendEvent sendEvent = new SendEvent();
                        sendEvent.setCode(DEL);
                        sendEvent.setPosition(position);
                        EventBus.getDefault().post(sendEvent);
                        finish();
                    }
                });
        return mTitleBuilder.build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        position = intent.getIntExtra("position", 0);
        XUtils.loadHeadIcon(mContext, new File(intent.getStringExtra("path")), mPhoto);
        mTitleBuilder.setLeftText(position + 1 + "/" + intent.getIntExtra("total", 1));
    }
}
