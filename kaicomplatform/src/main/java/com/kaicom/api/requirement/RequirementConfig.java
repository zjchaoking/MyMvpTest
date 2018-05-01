package com.kaicom.api.requirement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kaicom.fw.R;

/**
 * 功能说明 读取配置项的值 . 
 * Copyright copyright(c) 2015 杭州凯立通信有限公司 版权所有 
 * @author zhouhy
 * @version 2.0.0.2 创建日期 2015-4-28
 */

public class RequirementConfig {
	/**
	 * tag.
	 */
	private String tag = "requirement";

	/**
	 * 上下文.
	 */
	private Context context;
	/**
	 * 需要导入的配置文件 名,如:R.raw.requirement
	 */
	public int resource;
	/**
	 * 存放初始化配置数据HashMap 整个application有效.
	 */

	private HashMap<String, String> configMap = new HashMap<String, String>();

	/**
	 * 导入上下文.
	 * 
	 * @param context
	 *            context
	 */
	public RequirementConfig(Context context) {
		this.context = context;
	}
	/**
	 * 初始化.
	 * @param context 上下文 
	 * @param resource 配置文件名
	 */
	public RequirementConfig(Context context,int resource) {
		this.context = context;
		this.resource = resource;
	}
	/**
	 * 引入配置参数值.
	 */
	public void init() {

		loadProperties();

	}

	/**
	 * 获取配置文件信息.
	 */
	private void loadProperties() {
		InputStream is = null;
		Properties p = new Properties();
		try {
			is = getRequirementConfig(context,resource);
			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Enumeration<Object> keyEnum = p.keys();
		while (keyEnum.hasMoreElements()) {
			Object key = keyEnum.nextElement();
			String value = p.getProperty(key.toString().trim());
			configMap.put(key.toString(), value);

		}
		p = null;
	}
	
	
	/**
	 * 读取配置文件 requirement_config.properties到流文件 .
	 * 
	 * @param context
	 *            上下文
	 * @return InputStream
	 */
	public static InputStream getRequirementConfig(Context context,int resource) {
		byte[] result = getResource(context, resource);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				result);
		return byteArrayInputStream;
	}

	/**
	 * 结果以byte[]形式返回.
	 * 
	 * @param context
	 *            当前上下文
	 * @param resourceId
	 *            配置文件名
	 * @return byte 2012-9-12
	 */
	public static byte[] getResource(Context context, int resourceId) {
		try {
			// 加载配置文件
			InputStream inputStream = context.getResources().openRawResource(
					resourceId);
			byte[] dataBuf = new byte[inputStream.available()];
			inputStream.read(dataBuf);
			inputStream.close();
			return dataBuf;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 判断是否存在对应的配置项.
	 * 
	 * @param key
	 *            配置项名
	 * @return boolean 是否存在，存在为true 否则false
	 */
	public boolean ifContainConfig(String key) {
		boolean result = false;
		if (configMap != null && configMap.size() > 0) {
			result = configMap.containsKey(key);
		}
		return result;
	}

	/**
	 * 取单个KEY的值.
	 * 
	 * @param key
	 *            key
	 * @return key对应的值，如果不存在返回null
	 */
	public String getConfigValue(String key) {
		String result = null;
		if (TextUtils.isEmpty(key) || configMap == null
				|| configMap.size() == 0) {
			return result;
		}
		return configMap.get(key);

	}

    /**
     * 修改某个key的值
     *
     * @param key key
     */
    public void setConfigValue(String key, String value) {
        if (configMap == null || configMap.size() == 0) {
            return;
        }
        configMap.put(key, value);

    }

    /**
	 * 打印所有配置项的KEY 和VALUE.
	 * 
	 */
	public void printConfig() {
		if (configMap != null && configMap.size() > 0) {
			Iterator<Map.Entry<String, String>> it = configMap.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				Log.d(tag,
						"key= " + entry.getKey() + " and value= "
								+ entry.getValue());
			}
		}
	}

}
