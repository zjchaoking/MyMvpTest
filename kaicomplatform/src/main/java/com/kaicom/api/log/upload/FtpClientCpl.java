package com.kaicom.api.log.upload;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.kaicom.api.log.KlLoger;

import android.text.TextUtils;

/**
 * FTP上传
 * 
 * @author scj
 */
public class FtpClientCpl {

    /** 默认编码格式 */
    public static final String DEFAULT_ENCODE = "utf-8";
    /** 默认超时时间 */
    public static final int DEFAULT_CONN_TIME = 20 * 1000;

    private FtpUploadParam param;
    private FTPClient ftpClient;

    public FtpClientCpl(FtpUploadParam param) {
        this.param = param;
        ftpClient = new FTPClient();
        initConfig();
    }

    private void initConfig() {
        setConnectTimeout(DEFAULT_CONN_TIME);
        setEncode(DEFAULT_ENCODE);
        ftpClient.setBufferSize(1024);
    }

    public void setConnectTimeout(int time) {
        ftpClient.setConnectTimeout(time);
    }

    public void setEncode(String encode) {
        ftpClient.setControlEncoding(encode);
    }

    /**
     * ftp上传
     * @return
     */
    public FtpResult upload(FtpItem ftpItem) {
        FtpResult result = FtpResult.LOGIN_FAIL;
        FileInputStream fis = null;
        try {
            String serverFile = ftpItem.getServerFileName();
            if (TextUtils.isEmpty(serverFile))
                return FtpResult.ILLEGAL_PARAM;
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            fis = new FileInputStream(ftpItem.getLocalFile());
            if (ftpClient.storeFile(serverFile, fis)) { // 上传是否成功
                result = FtpResult.SUCCESS;
            } else
                result = FtpResult.FAIL;
        } catch (IOException e) {
            KlLoger.error("ftp上传出错", e);
            result = FtpResult.ERROR;
        }
        return result;
    }

    // 在ftp服务器上创建目录
    public boolean mkRemoteDir(String remotePath) {
        if (TextUtils.isEmpty(remotePath))
            return false;
        String[] pathes = remotePath.split("/");
        for (String onepath : pathes) {
            if (onepath == null || "".equals(onepath.trim())) {
                continue;
            }
            try {
                if (!ftpClient.changeWorkingDirectory(onepath)) {
                    ftpClient.makeDirectory(onepath);
                    boolean mkdir = ftpClient.changeWorkingDirectory(onepath);
                    if (!mkdir)
                        return false;
                }
            } catch (IOException e) {
                KlLoger.error("创建目录异常", e);
                return false;
            }
        }
        return true;
    }
    
    public boolean changeToParentDir() {
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 登录ftp
    public boolean login() {
        try {
            ftpClient.connect(param.getIp(), param.getPort());
            boolean loginResult = ftpClient.login(param.getUserName(),
                    param.getPassword());
            int returnCode = ftpClient.getReplyCode();
            return loginResult && FTPReply.isPositiveCompletion(returnCode);
        } catch (SocketException e) {
            KlLoger.error("ftp登陆socket异常", e);
        } catch (IOException e) {
            KlLoger.error("ftp登陆io异常", e);
        }
        return false;
    }

    public void logout() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            KlLoger.error("ftp退出io异常", e);
        }
    }

    public enum FtpResult {
        /** 上传成功 */
        SUCCESS,
        /** 上传失败 */
        FAIL,
        /** 参数异常 */
        ILLEGAL_PARAM,
        /** 出现异常 */
        ERROR,
        /** 登录失败 */
        LOGIN_FAIL,
        /** 未找到文件 */
        FILE_NOT_FOUND,
        /** 压缩文件失败 */
        ZIP_FAIL;
    }

}
