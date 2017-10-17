package com.xuanxing.tc.game.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.News;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.SearchList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 搜索-文章
 * Created by tianchao on 2017/10/17.
 */

public class ArticleFragment extends BaseFragment {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;

    private RecommendAdapter mRecommendAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    private String keyWord;
    private int keyType;
    private int page;

    public void setSearch(String keyWord, int keyType, int page){
        this.keyWord = keyWord;
        this.keyType = keyType;
        this.page = page;

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        mRecommendAdapter = new RecommendAdapter(mNewsInfos);

        rvRecommend.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvRecommend.setAdapter(mRecommendAdapter);
    }

    /**
     * 搜索
     * @param keyWord
     * @param keyType
     * @param page
     */
    private void search(String keyWord, int keyType, int page) {
        startProgressDialog(this.getContext());
        Observable<BaseBeanClass<SearchList>> search = mXuanXingApi.search(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), keyWord,
                keyType, page, 10).compose(RxUtil.<BaseBeanClass<SearchList>>rxSchedulerHelper());
        mRxManager.add(search.subscribe(new Action1<BaseBeanClass<SearchList>>() {
            @Override
            public void call(BaseBeanClass<SearchList> baseBean) {
                stopProgressDialog();
                if (baseBean.getCode().equals("0000")){
                    mRecommendAdapter.setNewData(baseBean.getData().getNewsList().getItems());
                    System.out.println("获取信息列表成功" + baseBean.getData().getNewsList().getItems().size());
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    @Override
    protected void initLazyView() {
        System.out.println("aaainitLazyView");
        search(keyWord, keyType, page);
    }
}
