package com.xuanxing.tc.game.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.adapter.VideoAdapter;
import com.xuanxing.tc.game.adapter.VideoAdapter.MyOnClickListener;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.Videos;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tc on 2017/8/24.
 */

public class VideoFragment extends BaseFragment implements MyOnClickListener {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;

    @BindView(R.id.swipe_refresh_video)
    SwipeRefreshLayout mRefreshLayout;

    private static final String URL =
            "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());

    private int mScrollState;

    private int firstVisible;//当前第一个可见的item

    private int visibleCount;//当前可见的item个数

    private JCVideoPlayerStandard currPlayer;

    private VideoAdapter mVideoAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

    private boolean isLoadData = false;

    private int total;
    private int totalPage;
    private int page;

    private boolean isRef = false;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_video;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                page = 1;
                loadData(page);
            }
        });

        mVideoAdapter = new VideoAdapter(this.getContext(), mNewsInfos);
        mVideoAdapter.setOnClickListener(this);
        mVideoAdapter.setOnLoadMoreListener(new RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                ToastUtils.showToast(ArticleFragment.this.getContext(), "加载");
                if (totalPage > page) {
                    page += 1;
                    loadData(page);
                } else {
                    mVideoAdapter.loadMoreEnd();//加载结束
                }
            }
        }, rvVideo);


        rvVideo.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvVideo.setAdapter(mVideoAdapter);
        rvVideo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        autoPlayVideo(recyclerView);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }
        });
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(RecyclerView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.video_player) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.video_player);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void initData() {

    }

    @Override
    public void setOnClick(View view, int isAttention, int pos) {
        if (MyApplication.loginInfo == null) {
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);
            ToastUtils.showToast(this.getContext(), "未登录，请先登录");
            return;
        }
        if (isAttention == mVideoAdapter.FOLLOW || isAttention == mVideoAdapter.CANCER_FOLLOW) {
            follow((Button) view, isAttention, pos);
        }
        if (isAttention == mVideoAdapter.SHARE) {

        }
    }

    /**
     * 关注
     *
     * @param bt
     * @param isAttention
     * @param pos
     */
    private void follow(final Button bt, final int isAttention, int pos) {
        Observable<BaseBean> follow = mXuanXingApi.follow(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), isAttention, "" + mNewsInfos.get(pos).getId()).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(follow.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    if (isAttention == 1) {
                        ToastUtils.showToast(VideoFragment.this.getContext(), "关注成功");
                    } else {
                        ToastUtils.showToast(VideoFragment.this.getContext(), "取消关注");
                    }
                    followResult(bt, isAttention);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    /**
     * 关注结果
     *
     * @param isAttention 0 取消关注 1 关注
     */
    private void followResult(Button bt, int isAttention) {
        Map map = new LinkedHashMap();
        String num = "";
        if (isAttention == mVideoAdapter.FOLLOW) {
            bt.setText("取消关注");
            num = "" + (Integer.parseInt(MyApplication.loginInfo.getAttentionNum()) + 1);
        }
        if (isAttention == mVideoAdapter.CANCER_FOLLOW) {
            bt.setText("关注");
            num = "" + (Integer.parseInt(MyApplication.loginInfo.getAttentionNum()) - 1);
        }
        map.put("followNum", num);
        //事件发送
        EventBus.getDefault().post(new SendEvent("followNum", num));
        XUtils.modUserInfo(VideoFragment.this.getContext(), map);
    }

    private void loadData(int page) {
        isLoadData = true;
        Observable<BaseBeanClass<Videos>> videoList = mXuanXingApi.getVideoList(page, 10)
                .compose(RxUtil.<BaseBeanClass<Videos>>rxSchedulerHelper());
        mRxManager.add(videoList.subscribe(new Action1<BaseBeanClass<Videos>>() {
            @Override
            public void call(BaseBeanClass<Videos> newsListBaseBeanListClass) {
                mRefreshLayout.setRefreshing(false); //刷新完成
                mVideoAdapter.loadMoreComplete(); //加载完成
                if (newsListBaseBeanListClass.getCode().equals("0000")) {
                     /* 总数-总页数 */
                    total = newsListBaseBeanListClass.getData().getVideoList().getTotalCount();
                    if (total % 10 > 0) {
                        totalPage = total / 10 + 1;
                    } else {
                        totalPage = total / 10;
                    }
                    if (isRef) { //刷新
                        isRef = false;
                        mNewsInfos = newsListBaseBeanListClass.getData().getVideoList().getItems();
                    } else { //加载
                        mNewsInfos.addAll(newsListBaseBeanListClass.getData().getVideoList().getItems());
                    }
                    mVideoAdapter.setNewData(mNewsInfos);
                } else {
                    toastMessage(newsListBaseBeanListClass.getCode(), newsListBaseBeanListClass.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void call(Throwable throwable) {
        super.call(throwable);
        mVideoAdapter.loadMoreFail(); //加载失败
        mRefreshLayout.setRefreshing(false); //刷新失败
    }

    @Override
    protected void initLazyView() {
        if (!isLoadData) {
            loadData(1);
        }
    }
}
