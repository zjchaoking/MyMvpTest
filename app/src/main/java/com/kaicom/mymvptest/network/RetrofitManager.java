package com.kaicom.mymvptest.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeoJin on 2018/4/26.
 */

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private static Retrofit retrofit;
    private String url = "http://chaoking.51vip.biz/MyTestWebProject/";

    private RetrofitManager() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(getOkHttpClent())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
                    .build();
        }

    }

    public static ApiServer getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofit.create(ApiServer.class);
    }

    private OkHttpClient getOkHttpClent(){
        OkHttpClient  okHttpClient= new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(new JsDownloadInterceptor(new JsDownloadListener() {
                    @Override
                    public void onStartDownload() {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onFinishDownload() {

                    }

                    @Override
                    public void onFail(String errorInfo) {

                    }
                }))
                .build();
        return okHttpClient;
    }

}
