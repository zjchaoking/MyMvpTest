package com.kaicom.api.upgrade.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h4>升级请求数据</h4>
 * 升级请求数据
 * 
 * @author wxf
 */
public class RequestData {
	
	private static final String TAG = "RequestData";
	
	String name = "";
	
	String netNumber = "";
	
	String machineCode = "";
	
	String currentVersion = "";
	
	String encryptedStr = "";
	
	String requstParams[][]={ {"WDTName", name} , 
							  {"NetNumber", netNumber}, 
							  {"MachineCode", machineCode}, 
							  {"CurrentVersion", currentVersion}, 
							  {"EncryptedStr", encryptedStr}
							};
	
	public RequestData() {
		// TODO Auto-generated constructor stub
	}
	
	public RequestData(String name, String netNumber, String machineCode,
			String currentVersion, String encryptedStr) {
		super();
		this.name = name;
		this.netNumber = netNumber;
		this.machineCode = machineCode;
		this.currentVersion = currentVersion;
		this.encryptedStr = encryptedStr;
		
		requstParams[0][1] = name;
		requstParams[1][1] = netNumber;
		requstParams[2][1] = machineCode;
		requstParams[3][1] = currentVersion;
		requstParams[4][1] = encryptedStr;
	}

	public RequestData(String[][] requstParams) {
		super();
		this.requstParams = requstParams;
	}

	public List<NameValuePair> getParams(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for (String param[] : requstParams) {
			params.add(new BasicNameValuePair(param[0], param[1]));
		}
		
		return params;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNetNumber() {
		return netNumber;
	}


	public void setNetNumber(String netNumber) {
		this.netNumber = netNumber;
	}


	public String getMachineCode() {
		return machineCode;
	}


	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}


	public String getCurrentVersion() {
		return currentVersion;
	}


	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}


	public String getEncryptedStr() {
		return encryptedStr;
	}


	public void setEncryptedStr(String encryptedStr) {
		this.encryptedStr = encryptedStr;
	}


	public String[][] getRequstParams() {
		return requstParams;
	}


	public void setRequstParams(String[][] requstParams) {
		this.requstParams = requstParams;
	}

	@Override
	public String toString() {
		return "RequestData{" +
				"currentVersion='" + currentVersion + '\'' +
				", name='" + name + '\'' +
				", netNumber='" + netNumber + '\'' +
				", machineCode='" + machineCode + '\'' +
				", encryptedStr='" + encryptedStr + '\'' +
				", requstParams=" + Arrays.toString(requstParams) +
				'}';
	}
}
