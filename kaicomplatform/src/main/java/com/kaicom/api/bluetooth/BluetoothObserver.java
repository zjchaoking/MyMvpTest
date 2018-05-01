package com.kaicom.api.bluetooth;

/**
 * 蓝牙观察者
 * <p>
 * 监听蓝牙的状态变化
 * @author scj
 */
interface BluetoothObserver {

	/**
	 * 蓝牙状态发生改变时调用
	 * @param state
	 */
	void onStateChanged(BluetoothState state);
	
}
