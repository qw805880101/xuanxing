package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.hjm.bottomtabbar.BottomTabBar;
import com.psylife.wrmvplibrary.base.WRBaseActivity;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.fragment.FindFragment;
import com.xuanxing.tc.game.fragment.HomeFragment;
import com.xuanxing.tc.game.fragment.LevelingFragment;
import com.xuanxing.tc.game.fragment.MyFragment;
import com.xuanxing.tc.game.fragment.PayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tc on 2017/8/24.
 */

public class HomeActivity extends WRBaseActivity implements OnClickListener {

    List<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    private Class fragmentArray[] = {HomeFragment.class, FindFragment.class, LevelingFragment.class, PayFragment.class, MyFragment.class};
    private LayoutInflater layoutInflater;


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
        String[] mTabs = {"首页", "发现", "代练", "交易", "我的"};
        int[] imageIds = {R.mipmap.shouye_p, R.mipmap.faxian_p, R.mipmap.faxian_p, R.mipmap.faxian_p, R.mipmap.wode_p};
        int[] imageId = {R.mipmap.shouye, R.mipmap.faxian, R.mipmap.faxian, R.mipmap.faxian, R.mipmap.wode};
        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < mTabs.length; i++) {
            bottomTabBar.addTabItem(mTabs[i], imageIds[i], imageId[i], fragmentArray[i]);
        }

        initPage();
    }

    @Override
    public void initdata() {

    }

//    private View getTabItemView(int i, String text, int id) {
//        layoutInflater = LayoutInflater.from(this);//加载布局管理器
//        //将xml布局转换为view对象
//        View view = layoutInflater.inflate(R.layout.tabcontent, null);
//        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
//        ImageView mImageView = (ImageView) view
//                .findViewById(R.id.image);
//        TextView mTextView = (TextView) view.findViewById(R.id.text);
//        mImageView.setBackgroundResource(id);
//        mTextView.setText(text);
//        return view;
//    }

    private void initPage() {
        HomeFragment fragment1 = new HomeFragment();
        FindFragment fragment2 = new FindFragment();
        LevelingFragment fragment3 = new LevelingFragment();
        PayFragment fragment4 = new PayFragment();
        MyFragment fragment5 = new MyFragment();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

    }

    @Override
    public void onClick(View v) {

    }
}
