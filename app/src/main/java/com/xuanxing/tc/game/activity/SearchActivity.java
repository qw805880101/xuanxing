package com.xuanxing.tc.game.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.SearchAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.FindList;
import com.xuanxing.tc.game.bean.SearchHead;
import com.xuanxing.tc.game.bean.SearchHistory;
import com.xuanxing.tc.game.bean.SearchHotKey;
import com.xuanxing.tc.game.bean.SearchHotKeyList;
import com.xuanxing.tc.game.fragment.RecommendFragment;
import com.xuanxing.tc.game.fragment.VideoFragment;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by admin on 2017/9/15.
 */

public class SearchActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.rv_search_list)
    RecyclerView rvSearchList;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.lin_search_result)
    LinearLayout linSearchResult;

    RecommendFragment recommendFragment;
    VideoFragment videoFragment;
    FragmentAdapter fragmentAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    List<SearchHead> data = new ArrayList<>();
    private SearchAdapter searchAdapter;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        txtCancel.setOnClickListener(this);
        XUtils.setIndicator(toolbarTab, 20, 20);
        etSearch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = etSearch.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > etSearch.getWidth()
                        - etSearch.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    etSearch.setText("");
                    rvSearchList.setVisibility(View.VISIBLE);
                    linSearchResult.setVisibility(View.GONE);
                }
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Toast.makeText(SearchActivity.this,"呵呵",Toast.LENGTH_SHORT).show();
                    rvSearchList.setVisibility(View.GONE);
                    linSearchResult.setVisibility(View.VISIBLE);
                    initFragment();
                }
                return false;
            }
        });
        SearchHistory history = new SearchHistory();
        SearchHistory hot = new SearchHistory();

        /**
         * 头部数据
         */
//        SearchHead historyHead = new SearchHead(true, "101");
//        historyHead.setHeadType("101");
//        data.add(historyHead);
//        for (int i = 0; i < 5; i++) {
//            history.setHistorySearchContent("阴阳师" + i);
//            history.setNum(5);
//            SearchHead historyHeadInfo = new SearchHead(history);
//            data.add(historyHeadInfo);
//        }
        SearchHead hotHead = new SearchHead(true, "102");
        hotHead.setHeadType("102");
        data.add(hotHead);
//        for (int i = 0; i < 6; i++) {
//            hot.setHistorySearchContent("英雄联盟" + i);
//            hot.setNum(6);
//            SearchHead historyHeadInfo = new SearchHead(hot);
//            data.add(historyHeadInfo);
//        }
        searchAdapter = new SearchAdapter(this, data);
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        rvSearchList.setAdapter(searchAdapter);
    }

    @Override
    public void initdata() {
        Observable<BaseBeanClass<SearchHotKeyList>> searchKey = mXuanXingApi.searchKey().compose(RxUtil.<BaseBeanClass<SearchHotKeyList>>rxSchedulerHelper());
        mRxManager.add(searchKey.subscribe(new Action1<BaseBeanClass<SearchHotKeyList>>() {
            @Override
            public void call(BaseBeanClass<SearchHotKeyList> searchHotKeyBaseBeanClass) {
                /**
                 * 内容数据
                 */
                if (searchHotKeyBaseBeanClass.getCode().equals("0000")) {
                    for (int i = 0; i < searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().size(); i++) {
                        SearchHotKey searchHotKey = searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().get(i);
                        SearchHistory searchHistory = new SearchHistory(searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().size(), searchHotKey.getKeyWord());
                        SearchHead historyHeadInfo = new SearchHead(searchHistory);
                        data.add(historyHeadInfo);
                    }
                    searchAdapter.setNewData(data);
                } else {
                    toastMessage(searchHotKeyBaseBeanClass.getCode(), searchHotKeyBaseBeanClass.getMsg());
                }
            }
        }, this));
    }

    private void initFragment() {
        if (recommendFragment == null) {
            recommendFragment = new RecommendFragment();
        }
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        mFragments.add(recommendFragment);
        mFragments.add(videoFragment);
        mFragments.add(recommendFragment);
        mFragments.add(videoFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    public void onClick(View v) {
        if (v == txtCancel) {
            this.finish();
        }
    }
}
