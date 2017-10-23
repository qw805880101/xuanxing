package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;

import rx.Observable;
import rx.functions.Action1;

/**
 * 更多主播
 * Created by tianchao on 2017/10/16.
 */

public class AnchorMoreActivity extends BaseActivity {

    private TitleBuilder mTitleBuilder;

    private String gameId;

    private int page;
    private int totalPage;
    private int total;

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
        String gameId = intent.getStringExtra("gameId");
        mTitleBuilder.setTitleText(gameName + "主播");
    }

    private void getLoadData(){
        Observable<BaseBean> anchorMore = mXuanXingApi.anchorMore(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), gameId, page, 10).compose(RxUtil.<BaseBean>rxSchedulerHelper());

        mRxManager.add(anchorMore.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {

            }
        }, this));
    }

}
