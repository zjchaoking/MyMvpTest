/**
 * <p>文件介绍信息</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 * @author
 * @version
 * @history
 */
package com.kaicom.api.network.event;

import com.kaicom.api.network.interfaces.INetworkEvent;

/**
 * CommonEvent.
 * 
 * @author qiyp
 * @version
 */
public class CommonEvent implements INetworkEvent {
	
	private boolean ifSuccess=true; 
	/**
	 * eventId.
	 */
	private String eventId = "-1";

	/**
	 * data.
	 */
	private byte[] data;
	
	/**
	 * 返回错误码.
	 */
	private int returnCode;
	/**
	 * 错误信息.
	 */
	private String errorInfo;
	
	/**
	 * CommonEvent.
	 */
	public CommonEvent(String eventId) {
		eventId = eventId;
	}

	/**
	 * 构造函数.
	 */
	public CommonEvent() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.INetworkEvent#getReturnCode()
	 */
	@Override
	public int getReturnCode() {
		// TODO Auto-generated method stub
		return this.returnCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.INetworkEvent#getErrorInfo()
	 */
	@Override
	public String getErrorInfo() {
		// TODO Auto-generated method stub
		return errorInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.INetworkEvent#getErrorNo()
	 */
	@Override
	public String getErrorNo() {
		// TODO Auto-generated method stub
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.INetworkEvent#setCharset(java.lang
	 * .String)
	 */
	@Override
	public void setCharset(String charset) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.interfaces.INetworkEvent#setData()
	 */
	@Override
	public void setData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.interfaces.INetworkEvent#getEventID()
	 */
	@Override
	public String getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kaicom.api.network.network.interfaces.INetworkEvent#getMessageBody ()
	 */
	@Override
	public byte[] getMessageBody() {
		// TODO Auto-generated method stub
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kaicom.api.network.network.interfaces.INetworkEvent#setMessageBody
	 * (byte[])
	 */
	@Override
	public void setMessageBody(byte[] data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kaicom.api.network.network.interfaces.INetworkEvent#setEventId
	 * (int)
	 */
	@Override
	public void setEventId(String eventId) {
		// TODO Auto-generated method stub
		this.eventId = eventId;
	}

	@Override
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;

	}

	@Override
	public boolean getIfSuccess() {
		// TODO Auto-generated method stub
		return this.ifSuccess;
	}

	@Override
	public void setIfSuccess(boolean ifSuccess) {
		// TODO Auto-generated method stub
		this.ifSuccess = ifSuccess;
	}

	/**
	 * @param errorInfo the errorInfo to set
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
