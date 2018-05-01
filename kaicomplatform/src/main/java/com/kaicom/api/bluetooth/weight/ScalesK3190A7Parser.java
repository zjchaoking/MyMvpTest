package com.kaicom.api.bluetooth.weight;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

import com.kaicom.api.bluetooth.BluetoothException;
import com.kaicom.api.log.KlLoger;

/**
 * A7蓝牙秤协议
 * 
 * @author scj
 * 
 */
public class ScalesK3190A7Parser extends BluetoothWeightParser {

	public void sendCommandFor(OutputStream output) throws IOException {
		sleep(20);
	}

	@Override
	public String parseData(byte[] data) throws BluetoothException {
		String dataStr = new String(data);
		System.out.println("dataStr:" + dataStr);
//		KlLoger.error("收到重量", dataStr);
		if (!confirmProtocol) {
			boolean flag = checkScalesType(dataStr);
			if (!flag)
				throw new BluetoothException("蓝牙秤类型错误");
		}

		String[] strs = dataStr.split("=");
		if (strs.length < 2)
			return null;

		for (int i = strs.length - 1; i >= 0; i--) {
			if (strs[i].length() > 6 && isNumberDecimals(strs[i])) {
				StringBuffer sb = new StringBuffer(strs[i]);
				float retData = Float.valueOf(sb.reverse().toString());
//				System.out.println(retData);
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
