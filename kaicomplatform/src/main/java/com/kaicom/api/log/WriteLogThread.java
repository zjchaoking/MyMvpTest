package com.kaicom.api.log;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kaicom.api.util.FileUtil;

/**
 * 写日志文件线程
 * @author scj
 */
// 暂时不用这个类
@Deprecated
public class WriteLogThread implements Runnable {
    
    private static final int WRITE_LOG = 0x13;
    private Handler mHandler;
    private String filePath;
    
    public WriteLogThread(String path) {
        this.filePath = path;
    }
    
    /**
     * 发送写日志文件命令
     * @param content 要写入的内容
     */
    public void sendWriteCmd(String content) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(WRITE_LOG, content);
            mHandler.sendMessage(msg);
        }
    }
    
    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WRITE_LOG && msg.obj != null)
                    try {
                        handleWriteLog((String)msg.obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        };
        Looper.loop();
    }

    protected void handleWriteLog(String content) throws IOException {
        FileUtil.writeFile(filePath, content + "\r\n", true);
    }
    
    public void release() {
        if (mHandler != null) {
            mHandler.removeMessages(WRITE_LOG);
            mHandler.getLooper().quit();
        }
    }
    
}