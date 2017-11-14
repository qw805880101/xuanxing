package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.AnchorVideoAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.MemberInfo;
import com.xuanxing.tc.game.fragment.AnchorVideoFragment;
import com.xuanxing.tc.game.fragment.RecommendFragment;
import com.xuanxing.tc.game.fragment.VideoFragment;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.functions.Action1;

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
    @BindView(R.id.txt_attention_num)
    TextView mAttentionNum;
    @BindView(R.id.txt_fans_num)
    TextView mFansNum;


    RecommendFragment recommendFragment;
    AnchorVideoFragment videoFragment;
    FragmentAdapter fragmentAdapter;

    private int anchorId;

    private List<Fragment> mFragments = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucent(AnchorActivity.this);
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
        toolbar.setNavigationIcon(R.mipmap.fanhui);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        XUtils.setIndicator(toolbarTab, 60, 60);
        final CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    mCollapsingToolbarLayout.setTitle(nickName.getText().toString());
                    mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.LEFT);
                    StatusBarUtil.setColor(AnchorActivity.this, AnchorActivity.this.getResources().getColor(R.color.title_bg_e83646));
                } else {
                    mCollapsingToolbarLayout.setTitle(nickName.getText().toString());
                    StatusBarUtil.setTranslucent(AnchorActivity.this);
                }
            }
        });
        initFragment();
    }

    private void initFragment() {
        if (recommendFragment == null) {
            recommendFragment = RecommendFragment.getRecommendFragment("" + anchorId, 1);
        }
        if (videoFragment == null) {
            videoFragment = AnchorVideoFragment.getVideoFragment("" + anchorId);
        }
        mFragments.add(recommendFragment);
        mFragments.add(videoFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        anchorId = intent.getIntExtra("anchorId", 0);
        getMemberInfo();
    }

    /**
     * 获取主播信息
     */
    private void getMemberInfo() {
        Observable<BaseBeanClass<BaseList>> memberInfo = mXuanXingApi.getOtherMemberInfo(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(),
                "" + anchorId).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(memberInfo.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    nickName.setText(baseBean.getData().getOtherMemberInfo().getNickName());
                    XUtils.loadHeadIcon(AnchorActivity.this, baseBean.getData().getOtherMemberInfo().getHeadIcon(), headImg);
                    sign.setText(baseBean.getData().getOtherMemberInfo().getIntro() == null || baseBean.getData().getOtherMemberInfo().getIntro().equals("") ? "这家伙很懒~" : baseBean.getData().getOtherMemberInfo().getIntro());
                    mAttentionNum.setText("" + baseBean.getData().getOtherMemberInfo().getAttentionNum());
                    mFansNum.setText("" + baseBean.getData().getOtherMemberInfo().getFansNum());
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }
}
