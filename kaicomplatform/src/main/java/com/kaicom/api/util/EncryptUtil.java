package com.kaicom.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import android.util.Base64;
/**
 * 
 * 功能说明:加密解密
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author PF05RN7V
 * @version 2.0.0.2
 * 创建日期   2015-4-28
 */

public class EncryptUtil {
	
	
	/** 
	 * 方法名称:transMapToString 
	 * 传入参数:map 
	 * 返回值:String 形如 usernamechenziwenpassword1234 
	*/  
	public static String transMapToString(Map map){  
	  java.util.Map.Entry entry;  
	  StringBuffer sb = new StringBuffer();  
	  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
	  {  
	    entry = (java.util.Map.Entry)iterator.next();  
	      sb.append(entry.getKey().toString()).append(null==entry.getValue()?"":  
	      entry.getValue().toString());  
	  }  
	  return sb.toString();  
	}  
   /**
    * base64加密.
    * @param str 应当加密的字符
    * @return 加密后的字符串
    */
	public static String base64(String str){
		String strBase64 = new String(Base64.encode(str.getBytes(),  android.util.Base64.NO_WRAP));
		return strBase64;
	}
	/**
	    * base64加密.
	    * @param str 应当加密的字符
	    * @return 加密后的字符串
	    */
	public static String base64(byte[] str){
		
		String strBase64 = new String(Base64.encode(str, android.util.Base64.NO_WRAP));
		return strBase64;
	}
	/**
	 * md5加密.
	 * @param str 应当加密的字符
	 * @return 加密后的字符串
	 */
	public static byte[]  md5(String str) {

	    byte[] hash;

	    try {
	    	
	        hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));

	    } catch (NoSuchAlgorithmException e) {

	        throw new RuntimeException("Huh, MD5 should be supported?", e);

	    } catch (UnsupportedEncodingException e) {

	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);

	    }

	    return hash;

	}
}
