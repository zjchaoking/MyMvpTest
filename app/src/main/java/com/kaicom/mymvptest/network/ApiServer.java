package com.kaicom.mymvptest.network;

import com.kaicom.mymvptest.network.response.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LeoJin on 2018/4/26.
 */

public interface ApiServer {

   @POST("DoLogin")
   @FormUrlEncoded
   Observable<BaseResponse> doLogin(@FieldMap Map<String,Object> map);

   @POST("DoLogin")
   @FormUrlEncoded
   Observable<BaseResponse> doRegister(@FieldMap Map<String,Object> map);

}
