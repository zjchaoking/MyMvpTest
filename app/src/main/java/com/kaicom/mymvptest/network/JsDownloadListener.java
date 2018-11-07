package com.kaicom.mymvptest.network;

/**
 * Description: 下载进度回调
 * Created by LeoJin on 2018/5/2.
 */

public interface JsDownloadListener {
    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);
}
