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
import java.net.UnknownHostException;

import com.kaicom.api.net.NetException;

/**
 * SocketClient
 * 
 * @author scj
 */
public class SocketClient {
	
    private Socket socket;
    private int bufferSize = 10 * 1024;
    private String host;
    private int port;
    private InputStream inStreamRec;
    private int connectTimeOut = 20 * 1000; // 连接超时
    private int soTimeOut = 60 * 1000; // 读写超时

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 连接服务器
     * 1) 连接服务器地址
     * 2) 设置keepAlive
     * 3) 从服务器获取InputStream
     * @throws NetException
     */
    public void connectionSocket() throws NetException {
        try {
            connectSocketAddress();
            setKeepAlive();
            getInputStreamFromServer();
        } catch (SocketTimeoutException e){
            e.printStackTrace();
            closeSocket();
            throw new NetException("连接服务器超时", e);
        } catch (Exception e) {
            e.printStackTrace();
            closeSocket();
            throw new NetException("连接服务器出错", e);
        }
    }

    /**
     * 连接服务器地址
     * @throws NetException
     * @throws IOException
     */
    private void connectSocketAddress() throws NetException, IOException {
        if (socket == null || socket.isClosed()) {
            socket = requestSocket(host, port);
            socket.setSoTimeout(soTimeOut);
        } else if (!socket.isConnected()) {
            SocketAddress sa = new InetSocketAddress(host, port);
            socket.connect(sa, connectTimeOut);
        }
    }

    /**
     * 设置keepAlive
     * @throws SocketException
     */
    private void setKeepAlive() throws SocketException {
        if (!socket.getKeepAlive()) {
            socket.setKeepAlive(true);
        }
    }
    
    /**
     * 从服务器获取InputStream
     * @throws IOException
     */
    private void getInputStreamFromServer() throws IOException {
        if (isConnected()) {
            inStreamRec = new BufferedInputStream(socket.getInputStream(),
                    bufferSize);
        }
    }

    /**
     * 新建一个socket对象
     * 
     * @param host
     * @param port
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    private Socket requestSocket(String host, int port)
            throws NetException {
        // 这里采用这个连接方式，并设置下超时时间，防止IP或端口错误，程序异常
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
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return true;
        }
        return false;
    }

    /**
     * 发送一个字节码
     * 
     * @param b
     */
    public void sendMsg(byte[] b) throws NetException {
        OutputStream out = null;
        try {
            if (isConnected()) {
                out = socket.getOutputStream();
                out.write(b);
            }
        } catch (IOException e) {
            closeSocket();
            throw new NetException("发送数据出错", e);
        }
    }

    /**
     * 得到一个输入流对象
     * 
     * @return
     */
    public InputStream getInputStream() {
        return inStreamRec;
    }

    /**
     * 关闭socket对象
     */
    public void closeSocket() throws NetException {
        try {
            if (socket != null) {
                socket.close();
            }
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
    public String getHOST() {
        return host;
    }

    /**
     * 设置IP地址
     * 
     * @param hOST
     */
    public void setHOST(String hOST) {
        host = hOST;
    }

    /**
     * 得到端口号
     * 
     * @return
     */
    public int getPORT() {
        return port;
    }

    /**
     * 设置端口号
     * 
     * @param pORT
     */
    public void setPORT(int pORT) {
        port = pORT;
    }

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int timeOut) {
		this.connectTimeOut = timeOut;
	}
	
	public int getSoTimeOut() {
	    return soTimeOut;
	}
	
	public void setSoTimeOut(int timeOut) {
	    this.soTimeOut = timeOut;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
    
}