package com.kaicom.api.blue;


/**
 * 获取蓝牙数据
 * 
 * @author hb
 * 
 */
public interface BluetoothDataListener {

	/**
	 * 读到蓝牙数据
	 * 
	 * @param data
	 */
	public void onReadBlueData(String data);

	/**
	 * 解析数据
	 * 
	 * @param data
	 */
	public void onResolveData(double data);
}
