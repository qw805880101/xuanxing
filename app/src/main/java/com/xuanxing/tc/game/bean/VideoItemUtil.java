package com.xuanxing.tc.game.bean;

import com.xuanxing.tc.game.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/31.
 */

public class VideoItemUtil {

    public static List<VideoInfo> getRecommendItem(){
        List<VideoInfo> videoInfos = new ArrayList<>();
        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));

        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));

        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));

        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));

        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));
        return videoInfos;
    }

}
