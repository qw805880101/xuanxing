package com.xuanxing.tc.game.api;

import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.LoginInfo;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/8/30.
 */

public interface Api {

    /**
     * 登录接口
     * @param socialUid     第三方登陆ID
     * @param deviceId      设备ID
     * @param deviceType    设备类型(1:Android,2:IOS)
     * @param loginType     登录类型（1、手机号码登录；2、QQ用户；3、微信）
     * @param nickname      昵称（第三方登录需传）
     * @param headIcon      头像路径（第三方登录需传）
     * @param mobile        手机号
     * @param smsCode       验证码
     * @return
     */
    @POST("api/member/mLogin")
    Observable<BaseBeanClass<LoginInfo>> login(@Query("social_uid") String socialUid,
                                               @Query("device_id") String deviceId,
                                               @Query("device_type") String deviceType,
                                               @Query("login_type") String loginType,
                                               @Query("nickname") String nickname,
                                               @Query("headIcon") String headIcon,
                                               @Query("mobile") String mobile,
                                               @Query("smsCode") String smsCode);


    @POST("xxx")
    Observable<Object> test(@Body RequestBody file);

}
