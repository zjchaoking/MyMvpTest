package com.kaicom.api.network.xml;

import java.util.Map;

import com.kaicom.api.util.EncryptUtil;


/**
 * 
 * 功能说明:生成xml,  允许加密.
 * Copyright copyright(c)  2015 杭州凯立通信有限公司  版权所有
 * @author PF05RN7V
 * @version 2.0.0.2
 * 创建日期   2015-4-28
 */
public class XmlCreate {
	/**
	 * xml body map. 
	 */
	public Map<String,Object> bodyMap;
	/**
	 * xml head map ,没有特别定义的head则为null.
	 */
	public Map<String,Object> headMap;
	
	
	/**
	 * 加密字符.
	 * @param str 被加密的字符串
	 * @param encryptType  加密类型
	 * @return 加密后的字符串
	 */
	public String getEncryptValue(String str,String encryptType){
		String result = "";
		if("base64".equals(encryptType)){
			result = EncryptUtil.base64(str);
		}else if("md5".equals(encryptType)){
			result = EncryptUtil.md5(str).toString();
		}		
		
		return result;
	}
    
	
	


}
