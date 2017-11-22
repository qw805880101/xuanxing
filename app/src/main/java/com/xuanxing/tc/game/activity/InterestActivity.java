package com.xuanxing.tc.game.activity;

import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.InterestAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.utils.InterestSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tianchao on 2017/11/22.
 */

public class InterestActivity extends BaseActivity {


    @BindView(R.id.bt_batch_change)
    Button mBtBatchChange;
    @BindView(R.id.bt_finish)
    Button mBtFinish;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.rv_interest)
    RecyclerView mRvInterest;
    @BindView(R.id.lin_rv_interest)
    LinearLayout mLinearLayout;

    private InterestAdapter mInterestAdapter;

    private List<String> data = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("请选择喜欢的游戏")
                .setTitleTextColor(this, R.color.white)
                .setRightText("跳过")
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InterestActivity.this.finish();
                    }
                })
                .build();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_interest;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        for (int i = 0; i < 12; i++) {
            data.add("111");
        }

        ViewTreeObserver vto = mLinearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int h = mLinearLayout.getHeight();
                int w = mLinearLayout.getWidth();
                mInterestAdapter.setHeight(h);
                mRvInterest.setAdapter(mInterestAdapter);
            }
        });

        mInterestAdapter = new InterestAdapter(data);
        mRvInterest.setLayoutManager(new GridLayoutManager(this, 3));
        mRvInterest.addItemDecoration(new InterestSpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_1)));


    }

    @Override
    public void initdata() {

    }

}
