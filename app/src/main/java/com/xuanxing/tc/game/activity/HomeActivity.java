package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.xuanxing.tc.bottomtabbar.BottomTabBar;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.fragment.FindFragment;
import com.xuanxing.tc.game.fragment.HomeFragment;
import com.xuanxing.tc.game.fragment.LevelingFragment;
import com.xuanxing.tc.game.fragment.MyFragment;
import com.xuanxing.tc.game.fragment.PayFragment;
import com.xuanxing.tc.game.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * <p>
 * Created by tc on 2017/8/24.
 */

public class HomeActivity extends BaseActivity implements OnClickListener, BottomTabBar.OnTabChangeListener {

    List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    private Class fragmentArray[] = {HomeFragment.class, FindFragment.class, /*LevelingFragment.class, PayFragment.class,*/ MyFragment.class};

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        bottomTabBar.init(getSupportFragmentManager());
        bottomTabBar.setChangeColor(getResources().getColor(R.color.title_bg_e83646), getResources().getColor(R.color.txt_828282));
        bottomTabBar.setTabPadding(this.getResources().getDimensionPixelSize(R.dimen.top_10), this.getResources().getDimensionPixelSize(R.dimen.middle_5), this.getResources().getDimensionPixelSize(R.dimen.bottom_10));
        bottomTabBar.setOnTabChangeListener(this);
        String[] mTabs = {"首页", "发现", /*"代练", "交易",*/ "我的"};
        int[] imageIds = {R.mipmap.shouye_p, R.mipmap.faxian_p, /*R.mipmap.faxian_p, R.mipmap.faxian_p,*/ R.mipmap.wode_p};
        int[] imageId = {R.mipmap.shouye, R.mipmap.faxian, /*R.mipmap.faxian, R.mipmap.faxian,*/ R.mipmap.wode};
        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < mTabs.length; i++) {
            bottomTabBar.addTabItem(mTabs[i], imageIds[i], imageId[i], fragmentArray[i]);
        }
    }

    @Override
    public void initdata() {

    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onTabChange(int position, String name) {

    }
}
