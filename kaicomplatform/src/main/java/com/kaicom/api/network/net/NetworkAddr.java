package com.kaicom.api.network.net;

/**
 * 源文件名称：NetworkAddress.java.
 * 软件著作权：恒生电子股份有限公司 版权所有.
 * 系统名称：Android dtk.
 * 模块名称：网络地址.
 * 功能说明: <br>
 * 作者 ：yukk@hundsun.com .
 * 开发时间：2011-5-31 上午02:39:21.
 * 审核 : .
 */
public class NetworkAddr {

    /** IP地址. **/
    private String ip;

    /** 站点名称. **/
    private String name;

    /** HTTPS端口 .**/
    private short httpsPort;

    /** HTTP端口. **/
    private short httpPort;

    /** 输入完整地址. **/
    private String inputAddr;

    /**
     * Network构造函数，若格式错误 抛出异常.
     * 
     * @param address
     *            格式为192.168.71.23:7001:7002:站点名称
     */
    public NetworkAddr(String address) {
        
        inputAddr = address;
    }
/**
 * 
 * @return String
 * @throws NullPointerException NullPointerException
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
     * 设置https端口.
     * 
     * @param httpPort  https端口
     */
    public void setHttpPort(short httpPort) {
        this.httpPort = httpPort;
    }

    /**
     * 获取https端口.
     * 
     * @return https端口
     */
    public short getHttpsPort() {
        return httpsPort;
    }

    /**
     * 设置https端口.
     * 
     * @param httpsPort
     *            https端口
     */
    public void setHttpsPort(short httpsPort) {
        this.httpsPort = httpsPort;
    }
	/**
	 * @return the inputAddr
	 */
	public String getInputAddr() {
		return inputAddr;
	}
	/**
	 * @param inputAddr the inputAddr to set
	 */
	public void setInputAddr(String inputAddr) {
		this.inputAddr = inputAddr;
	}
    
    
}
