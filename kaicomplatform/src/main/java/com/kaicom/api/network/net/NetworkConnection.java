package com.kaicom.api.network.net;

import java.util.LinkedList;

import android.app.Activity;

import com.kaicom.api.network.exception.NetworkException;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.net.params.NetParams;

/**
 * 源文件名称：NetworkConnection.java.
 * 软件著作权：恒生电子股份有限公司 版权所有.
 * 系统名称：MACS<br>
 * 模块名称：网络连接抽象类.
 * 功能说明: 用于4种不通通道的统一父类.
 * 作者 ：yukk@hundsun.com <br>
 * 开发时间：2011-5-25 下午01:26:36 <br>
 * 审核 : <br>
 */
public abstract class NetworkConnection {
/**
 * TAG.
 */
    public static String TAG = "NetworkConnection";

    /** 请求队列 .**/
    protected LinkedList<Object> mRequestQueue = new LinkedList<Object>();

    /** 返回调用. **/
    protected NetworkService mCallback = null;

    /** 连接当前网络地址. **/
    protected String mAddress = null;

    /** 是否运行线程. **/
    protected boolean isRunning = false;

    /** 发送数据队列. **/
    // protected ConcurrentLinkedQueue<byte[]> resendQueue = new
    // ConcurrentLinkedQueue<byte[]>();

    /**
     * 构造函数.
     * 
     * @param caller
     *            -- 网络监听回调对象
     */
    protected NetworkConnection(NetworkService caller) {
        mCallback = caller;
    }

    /**
     * 创建连接.
     * 
     * @param addr
     *            网络地址    
     */
    public void openConnection(String addr) {
        closeConnection();
        isRunning = true;
        mAddress = addr;
        startRecvThread();
    }

    /**
     * 关闭连接.
     * 
     * @return 是否关闭成功
     */
    public abstract boolean closeConnection();

    /**
     * 发送二进制流.
     * 
     * @param data
     *            二进制数据
     */
    public abstract void send(byte[] data);
   /**
    * 发送请求事件 .
    * @param networkEvent 网络请求事件    
    */
    public abstract void send(INetworkEvent networkEvent);

    /**
     * 发送请求对象.
     * 
     * @param obj
     *            请求对象
     */
    public abstract void send(Object obj);

    /**
     * 重连 内部方法.
     */
    public abstract void reconnect();

    /**
     * 启动异步接收线程.
     */
    public abstract void startRecvThread();

    /** 关闭连接 .**/
    public abstract void shutdown();

    /** 关闭连接 .**/
    public abstract void netWorkError();
/**
 * 
 * @param reqURL reqURL
 * @param params params
 */
    public abstract void PutMethod(String reqURL, String params);

    /**
     * 设置网络地址.
     * @param addr addr
     **/
    public void setNetworkAddr(NetworkAddr addr) {
        this.mAddress = addr.getInputAddr();
    }

    /**
     * 是否持久连接.
     * 
     * @return boolean
     */
    public boolean isPersistentConn() {
        return true;
    }

    /**
     * 立即发送.
     * 
     * @param event event
     */
    public void sendImmediately(INetworkEvent event) {
        send(event);
    }
	  /**
	   * 发送事件.
	   * @param data data
	   * @param activity activity
	   */
	public void send(byte[] data, Activity activity) {
		// TODO Auto-generated method stub
		
	}

}