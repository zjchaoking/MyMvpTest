/**
 * 
 */
package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;

import com.kaicom.api.bluetooth.BluetoothException;

/**
 * @author wxf
 *
 */
public class ScalesMinSiDaParser extends BluetoothWeightParser {
	
	public void sendCommandFor(OutputStream output) throws IOException {
		sleep(36);
	}

	/* (non-Javadoc)
	 * @see com.kaicom.api.bluetooth.weight.BluetoothWeightParser#parseData(byte[])
	 */
	@Override
	public String parseData(byte[] data) throws BluetoothException {
		String dataStr = new String(data);
		if (!confirmProtocol) {
			boolean flag = checkScalesType(dataStr);
			if (!flag)
				throw new BluetoothException("蓝牙秤类型错误");
		}
		int start = dataStr.indexOf("+");
		int end = dataStr.indexOf("k");
		if( start > 0 &&  end > 0){
			if(end <= dataStr.length() && start < end){
				String temp = dataStr.substring(start + 1, end);
				if(isNumberDecimals(temp.trim())){
					float retData = Float.valueOf(temp.trim());
					dataStr = "";
					return retData + "";
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			
		}
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kaicom.api.bluetooth.weight.BluetoothWeightParser#checkScalesType(java.lang.String)
	 */
	@Override
	protected boolean checkScalesType(String dataStr) {
		if (dataStr.matches(".*[AD=].*")) {
			return false;
		}

		if(dataStr.matches(".*[STkGg].*")){
			confirmProtocol = true;
			return true;
		}
		
		return true;
	}

	

}
