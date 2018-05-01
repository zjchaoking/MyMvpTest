package com.kaicom.api.log.upload;

import android.os.Handler;
import android.os.Looper;

import com.kaicom.api.log.upload.FtpClientCpl.FtpResult;

import java.io.IOException;

import static com.kaicom.api.util.FileUtil.deleteFile;
import static com.kaicom.api.util.FileUtil.isFileExist;
import static com.kaicom.api.util.FileUtil.zipFile;

/**
 * 上传日志的线程
 * 
 * @author scj
 */
public class FtpUploadRunner implements Runnable {
    
    private String serverFileName;
    private String localFile;
    private Handler handler;
    private FtpUploadListener listener;
    private FtpResult result;
    private String serverPath = "KlLog";
    
    /**
     * ftp上传线程
     * @param serverFileName ftp上的文件名
     * @param localFile 本地文件名
     * @param listener 上传结果回调接口
     */
    public FtpUploadRunner(String serverFileName, String localFile, FtpUploadListener listener) {
        this.serverFileName = serverFileName;
        this.localFile = localFile;
        this.listener = listener;
        handler = new Handler(Looper.getMainLooper());
    }
    
    public void setServerPath(String path) {
    	this.serverPath = path;
    }
    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
    @Override
    public void run() {
        if (!isFileExist(localFile)) {
            result = FtpResult.FILE_NOT_FOUND;
            handler.post(failRunner);
        }
        // 压缩文件
        String zipFile = localFile.substring(0, localFile.lastIndexOf(".") + 1) + "zip";
        try {
            zipFile(localFile, zipFile);
        } catch (IOException e) {
        	result = FtpResult.ZIP_FAIL;
        	handler.post(failRunner);
        }
        
        if (!isFileExist(zipFile)) {
            result = FtpResult.FILE_NOT_FOUND;
            handler.post(failRunner);
        }
        
        // 设置ftp参数 
        FtpUploadParam param = new FtpUploadParam();
        FtpItem ftpItem = new FtpItem();
        ftpItem.setLocalFile(zipFile);
        ftpItem.setServerFileName(serverFileName);
        
        // 登陆ftp并上传
        FtpClientCpl cpl = new FtpClientCpl(param);
        if (cpl.login()) {
            cpl.mkRemoteDir(serverPath);
            result = cpl.upload(ftpItem);
            cpl.logout();
        }
        
        // 处理上传结果
        if (result == FtpResult.SUCCESS) {
            deleteFile(zipFile);
            handler.post(successRunner);
        } else {
            result = FtpResult.ERROR;
            handler.post(failRunner);
        }
    }
    
    private Runnable successRunner = new Runnable() {

        @Override
        public void run() {
            listener.onSuccess();
        }
    };
    
    private Runnable failRunner = new Runnable() {
        
        @Override
        public void run() {
            listener.onFail(result);
        }
    };
    
    public interface FtpUploadListener {
        
        void onSuccess();
        
        void onFail(FtpResult result);
    }

}
