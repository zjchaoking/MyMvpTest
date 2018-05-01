package com.kaicom.api.network.listener;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.kaicom.api.network.error.ErrorConstant;
import com.kaicom.api.network.interfaces.INetworkEvent;

/**
 * 
 * 功能说明:实现网络监听接口.
 * Copyright copyright(c) 2015 杭州凯立通信有限公司 版权所有 
 * @author zhy
 * @version 2.0.0.2 创建日期 2015-4-29
 */
public class NetworkListenerImpl extends NetworkListener {
	/**
	 * handler.
	 */
	private Handler handler = null;
	/**
	 * activity.
	 */
	private Activity activity;

	/**
	 * 
	 * @param handler
	 *            handler
	 * @param activityId
	 *            activityId
	 */
	public void setHandler(Handler handler, Activity activityId) {
		this.handler = handler;
		this.activity = activityId;
	}
   /**
    * 将网络返回值返回给调用Activity处理.
    */
	@Override
	public void onNetResponse(INetworkEvent event) {
		//if (null != this.activity && !activity.isFinishing()) {
			Message msg = new Message();
			msg.obj = event;
			handler.sendMessage(msg);
//		} else {
//			// 销毁进度提示
//			//System.out.println("销毁 进度条 ");
//			Message msg = new Message();
//			event.setReturnCode(ErrorConstant.OTHER_ERROR);
//			msg.obj = event;
//			handler.sendMessage(msg);
//		}

	}
}
