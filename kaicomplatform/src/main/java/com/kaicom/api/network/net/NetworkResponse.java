/**
 * <p>文件介绍信息</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 * @author
 * @version
 * @history
 */
package com.kaicom.api.network.net;

import android.app.Activity;

/**
 * NetworkResponse.
 * 
 * @author qiyp
 * @version
 */
public interface NetworkResponse {
    /**
     * Network应答.
     * @param data data
     * @param offset offset
     */
    public void onResponse(byte[] data, int offset);

   /**
    * Network应答.
    * @param data data
    * @param offset offset
    * @param eventId eventId
    * @param Activity activity
    */
    public void onResponse(byte[] data, int offset, String eventId);

    /**
     * 连接关闭事件.
     */
    public void onClosed();

    /**
     * 连接返回.
     * 
     * @param success
     *            是否连接成功
     */
    public void onConnect(boolean success);

    /**
     *  错误返回.
     * @param packetId packetId
     * @param responseCode responseCode
     * @param responseMessage responseMessage
     */
    void onError(int packetId, int responseCode, String responseMessage);

   /**
    * 错误返回.
    * @param packetId packetId
    * @param responseCode responseCode
    * @param responseMessage responseMessage
    * @param eventId eventId
    */
    void onError(int packetId, int responseCode, String responseMessage,
    		String eventId);

    /**
     * 超时事件.
     */
    void onTimeOut();

 
}
