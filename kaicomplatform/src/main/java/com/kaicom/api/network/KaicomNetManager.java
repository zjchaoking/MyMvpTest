/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 */
package com.kaicom.api.network;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.network.exception.NetworkException;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.net.NetworkAddr;
import com.kaicom.api.network.net.NetworkFactory;
import com.kaicom.api.network.net.NetworkManager;
import com.kaicom.api.network.net.params.NetParams;

/**
 * 连接层管理类.
 * 
 * @author Administrator
 * 
 */
public class KaicomNetManager {
    /**
     * httpConn.
     */
    private static NetworkManager httpConn = null;
    /**
     * httpsConn.
     */
    private static NetworkManager httpsConn = null;
    /**
     * list.
     */
    private static List<String> list = new ArrayList<String>();

    /**
     * 断开链接.
     * 
     */
    public static void stopConnection() {
        if (httpConn != null)
            httpConn.terminate();
        httpConn = null;

        if (httpsConn != null)
            httpsConn.terminate();
        httpsConn = null;
    }
	/**
	 * 发送请求.
	 * @param event event
	 * @param handler handler
	 * @param netParams netParams
	 * @param  Activity activity
	 * @return String
	 */
   
    public static String sendEvent(INetworkEvent event, Handler handler, String address,
                                   NetParams netParams, Activity activity) {
        String ret;
        NetworkManager mConn = httpConn;        
      //取配置文件中的网络请求地址
        if (null == list || list.size() == 0) {
            initAddr(address);
        }
        if (mConn == null) {
            mConn = initConnection(netParams.getNetworkType());
        }
        mConn.postEvent(event, handler, activity);
        ret = event.getEventId();
        return ret;
    }
/**
 * 
 * @param mConnType mConnType
 * @return NetworkManager
 */
    private static NetworkManager initConnection(int mConnType) {
        NetworkManager mConn = null;
        switch (mConnType) {
       
        case NetworkManager.CONN_TYPE_HTTP:
            httpConn = NetworkFactory
                    .getStaticNetManager(NetworkManager.CONN_TYPE_HTTP);
            try {
                httpConn.setNetworkType(NetworkManager.CONN_TYPE_HTTP);
                httpConn.setNetworkAddrList(list);
                httpConn.establishConnection();
            } catch (NetworkException e) {
                e.printStackTrace();
            }
            mConn = httpConn;
            break;
        default:
            httpConn = NetworkFactory
                    .getStaticNetManager(NetworkManager.CONN_TYPE_HTTP);
            try {
                httpConn.setNetworkType(NetworkManager.CONN_TYPE_HTTP);
                httpConn.setNetworkAddrList(list);
                httpConn.establishConnection();
            } catch (NetworkException e) {
                e.printStackTrace();
            }
            mConn = httpConn;
            break;
        }

        return mConn;
    }

    /**
     * 先只支持一个站点.
     * @param address address
     * @param connType connType
     * @return boolean
     */
    public static boolean initConnection(String address, final int connType) {

        if (address != null && address.length() > 0) {
            final String[] ayAddress = address.split(",");

            for (int i = 0; i < ayAddress.length; i++) {
               
                list.add(ayAddress[i]);
            }
            return true;

        }
        return false;
    }
/**
 * 
 * @param address address
 * @return boolean
 */
    public static boolean initAddr(String address) {

        if (address != null && address.length() > 0) {
            final String[] ayAddress = address.split(",");

            for (int i = 0; i < ayAddress.length; i++) {
              
                list.add(ayAddress[i]);
            }
            return true;

        }

        return false;
    }
/**
 * getNetworkAddrList.
 * @return List
 */
    public static List<String> getNetworkAddrList() {
        return list;
    }
}
