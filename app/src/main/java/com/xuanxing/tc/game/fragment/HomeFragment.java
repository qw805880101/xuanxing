package com.xuanxing.tc.game.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HomeActivity;
import com.xuanxing.tc.game.api.HomeApi;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.HomeFragmentInfo;
import com.xuanxing.tc.game.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tc on 2017/8/24.
 */

public class HomeFragment extends BaseFragment implements HomeApi, HomeActivity.VpSetCurrentItem {

    @BindView(R.id.vp_home)
    CustomViewPager vpHome;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        System.out.println("HomeFragment");

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

        HomeActivity homeActivity = (HomeActivity) this.getActivity();
        homeActivity.setVpSetCurrentItem(this);
    }

    @Override
    public Observable<Object> test(RequestBody file) {
        return RxService.createApi(HomeApi.class).test(file).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public void setCurrentItem(int item) {
        vpHome.setCurrentItem(item);
    }
}
