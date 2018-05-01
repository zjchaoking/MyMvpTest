package com.kaicom.api.log.upload;

import static android.text.TextUtils.*;

/**
 * ftp上传参数设置
 * 
 * @author scj
 */
public class FtpUploadParam {

    /** 默认的上传ip */
    public static final String DEFAULT_UPLOAD_IP = "122.224.143.206";
    /** 默认的上传port */
    public static final int DEFAULT_UPLOAD_PORT = 1188;
    /** ftp用户 */
    public static final String DEFAULT_USER = "kaicomms";
    /** ftp密码 */
    public static final String DEFAULT_PWD = "kaicom!1234";
    /** 默认的服务器路径 */
    public static final String DEFAULT_REMOTE_PATH = "KlLog/";
    
    private String ip;
    private int port = DEFAULT_UPLOAD_PORT;
    private String userName;
    private String password;

    public String getIp() {
        return isEmpty(ip) ? DEFAULT_UPLOAD_IP : ip;
    }
    
    /**
     * 设置ftp上传的ip
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public String getUserName() {
        return isEmpty(userName) ? DEFAULT_USER : userName;
    }
    
    /**
     * 设置ftp登录用户名
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return isEmpty(password) ? DEFAULT_PWD : password;
    }
    
    /**
     * 设置ftp登录密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
