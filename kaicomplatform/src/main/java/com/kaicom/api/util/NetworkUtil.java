package com.kaicom.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Log;

import com.kaicom.api.network.net.params.NetParams;
/**
 * 
 * 功能说明：网络相关公用函数 .
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author PF05RN7V
 * @version 2.0.0.2
 * 创建日期   2015-4-29
 */
public  final class NetworkUtil {
	/**
	 * tag.
	 */
	public static String tag = "networkUtil";
	 /**
     * Byte转 NetParams Object.
     * @param bytes  bytes
     * @return NetParams
     */
    public static NetParams ByteToObject(byte[] bytes)
    {
        NetParams obj = new NetParams();
        try
        {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = (NetParams) oi.readObject();

            bi.close();
            oi.close();
        } catch (Exception e)
        {
           Log.e(tag,"translation" + e.getMessage());
            e.printStackTrace();
        }

        return obj;
    }
    
    /**
     * 组装网络参数.
     * @param netParams  netParams
     * @return byte[]
     */
    public static byte[] netParamstoByte(NetParams netParams)
    {

        byte[] bytes = new byte[1024];

        try
        {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(netParams);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        }
        catch (Exception e)
        {
            Log.e(tag,"translation" + e.getMessage());
            e.printStackTrace();
        }

        return (bytes);
    }

}
