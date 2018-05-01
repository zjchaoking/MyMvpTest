package com.kaicom.api.net.download;


public interface IDownloadType<P, R> {

    String getTypeName();
    
    DownloadFactory<P, R> getDownloadFactory();
    
}
