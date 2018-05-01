package com.kaicom.api.upgrade.response;

/**
 * <h3>升级协议响应</h3>
 * 
 * <p>升级协议返回内容封装
 * <br><br>
 * 
 * UpdateType 是否升级标示
 * <br>
 * FileName 文件名
 * <br>
 * FileUrl 文件地址
 * 
 * @author wxf
 */
public class IsUpgradeResponse {
	
	private int UpdateType;
	
	private String FileName = "";
	
	private String FileUrl = "";

	private String MD5 = "";
	
	public int getUpdateType() {
		return UpdateType;
	}

	public void setUpdateType(int updateType) {
		UpdateType = updateType;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileUrl() {
		return FileUrl;
	}

	public void setFileUrl(String fileUrl) {
		FileUrl = fileUrl;
	}
	
	public boolean isUpgrade(){
		
		return UpdateType > 0;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	
	
}
