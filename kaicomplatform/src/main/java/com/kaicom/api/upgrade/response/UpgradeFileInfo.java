package com.kaicom.api.upgrade.response;

import java.io.InputStream;

/**
 * 
 * <h3>http资源文件信息</h3>
 * 
 * <p>http文件资源
 * <br>
 * length 文件大小
 * <br>
 * InputStream 流对象
 * 
 * @author wxf
 */
public class UpgradeFileInfo {

	private long length;
	
	private InputStream is;

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public UpgradeFileInfo(long length, InputStream is) {
		super();
		this.length = length;
		this.is = is;
	}
	
	
	
}
