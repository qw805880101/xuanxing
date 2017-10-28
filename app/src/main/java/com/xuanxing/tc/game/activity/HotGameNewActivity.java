package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.GameInfo;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.fragment.game.NewFragment;
import com.xuanxing.tc.game.fragment.game.RaidersFragment;
import com.xuanxing.tc.game.fragment.game.VideoFragment;
import com.xuanxing.tc.game.fragment.search.AnchorFragment;
import com.xuanxing.tc.game.fragment.search.ArticleFragment;
import com.xuanxing.tc.game.fragment.search.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 热门游戏-最新
 * <p>
 * Created by tianchao on 2017/10/28.
 */

public class HotGameNewActivity extends BaseActivity {

    @BindView(R.id.toolbar_tab)
    TabLayout mToolbarTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private TitleBuilder mTitleBuilder;

    private HotGameList gameInfo;

    private NewFragment mNewFragment; //最新
    private VideoFragment mVideoFragment; //视频
    private NewFragment mRaidersFragment; //攻略

    private FragmentAdapter fragmentAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        mTitleBuilder = new TitleBuilder(this);
        mTitleBuilder.setTitleBgRes(R.color.title_bg_e83646)
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        return mTitleBuilder.build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_game_new;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        gameInfo = (HotGameList) intent.getSerializableExtra("gameInfo");
        mTitleBuilder.setTitleText(gameInfo.getCategoryName());
        initF();
    }

    private void initF() {
        if (mNewFragment == null) {
            mNewFragment = NewFragment.getInstance(gameInfo, 0);
        }
        if (mVideoFragment == null) {
            mVideoFragment = VideoFragment.getInstance(gameInfo);
        }
        if (mRaidersFragment == null) {
            mRaidersFragment = NewFragment.getInstance(gameInfo, 3);
        }
        mFragments.add(mNewFragment);
        mFragments.add(mVideoFragment);
        mFragments.add(mRaidersFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mToolbarTab));
        mToolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
}
