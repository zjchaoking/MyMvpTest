package com.kaicom.api.net.spi.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.text.TextUtils;

import com.kaicom.api.log.KlLoger;
import com.kaicom.api.net.NetException;

/**
 * socket管理类
 * 
 * @author scj
 */
public class SocketManager {

    public static final String ENCODE_GBK = "gbk";

    private SocketClient socket;

    public SocketManager(String ip, int port) {
        socket = new SocketClient(ip, port);
    }

    /**
     * 向服务器发送请求（默认GBK转码）
     * @param msg
     * @throws NetException
     */
    public void sendMessage(String msg) throws NetException {
        if (!TextUtils.isEmpty(msg))
            try {
                socket.sendMsg(msg.getBytes(ENCODE_GBK));
            } catch (UnsupportedEncodingException e) {
                throw new NetException("String to bytes(gbk) error", e);
            }
    }

    /**
     * 向服务器发送字节消息
     * 
     * @param msg
     * @throws NetException 
     */
    public void sendMessage(byte[] msg) throws NetException {
        socket.sendMsg(msg);
    }

    /**
     * 读取数据,前五个字节为消息头
     * 
     * @return
     * @throws NetException 
     */
    public String getResult() throws NetException {
        String result = "";
        byte[] head = new byte[5];
        InputStream inStreamRec = socket.getInputStream();
        try {
            if (inStreamRec == null) {
                throw new NetException("未收到服务器的响应");
            }
            inStreamRec.read(head);
            result = handleResponse(result, head, inStreamRec);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NetException("读取服务器数据出错", e);
        } finally {
            if (socket != null) {
                socket.closeSocket();
                socket = null;
            }
        }
        KlLoger.info("测试网络", "getResponse：" + result);
        return result;
    }

    /**
     * 处理服务器返回的结果
     * @param result
     * @param head
     * @param inStreamRec
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private String handleResponse(String result, byte[] head,
            InputStream inStreamRec) throws IOException,
            UnsupportedEncodingException {
        String headStr = new String(head, 0, head.length);
        int position = isNumeric(headStr);
        int messageLength = Integer.valueOf((headStr.substring(0, position)
                .trim()));
        byte[] body = new byte[messageLength];
        int length = 0;
        int total = 0;
        while ((length = inStreamRec.read(body, total, 1)) != -1) {
            total = total + length;
            if (total >= messageLength) {
                String bodyStr1 = new String(body, 0, messageLength, ENCODE_GBK);
                result = bodyStr1;
                break;
            }
        }
        return result;
    }

    /**
     * 连接socket，可在连接之前设设置超时时间或缓冲区大小
     */
    public void connect() throws NetException {
        socket.connectionSocket();
    }

    /**
     * 判断是连上
     * @return
     */
    public boolean isConnected() {
        return socket.isConnected();
    }

    /**
     * 判断字符串内第一个非数字的位置
     * 
     * @param str
     * @return
     */
    private int isNumeric(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return i;
            }
        }
        return length;
    }
    
    public SocketClient getSocketClient() {
    	return socket;
    }

}
