package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;

import com.kaicom.api.bluetooth.BluetoothException;

/**
 * TDI300/200协议解析
 * @author scj
 *
 */
public class ScalesTDI300Parser extends BluetoothWeightParser {

	private byte[] TDI300 = { 0x41, 0x54, 0x56, 0x31, 0x51, 0x30, 0x0d, 0x02,
			0x41, 0x44, 0x00, 0x05, 0x03 };

	@Override
	public void sendCommandFor(OutputStream output) throws IOException {
		output.write(TDI300);
		sleep(30);
	}

	@Override
	public String parseData(byte[] data) throws BluetoothException {
		String dataStr = new String(data);

		if (!confirmProtocol) {
			boolean flag = checkScalesType(dataStr);
			if (!flag)
				throw new BluetoothException("蓝牙秤类型错误");
		}

		String[] strs = dataStr.split("AD");
		if (strs.length < 2)
			return null;

		if (strs[1].length() > 6) {
			String s = strs[1].substring(0, 6);
			if (!isNumberDecimals(s))
				return null;

			float it = Float.valueOf(s);
			char ch = strs[1].charAt(6);
			if (!isNumber(ch)) {
				return null;
			}
			String result = it / 1000 + "";
			return result;
		}

		return null;
	}

	@Override
	protected boolean checkScalesType(String dataStr) {
		if (dataStr.indexOf("=") > 0) {
			return false;
		}
		
		if (dataStr.indexOf("+") > 0) {
			return false;
		}

		if (dataStr.indexOf("AD") > 0) {
			confirmProtocol = true;
			return true;
		}

		if (dataStr.length() > 12
				&& dataStr.indexOf("AD") < 0) {
			return false;
		}
		return true;
	}

}
