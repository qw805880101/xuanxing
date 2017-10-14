package com.xuanxing.tc.game.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HomeActivity;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.adapter.VideoAdapter;
import com.xuanxing.tc.game.adapter.VideoAdapter.MyOnClickListener;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseBeanListClass;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.VedioList;
import com.xuanxing.tc.game.bean.VideoItemUtil;
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
        mVideoAdapter.setOnClickListener(this);
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
                    mNewsInfos = newsListBaseBeanListClass.getData().getVideoList();
                    mVideoAdapter.setNewData(mNewsInfos);
                } else {
                    toastMessage(newsListBaseBeanListClass.getCode(), newsListBaseBeanListClass.getMsg());
                }
            }
        }, this));

    }

    @Override
    public void setOnClick(View view, int type, int pos) {
        if (type == mVideoAdapter.FOLLOW) {
            follow(pos);
        }
        if (type == mVideoAdapter.SHARE) {

        }
    }

    private void follow(int pos) {
        if (MyApplication.loginInfo == null) {
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);
            ToastUtils.showToast(this.getContext(), "未登录，请先登录");
            return;
        }
        final Map map = new LinkedHashMap();
        final String num = "" + (Integer.parseInt(MyApplication.loginInfo.getAttentionNum()) + 1);
        map.put("followNum", num);
        Observable<BaseBean> follow = mXuanXingApi.follow(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), /*"" + mNewsInfos.get(pos).getMemberId())*/ "123").compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(follow.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    ToastUtils.showToast(VideoFragment.this.getContext(), "关注成功");
                    //事件发送
                    EventBus.getDefault().post(new SendEvent("followNum", num));
                    XUtils.modUserInfo(VideoFragment.this.getContext(), map);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

}
