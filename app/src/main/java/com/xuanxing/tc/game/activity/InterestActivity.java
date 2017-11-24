package com.xuanxing.tc.game.activity;

import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
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
import com.xuanxing.tc.game.utils.CustomLinearLayoutManager;
import com.xuanxing.tc.game.utils.InterestSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tianchao on 2017/11/22.
 */

public class InterestActivity extends BaseActivity implements ChannelState, OnClickListener {

    @BindView(R.id.bt_batch_change)
    Button mBtBatchChange;
    @BindView(R.id.bt_finish)
    Button mBtFinish;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.rv_interest)
    RecyclerView mRvInterest;
    @BindView(R.id.lin_rv_interest)
    LinearLayout mLinearLayout;

    private InterestAdapter mInterestAdapter;

    private List<HotGameList> data = new ArrayList<>();
    private List<HotGameList> allData = new ArrayList<>();

    private int limit;

    private int page = 1;

//    private int totalPage;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("请选择喜欢的游戏")
                .setTitleTextColor(this, R.color.white)
                .setRightText("跳过")
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InterestActivity.this.finish();
                    }
                })
                .build();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_interest;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mBtBatchChange.setOnClickListener(this);
        mBtFinish.setOnClickListener(this);

        ViewTreeObserver vto = mLinearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int h = mLinearLayout.getHeight();
                int w = mLinearLayout.getWidth();
                mInterestAdapter.setHeight(h);
                if (h / 4 > 320) {
                    limit = 12;
                } else {
                    limit = 9;
                }
                loadData();
            }
        });

        mInterestAdapter = new InterestAdapter(data);
        mInterestAdapter.setChannelState(this);
        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(this, 3);
        customLinearLayoutManager.setScrollEnabled(false);
        mRvInterest.setLayoutManager(customLinearLayoutManager);
        mRvInterest.addItemDecoration(new InterestSpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_1)));
        mRvInterest.setAdapter(mInterestAdapter);
    }

    @Override
    public void initdata() {
//        loadData();
    }

    @Override
    public void channelState(View view, int position, int type) {
        ImageView imageView = (ImageView) view;
        addOrDelGame(imageView, data.get(position).getType(), data.get(position).getCategoryCode(), position);
    }

    private void loadData() {
        Observable<BaseBeanClass<BaseList>> getListGame = mXuanXingApi.getLikeGameList(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(), page, 100).compose(RxUtil.<BaseBeanClass<BaseList>>rxSchedulerHelper());
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

                    change();
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    private void addOrDelGame(final ImageView imageView, final int type, String gameCategoryCode, final int position) {
        Observable<BaseBean> addOrDelGame = mXuanXingApi.addOrDelLikeGame(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(), type, gameCategoryCode).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(addOrDelGame.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    if (type == 1) {
                        imageView.setImageResource(R.mipmap.jian);
                        data.get(position).setType(0);
                    }
                    if (type == 0) {
                        imageView.setImageResource(R.mipmap.jia);
                        data.get(position).setType(1);
                    }
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void onClick(View view) {
        if (view == mBtBatchChange) {
//            if (page < totalPage) {
//                page++;
//                loadData();
//            }
            change();
        }
        if (view == mBtFinish) {
            InterestActivity.this.finish();
        }
    }

    public void change() {
        data.clear();
        List<HotGameList> list = new ArrayList<>();

        list.addAll(allData);

        // 初始化随机数
        Random rand = new Random();

        // 取得集合的长度，for循环使用
        int size = list.size();

        // 遍历整个items数组
        for (int i = 0; i < limit; i++) {
            // 任意取一个0~size的整数，注意此处的items.size()是变化的，所以不能用前面的size会发生数组越界的异常
            int myRand = rand.nextInt(list.size());
            //将取出的这个元素放到存放结果的集合中
            data.add(list.get(myRand));
            //从原始集合中删除该元素防止重复。注意，items数组大小发生了改变
            list.remove(myRand);
        }
        mInterestAdapter.setNewData(data);
    }

}
