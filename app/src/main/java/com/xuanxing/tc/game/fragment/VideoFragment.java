package com.xuanxing.tc.game.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.VideoAdapter;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.VideoItemUtil;
import com.xuanxing.tc.game.utils.OnlineVideoListItem;
import com.xuanxing.tc.game.utils.VideoListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tc on 2017/8/24.
 */

public class VideoFragment extends BaseFragment {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;

    //视频数据，相当于普通adapter里的datas
    private List<VideoListItem> mLists = new ArrayList<>();

    //它充当ListItemsVisibilityCalculator和列表（ListView, RecyclerView）之间的适配器（Adapter）。
    private RecyclerViewItemPositionGetter mItemsPositionGetter;

    //ListItemsVisibilityCalculator可以追踪滑动的方向并在过程中计算每个Item的可见度
    //SingleListViewItemActiveCalculator会在滑动时获取每个View的可见度百分比.
    //所以其构造方法里需要传入mLists，而mLists里的每个item实现了ListItem接口
    //的getVisibilityPercents方法，也就是返回当前item可见度的方法.
    //这样ListItemsVisibilityCalculator就可以计算当前item的可见度了.

    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mLists);

    //SingleVideoPlayerManager就是只能同时播放一个视频。
    //当一个view开始播放时，之前那个就会停止
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {
        }
    });

    private static final String URL =
            "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());

    private int mScrollState;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {

        //添加视频数据
        for (int i = 0; i < 10; ++i) {
            mLists.add(new OnlineVideoListItem(mVideoPlayerManager, "测试", "http://115.159.159.65:8080/EAsy/cover.jpg", URL));
        }

        return R.layout.fragment_home_video;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        try{
            rvVideo.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rvVideo.setAdapter(new VideoAdapter(this.getContext(), VideoItemUtil.getRecommendItem(), mLists));
//
//            rvVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                    mScrollState = scrollState;
//                    if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !mLists.isEmpty()){
//
//                        mVideoVisibilityCalculator.onScrollStateIdle(
//                                mItemsPositionGetter,
//                                mLayoutManager.findFirstVisibleItemPosition(),
//                                mLayoutManager.findLastVisibleItemPosition());
//                    }
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    if(!mLists.isEmpty()){
//                        mVideoVisibilityCalculator.onScroll(
//                                mItemsPositionGetter,
//                                mLayoutManager.findFirstVisibleItemPosition(),
//                                mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
//                                mScrollState);
//                    }
//                }
//            });
//
//            mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, rvVideo);

        }catch (Exception e){

        }
    }

    //文档上的默认实现，复制下来
    //onResume()中调用方法，使屏幕亮起时启动对View的可见度的计算。
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(!mLists.isEmpty()){
//            // need to call this method from list view handler in order to have filled list
//
//            rvVideo.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    mVideoVisibilityCalculator.onScrollStateIdle(
//                            mItemsPositionGetter,
//                            mLayoutManager.findFirstVisibleItemPosition(),
//                            mLayoutManager.findLastVisibleItemPosition());
//
//                }
//            });
//        }
//    }


//    @Override
//    public void onStop() {
//        super.onStop();
//        mVideoPlayerManager.resetMediaPlayer(); // 页面不显示时, 释放播放器
//    }
}
