package com.kaicom.api.network.net;

import java.util.List;

import android.app.Activity;
import android.os.Handler;

import com.kaicom.api.network.exception.NetworkException;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.listener.NetworkListener;

/**
 * 
 * 功能说明:网络层管理. 
 * Copyright copyright(c) 2015 杭州凯立通信有限公司 版权所有
 * @author PF05RN7V
 * @version 2.0.0.2 
 * 创建日期 2015-4-29
 */
public interface NetworkManager {

	/**
	 * http类型请求.
	 */
	public static final int CONN_TYPE_HTTP = 2;

	/**
	 * 设置某事件网络监听.
	 * 
	 * @param mListener
	 *            mListener
	 * @param event
	 *            event
	 */
	public void setNetworkListener(NetworkListener mListener,
			INetworkEvent event);

	/**
	 * 发送event.
	 * 
	 * @param event
	 *            event
	 * @param Handler
	 *            handler
	 * @param activity
	 *            activity
	 */
	public void postEvent(INetworkEvent event, Handler handler,
			Activity activity);

	/**
	 * 发送event.
	 * 
	 * @param event
	 *            event
	 * @param listener
	 *            listener
	 */
	public void postEvent(INetworkEvent event, NetworkListener listener);

	/**
	 * 发送事件至服务器.
	 * 
	 * @param event
	 *            event
	 */
	public void postEvent(INetworkEvent event);

	/**
	 * 建立连接.
	 * 
	 * @param type
	 *            通讯模式
	 * @throws NetworkException
	 *             NetworkException
	 */
	public void establishConnection(int type) throws NetworkException;

	/**
	 * 设置网络类型，长连接、短连接或各种加密方式. <br>
	 * 值为NetworkManager.CONN_TYPE_TCP
	 * 
	 * @param type
	 *            type
	 * @throws NetworkException
	 *             NetworkException
	 */
	public void setNetworkType(int type) throws NetworkException;

	/**
	 * 建立连接.
	 * 
	 * @throws NetworkException
	 *             NetworkException
	 */
	public void establishConnection() throws NetworkException;

	/**
	 * 设置网络地址列表.
	 * 
	 * @param list
	 *            list
	 */
	public void setNetworkAddrList(List<String> list);

	/**
	 * 获取connection.
	 * 
	 * @return NetworkConnection
	 * 
	 */
	public NetworkConnection getConnection();

	/**
	 * 关闭网络.
	 */
	public void terminate();

}
