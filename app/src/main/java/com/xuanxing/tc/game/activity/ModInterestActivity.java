package com.xuanxing.tc.game.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.InterestAdapter;
import com.xuanxing.tc.game.adapter.InterestAdapter.ChannelState;
import com.xuanxing.tc.game.adapter.ModInterestAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseList;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.bean.ModInterestList;
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

//    @BindView(R.id.rv_added)
//    RecyclerView mRvAdded;
//    @BindView(R.id.rv_no_added)
//    RecyclerView mRvNoAdded;

    @BindView(R.id.rv_interest)
    RecyclerView rvInterest;

    private List<HotGameList> addedData = new ArrayList<>();
    private List<HotGameList> noAddedData = new ArrayList<>();
    private List<HotGameList> allData = new ArrayList<>();

    private List<ModInterestList> mModInterestList = new ArrayList<>();

    private ModInterestAdapter mModInterestAdapter;

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
        mModInterestAdapter = new ModInterestAdapter(mModInterestList, this);

        rvInterest.setLayoutManager(new LinearLayoutManager(this));
        rvInterest.setAdapter(mModInterestAdapter);
    }

    @Override
    public void initdata() {
        getAllData();
    }

    /**
     * 获取喜欢的游戏列表
     */
    private void getMLikeGame() {
        mModInterestList.clear();
        Observable<BaseBeanClass<BaseList>> mLikeGame = mXuanXingApi.mGetLikeGameList(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token()).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(mLikeGame.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseListBaseBeanClass) {
                if (baseListBaseBeanClass.getCode().equals("0000")) {
                    addedData = baseListBaseBeanClass.getData().getLikeGameList();
                    noAddedData = getNoAdded(allData, addedData);
                    for (HotGameList hotGameList : addedData) {
                        hotGameList.setType(0);
                    }
                    ModInterestList modInterestList = new ModInterestList();
                    modInterestList.setAddedData(addedData);
                    modInterestList.setNoAddedData(noAddedData);
                    mModInterestList.add(modInterestList);
                    mModInterestAdapter.setNewData(mModInterestList);
                } else {
                    toastMessage(baseListBaseBeanClass.getCode(), baseListBaseBeanClass.getMsg());
                }
            }
        }, this));
    }

    /**
     * 添加或删除
     * @param type
     * @param gameCategoryCode
     * @param position
     */
    private void addOrDelGame(final int type, String gameCategoryCode, final int position) {
        mModInterestList.clear();
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
                    ModInterestList modInterestList = new ModInterestList();
                    modInterestList.setAddedData(addedData);
                    modInterestList.setNoAddedData(noAddedData);
                    mModInterestList.add(modInterestList);
                    mModInterestAdapter.setNewData(mModInterestList);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    /**
     * 获取全部游戏列表
     */
    private void getAllData() {
        Observable<BaseBeanClass<BaseList>> getListGame = mXuanXingApi.getLikeGameList(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(), 1, 100).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
        mRxManager.add(getListGame.subscribe(new Action1<BaseBeanClass<BaseList>>() {
            @Override
            public void call(BaseBeanClass<BaseList> baseBean) {
                if (baseBean.getCode().equals("0000")) {
//                    data.clear();
//                    if (baseBean.getData().getSelectLikeGameList().getTotalCount() % limit > 1) {
//                        totalPage = baseBean.getData().getSelectLikeGameList().getTotalCount() / limit + 1;
//                    } else {
//                        totalPage = baseBean.getData().getSelectLikeGameList().getTotalCount() / limit;
//                    }

                    allData.addAll(baseBean.getData().getSelectLikeGameList().getItems());
                    getMLikeGame();
//                    change();
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

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<HotGameList> getNoAdded(List<HotGameList> list1, List<HotGameList> list2) {

        for (int i = 0; i < list2.size(); i++) {
            for (HotGameList string : list1) {
                if (list2.get(i).getId() == string.getId()) {
                    list1.remove(string);
                    break;
                }
            }
        }
        return list1;

    }

}
