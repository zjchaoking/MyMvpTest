/**
 * <p>文件介绍信息</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 * @author
 * @version
 * @history
 */
package com.kaicom.api.network.listener;

import com.kaicom.api.network.net.NetworkManager;

/**
 * NetworkStatusListener.
 * 
 * @author qiyp
 * @version
 */
public interface NetworkStatusListener {
    /**
     * 连接事件.
     * @param manager manager
     * @param success success
     */
    public void onConnect(NetworkManager manager, boolean success);

   /**
    * 关闭事件.
    * @param manager manager
    */
    public void onClose(NetworkManager manager);

    /**
     * 通道鉴权返回.
     * @param status status
     */
    public void onAuthenticate(int status);
}
