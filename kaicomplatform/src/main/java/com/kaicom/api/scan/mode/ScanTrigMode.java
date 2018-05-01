package com.kaicom.api.scan.mode;

import android.view.KeyEvent;

/**
 * 扫描触发方式
 * 
 * @author scj
 */
public interface ScanTrigMode {
    
    public boolean event(int keyCode, KeyEvent event);
    
}
