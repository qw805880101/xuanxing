package com.xuanxing.tc.game.api;

import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.BaseBeanListClass;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.bean.VedioList;

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
     *
     * @param socialUid  第三方登陆ID
     * @param deviceId   设备ID
     * @param deviceType 设备类型(1:Android,2:IOS)
     * @param loginType  登录类型（1、手机号码登录；2、QQ用户；3、微信）
     * @param nickname   昵称（第三方登录需传）
     * @param headIcon   头像路径（第三方登录需传）
     * @param mobile     手机号
     * @param smsCode    验证码
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

    /**
     * 获取验证码
     *
     * @param mobile
     * @return
     */
    @POST("api/member/msgSend")
    Observable<BaseBean> getSmsCode(@Query("mobile") String mobile);

    /**
     * 获取首页推荐列表
     *
     * @param requestBody
     * @param page        第几页,默认第1页
     * @param limit       每页几条,默认10条
     * @return
     */
    @POST("api/home/newsList")
    Observable<BaseBeanListClass<NewsInfo>> getNewsList(/*@Body RequestBody requestBody,*/
                                                        @Query("page") String page,
                                                        @Query("limit") String limit);

    /**
     * 获取首页视频列表
     *
     * @param page
     * @param limit
     * @return
     */
    @POST("api/home/videoList")
    Observable<BaseBeanClass<VedioList>> getVedioList(/*@Body RequestBody requestBody,*/
                                                      @Query("page") String page,
                                                      @Query("limit") String limit);

    /**
     * 编辑用户信息
     * @param app_m_id
     * @param app_p_token
     * @param nickname
     * @param birthday
     * @param alipay
     * @param sex
     * @param headicon
     * @param intro
     * @return
     */
    @POST("api/member/mEditInfo")
    Observable<BaseBean> modUserInfo(@Query("app_m_id") String app_m_id,
                                     @Query("app_p_token") String app_p_token,
                                     @Query("nickname") String nickname,
                                     @Query("birthday") String birthday,
                                     @Query("alipay") String alipay,
                                     @Query("sex") String sex,
                                     @Query("headicon") String headicon,
                                     @Query("intro") String intro);

    /**
     * 退出登录
     * @param app_m_id
     * @param app_p_token
     * @return
     */
    @POST("api/member/mLogout")
    Observable<BaseBean> loginOut(@Query("app_m_id") String app_m_id,
                                     @Query("app_p_token") String app_p_token);

    /**
     * 关注用户
     * @param app_m_id
     * @param app_p_token
     * @param newsMemberId
     * @return
     */
    @POST("api/relation/addAttention")
    Observable<BaseBean> follow(@Query("app_m_id") String app_m_id,
                                  @Query("app_p_token") String app_p_token,
                                  @Query("newsMemberId") String newsMemberId);


    @POST("xxx")
    Observable<Object> test(@Body RequestBody file);

}
