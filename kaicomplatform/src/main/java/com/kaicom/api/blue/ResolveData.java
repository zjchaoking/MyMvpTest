package com.kaicom.api.blue;

import android.os.Handler;

import com.kaicom.api.util.StringUtil;

/**
 * 解析蓝牙获取的数据
 * 
 * @author hb
 * 
 */
public class ResolveData {
	private BluetoothDataListener blueDataListener;

	// 蓝牙协议特殊字节,如果后面有添加的特殊字段分隔符,需要在后面添加
	private byte[] specialField = { 0x3d, 0x41, 0x44, 0x3, 0x2 };

	// 蓝牙协议特殊字节
	private byte[] OX3_OX2 = { 0x3, 0x2, 0x2b };

	// 把OX3_OX2转化成String
	private final String STR_OX3_OX2 = new String(OX3_OX2);

	// 获取到的重量
	private double weightData = 0;

	// 异步handler
	private Handler mHandler = new Handler();

	// 读到蓝牙的数据
	private String readData = "";

	/**
	 * 构造函数
	 * 
	 * @param blueDataListener
	 *            获取解析数据的接口,用于上层实现,拿到蓝牙的数据
	 */
	public ResolveData(BluetoothDataListener blueDataListener) {
		this.blueDataListener = blueDataListener;
	}

	/**
	 * 根据specialField查找接收到的特殊字段,来解析数据;
	 * 
	 * @return temp
	 */
	private byte modifySpecialField(byte[] rece, int offset) {
		try {
			for (int i = 0; i < offset; i++) {
				if (rece[i] == 0x3d) {// =
					return rece[i];
				} else if (rece[i] == 0x41 && rece[i + 1] == 0x44) { // AD
					return rece[i];
				} else if (rece[i] == 0x3 && rece[i + 1] == 0x2) { // 03 02
					return rece[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 解析蓝牙获取到的数据;
	 * 
	 * @throws Exception
	 */
	public boolean resolveBlueData(byte[] rece, int offset) throws Exception {
		boolean ret = false;
		if (offset > 0) {
			switch (modifySpecialField(rece, offset)) {
			case 0x3d:// '='
				ret = resolve_OX3D(rece, offset, "=");
				break;
			case 0x41:// 'A'
			case 0x44:// 'D'
				ret = resolve_OX4X(rece, offset, "AD\\+");
				break;
			case 0x03:
			case 0x02:
				ret = resolve_OX4X(rece, offset, STR_OX3_OX2);
				break;
			default:
				break;
			}
		}

		return ret;
	}

	/**
	 * 解析蓝牙数据,该数据是以"AD"区分 <br>
	 * 'OX4X'表示"AD"
	 * 
	 * @param rece
	 *            蓝牙read的数据
	 * @param offset
	 *            读到多少个字节
	 * @param field
	 *            特殊字段
	 * @return
	 * @throws Exception
	 */
	public boolean resolve_OX4X(byte[] rece, int offset, String field) throws Exception {
		boolean ret = false;
		try {
			String[] str = getSplitData(rece, offset, field);
			if (str != null)
				ret = getBlueData_OX4X(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 解析蓝牙数据,该数据是以'='区分 <br>
	 * "OX3D"表示"="
	 * 
	 * @param rece
	 *            蓝牙read的数据
	 * @param offset
	 *            读到多少个字节
	 * @param field
	 *            特殊字段
	 * @return
	 * @throws Exception
	 */
	private boolean resolve_OX3D(byte[] rece, int offset, String field) throws Exception {

		boolean ret = false;
		try {
			String[] str = getSplitData(rece, offset, field);
			if (str != null)
				ret = getBlueData_OX3D(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 把byte转换成String 从0到offset长度 把data根据特殊字段分割
	 * 
	 * @param rece
	 *            蓝牙read的数据
	 * @param offset
	 *            读到多少个字节
	 * @param field
	 *            特殊字段
	 * @return 分割string小于2 return null
	 * @throws Exception
	 */
	private String[] getSplitData(byte[] rece, int offset, String field) throws Exception {
		String data = new String(rece, 0, offset, "utf-8");
		String[] str = data.split(field);
		if (str.length < 2)
			return null;
		return str;
	}

	/**
	 * 解析数据从String的后面开始解析,再把string反转<br>
	 * 最后把retData转换成string,调用接口
	 * 
	 * @param str
	 * @return
	 */
	private boolean getBlueData_OX3D(String[] str) throws Exception {
		boolean ret = false;

		for (int i = str.length - 1; i >= 0; i--) {
			if (str[i].length() > 5 && str[i].matches("[0-9.]+")) {
				StringBuffer sb = new StringBuffer(str[i]);
				float retData = Float.valueOf(sb.reverse().toString());

				if (retData < 0) {
					continue;
				} else {
					postResolveData(retData);
					ret = true;
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * 解析数据从String的后面开始解析,再把retData除以10的倍数<br>
	 * 最后把retData转换成string,调用接口
	 * 
	 * @param str
	 * @return
	 */
	private boolean getBlueData_OX4X(String[] str) throws Exception {
		boolean ret = false;
		for (int i = str.length - 1; i >= 0; i--) {
			if (str[i].length() > 6) {
				if (!StringUtil.isNumberDecimals(str[i].substring(0, 6))) {
					continue;
				}
				float retData = Float.valueOf(str[i].substring(0, 6));
				char ch = str[i].charAt(6);
				if (!str[i].matches("[0-9]+")) {//A1+
					retData = retData / TenMultiple(10, Character.digit(ch, 10));
				} else {
					retData = retData / 1000; //TD200的协议
				}
				if (retData < 0) {
					continue;
				} else {
					postResolveData(retData);
					ret = true;
					continue;
				}
			}
		}

		return ret;
	}

	/**
	 * 获取多少个十的倍数
	 * 
	 * @param x
	 *            10
	 * @param y
	 *            几次轮训
	 * @return 假如y = 3 返回1000
	 */
	private float TenMultiple(int x, int y) {
		float ret = 1;
		while (y > 0) {
			ret *= x;
			y--;
		}
		return ret;
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			blueDataListener.onResolveData(weightData);
			// weightData = 0; // 解析的重量还原初始值
			if (!readData.equals(""))
				blueDataListener.onReadBlueData(readData);
			readData = "";// 读到的数据还原初始值
		}
	};

	/**
	 * 通过异步的方式响应已解析的接口数据
	 * 
	 * @param data
	 */
	public void postResolveData(double data) {
		weightData = data;
		mHandler.post(mRunnable);
	}

	/**
	 * 通过异步的方式响应已读到的接口数据
	 * 
	 * @param data
	 */
	public void postReadData(String data) {
		readData = data;
		mHandler.post(mRunnable);
	}
}
