package com.kaicom.api.bluetooth;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * 蓝牙工具类
 * 
 * @author scj
 * 
 */
public class BluetoothUtils {

	public static void enableBluetooth() {
		BluetoothAdapter.getDefaultAdapter().enable();
	}

	public static void disableBluetooth() {
		BluetoothAdapter.getDefaultAdapter().disable();
	}

	public static boolean isBluetoothEnabled() {
		return BluetoothAdapter.getDefaultAdapter().isEnabled();
	}

	public static boolean isBluetoothAddress(String address) {
		return BluetoothAdapter.checkBluetoothAddress(address);
	}
	
	/**
	 * 跳转到系统蓝牙设置界面
	 * @param activity
	 */
	public static void startActvityForBluetoothSettings(Activity activity) {
		Intent intent = new Intent("android.settings.BLUETOOTH_SETTINGS");
		activity.startActivity(intent);
	}

	/**
	 * 获得标准的蓝牙地址
	 * 
	 * @param addr
	 *            蓝牙地址
	 * @return 返回一个标准的蓝牙地址,如果地址不正确返回null
	 */
	@SuppressLint("DefaultLocale")
	public static synchronized String convertStandardBluetoothAddress(
			String address) {
		if (TextUtils.isEmpty(address))
			return null;
		address = address.toUpperCase();

		if (isBluetoothAddress(address))
			return address;

		if (address.matches("[0-9A-F]{12}")) {
			StringBuilder newAddress = new StringBuilder();
			for (int i = 0; i < 6; i++) {
				newAddress.append(address.charAt(i * 2)).append(
						address.charAt(2 * i + 1));
				if (i < 5)
					newAddress.append(":");
			}
			return newAddress.toString();
		}

		return null;
	}

	/**
	 * 判断蓝牙设备是否已经配对
	 * 
	 * @return
	 */
	public static boolean isBonded(String address) {
		if (!isBluetoothAddress(address)) {
			return false;
		}
		BluetoothDevice device = getBluetoothDevice(address);
		return isBonded(device);
	}

	/**
	 * 判断蓝牙设备是否已经配对
	 * 
	 * @return
	 */
	public static boolean isBonded(BluetoothDevice device) {
		if (device == null)
			return false;
		return device.getBondState() == BluetoothDevice.BOND_BONDED;
	}

	/**
	 * 通过蓝牙地址获取蓝牙设备
	 * 
	 * @param address
	 * @return
	 * @throws IllegalArgumentException
	 *             蓝牙地址错误
	 */
	public static synchronized BluetoothDevice getBluetoothDevice(String address) {
		if (!isBluetoothAddress(address)) {
			Log.e("蓝牙地址", address);
			throw new IllegalArgumentException("蓝牙地址错误:address");
		}
		return BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
	}

	/**
	 * 执行蓝牙配对
	 * @param btDevice
	 * @return
	 * @throws Exception 
	 */
	public static boolean createBond(BluetoothDevice btDevice) throws Exception {
		Method createBondMethod = btDevice.getClass().getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	/**
	 * 取消配对
	 * @param btDevice
	 * @return
	 * @throws Exception
	 */
	public static boolean removeBond(BluetoothDevice btDevice)
			throws Exception {
		Method removeBondMethod = btDevice.getClass().getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}

	/**
	 * 设置蓝牙配对码
	 * @param device
	 * @param pinCode
	 * @return
	 * @throws Exception
	 */
	public static boolean setPin(BluetoothDevice device, String pinCode)
			throws Exception {
		Method autoBondMethod = device.getClass().getMethod("setPin",
				new Class[] { byte[].class });
		Boolean result = (Boolean) autoBondMethod.invoke(device,
				convertPinToBytes(pinCode));
		return result;
	}
	
	/**
	 * 将pin字符串转成字节数组
	 * @param pin
	 * @return
	 */
    public static byte[] convertPinToBytes(String pin) {
        if (pin == null) {
            return null;
        }
        byte[] pinBytes;
        try {
            pinBytes = pin.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
        if (pinBytes.length <= 0 || pinBytes.length > 16) {
            return null;
        }
        return pinBytes;
    }
    
    public static boolean setPassKey(BluetoothDevice device, int passKey) throws Exception {
    	Method method = device.getClass().getMethod(
				"setPasskey", new Class[] { int.class });
		Boolean returnValue = (Boolean) method.invoke(device, passKey);
		return returnValue.booleanValue();
    }

	/**
	 * 取消配对框
	 * @param device
	 * @return
	 * @throws Exception
	 */
	public static boolean cancelBondProcess(BluetoothDevice device)
			throws Exception {
		Method method = device.getClass().getMethod(
				"cancelBondProcess");
		Boolean returnValue = (Boolean) method.invoke(device);
		return returnValue.booleanValue();
	}

}
