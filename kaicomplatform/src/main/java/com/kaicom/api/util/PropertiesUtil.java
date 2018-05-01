package com.kaicom.api.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * <h3>Properties工具类</h3>
 * 
 * <p>
 * 主要是提供一些对properties文件的操作方法
 * 
 * @author scj
 */
public final class PropertiesUtil {

	private PropertiesUtil() {
		throw new RuntimeException("…（⊙＿⊙；）…");
	}

	/**
	 * 从assets目录中的properties中获取值
	 * 
	 * @param ctx
	 * @param file
	 * @param key
	 * @param defaultValue
	 * @return value
	 */
	public static String getValueFromAssets(Context ctx, String file, String key, String defaultValue) {
		Properties pro = readPropertiesFromAssets(ctx, file);
		return pro.getProperty(key, defaultValue);
	}

	/**
	 * 从assets目录中读取Properties文件
	 * 
	 * @param context
	 * @param file
	 *            文件名称
	 * @return Properties对象
	 */
	public static Properties readPropertiesFromAssets(Context context, String file) {
		Properties pro = new Properties();
		AssetManager asset = context.getAssets();
		try {
			InputStream in = asset.open(file);
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pro;
	}

	/**
	 * 从properties中获取值
	 * 
	 * @param key
	 * @return value
	 */
	public static String getValue(String key, String file) {
		Properties pro = readPropertiesFile(file);
		return pro.getProperty(key, "");
	}

	/**
	 * 读取Properties文件
	 * 
	 * @param file
	 *            文件绝对路径
	 * @return Properties
	 */
	public static Properties readPropertiesFile(String file) {
		Properties pro = new Properties();
		try {
			InputStream in = new FileInputStream(file);
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pro;
	}

	/**
	 * 将数据放入properties文件
	 * 
	 * @param file
	 * @param key
	 * @param value
	 */
	public static boolean putValue(String file, String key, String value) {
		Properties pro = readPropertiesFile(file);
		pro.setProperty(key, value);
		return saveProperties(pro, file);
	}

	/**
	 * 保存properties文件
	 * 
	 * @param pro
	 * @param file
	 * @return
	 */
	public static boolean saveProperties(Properties pro, String file) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			pro.store(fos, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
