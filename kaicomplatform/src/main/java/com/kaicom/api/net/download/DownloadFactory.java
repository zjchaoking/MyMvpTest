package com.kaicom.api.net.download;


public interface DownloadFactory<P, R> {

    DownloadRequest getRequest(P param);
    
    DownloadResponse getResponse(R resut);
    
    @SuppressWarnings("rawtypes")
    DownloadDaoOperator getDao();
    
}
