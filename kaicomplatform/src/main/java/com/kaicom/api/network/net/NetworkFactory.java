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

import com.kaicom.api.network.net.NetworkService;

/**
 *NetworkFactory.
 * 
 * @author qiyp
 * @version
 */
public class NetworkFactory {
    /**
     * httpManager.
     */
    private static NetworkManager httpManager = null;
   /**
    * httpsManager.
    */
    private static NetworkManager httpsManager = null;
/**
 * NetworkFactory.
 */
    private NetworkFactory() {
        httpManager = new NetworkService();
        httpsManager = new NetworkService();
    }

    /**
     * 获取静态的网络连接管理类.
     * 
     * @param mConnType
     *            网络类型
     * 
     * @return 静态网络连接管理实例
     */
    public static NetworkManager getStaticNetManager(int mConnType) {
        NetworkManager mConn = httpManager;
        switch (mConnType) {
       
        case NetworkManager.CONN_TYPE_HTTP:
            if (httpManager == null) {
                httpManager = new NetworkService();
            }
            mConn = httpManager;
            break;
        default:
            if (httpManager == null) {
                httpManager = new NetworkService();
            }
            mConn = httpManager;
            break;
        }

        return mConn;
    }

    /**
     * 实例化新的网络连接管理类.
     * 
     * @return 网络连接管理实例
     */
    public static NetworkManager getNetManager() {
        if (null != httpManager) {
            return httpManager;
        }

        return new NetworkService();
    }
}
