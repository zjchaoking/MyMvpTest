package com.kaicom.api.blue.manager;

import android.content.Context;

/**
 * 蓝牙管理基类
 * 
 * @author hb
 * 
 */
public class BaseThread extends Thread {
	public Context context;

	// 蓝牙连接状态
	public Boolean isRead = true;

	public BaseThread(Context context) {
		this.context = context;
	}
}
