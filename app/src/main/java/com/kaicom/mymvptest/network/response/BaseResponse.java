package com.kaicom.mymvptest.network.response;

/**
 * Created by LeoJin on 2018/4/29.
 */

public class BaseResponse {
    private boolean isSuccess;//是否请求成功
    private String errorCode="";
    private String errorMsg="";//错误信息提示


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
