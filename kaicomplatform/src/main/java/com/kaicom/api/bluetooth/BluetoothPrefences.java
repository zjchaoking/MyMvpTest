package com.kaicom.api.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class BluetoothPrefences {

	private static BluetoothPrefences instance;
	private SharedPreferences mSp;

	private BluetoothPrefences(Context context) {
		mSp = context
				.getSharedPreferences("kl_bluetooth", Context.MODE_PRIVATE);
	}

	public synchronized static BluetoothPrefences getInstance(Context context) {
		if (instance == null) {
			instance = new BluetoothPrefences(context);
		}
		return instance;
	}

	/**
	 * 设置蓝牙秤类型
	 * @param macAddress
	 * @param type
	 */
	public void setScalesType(String macAddress, ScalesType type) {
		mSp.edit().putString("ScalesType_" + macAddress, type.name()).commit();
	}

	/**
	 * 根据蓝牙设备获取蓝牙秤类型
	 * @param device
	 * @return
	 */
	public ScalesType getScalesType(BluetoothDevice device) {
		return getScalesType(device.getAddress());
	}

	/**
	 * 根据蓝牙地址获取蓝牙秤类型
	 * @param macAddress
	 * @return
	 */
	public ScalesType getScalesType(String macAddress) {
		String scalesName = mSp.getString("ScalesType_" + macAddress,
				ScalesType.Scales_None.name());
		if (TextUtils.isEmpty(scalesName)) {
			return null;
		} else {
			return ScalesType.valueOf(scalesName);
		}
	}

	/**
	 * 设置称重数据小数位
	 * @param type
	 */
	public void setDecimalType(WeightDecimalType type) {
		mSp.edit().putString("DecimalType", type.name()).commit();
	}

	/**
	 * 获取称重数据小数保留位设置
	 * @return
	 */
	public WeightDecimalType getWeightDecimalType() {
		String name = mSp.getString("DecimalType",
				WeightDecimalType.NO_KEEP.name());
		if (TextUtils.isEmpty(name)) {
			return null;
		} else {
			return WeightDecimalType.valueOf(name);
		}
	}

	private static final String K_LAST_DEVICE_ADDRESS = "LastDeviceAddress";

	/**
	 * 保存最后一次连接的设备地址
	 * @param address
	 */
	public void setLastDeviceAddress(String address) {
		mSp.edit().putString(K_LAST_DEVICE_ADDRESS, address).commit();
	}

	/**
	 * 获得最后一次连接设备的地址
	 * @return
	 */
	public String getLastDeviceAddress() {
		return mSp.getString(K_LAST_DEVICE_ADDRESS, "");
	}

}
