package com.xuanxing.tc.game.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by tc on 2017/8/24.
 */

public class VideoFragment extends BaseFragment {

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        System.out.println("VideoFragment");

        return R.layout.fragment_home_video;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


    }
}
