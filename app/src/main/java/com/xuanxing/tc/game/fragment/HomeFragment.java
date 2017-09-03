package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HomeActivity;
import com.xuanxing.tc.game.api.HomeApi;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by tc on 2017/8/24.
 */

public class HomeFragment extends BaseFragment implements HomeApi, View.OnClickListener {

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        RecommendFragment recommendFragment = new RecommendFragment();
        VideoFragment videoFragment = new VideoFragment();

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
    public Observable<Object> test(RequestBody file) {
        return RxService.createApi(HomeApi.class).test(file).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public void onClick(View v) {

        if (v == imageTongzhi) {

        }

        if (v == imageSearch) {
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
}
