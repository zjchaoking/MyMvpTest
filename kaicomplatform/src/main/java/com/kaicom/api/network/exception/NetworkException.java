package com.kaicom.api.network.exception;

/**
 * 源文件名称：NetworkException.java.
 * 软件著作权：恒生电子股份有限公司 版权所有.
 * 系统名称：Android dtk.
 * 模块名称：网络层异常.
 * 功能说明: 作为DTK开发网络部分异常类.
 * 作者 ：yukk@hundsun.com .
 * 开发时间：2011-6-13 下午01:41:27 .
 * 审核 :.
 */
public class NetworkException extends Exception {
/**
 * serialVersionUID.
 */
    private static final long serialVersionUID = -2079213165753393757L;
/**
 * NetworkException.
 * @param str str
 */
    public NetworkException(String str) {
        super(str);
    }
/**
 * NetworkException.
 * @param str str
 * @param e e
 */
    public NetworkException(String str, Throwable e) {
        super(str, e);
    }
}
