package com.kaicom.api.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.kaicom.api.KaicomApplication.app;

/**
 * 网络状态工具类
 * 
 * @author scj
 * 
 */
public final class NetUtil {

	private NetUtil() {
		throw new RuntimeException("…（⊙＿⊙；）…");
	}

	/**
	 * 判断当前网络是否可用
	 * 
	 * @return 如果可用返回true，否则返回false
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			NetworkInfo netinfo = cm.getActiveNetworkInfo();
			if (netinfo == null) {
				return false;
			}
			return netinfo.isConnected();
		}
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi() {
		ConnectivityManager cm = (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		return netinfo.getType() == ConnectivityManager.TYPE_WIFI;
	}
	/**
	 * 判断GPRS是否打开
	 *
	 * @return
	 */
	public static boolean isGprs() {
		ConnectivityManager cm = (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		return netinfo.getType() == ConnectivityManager.TYPE_MOBILE;
	}

	/**
	 * 打开网络设置界面
	 * 
	 * @param activity
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		activity.startActivity(intent);
	}

	/**
	 * 返回ip地址
	 * 
	 * @return
	 */
	public static String getIpAddress() {
		WifiManager wifiManager = (WifiManager) app
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		int intIp = info.getIpAddress();
		byte[] bytes = int2byte(intIp);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			sb.append(bytes[i] & 0xFF);
			if (i < 3) {
				sb.append(".");
			}
		}
		return sb.toString();
	}

	private static byte[] int2byte(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (0xff & i);
		bytes[1] = (byte) ((0xff00 & i) >> 8);
		bytes[2] = (byte) ((0xff0000 & i) >> 16);
		bytes[3] = (byte) ((0xff000000 & i) >> 24);
		return bytes;
	}

	/**
	 * 获得当前连接的速度
	 * 
	 * @return
	 */
	public static int getLinkSpeed() {
		WifiManager wifiManager = (WifiManager) app
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getLinkSpeed();
	}

	/**
	 * 获得MAC地址
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		WifiManager wifiManager = (WifiManager) app
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 获得Wifi的状态
	 * 
	 * @return One of {@link WifiManager#WIFI_STATE_DISABLED},
	 *         {@link WifiManager#WIFI_STATE_DISABLING},
	 *         {@link WifiManager#WIFI_STATE_ENABLED},
	 *         {@link WifiManager#WIFI_STATE_ENABLING},
	 *         {@link WifiManager#WIFI_STATE_UNKNOWN}
	 */
	public static int getWifiState() {
		WifiManager wifiManager = (WifiManager) app
				.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getWifiState();
	}

	/**
	 * 将域名解析成ip
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIpByDomainName(String domainName)
			throws UnknownHostException {
		InetAddress address = InetAddress.getByName(domainName);
		return address.getHostAddress();
	}

}
