package com.kaicom.api.upgrade;

import android.os.Environment;

import com.kaicom.api.util.FileUtil;

import java.io.File;


/**
 * <h4>升级配置</h4>
 * <p>配置地址，端口，信息
 * <br>
 * addr 服务器地址
 * <br>
 * port 端口
 * <br>
 * defultTempPath 默认为SD卡根目录
 * <br>
 * tmpFilePath 设置目录
 * <br>
 * installType 安装软件方式(默认屏蔽<取消button, 完成button> 配置)
 * <br>
 * isAutoInstall 是否自动安装(默认自动安装(true), 否则,false)
 * @author wxf
 */
public class UpgradeConfig {
	
	private static final String TAG="UpgradeConfig";
	
	/**
	 * 恢复系统默认
	 */
	public static final int ACTION_DEFAULT = 0x0;				//恢复系统默认
	
	/**
	 * 都屏蔽(取消button, 完成button)
	 */
	public static final int ACTION_DIS_CANCEL_FINISH = 0X5A;	//都屏蔽
	
	/**
	 * 屏蔽取消button
	 */
	public static final int ACTION_DIS_CANCEL = 0XA;  			//屏蔽取消button 
	
	/**
	 * 屏蔽完成button
	 */
	public static final int ACTION_DIS_FINISH = 0x50; 			//屏蔽完成button
	
	
	private String addr="";
	
	private int port;
	
	private int installType = ACTION_DIS_CANCEL_FINISH;
	
	public static final String defultTempPath = Environment
            .getExternalStorageDirectory() + File.separator;
	
	private String tmpFilePath;
	
	private boolean isAutoInstall = false;

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setTmpFilePath(String tmpFilePath) throws Exception{
		
		if(FileUtil.makeDirs(tmpFilePath)){
			this.tmpFilePath = tmpFilePath;
		}else{
			this.tmpFilePath = defultTempPath;
			throw new Exception("创建路径失败");
		}
		
	}
	
	
	public String getTmpFilePath() {
		return tmpFilePath;
	}
	
	public UpgradeConfig(){
		// TODO Auto-generated constructor stub
		this.tmpFilePath = defultTempPath;
	}
	
	public UpgradeConfig(String addr, int port) {
		// TODO Auto-generated constructor stub
		this.addr = addr;
		this.port = port;
		this.tmpFilePath = defultTempPath;
	}
	
	public UpgradeConfig(String addr, int port, boolean isAutoInstall) {
		// TODO Auto-generated constructor stub
		this.addr = addr;
		this.port = port;
		this.tmpFilePath = defultTempPath;
		this.isAutoInstall = isAutoInstall;
	}
	
	public UpgradeConfig(String addr, int port, String tmpFilePath, 
						boolean isAutoInstall) throws Exception{
		// TODO Auto-generated constructor stub
		this.addr = addr;
		this.port = port;
		this.tmpFilePath = tmpFilePath;
		this.isAutoInstall = isAutoInstall;
		setTmpFilePath(tmpFilePath);
	}


	@Override
	public String toString() {
		return "UpgradeConfig [addr=" + addr + ", port=" + port
				+ ", installType=" + installType + ", tmpFilePath="
				+ tmpFilePath + ", isAutoInstall=" + isAutoInstall + "]";
	}

	public int getInstallType() {
		return installType;
	}

	public void setInstallType(int installType) {
		this.installType = installType;
	}

	public boolean isAutoInstall() {
		return isAutoInstall;
	}

	public void setAutoInstall(boolean isAutoInstall) {
		this.isAutoInstall = isAutoInstall;
	}

	public String getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}
	
	
	
}
