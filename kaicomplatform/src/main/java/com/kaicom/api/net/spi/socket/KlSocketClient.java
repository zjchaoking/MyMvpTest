package com.kaicom.api.net.spi.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.kaicom.api.net.NetException;

public class KlSocketClient {

    public static int DEFAULT_SIZE = 10 * 1024;
    public static int DEFAULT_CONNECT_TIME_OUT = 20 * 1000;
    public static int DEFAULT_REQUEST_TIME_OUT = 60 * 1000;
    public static String DEFAULT_ENCODE = "utf-8";

    private Socket mSocket;
    private String mIp;
    private int mPort;
    private int bufferSize = DEFAULT_SIZE; // 缓冲大小
    private InputStream mInputStream;
    private int connectTimeOut = DEFAULT_CONNECT_TIME_OUT; // 连接超时
    private int requestTimeOut = DEFAULT_REQUEST_TIME_OUT; // 读写超时
    private String encode = DEFAULT_ENCODE;

    public KlSocketClient(String ip, int port) {
        this.mIp = ip;
        this.mPort = port;
    }

    /**
     * 连接服务器
     * 1) 连接服务器地址
     * 2) 设置keepAlive
     * 3) 从服务器获取InputStream
     * @throws NetException
     */
    public void connect() throws NetException {
        try {
            connectSocketAddress();
            setKeepAlive();
            getInputStreamFromServer();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            closeConnection();
            throw new NetException("连接服务器超时", e);
        } catch (Exception e) {
            e.printStackTrace();
            closeConnection();
            throw new NetException("连接服务器出错", e);
        }
    }

    /**
     * 连接服务器地址
     * @throws NetException
     * @throws IOException
     */
    private void connectSocketAddress() throws NetException, IOException {
        if (mSocket == null || mSocket.isClosed()) {
            mSocket = requestSocket(mIp, mPort);
            mSocket.setSoTimeout(requestTimeOut);
        } else if (!mSocket.isConnected()) {
            SocketAddress sa = new InetSocketAddress(mIp, mPort);
            mSocket.connect(sa, connectTimeOut);
        }
    }

    /**
     * 设置keepAlive
     * @throws SocketException
     */
    private void setKeepAlive() throws SocketException {
        if (!mSocket.getKeepAlive()) {
            mSocket.setKeepAlive(true);
        }
    }

    /**
     * 从服务器获取InputStream
     * @throws IOException
     */
    private void getInputStreamFromServer() throws IOException {
        if (isConnected()) {
            mInputStream = new BufferedInputStream(mSocket.getInputStream(),
                    bufferSize);
        }
    }

    /**
     * 新建一个socket对象
     * 
     * @param host
     * @param port
     * @return
     * @throws NetException
     */
    private Socket requestSocket(String host, int port) throws NetException {
        Socket socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(host, port);
        try {
            socket.connect(isa, connectTimeOut);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NetException("连接服务器出错", e);
        }
        return socket;
    }

    /**
     * 判断socket是否连接
     * 
     * @return
     */
    public boolean isConnected() {
        if (mSocket != null && mSocket.isConnected() && !mSocket.isClosed()) {
            return true;
        }
        return false;
    }

    /**
     * 发送一个字节码
     * 
     * @param b
     */
    public void send(byte[] b) throws NetException {
        OutputStream out = null;
        try {
            if (isConnected()) {
                out = mSocket.getOutputStream();
                out.write(b);
                out.flush();
            }
        } catch (IOException e) {
            closeConnection();
            throw new NetException("发送数据出错", e);
        }
    }
    
    

    /**
     * 得到一个输入流对象
     * 
     * @return
     */
    public InputStream getInputStream() {
        return mInputStream;
    }

    /**
     * 关闭连接
     */
    public void closeConnection() throws NetException {
        try {
            if (mSocket != null)
                mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NetException("关闭socket出错", e);
        }
    }

    /**
     * 得到IP地址
     * 
     * @return
     */
    public String getIp() {
        return mIp;
    }

    /**
     * 设置IP地址
     * 
     * @param ip
     */
    public void setIp(String ip) {
        mIp = ip;
    }

    /**
     * 得到端口号
     * 
     * @return
     */
    public int getPort() {
        return mPort;
    }

    /**
     * 设置端口号
     * 
     * @param port
     */
    public void setPort(int port) {
        mPort = port;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int timeOut) {
        this.connectTimeOut = timeOut;
    }

    public int getRequestTimeOut() {
        return requestTimeOut;
    }

    public void setRequestTimeOut(int timeOut) {
        this.requestTimeOut = timeOut;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

}
