package com.kaicom.api.net.spi;

/**
 * 
 * 功能说明:网络地址. 
 * Copyright copyright(c) 2015 杭州凯立通信有限公司 版权所有
 * 
 * @author zhouhy
 * @version 2.0.0.2 创建日期 2015-4-28
 */
public class NetworkAddress {
	/** IP地址. **/
	private String ip;

	/** 站点名称. **/
	private String name;

	/** HTTP端口. **/
	private short httpPort;

	/** 输入完整地址. **/
	private String inputAddr;

	/**
	 * Network构造函数，若格式错误 抛出异常.
	 * 
	 * @param address
	 *            格式为192.168.71.23:7001:站点名称
	 */
	public NetworkAddress(String address) {
		
		inputAddr = address;
	}

	/**
	 * 
	 * @return String
	 * @throws NullPointerException
	 *             NullPointerException
	 */
	public String getCompleteAddr() throws NullPointerException {
		if (inputAddr == null)
			throw new NullPointerException("无效地址");
		return inputAddr;
	}

	/**
	 * 获取IP地址.
	 * 
	 * @return IP地址
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP地址.
	 * 
	 * @param ip
	 *            ip地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取站点名称.
	 * 
	 * @return 站点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置站点名称.
	 * 
	 * @param name
	 *            站点名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取http端口.
	 * 
	 * @return http端口
	 */
	public short getHttpPort() {
		return httpPort;
	}

	/**
	 * 设置http端口.
	 * 
	 * @param httpPort
	 *            https端口
	 */
	public void setHttpPort(short httpPort) {
		this.httpPort = httpPort;
	}

}
