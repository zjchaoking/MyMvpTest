package com.kaicom.api.blue;

/**
 * 蓝牙名称地址实体类
 * 
 * @author hb
 * 
 */
public class NameAddrEntity {
	// 蓝牙名称
	private String bluetoothName;

	// 蓝牙mac地址
	private String bluetoothAddr;

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getBluetoothAddr() {
		return bluetoothAddr;
	}

	public void setBluetoothAddr(String bluetoothAddr) {
		this.bluetoothAddr = bluetoothAddr;
	}

	public String toString() {
		return bluetoothName.concat("   ").concat(bluetoothAddr);
	}
}
