package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

import com.kaicom.api.bluetooth.BluetoothException;

/**
 * 蓝牙重量解析器
 * @author scj
 *
 */
public abstract class BluetoothWeightParser {
	
	protected boolean confirmProtocol;

	/**
	 * 发送蓝牙命令<br>
	 * 默认空实现,命令型蓝牙需实现
	 * @param output
	 */
	public void sendCommandFor(OutputStream output) throws IOException {
	}
	
	/** 是否已确定协议 */
	public boolean isConfirmProtocol() {
		return confirmProtocol;
	}
	
	/**
	 * 解析协议数据
	 * @param data
	 * @return 返回
	 * @throws BluetoothException
	 */
	public abstract String parseData(byte[] data) throws BluetoothException;
	
	/**
	 * 检查秤类型
	 * @return
	 */
	protected abstract boolean checkScalesType(String dataStr);
	
	/**
	 * 判断是否由数字组成
	 * @param str
	 * @return
	 */
	public static boolean isNumberDecimals(String str) {
		if (TextUtils.isEmpty(str))
			return false;
		Pattern pattern = Pattern.compile("^[0-9]+$|^-?[0-9]+\\.?[0-9]+$|^[0-9]+\\.?[0-9]+(-)+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	static boolean isNumber(char c) {
		return c >= 48 && c <= 57;
	}
	
	protected void sleep(long mill) {
		try {
			TimeUnit.MILLISECONDS.sleep(mill);
		} catch (InterruptedException e) {
		}
	}
	
}
