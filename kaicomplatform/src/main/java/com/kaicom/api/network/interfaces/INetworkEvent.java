package com.kaicom.api.network.interfaces;

/**
 * 
 * 功能说明:网络事件接口 Copyright copyright(c) 2015 杭州凯立通信有限公司 版权所有
 * 
 * @author PF05RN7V
 * @version 2.0.0.2 创建日期 2015-4-28
 */
public interface INetworkEvent {
	

	/**
	 * 返回码.
	 * 
	 * @return int
	 */
	int getReturnCode();

	/**
	 * 返回错误内容.
	 * 
	 * @return String
	 */
	String getErrorInfo();

	/**
	 * 错误码.
	 * 
	 * @return String
	 */

	String getErrorNo();

	/**
	 * 返回编码 .
	 * 
	 * @param charset
	 *            charset
	 */

	public void setCharset(String charset);

	/**
	 * 获取事件ID.
	 * 
	 * @return 事件ID
	 */
	String getEventId();

	/**
	 * 
	 * @param eventId
	 *            eventId
	 */
	void setEventId(String eventId);

	/**
	 * setData.
	 */
	public void setData();

	/**
	 * 获取业务数据.
	 * 
	 * @return 业务数据二进制
	 */
	byte[] getMessageBody();
	
	/**
	 * 设置业务报文数据.
	 * 
	 * @param data
	 *            data
	 */
	void setMessageBody(byte[] data);
	

	/**
	 * 
	 * @param returnCode
	 *            returnCode
	 */

	void setReturnCode(int returnCode);
	/**
	 * 取是否成功.
	 * @return boolean
	 */
	boolean getIfSuccess();
	/**
	 * 设置是否成功 .
	 * @param ifSuccess  ifSuccess
	 */
	void setIfSuccess(boolean ifSuccess);
	
	
}
