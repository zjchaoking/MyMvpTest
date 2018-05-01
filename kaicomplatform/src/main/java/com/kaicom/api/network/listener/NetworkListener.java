
package com.kaicom.api.network.listener;

import android.app.Activity;

import com.kaicom.api.network.interfaces.INetworkEvent;

/**
 * 
 * 功能说明:网络监听接口.
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author PF05RN7V
 * @version 2.0.0.2
 * 创建日期   2015-4-29
 */
public abstract class NetworkListener {
   /**
    * 网络回调接口.
    * @param event  网络事件
    * @param activity 回调的activity
    */
    public abstract void onNetResponse(INetworkEvent event);
}
