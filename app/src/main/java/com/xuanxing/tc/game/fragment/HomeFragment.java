package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.xuanxing.tc.game.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tc on 2017/8/24.
 */

public class HomeFragment extends WRBaseFragment {


    @BindView(R.id.fl_home)
    FrameLayout flHome;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


    }
}
