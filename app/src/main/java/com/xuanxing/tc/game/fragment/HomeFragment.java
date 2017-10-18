package com.xuanxing.tc.game.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.SearchActivity;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tc on 2017/8/24.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.vp_home)
    CustomViewPager vpHome;
    @BindView(R.id.image_tongzhi)
    ImageView imageTongzhi;
    @BindView(R.id.bt_recommend)
    Button btRecommend;
    @BindView(R.id.bt_video)
    Button btVideo;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.title_home)
    LinearLayout titleHome;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;

    List<Fragment> fragments = new ArrayList<>();

    private RecommendFragment recommendFragment;
    private  VideoFragment videoFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        recommendFragment = new RecommendFragment();
        videoFragment = new VideoFragment();

        fragments.add(recommendFragment);
        fragments.add(videoFragment);

        //绑定Fragment适配器
        vpHome.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments));

        imageTongzhi.setOnClickListener(this);
        imageSearch.setOnClickListener(this);
        btRecommend.setOnClickListener(this);
        btVideo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        if (v == imageTongzhi) {

        }

        if (v == imageSearch) {
            startActivity(new Intent(this.getContext(), SearchActivity.class));
        }

        if (v == btRecommend) {
            recommendOrVideo(true);
        }

        if (v == btVideo) {
            recommendOrVideo(false);
        }
    }

    public void recommendOrVideo(boolean isROV) {
        if (isROV) {
            vpHome.setCurrentItem(0);
            btRecommend.setTextColor(this.getResources().getColor(R.color.title_bg_e83646));
            btRecommend.setBackgroundResource(R.drawable.title_bt_bg_left_f);
            btRecommend.setEnabled(false);
            btVideo.setTextColor(this.getResources().getColor(R.color.white));
            btVideo.setBackgroundResource(R.drawable.title_bt_bg_right_z);
            btVideo.setEnabled(true);
        } else {
            vpHome.setCurrentItem(1);
            btRecommend.setTextColor(this.getResources().getColor(R.color.white));
            btRecommend.setBackgroundResource(R.drawable.title_bt_bg_left_z);
            btRecommend.setEnabled(true);
            btVideo.setTextColor(this.getResources().getColor(R.color.title_bg_e83646));
            btVideo.setBackgroundResource(R.drawable.title_bt_bg_right_f);
            btVideo.setEnabled(false);
        }
    }

    @Override
    protected void initLazyView() {
        LogUtil.d("Home_initLazyView");
    }
}
