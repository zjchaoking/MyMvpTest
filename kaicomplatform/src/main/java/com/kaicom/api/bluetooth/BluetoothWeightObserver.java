package com.kaicom.api.bluetooth;

/**
 * 蓝牙称重观察者
 * <p>
 * 监听称重数据的变化
 * @author scj
 *
 */
public interface BluetoothWeightObserver extends BluetoothObserver {

	/**
	 * 蓝牙重量变化
	 * @param weight-重量
	 */
	void onResolveWeightData(String weight);
	
}
