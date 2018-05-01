package com.kaicom.api.bluetooth.weight;

import android.text.TextUtils;
import android.util.Log;

import com.kaicom.api.bluetooth.BluetoothException;
import com.kaicom.api.log.KlLoger;
import com.kaicom.api.log.LogInfo;
import com.kaicom.api.util.DateUtil;
import com.kaicom.api.util.StringUtil;
import com.kaicom.api.util.SystemUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 耀华XK3190-A27E
 * @author fubin
 *
 */
public class ScalesXK3190A27EParser extends BluetoothWeightParser {

	/**
	 * 净重
	 */
	private static final String NET_WEIGHT_CMD = "wn";
	/**
	 * 毛重
	 */
	private static final String GROSS_WEIGHT_CMD = "ww";
	/**
	 * 皮重
	 */
	private static final String TARE_WEIGHT_CMD = "wt";
	private static final String END_CMD = "kg";

	@Override
	public void sendCommandFor(OutputStream output) throws IOException {
		sleep(20);
	}

	@Override
	public String parseData(byte[] data) throws BluetoothException {
		String dataStr = new String(data);
		KlLoger.debug("time="+ DateUtil.dateFormat(new Date(), "HH:mm:ss.SSSS")+dataStr);
		if (!confirmProtocol) {
			boolean flag = checkScalesType(dataStr);
			if (!flag)
				throw new BluetoothException("蓝牙秤类型错误");
		}
		String[] strs = dataStr.split("\\n");
		if (strs.length < 1)
			return null;
		for (int i = strs.length - 1; i >= 0; i--) {
			int start = startCmdIndex(strs[i]);
			int end = strs[i].indexOf(END_CMD);
			if (start >= 0 && end > (start+2)) {
				String str = StringUtil.chomp(strs[i].substring(start + 2, end));
				if (isNumberDecimals(str)) {
					float retData = Float.valueOf(str);
					KlLoger.debug("time="+ DateUtil.dateFormat(new Date(),"HH:mm:ss.SSSS")+";pareResult="+retData);
					return retData + "";
				}
			}
		}
		return null;
	}

	private int startCmdIndex(String data) throws BluetoothException{
		int index = data.indexOf(NET_WEIGHT_CMD);
		if (index != -1)
			return index;
		index = data.indexOf(GROSS_WEIGHT_CMD);
		if (index != -1)
			return index;
		index = data.indexOf(TARE_WEIGHT_CMD);
		return index;
	}

	@Override
	protected boolean checkScalesType(String dataStr) {
		try {
			if (dataStr.matches(".*[(wn|ww|wt)\\+].*")) {
				return false;
			}

			if (startCmdIndex(dataStr) > 0) {
				confirmProtocol = true;
				return true;
			}

			if (dataStr.length() > 8 && startCmdIndex(dataStr) < 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
