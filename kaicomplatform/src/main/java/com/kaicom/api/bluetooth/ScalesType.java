package com.kaicom.api.bluetooth;

/**
 * 秤类型
 * @author scj
 *
 */
public enum ScalesType {
	
	Scales_None("无"),
	K3190A1("耀华K3190A1_TYPE"), 
	K3190AX("耀华K3190AX_TYPE"), 
	K3190A_Plus("耀华K3190A1+_TYPE"),
	K3190A7("耀华K3190A7_TYPE"), 
	K3190A12("耀华K3190A12_TYPE"),
	TDI300("天合TDI300_TYPE"), 
	TDI200A1("天合TDI200A1_TYPE"), 
	QuanHeng("上海权衡公司"),
	XK3190A27PlusE("耀华XK3190-A27+E"),
	MINSIDA("敏思达"),
	XK3190A27E("耀华XK3190-A27E");
	
	private String mDesc;
	
	private ScalesType(String desc) {
		mDesc = desc;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
	public synchronized static String[] getDescArray() {
		ScalesType[] scalesTypes = ScalesType.values();
		String[] array = new String[scalesTypes.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = scalesTypes[i].getDesc();
		}
		return array;
	}
	
}
