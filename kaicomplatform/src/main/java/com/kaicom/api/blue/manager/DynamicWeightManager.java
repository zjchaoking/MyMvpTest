package com.kaicom.api.blue.manager;

//import static com.kaicom.api.blue.BluetoothDataListener.CONNECT_FAIL;
//import static com.kaicom.api.blue.BluetoothDataListener.CONNECT_ING;
//import static com.kaicom.api.blue.BluetoothDataListener.READ_FAIL;

import java.io.IOException;

import android.content.Context;

import com.kaicom.api.blue.BluetoothDataListener;
import com.kaicom.fw.R;

/**
 * 命令模式动态秤管理,关于动态秤操作需要用这个管理类
 * 
 * @author hb
 * 
 */
public class DynamicWeightManager extends BaseThread {

	// 动态秤获取重量命令
	private final byte[] dynamicByte = { 0x40, 0x02, 0x4f, 0x01, 0x52, 0x2a };

	// 设置动态电子秤前面扫描命令
	private final byte[] frontByte = { 0x40, 0x02, 0x4F, 0x03, 0x52, 0x2A };

	// 设置动态电子秤后面扫描命令
	private final byte[] laterByte = { 0x40, 0x02, 0x4F, 0x02, 0x52, 0x2A };

	// 清空数据命令
	private final byte[] clearByte = { 0x40, 0x02, 0x4f, 0x05, 0x52, 0x2a };

	// 需要读的字节数
	private int offset = 0;

	// 获取缓存数据buffer
	private final byte[] buffer = new byte[512];

	// 蓝牙mac地址
	private String bluetoothAddress;

	// 蓝牙数据接口
	private BluetoothDataListener blueDataListener;

	// 连接蓝牙对象
	private ConnectManager mBlueToothConnect;

	/**
	 * 命令模式动态秤构造方法
	 * 
	 * @param machineCode
	 *            蓝牙mac地址
	 * @param blueDataListener
	 *            显示蓝牙重量接口
	 */
	public DynamicWeightManager(Context context, String bluetoothAddress,
			BluetoothDataListener blueDataListener) {
		super(context);
		this.bluetoothAddress = bluetoothAddress;
		this.blueDataListener = blueDataListener;
	}

	/**
	 * 连接蓝牙线程并获取数据
	 * 
	 */
	@Override
	public void run() {
		super.run();
		try {
			blueDataListener.onResolveData(R.id.connecting);
			mBlueToothConnect = ConnectManager.getinstance(context);
			mBlueToothConnect.connectBluetooth(bluetoothAddress);
		} catch (Exception e) {
			blueDataListener.onResolveData(R.id.connect_fail);
			closeBluetooth();
			e.printStackTrace();
		}
	}

	/**
	 * 根据固定的长度读数据,固定是offset字节 <br>
	 * 但offset不能大于512个字节
	 * 
	 * @param offset
	 */
	public void read(int offset) {
		this.offset = offset;
		clearBuffer();
		readBuffer();
	}

	/**
	 * 根据默认长度读数据,默认14个字节
	 */
	public void read() {
		this.offset = 14;
		clearBuffer();
		readBuffer();
	}

	/**
	 * 读数据,一个字节一个字节读
	 */
	private void readBuffer() {
		int i = 0, c = -1;
		while (i < offset) {
			try {
				if ((c = mBlueToothConnect.getmInputStream().read()) == -1) {
					break;
				}
				buffer[i++] = (byte) c;
				c = -1;
			} catch (IOException e) {
				if (c == -1)
					blueDataListener.onResolveData(R.id.read_fail);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 清空buffer
	 */
	private void clearBuffer() {
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = 0;
	}

	/**
	 * 发送蓝牙命令,用于命令模式蓝牙电子秤<br>
	 * 通过命令可以获取重秤或设置蓝牙秤
	 * 
	 * @param command
	 *            指定命令
	 * @throws IOException
	 */
	public void sendCommand(byte[] command) throws IOException {
		mBlueToothConnect.getmOutputStream().write(command);
		mBlueToothConnect.getmOutputStream().flush();
	}

	/**
	 * 发送蓝牙命令,用于命令模式蓝牙电子秤<br>
	 * 用默认命令 0x40, 0x02, 0x4f, 0x01, 0x52, 0x2a
	 * 
	 * @throws IOException
	 */
	public void sendCommand() throws IOException {
		mBlueToothConnect.getmOutputStream().write(dynamicByte);
		mBlueToothConnect.getmOutputStream().flush();
	}

	/**
	 * 设置动态电子秤前面扫描
	 * 
	 * @throws IOException
	 */
	public void setFrontScan() throws IOException {
		mBlueToothConnect.getmOutputStream().write(frontByte);
		mBlueToothConnect.getmOutputStream().flush();
	}

	/**
	 * 设置动态电子秤后面扫描
	 * 
	 * @throws IOException
	 */
	public void setLaterScan() throws IOException {
		mBlueToothConnect.getmOutputStream().write(laterByte);
		mBlueToothConnect.getmOutputStream().flush();
	}

	/**
	 * 清空数据命令,用于动态秤
	 * 
	 * @throws IOException
	 */
	public void clearDataCommand() throws IOException {
		mBlueToothConnect.getmOutputStream().write(clearByte);
		mBlueToothConnect.getmOutputStream().flush();
	}

	/**
	 * 获取称重数据,必须调用了sendCommand和read方法后才能读到数据
	 * 
	 * @throws Exception
	 */
	public void getWeightData() throws Exception {
		blueDataListener.onReadBlueData(new String(buffer, 0, buffer.length,
				"utf-8"));
	}

	/**
	 * 关闭蓝牙
	 */
	public void closeBluetooth() {
		if (mBlueToothConnect != null)
			mBlueToothConnect.cancelConnect();
	}
}