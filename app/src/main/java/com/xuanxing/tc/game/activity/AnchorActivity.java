package com.xuanxing.tc.game.activity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.fragment.RecommendFragment;
import com.xuanxing.tc.game.fragment.VideoFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主播
 * <p>
 * Created by admin on 2017/9/14.
 */

public class AnchorActivity extends BaseActivity {

    @BindView(R.id.head_img)
    CircleImageView headImg;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.sign)
    TextView sign;
    @BindView(R.id.lin_follow)
    LinearLayout linFollow;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    RecommendFragment recommendFragment;
    VideoFragment videoFragment;
    FragmentAdapter fragmentAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucent(this);
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_anchor;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setIndicator(toolbarTab, 80, 80);

        final CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -loginLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("Star");
                    StatusBarUtil.setColor(AnchorActivity.this, AnchorActivity.this.getResources().getColor(R.color.title_bg_e83646));
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                    StatusBarUtil.setTranslucent(AnchorActivity.this);
                }
            }
        });

        initFragment();
    }

    @Override
    public void initdata() {

    }

    private void initFragment(){
        if(recommendFragment==null){
            recommendFragment=new RecommendFragment();
        }
        if(videoFragment==null){
            videoFragment=new VideoFragment();
        }
        mFragments.add(recommendFragment);
        mFragments.add(videoFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
