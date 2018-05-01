/**
 * <p>文件介绍信息</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 * @author
 * @version
 * @history
 */
package com.kaicom.api.network.error;

/**
 * ErrorConstant.
 * 
 * @author qiyp
 * @version
 */
public interface ErrorConstant {
    /**
     * 连接正常.
     */
    public static final int CONNECT_OK = 0;

    /**
     * 认证OK.
     */
    public static final int AUTHENTICATE_OK = 0;
    /**
     * 连接关闭.
     */
    public static final int CONNECT_CLOSED = -1;

    /**
     * 网络不可用.
     */
    public static final int NETWORK_NOT_ENABLE = -101;

    /**
     * 连接超时.
     */
    public static final int CONNECT_TIME_OUT = -10200;

    /**
     * 连接断开.
     */
    public static final int DISCONNECTED = -10300;
    /**
     * 手机网络不可用.
     */
    public static final int MOBILE_NETWORK_DISABLE = -10400;
    /**
     * 服务端网络不可用.
     */
    public static final int SERVER_NETWORK_DISABLE = -10500;

    /**
     * 认证失败.
     */
    public static final int AUTHENTICATE_FAILED = -106;

    /**
     * URL初始化异常.
     */
    public static final int URL_ERROR = -109;
    /**
     * OTHER_ERROR.
     */

    public static final int OTHER_ERROR = -999;
}
