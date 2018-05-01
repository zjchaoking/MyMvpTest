
package com.kaicom.api.network.channel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.kaicom.api.network.error.ErrorConstant;
import com.kaicom.api.network.error.ErrorUtils;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.net.NetworkAddr;
import com.kaicom.api.network.net.NetworkConnection;
import com.kaicom.api.network.net.NetworkService;
import com.kaicom.api.network.net.params.NetParams;
import com.kaicom.api.util.NetUtil;
import com.kaicom.api.util.NetworkUtil;

/**
 * 
 * 功能说明:http非加密通道
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author zhy
 * @version 2.0.0.2
 * 创建日期   2015-4-29
 */
public class HttpChannel extends NetworkConnection
{
    /**
     *  线程池.
     */
    ThreadPoolExecutor executor = null;
   
    
    /**
     *  构造函数.
     * @param caller caller
     */
    
    public HttpChannel(NetworkService caller)
    {
        super(caller);
        HttpURLConnection.setDefaultRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

    }

    @Override
    public boolean closeConnection()
    {
        isRunning = false;
        shutdown();
        return true;
    }

    @Override
    public void reconnect()
    {
        openConnection(mAddress);
    }
  /**
   * openConnection.
   * @param addr addr
   */
    public void openConnection(String addr)
    {
        super.openConnection(addr);
    }

    @Override
    public void send(byte[] data)
    {
        SendRunnable run = new SendRunnable(data);
        executor.execute(run);
    }
  
    @Override
    public void send(INetworkEvent networkEvent)
    {
        SendRunnable run = new SendRunnable(networkEvent);
        executor.execute(run);
    }
    @Override
    public void shutdown()
    {
        if (null != executor)
        {
            executor.shutdownNow();
            executor = null;
        }

    }

    @Override
    public void startRecvThread()
    {
        executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }
        /**
         * 发http请求.
         * @author zhouhy04159
         *
         */
    class SendRunnable implements Runnable
    {
        /**
         * bin.
         */
        private byte[] bin = null;
        /**
         * INetworkEvent.
         */
        INetworkEvent ne;
        /**
         * url.
         */
        URL url = null;
        /**
         * methed.
         */
        Activity activity ;
        String methed = "GET";
        /**
         * params.
         */
        String params = "";
        /**
         * 构造函数.
         */

        public SendRunnable()
        {
        }
        /**
         * 构造函数.
         * @param bt bt
         */

        public SendRunnable(byte[] bt)
        {
            bin = bt;
        }
        public SendRunnable(byte[] bt,Activity activity)
        {
            bin = bt;
            this.activity = activity;
        }
        /**
         * 构造函数.
         * @param networkEvent networkEvent
         */
        public SendRunnable(INetworkEvent networkEvent)
        {
            ne = networkEvent;
            bin = networkEvent.getMessageBody();
        }
        public SendRunnable(INetworkEvent networkEvent,Activity activity)
        {
            ne = networkEvent;
            bin = networkEvent.getMessageBody();
            this.activity = activity;
        }
        /**
         * bt.
         * @param bt bt
         */
        public void setBin(byte[] bt)
        {
            bin = bt;
        }
        /**
         * packetId.
         */
        private int packetId = 0;
        /**
         * setPacketId.
         * @param id id
         */

        public void setPacketId(int id)
        {
            packetId = id;
        }
        /**
         * parseData.
         * @param netParams netParams
         * @throws MalformedURLException MalformedURLException
         */
        private void parseData (NetParams netParams) throws MalformedURLException {
            
            if(netParams==null)
            {
                return ;
            }
            if(netParams.getUrl()!=null&&!"".equals(netParams.getUrl())&&!mAddress.equals(netParams.getUrl())){
               url = new URL(mAddress+netParams.getUrl()+ NetParams.SUFFIX);
            }else{
            	url = new URL(mAddress+ NetParams.SUFFIX);
            }
            switch (netParams.getMethod())
            {
                case NetParams.GET:
                    this.methed = "GET";
                    break;
                case NetParams.POST:
                    this.methed = "POST";
                    if(netParams==null)
                    {
                        return ;
                    }
                    this.params = netParams.getParams().trim();
                    break;
                case NetParams.PUT:
                    this.methed = "PUT";
                    if(netParams==null)
                    {
                        return ;
                    }
                    this.params = netParams.getParams().trim();
                    break;
                case NetParams.DELETE:
                    this.methed = "DELETE";
                    break;
                default:
                    break;
            }
        }

        @Override
        public void run()
        {
            if (!NetUtil.isNetworkAvailable())
            {
                mCallback.onError(packetId, ErrorConstant.MOBILE_NETWORK_DISABLE,
                        ErrorUtils.getErrorInfoByNo(ErrorConstant.MOBILE_NETWORK_DISABLE), ne.getEventId());
                return;
            }
            
            NetParams netParams = NetworkUtil.ByteToObject(bin);
            try {
                this.parseData(netParams);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                mCallback.onError(
                            packetId,
                            ErrorConstant.URL_ERROR,
                            ErrorUtils.getErrorInfoByNo(ErrorConstant.URL_ERROR), ne.getEventId());
                return;
            }
            
            HttpURLConnection conn;
            try
            {
              
                conn = (HttpURLConnection) url.openConnection();
                //程序运行过程中断网发生挂机事件进行的修改
                if(conn==null) {
                    return ;
                }
              //添加事件id 
                conn.addRequestProperty("Cookie", "eventId=" + ne.getEventId());    
                conn.setRequestMethod(methed);
                conn.setReadTimeout(netParams.getReadTimeOut());
                conn.setConnectTimeout(netParams.getConnectionTimeOut());
                conn.setUseCaches(false);
                conn.setRequestProperty("Connection", "keep-alive");
                if(netParams.isIfJson()){
                	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                }else{
    			conn.setRequestProperty("Content-type", "text/html; charset=utf-8");    
                }
                // 是否有参数需要设置输出
                if (!TextUtils.isEmpty(params.trim())) {
                    Log.d("network request params:", params);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream oos = conn.getOutputStream();

                    oos.write(params.getBytes("UTF-8")); 
                    oos.flush();
                    oos.close();
                }else{
                    conn.connect();
                }
                
                int responseCode;
                try
                {
                    responseCode = conn.getResponseCode();
                }
                catch (SocketTimeoutException e)
                {
                	
                    mCallback
                            .onError(packetId, ErrorConstant.CONNECT_TIME_OUT, ErrorUtils.getErrorInfoByNo(ErrorConstant.CONNECT_TIME_OUT), ne.getEventId());
                    return;
                }
                if (responseCode == 200)
                {
                   
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 64);
                    // 得到返回流
                    if(conn==null) {
                        return;
                    }
                    InputStream input = conn.getInputStream();

                    try
                    {
                        byte[] buf = new byte[1024 * 64];
                        int n;
                        while ((n = input.read(buf)) > 0)
                        {
                            baos.write(buf, 0, n);
                        }
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        input.close();
                    }
                   Log.d("network return msg: ", baos.toString());
                    byte[] content = baos.toByteArray();
                    mCallback.onResponse(content, 4, ne.getEventId());
                 
                }
                else
                {
                    String responseMessage = "网络无法连接";//conn.getResponseMessage();
                    Log.d("network return msg: ", responseCode + " " +responseMessage);
                    mCallback.onError(packetId, responseCode, responseMessage, ne.getEventId());
                }
            }
            catch (SocketTimeoutException e)
            {
            
                mCallback.onError(packetId, ErrorConstant.CONNECT_TIME_OUT, ErrorUtils.getErrorInfoByNo(ErrorConstant.CONNECT_TIME_OUT), ne.getEventId());
            }
            catch (IOException e)
            {
            	
                mCallback.onError(packetId, ErrorConstant.SERVER_NETWORK_DISABLE,
                        ErrorUtils.getErrorInfoByNo(ErrorConstant.SERVER_NETWORK_DISABLE), ne.getEventId());
            }
        }
    }

    @Override
    public void send(Object obj)
    {
        if (obj instanceof Object)
        {
            INetworkEvent ne = INetworkEvent.class.cast(obj);
            SendRunnable run = new SendRunnable(ne);
            startRecvThread();
            executor.execute(run);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hundsun.armo.sdk.interfaces.net.NetworkConnection#isPersistentConn()
     */
    @Override
    public boolean isPersistentConn()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see com.hundsun.armo.sdk.interfaces.net.NetworkConnection#netWorkError()
     */
    @Override
    public void netWorkError()
    {
        reconnect();
    }

    /* (non-Javadoc)
     * @see com.kaicom.api.network.network.net.NetworkConnection#PutMethod(java.lang.String, java.lang.String)
     */
    @Override
    public void PutMethod(String reqURL, String params)
    {
        // TODO Auto-generated method stub

    }

	
    
}
