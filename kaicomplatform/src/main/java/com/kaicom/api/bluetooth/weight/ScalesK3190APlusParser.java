package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;

import com.kaicom.api.bluetooth.BluetoothException;
import com.kaicom.api.log.KlLoger;

/**
 * A1+蓝牙秤协议, 命令类型的蓝牙秤
 * @author scj
 *
 */
public class ScalesK3190APlusParser extends BluetoothWeightParser {

	public static final byte[] A1_PLUS = { 0x02, 0x41, 0x44, 0x30, 0x35, 0x03 };

	@Override
	public void sendCommandFor(OutputStream output) throws IOException {
		output.write(A1_PLUS);
		sleep(30);
	}

	@Override
	public String parseData(byte[] data) throws BluetoothException {
		String dataStr = new String(data);

		// 判断是否选错秤类型
		if (!confirmProtocol) {
			boolean flag = checkScalesType(dataStr);
			if (!flag)
				throw new BluetoothException("蓝牙秤类型错误");
		}

		String[] strs = dataStr.split("AD");

		if (strs.length < 2)
			return null;

		if (strs[1].length() > 7) {
			String s = strs[1].substring(0, 7);

			float it = Float.valueOf(s);
			char ch = strs[1].charAt(7);
			if (!isNumber(ch)) {
				return null;
			}
			String result = it / mypow(10, Character.digit(ch, 10)) + "";
			return result;
		}
		return null;
	}

	@Override
	protected boolean checkScalesType(String dataStr) {
		if (dataStr.indexOf("=") > 0) {
			return false;
		}

		if (dataStr.indexOf("AD+") > 0 || dataStr.indexOf("AD-") > 0) {
			confirmProtocol = true;
			return true;
		}

		if (dataStr.length() > 12
				&& dataStr.indexOf("AD+") < 0) {
			KlLoger.error("A1+蓝牙秤协议错误:" +dataStr);
			return false;
		}
		return true;
	}

	private float mypow(int x, int y) {
		float ret = 1;
		while (y > 0) {
			ret *= x;
			y--;
		}
		return ret;
	}

}
