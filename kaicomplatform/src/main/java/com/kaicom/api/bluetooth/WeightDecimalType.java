package com.kaicom.api.bluetooth;

public enum WeightDecimalType {

	NO_KEEP("无"), KEEP_ONE("小数点后保留1位"), KEEP_TWO("小数点后保留2位"), KEEP_THREE("小数点后保留3位");
	
	private String mDesc;
	
	private WeightDecimalType(String desc) {
		mDesc = desc;
	}
	
	public String getDesc() {
		return mDesc;
	}
	
	public synchronized static String[] getDescArray() {
		WeightDecimalType[] decimalTypes = WeightDecimalType.values();
		String[] array = new String[decimalTypes.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = decimalTypes[i].getDesc();
		}
		return array;
	}
	
}
