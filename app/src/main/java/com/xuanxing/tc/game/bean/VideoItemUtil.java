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
                "12:00", R.mipmap.video, "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "1.2K"));

        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
                "12:00", R.mipmap.video, "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", "1.2K"));

//        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
//                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));
//
//        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
//                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));
//
//        videoInfos.add(new VideoInfo(R.mipmap.image_test, "高爽",
//                "12:00", R.mipmap.video, "干货教学：德邦全方位分析，老司机踏遍德玛西亚", "1.2K"));
        return videoInfos;
    }

}
