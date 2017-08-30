package com.xuanxing.tc.game.api;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/8/30.
 */

public interface HomeApi {

    @POST("xxx")
    Observable<Object> test(@Body RequestBody file);

}
