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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;

import com.kaicom.api.network.channel.HttpChannel;
import com.kaicom.api.network.error.ErrorConstant;
import com.kaicom.api.network.event.EventFactory;
import com.kaicom.api.network.exception.NetworkException;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.interfaces.INetworkService;
import com.kaicom.api.network.listener.NetworkListener;
import com.kaicom.api.network.listener.NetworkListenerImpl;
import com.kaicom.api.network.listener.NetworkStatusListener;
import com.kaicom.api.network.net.params.NetParams;
import com.kaicom.api.util.NetUtil;

/**
 * NetworkService.
 * 
 * @author qiyp
 * @version
 */
public class NetworkService implements INetworkService {

	/** 连接类型 . **/
	private int mConnType = CONN_TYPE_HTTP;

	/** 是否终止. **/
	private boolean isTerminate = false;

	/** 是否可用 0:可用; -1:默认；-101:网络不可用 具体错误请参见{@link } . **/
	private int enable = ErrorConstant.DISCONNECTED;
	/** 最后次发送数据时间. **/
	private long mLastSendedTime = System.currentTimeMillis();
	/**
	 * mLastReceviedTime.
	 */
	private long mLastReceviedTime = System.currentTimeMillis();
	/**
	 * defaultCharset.
	 */
	private String defaultCharset = "UTF-8";
	/**
	 * maxSecond.
	 */
	private int maxSecond = 1;
	/** 地址列表. **/
	private List<String> mAddrs = new ArrayList<String>();
	/** 地址索引 . **/
	private int mAddrIndex = 0;
	/**
	 * httpParam.
	 */
	private NetParams httpParam;

	/**
	 * 用于操作mConnection的锁.
	 */
	private byte[] lock = new byte[0];
	/**
	 * mConnection.
	 */
	private NetworkConnection mConnection;
	/**
	 * mNetWorkStatusListener.
	 */

	protected NetworkStatusListener mNetWorkStatusListener = null;
	/**
	 * 监听接口.
	 */
	NetworkListener mListener;
	/** 监听Key List. **/
	private LinkedList<NetworkListener> mLsnList = null;

	/**
	 * NetworkService.
	 */
	public NetworkService() {
		mLsnList = new LinkedList<NetworkListener>();
	}

	/**
	 * 是否可用.
	 * 
	 * @return boolean
	 */
	public boolean enable() {
		return enable == ErrorConstant.MOBILE_NETWORK_DISABLE
				|| enable != ErrorConstant.NETWORK_NOT_ENABLE;
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		enable = ErrorConstant.NETWORK_NOT_ENABLE;
		isTerminate = true;
		synchronized (lock) {
			if (mConnection != null) {
				
				mConnection = null;
			}
		}
	}

	@Override
	public void setNetworkListener(NetworkListener mListener,
			INetworkEvent event) {		
		this.mListener = mListener;
		mLsnList.add(this.mListener);
	}

	/**
	 * 提交网络请求.
	 */
	@Override
	public void postEvent(INetworkEvent event, Handler handler,
			Activity activity) {		
		NetworkListenerImpl listener = new NetworkListenerImpl();
		listener.setHandler(handler, activity);
		setNetworkListener(listener, event);
		this.postEvent(event);
	}

	@Override
	public void postEvent(INetworkEvent event) {
		
		if (mConnection != null) {
			event.setCharset(defaultCharset);
			mConnection.send(event);
		}
	}

	/**
	 * 最大重连等待秒数.
	 * 
	 * @param maxSecond
	 *            the maxSecond to set
	 */
	public void setMaxSecond(int maxSecond) {
		if (maxSecond >= 0)
			this.maxSecond = maxSecond;
	}

	
	@Override
	public void establishConnection(int type) throws NetworkException {
		// TODO Auto-generated method stub
		mConnType = type;
		establish();
	}

	/**
	 * 实例化连接类.
	 * 
	 * @throws NetworkException
	 *             NetworkException
	 */
	private synchronized void establish() throws NetworkException {
		if (!enable()) {
			if (mNetWorkStatusListener != null) {
				mNetWorkStatusListener.onConnect(this, false);
			}
			return;
		}
		switch (mConnType) {

		case NetworkManager.CONN_TYPE_HTTP:
			mConnection = new HttpChannel(this);
			mConnection.openConnection(mAddrs.get(0));
			break;
		default:
			break;
		}

		isTerminate = false;
	}

	
	@Override
	public void onResponse(byte[] data, int offset, String eventId) {		
		if (data == null)
			return;
		try {
			INetworkEvent event = EventFactory.getEvent();
			event.setEventId(eventId);
			event.setMessageBody(data);
			event.setCharset(defaultCharset);
			onEventRes(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param event
	 *            event
	 */
	private void onEventRes(INetworkEvent event) {
		mListener.onNetResponse(event);

	}

	
	@Override
	public void onClosed() {
		

	}

	
	@Override
	public void onConnect(boolean success) {
		
		synchronized (lock) {
			if (mConnection != null) {
				if (!NetUtil.isNetworkAvailable()) {
					if (mNetWorkStatusListener != null) {
						mNetWorkStatusListener.onConnect(this, success);
					}
					
					return;
				}
			}
		}
	}

	
	@Override
	public void onError(int packetId, int responseCode, String responseMessage) {		
		try {
			INetworkEvent event = EventFactory.getEvent();
			String msg = responseMessage;
			event.setIfSuccess(false);
			event.setReturnCode(responseCode);
			event.setMessageBody(msg.getBytes(defaultCharset));
			event.setCharset(defaultCharset);
			onEventRes(event);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void onTimeOut() {
		

	}

	
	@Override
	public void setNetworkType(int type) throws NetworkException {
		

	}

	@Override
	public void establishConnection() throws NetworkException {
		
		establish();
	}

	
	@Override
	public void setNetworkAddrList(List<String> list) {
		
		this.mAddrs = list;
	}

	
	@Override
	public void onResponse(byte[] data, int offset) {
		
		if (data == null)
			return;
		try {
			
			INetworkEvent event = EventFactory.getEvent();
			event.setMessageBody(data);
			event.setCharset(defaultCharset);
			onEventRes(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回当前链接.
	 * @return NetworkConnection
	 */

	public NetworkConnection getConnection() {
		return this.mConnection;
	}

	@Override
	public void postEvent(INetworkEvent event, NetworkListener listener) {
		setNetworkListener(listener, event);
		this.postEvent(event);
	}

	@Override
	public void onError(int packetId, int responseCode, String responseMessage,
			String eventId) {
		try {
			INetworkEvent event = EventFactory.getEvent();
			event.setEventId(eventId);
			String msg = responseMessage;
			event.setIfSuccess(false);
			event.setReturnCode(responseCode);
			event.setMessageBody(msg.getBytes(defaultCharset));
			event.setCharset(defaultCharset);
			onEventRes(event);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}

}
