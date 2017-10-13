package com.xuanxing.tc.game.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.VideoAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseBeanListClass;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.VedioList;
import com.xuanxing.tc.game.bean.VideoItemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by tc on 2017/8/24.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;

    private static final String URL =
            "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());

    private int mScrollState;

    private int firstVisible;//当前第一个可见的item

    private int visibleCount;//当前可见的item个数

    private JCVideoPlayerStandard currPlayer;

    private VideoAdapter mVideoAdapter;

    private List<NewsInfo> mNewsInfos = new ArrayList<>();

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

        mVideoAdapter = new VideoAdapter(this.getContext(), mNewsInfos);

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
        Observable<BaseBeanClass<VedioList>> vedioList = mXuanXingApi.getVedioList("1", "10")
                .compose(RxUtil.<BaseBeanClass<VedioList>>rxSchedulerHelper());
        mRxManager.add(vedioList.subscribe(new Action1<BaseBeanClass<VedioList>>() {
            @Override
            public void call(BaseBeanClass<VedioList> newsListBaseBeanListClass) {
                if (newsListBaseBeanListClass.getCode().equals("0000")) {
                    mVideoAdapter.setNewData(newsListBaseBeanListClass.getData().getVideoList());
                } else {
                    toastMessage(newsListBaseBeanListClass.getCode(), newsListBaseBeanListClass.getMsg());
                }
            }
        }, this));

    }

}
