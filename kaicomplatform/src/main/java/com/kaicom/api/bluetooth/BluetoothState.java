package com.kaicom.api.bluetooth;

/**
 * 蓝牙状态
 * @author scj
 *
 */
public enum BluetoothState {

	/** 初始状态，未做任何操作 */
	NONE("蓝牙未连接"),
	
	/** 正在连接 */
	CONNECTING("正在连接蓝牙"),
	
	/** 已连接 */
	CONNECTED("蓝牙连接成功"),
	
	/** 连接失败 */
	CONNECT_FAIL("蓝牙连接失败"),
	
	/** 断开连接 */
	CONNECT_BREAK("蓝牙断开连接"),
	
	/** 未知秤类型 */
	UNKNOW_SCALES("未知秤类型"),
	
	/** 读取数据出错 */
	READ_ERROR("蓝牙读取数据出错"),
	
	/** 解析出错 */
	PARSE_ERROR("数据解析出错, 请确认秤类型"), 
	
	/** 切换秤类型 */
	CHANGE_SCALES("正在检测秤类型");
	
	private String mDesc;
	
	private BluetoothState(String desc) {
		mDesc = desc;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
}
