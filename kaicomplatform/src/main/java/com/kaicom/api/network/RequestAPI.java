
package com.kaicom.api.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.kaicom.api.network.event.EventFactory;
import com.kaicom.api.network.interfaces.INetworkEvent;
import com.kaicom.api.network.net.NetworkManager;
import com.kaicom.api.network.net.params.NetParams;
import com.kaicom.api.util.NetworkUtil;




/**
 * 
 * 功能说明:后台数据请求类.
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author PF05RN7V
 * @version 2.0.0.2
 * 创建日期   2015-4-29
 */
public class RequestAPI {
    /**
     * encrypt.
     */
    public   boolean encrypt = true;
    public static   String encode = "UTF-8";
    
    /**
     * loginURL.
     */
    public final static String loginURL = "/accounts/authentication";
    public final static String loginEventId = "G002";
    
    public void setEncode(String encode) {
    	this.encode = encode;
    }
    /**
     * authURL.
     */
    /**
     * 取参数 .
     * @param map  map
     * @return String
     */
    public static String getParams(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = map.get(key);
            if (value instanceof String) {
                String tempValue = ((String) value).trim();
                if (TextUtils.isEmpty(tempValue))
                    continue;
                
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(tempValue);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            } else {
                Log.e("params error", "parameter named " + key
                        + "'s value is not a string type!");
                return "";
            }
        }

        return sb.toString();
    }

//    /**
//     * 登录认证.
//     *
//     * @param handler  handler
//     * @param userName userName
//     * @param pwd      pwd
//     * @return String
//     */
//    public static String Login(Handler handler, String userName, String pwd,
//                               Activity activity) {
//        INetworkEvent event = EventFactory.getEvent();
//        event.setEventId(loginEventId);
//        NetParams netParams = new NetParams(loginURL);
//        netParams.setMethod(NetParams.POST);
//        netParams.setNetworkType(NetworkManager.CONN_TYPE_HTTP);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("username", userName);
//        map.put("pwd", pwd);
//
//        netParams.setParams(RequestAPI.getParams(map));
//        event.setMessageBody(NetworkUtil.netParamstoByte(netParams));
//
//        return KaicomNetManager.sendEvent(event, handler, netParams, activity);
//    }

   
}
