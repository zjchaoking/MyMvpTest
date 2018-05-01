package com.kaicom.api.scan;

/**
 * 扫描观察者
 * 
 * @author scj
 */
public interface ScanObserver {

    /**
     * 在该方法中处理扫描获得的数据
     * @param result 扫描结果
     */
    public void onScan(String result);
    
}
