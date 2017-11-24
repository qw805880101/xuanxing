package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.InterestAdapter;
import com.xuanxing.tc.game.adapter.InterestAdapter.ChannelState;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.InterestSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 修改感兴趣游戏界面
 * <p>
 * Created by tianchao on 2017/11/24.
 */

public class ModInterestActivity extends BaseActivity implements ChannelState {

    @BindView(R.id.rv_added)
    RecyclerView mRvAdded;
    @BindView(R.id.rv_no_added)
    RecyclerView mRvNoAdded;

    private InterestAdapter addedAdapter;
    private InterestAdapter noAddedAdapter;

    private List<HotGameList> addedData = new ArrayList<>();
    private List<HotGameList> noAddedData = new ArrayList<>();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("感兴趣的")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ModInterestActivity.this.finish();
                    }
                })
                .setRightText("完成")
                .setRightOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModInterestActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mod_interest;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        addedAdapter = new InterestAdapter(addedData);
        addedAdapter.setChannelState(this);
        noAddedAdapter = new InterestAdapter(noAddedData);
        noAddedAdapter.setChannelState(this);

        mRvAdded.setLayoutManager(new GridLayoutManager(this, 3));
        mRvAdded.addItemDecoration(new InterestSpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_1)));
        mRvAdded.setAdapter(addedAdapter);

        mRvNoAdded.setLayoutManager(new GridLayoutManager(this, 3));
        mRvNoAdded.addItemDecoration(new InterestSpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_1)));
        mRvNoAdded.setAdapter(noAddedAdapter);

    }

    @Override
    public void initdata() {
        getMLikeGame();
    }

    private void getMLikeGame() {
        Observable<BaseBeanClass<BaseList>> mLikeGame = mXuanXingApi.mGetLikeGameList(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token()).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(mLikeGame.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseListBaseBeanClass) {
                if (baseListBaseBeanClass.getCode().equals("0000")) {
                    addedData = baseListBaseBeanClass.getData().getLikeGameList();
                    for (HotGameList hotGameList : addedData) {
                        hotGameList.setType(0);
                    }
                    addedAdapter.setNewData(addedData);
                } else {
                    toastMessage(baseListBaseBeanClass.getCode(), baseListBaseBeanClass.getMsg());
                }
            }
        }, this));
    }

    private void addOrDelGame(final int type, String gameCategoryCode, final int position) {
        Observable<BaseBean> addOrDelGame = mXuanXingApi.addOrDelLikeGame(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(), type, gameCategoryCode).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(addOrDelGame.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    if (type == 1) {
                        noAddedData.get(position).setType(0);
                        addedData.add(noAddedData.get(position));
                        noAddedData.remove(position);
                    }
                    if (type == 0) {
                        addedData.get(position).setType(1);
                        noAddedData.add(addedData.get(position));
                        addedData.remove(position);
                    }
                    addedAdapter.setNewData(addedData);
                    noAddedAdapter.setNewData(noAddedData);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void channelState(View view, int position, int type) {
        if (type == 1) {
            addOrDelGame(type, noAddedData.get(position).getCategoryCode(), position);
        }
        if (type == 0) {
            addOrDelGame(type, addedData.get(position).getCategoryCode(), position);
        }
    }
}
