package com.kaicom.api.network.net.params;

import java.io.Serializable;

import com.kaicom.api.network.net.NetworkManager;

/**
 * 类描述.
 * 
 * @author hundsun
 * @version 1.0 2014-2-7 改订
 * @since 1.0
 */
public class NetParams implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * GET.
	 */

	public static final int GET = 100;
	/**
	 * POST.
	 */
	public static final int POST = 200;
	/**
	 * PUT.
	 */
	public static final int PUT = 300;
	/**
	 * DELETE.
	 */
	public static final int DELETE = 400;
	/**
	 * 后缀如.json.
	 */
	
	public static final String SUFFIX = "";

	/** 具体业务请求URL. **/
	private String url;

	/** 请求方法 . **/
	private int method;

	/** 请求参数. **/
	private String params;

	/** 请求类型 . **/
	private int networkType;
	/**
	 * 连接超时时间.
	 */
	private int connectionTimeOut = 60000;
	/**
	 * 读数据超时时间.
	 */
	private int readTimeOut = 10000;
	/**
	 * 是否服务端为JSON样式.
	 */
	private boolean ifJson = false;

	/**
	 * 
	 * @param url
	 *            url
	 */
	public NetParams(String url) {
		this.url = url;
		this.method = NetParams.GET;
		this.params = null;
		this.networkType = NetworkManager.CONN_TYPE_HTTP;
	}

	/**
	 * NetParams.
	 */
	public NetParams() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return int
	 */
	public int getNetworkType() {
		return networkType;
	}

	/**
	 * 
	 * @param networkType
	 *            networkType
	 */
	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}

	/**
	 * 
	 * @return int
	 */
	public int getMethod() {
		return method;
	}

	/**
	 * 
	 * @param method
	 *            method
	 */
	public void setMethod(int method) {
		this.method = method;
	}

	/**
	 * 
	 * @return String
	 */
	public String getParams() {
		return params;
	}

	/**
	 * 
	 * @param params
	 *            params
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the connectionTimeOut
	 */
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	/**
	 * @param connectionTimeOut the connectionTimeOut to set
	 */
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	/**
	 * @return the readTimeOut
	 */
	public int getReadTimeOut() {
		return readTimeOut;
	}

	/**
	 * @param readTimeOut the readTimeOut to set
	 */
	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	/**
	 * @return the ifJson
	 */
	public boolean isIfJson() {
		return ifJson;
	}

	/**
	 * @param ifJson the ifJson to set
	 */
	public void setIfJson(boolean ifJson) {
		this.ifJson = ifJson;
	}
	

}
