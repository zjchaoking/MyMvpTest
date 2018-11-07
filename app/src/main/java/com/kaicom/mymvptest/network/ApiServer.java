package com.kaicom.mymvptest.network;

import com.kaicom.mymvptest.network.response.BaseResponse;
import com.kaicom.mymvptest.network.response.CheckSoftUpgradeResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

   @POST("CheckSoftUpgrade")
   @FormUrlEncoded
   Observable<CheckSoftUpgradeResponse> checkSoftUpgrade(@Field("serverCode") String serverCode, @Field("versionCode") String versionCode);

   @Streaming
   Call<ResponseBody> doSoftUpgrade(@Url String url);

}
