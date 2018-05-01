package com.kaicom.api.manager;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.KaicomJNIProxy;

/**
 * Led灯管理工具
 * 注：在主线程调用
 * @author scj
 *
 */
public class LedFlashManager {

    /** 开启右边绿灯 */
    private static final int WHAT_RIGHT_GREEN_ON = 0x11;
    /** 开启右边绿灯 */
    private static final int WHAT_RIGHT_GREEN_OFF = 0x12;
    /** 开启红灯 */
    private static final int WHAT_RED_ON = 0x21;
    /** 关闭红灯 */
    private static final int WHAT_RED_OFF = 0x22;
    
    /** 红灯闪烁次数 */
    private static final int RED_FLASH_COUNT = 6;
    /** 红灯亮的时间 */
    private static final int RED_OPEN_TIME = 40;
    /** 绿灯灭的时间 */
    private static final int RED_CLOSE_TIME = 30;
    
    private KaicomJNIProxy kaicomJNI;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == WHAT_RIGHT_GREEN_ON) {
                kaicomJNI.setGPIORightGreen(true);
                sendEmptyMessageDelayed(WHAT_RIGHT_GREEN_OFF, 250);
            } else if (msg.what == WHAT_RIGHT_GREEN_OFF) {
                kaicomJNI.setGPIORightGreen(false);
            } else if (msg.what == WHAT_RED_ON && msg.arg1 > 0) {
                kaicomJNI.setGPIOLeftRed(true);
                kaicomJNI.setGPIORightRed(true);
                Message msg2 = obtainMessage(WHAT_RED_OFF);
                msg2.arg1 = msg.arg1;
                sendMessageDelayed(msg2, RED_OPEN_TIME);
            } else if (msg.what == WHAT_RED_OFF) {
                kaicomJNI.setGPIOLeftRed(false);
                kaicomJNI.setGPIORightRed(false);
                Message msg2 = obtainMessage(WHAT_RED_ON);
                msg2.arg1 = msg.arg1 - 1;
                sendMessageDelayed(msg2, RED_CLOSE_TIME);
            }
            
        };
    };
    
    private LedFlashManager() {
        kaicomJNI = KaicomApplication.kaicomJNIProxy;
        
    }
    
    public static LedFlashManager getInstance() {
        return LedFlashHolder.instance;
    }
    
    private static class LedFlashHolder {
        private static LedFlashManager instance = new LedFlashManager();
    }
    
    /**
     * 正常提示-绿灯单闪
     */
    public void normalFlash() {
        mHandler.sendEmptyMessage(WHAT_RIGHT_GREEN_ON);
    }
    
    /**
     * 异常提示-双红灯快速连闪
     */
    public void errorFlash() {
        Message msg = mHandler.obtainMessage(WHAT_RED_ON);
        msg.arg1 = RED_FLASH_COUNT;
        mHandler.sendMessage(msg);
    }
}
