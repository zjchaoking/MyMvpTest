package com.kaicom.api.upgrade;

/**
 * <h4>升级接口</h4>
 * <p>升级信息接口
 * <br>
 * onStartUpdate 开始更新
 * <br>
 * onProgressUpdate 更新进度
 * <br>
 * onFinished 更新完成
 * <br>
 * onFailed 更新失败
 * 
 * @author wxf
 */
public interface UpgradeStatus {
	
	void onStartUpdate();
	
	
	void onProgressUpdate(int values);
	
	
	void onFinished(String cause);
	
	void onFailed(String cause);
	
}
