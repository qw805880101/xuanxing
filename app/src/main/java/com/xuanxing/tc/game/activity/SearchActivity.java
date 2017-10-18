package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.SearchAdapter;
import com.xuanxing.tc.game.adapter.SearchAdapter.SearchOnclick;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.SearchHead;
import com.xuanxing.tc.game.bean.SearchHistory;
import com.xuanxing.tc.game.bean.SearchList;
import com.xuanxing.tc.game.bean.SearchHotKey;
import com.xuanxing.tc.game.bean.SearchHotKeyList;
import com.xuanxing.tc.game.fragment.RecommendFragment;
import com.xuanxing.tc.game.fragment.search.AnchorFragment;
import com.xuanxing.tc.game.fragment.search.ArticleFragment;
import com.xuanxing.tc.game.fragment.search.UserFragment;
import com.xuanxing.tc.game.fragment.search.VideoFragment;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

import static com.xuanxing.tc.game.bean.SearchHistory.HISTORY;
import static com.xuanxing.tc.game.bean.SearchHistory.HOT;

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

    private FragmentAdapter fragmentAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    List<SearchHead> data = new ArrayList<>();
    private SearchAdapter searchAdapter;

    private String oldSearchKey = "";

    private ArticleFragment mArticleFragment;
    private VideoFragment videoFragment;
    private AnchorFragment mAnchorFragment;
    private UserFragment mUserFragment;

    private SearchHead historyHead;

    private SearchList searchHistoryList; //搜索历史列表

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
                    searchAdapter.setNewData(data);
                }
                return false;
            }
        });

        /**
         * 点击搜索
         */
        etSearch.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (MyApplication.loginInfo == null) {
                        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    String newSearchKey = etSearch.getText().toString().trim();
                    if (newSearchKey.equals("")) {
                        return true;
                    }
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    search(newSearchKey, HISTORY);
                }
                return false;
            }
        });

        searchAdapter = new SearchAdapter(this, data);
        searchAdapter.setOnclick(new SearchOnclick() {
            @Override
            public void setOnclick(View view, String searchKey, int type) {
                if (view.getId() == R.id.iv_search_hear_del) {
                    XUtils.clearHistoryList(SearchActivity.this);
                    /* 删除搜索历史 */
                    for (Iterator<SearchHead> it = data.iterator(); it.hasNext(); ) {
                        SearchHead s = it.next();
                        if (s.t != null && s.t.getType() == SearchHistory.HISTORY || s.getHeadType() != null && s.getHeadType().equals("101")) {
                            it.remove();
                        }
                    }
                    searchHistoryList.getSearchHistories().clear();
                    searchAdapter.setNewData(data);
                }
                if (searchKey != null && !searchKey.equals("")) {
                    search(searchKey, type);
                    etSearch.setText(searchKey);
                    etSearch.setSelection(etSearch.length());
                }
            }
        });
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        rvSearchList.setAdapter(searchAdapter);

        initF();
    }

    @Override
    public void initdata() {
        setHistory();
        /**
         * 添加热门搜索
         */
        Observable<BaseBeanClass<SearchHotKeyList>> searchKey = mXuanXingApi.searchKey().compose(RxUtil.<BaseBeanClass<SearchHotKeyList>>rxSchedulerHelper());
        mRxManager.add(searchKey.subscribe(new Action1<BaseBeanClass<SearchHotKeyList>>() {
            @Override
            public void call(BaseBeanClass<SearchHotKeyList> searchHotKeyBaseBeanClass) {
                if (searchHotKeyBaseBeanClass.getCode().equals("0000")) {
                    SearchHead hotHead = new SearchHead(true, "102");
                    hotHead.setHeadType("102");
                    data.add(hotHead);
                    for (int i = 0; i < searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().size(); i++) {
                        SearchHotKey searchHotKey = searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().get(i);
                        SearchHistory searchHistory = new SearchHistory(searchHotKey.getKeyWord(), searchHotKeyBaseBeanClass.getData().getSearchHotKeyList().size(), HOT);
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

    /**
     * 添加历史搜素
     */
    private void addHistory(String newSearchKey) {
        if (searchHistoryList.getSearchHistories() == null || searchHistoryList.getSearchHistories().size() == 0) {
            setHistory();
            return;
        }
        SearchHistory history = new SearchHistory();
        history.setHistorySearchContent(newSearchKey);
        history.setNum(searchHistoryList.getSearchHistories().size() + 1);
        history.setType(SearchHistory.HISTORY);
        SearchHead historyHeadInfo = new SearchHead(history);
        data.add(1, historyHeadInfo);
        searchAdapter.setNewData(data);
        searchHistoryList = XUtils.getHistorySearch(this);
    }

    /**
     * 更新历史搜素位置
     *
     * @param newSearchKey
     */
    private void refHistory(String newSearchKey) {
        for (Iterator<SearchHead> it = data.iterator(); it.hasNext(); ) {
            SearchHead s = it.next();
            if (s.t != null && s.t.getType() == SearchHistory.HISTORY && s.t.getHistorySearchContent().equals(newSearchKey)) {
                it.remove();
            }
        }
        addHistory(newSearchKey);
    }

    /**
     * 设置历史搜索
     */
    private void setHistory() {
        searchHistoryList = XUtils.getHistorySearch(this);
        if (searchHistoryList.getSearchHistories() != null && searchHistoryList.getSearchHistories().size() > 0) {
            historyHead = new SearchHead(true, "101");
            historyHead.setHeadType("101");
            data.add(0, historyHead);
            for (int i = 0; i < searchHistoryList.getSearchHistories().size(); i++) {
                SearchHistory history = new SearchHistory();
                history.setHistorySearchContent(searchHistoryList.getSearchHistories().get(i).getHistorySearchContent());
                history.setNum(searchHistoryList.getSearchHistories().size());
                history.setType(SearchHistory.HISTORY);
                SearchHead historyHeadInfo = new SearchHead(history);
                data.add(i + 1, historyHeadInfo);
            }
        }
    }

    private void initF() {
        if (mArticleFragment == null) {
            mArticleFragment = new ArticleFragment();
        }
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        if (mAnchorFragment == null) {
            mAnchorFragment = new AnchorFragment();
        }
        if (mUserFragment == null) {
            mUserFragment = new UserFragment();
        }
        mFragments.add(mArticleFragment);
        mFragments.add(videoFragment);
        mFragments.add(mAnchorFragment);
        mFragments.add(mUserFragment);
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void startSearch(String keyWord, int page){
//        viewPager.setCurrentItem(0);
        mArticleFragment.setSearch(keyWord, 1, page, false);
        videoFragment.setSearch(keyWord, 2, page, false);
        mAnchorFragment.setSearch(keyWord, 3, page, false);
        mUserFragment.setSearch(keyWord, 4, page, false);
        if (mArticleFragment.getUserVisibleHint()){
            mArticleFragment.setUserVisibleHint(true);
        }
        if (videoFragment.getUserVisibleHint()){
            videoFragment.setUserVisibleHint(true);
        }
        if (mAnchorFragment.getUserVisibleHint()){
            mAnchorFragment.setUserVisibleHint(true);
        }
        if (mUserFragment.getUserVisibleHint()){
            mUserFragment.setUserVisibleHint(true);
        }
    }

    /**
     * 搜索
     *
     * @param newSearchKey 搜索关键字
     * @param type         搜索关键字类型
     */
    private void search(String newSearchKey, int type) {
        if (type == HOT) {
            rvSearchList.setVisibility(View.GONE);
            linSearchResult.setVisibility(View.VISIBLE);
            startSearch(newSearchKey, 1);
        }
        if (type == HISTORY) {
            if (!oldSearchKey.equals(newSearchKey) && !newSearchKey.equals("") || !newSearchKey.equals("") && linSearchResult.getVisibility() != View.VISIBLE) {
                rvSearchList.setVisibility(View.GONE);
                linSearchResult.setVisibility(View.VISIBLE);
                startSearch(newSearchKey, 1);
                boolean isAdd = XUtils.setHistorySearch(SearchActivity.this, newSearchKey);
                if (isAdd)
                    addHistory(newSearchKey);
                else
                    refHistory(newSearchKey);
            } else if (newSearchKey.equals("")) {
                rvSearchList.setVisibility(View.VISIBLE);
                linSearchResult.setVisibility(View.GONE);
            }
            oldSearchKey = newSearchKey;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtCancel) {
            this.finish();
        }
    }
}
