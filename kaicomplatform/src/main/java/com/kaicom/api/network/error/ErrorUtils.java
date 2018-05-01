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
 * ErrorUtils.
 * 
 * @author qiyp
 * @version
 */
public class ErrorUtils {
    /**
     * 根据错误号获取错误信息，用于自定义错误信息.
     * 
     * @param errorNo
     *            错误号
     * @return 错误信息
     */
    public static String getErrorInfoByNo(int errorNo) {
        switch (errorNo) {
        case ErrorConstant.CONNECT_OK:
            return "发送失败请重试！";
        case ErrorConstant.CONNECT_TIME_OUT:
            return "网络超时，请稍后重试！";
        case ErrorConstant.DISCONNECTED:
            return "网络断开，请稍后重试！";
        case ErrorConstant.MOBILE_NETWORK_DISABLE:
            return "无网络信号或者网络信号不稳定，请稍后重试！";
        case ErrorConstant.SERVER_NETWORK_DISABLE:
            return "网络无法连接成功，请稍后重试！";
        case ErrorConstant.AUTHENTICATE_FAILED:
            return "与服务器认证失败";
        case ErrorConstant.NETWORK_NOT_ENABLE:
            return "网络不可用!";
        case ErrorConstant.CONNECT_CLOSED:
            return "数据连接关闭";
        case ErrorConstant.URL_ERROR:
            return "URL异常";
        default:
            return "其他错误！";
        }
    }
}
