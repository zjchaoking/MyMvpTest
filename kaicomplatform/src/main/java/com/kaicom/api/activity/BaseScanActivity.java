package com.kaicom.api.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.kaicom.api.scan.ScanManager;
import com.kaicom.api.scan.ScanObserver;
import com.kaicom.api.scan.ScanManager.TrigModeType;

/**
 * <h4>BaseScanActivity</h4>
 * 已实现扫描接口,并在其生命周期方法中控制扫描头的开关
 * 
 * @author scj
 */
@Deprecated
public abstract class BaseScanActivity extends BaseActivity implements ScanObserver {

    protected ScanManager scanManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanManager = ScanManager.getInstance();
        scanManager.attch(this);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanManager.detach(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (scanManager.isScanning()) {
            endScan();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (scanManager.scanEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (scanManager.scanEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    
    /**
     * 开始扫描<br>
     * 客户程序需要特殊处理时可直接调用
     */
    protected void startScan() {
        scanManager.startScan();
    }
    
    /**
     * 结束扫描<br>
     * 客户程序需要特殊处理时可直接调用
     */
    protected void endScan() {
        scanManager.endScan();
    }
    
    /**
     * 设置扫描模式
     * @param type
     */
    protected void setScanModeType(TrigModeType type) {
        scanManager.setTrigMode(type);
    }
    
}
