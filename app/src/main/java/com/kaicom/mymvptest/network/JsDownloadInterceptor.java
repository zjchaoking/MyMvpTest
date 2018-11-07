package com.kaicom.mymvptest.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Description: 带进度 下载  拦截器
 * Created by LeoJin on 2018/5/2.
 */

public class JsDownloadInterceptor implements Interceptor {
    private JsDownloadListener downloadListener;

    public JsDownloadInterceptor(JsDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new JsResponseBody(response.body(), downloadListener)).build();
    }
}
