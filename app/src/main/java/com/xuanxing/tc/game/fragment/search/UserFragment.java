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
import com.xuanxing.tc.game.bean.NewsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 搜索-用户
 * Created by tianchao on 2017/10/17.
 */

public class UserFragment extends BaseFragment {

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
        Observable<BaseBean> search = mXuanXingApi.search(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), keyWord,
                keyType, page, 10).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(search.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {

            }
        }, this));
    }

    @Override
    protected void initLazyView() {
        search(keyWord, keyType, page);
    }
}
