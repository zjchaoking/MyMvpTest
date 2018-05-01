package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;

import com.kaicom.api.bluetooth.BluetoothException;

/**
 * A12蓝牙秤协议, 命令类型的蓝牙秤
 * 
 * @author scj
 *
 */
public class ScalesK3190A12Parser extends BluetoothWeightParser {

	@Override
	public void sendCommandFor(OutputStream output) throws IOException {
		output.write('R');
		output.flush();
		sleep(70);
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

		String[] strs = dataStr.split("=");
		if (strs.length < 2)
			return null;

		for (int i = strs.length - 1; i >= 0; i--) {
			if (strs[i].length() > 5 && isNumberDecimals(strs[i])) {
				StringBuffer sb = new StringBuffer(strs[i]);
				float retData = Float.valueOf(sb.reverse().toString());
				return retData + "";
			}
		}
		return null;
	}

	@Override
	protected boolean checkScalesType(String dataStr) {
		if (dataStr.matches(".*[AD\\+].*")) {
			return false;
		}

		if (dataStr.indexOf("=") > 0) {
			confirmProtocol = true;
			return true;
		}

		if (dataStr.length() > 8 && dataStr.indexOf("=") < 0) {
			return false;
		}
		return true;
	}

}
