package com.kaicom.api.bluetooth;

import java.util.Set;

/**
 * 蓝牙搜索监听事件
 * @author scj
 */
public interface DiscoveryListener {
	
	/**
	 * 开始搜索设备
	 */
	void onStartDiscovery();
	
	/**
	 * 搜索结束
	 * @param size-搜索到的设备数量
	 */
	void onFinishDiscovery(int size);
	
	/**
	 * 找到未配对的设备
	 * @param set 
	 */
	void onFoundUnpairedDevices(Set<BluetoothDeviceItem> set);
	
}
