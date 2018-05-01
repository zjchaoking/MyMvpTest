package com.kaicom.api.blue.manager;

//import static com.kaicom.api.blue.BluetoothDataListener.CONNECT_FAIL;
//import static com.kaicom.api.blue.BluetoothDataListener.CONNECT_ING;
//import static com.kaicom.api.blue.BluetoothDataListener.INIT_SUCCESS;
//import static com.kaicom.api.blue.BluetoothDataListener.NO_OPEN;
//import static com.kaicom.api.blue.BluetoothDataListener.READ_FAIL;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.kaicom.api.blue.BluetoothDataListener;
import com.kaicom.api.blue.BluetoothUtil;
import com.kaicom.api.blue.ResolveData;
import com.kaicom.api.log.KlLoger;
import com.kaicom.fw.R;



/**
 * 蓝牙通信管理类
 * 
 * @author hb
 */
public class BluetoothWeightManager extends BaseThread {

	// 蓝牙mac地址
	private String bluetoothAddress;

	// read数据的缓存
	private byte[] rece = new byte[100];

	// 发送蓝牙电子秤的命令,只有蓝牙电子秤是命令模式下才需要
	private final byte[] TDI300 = { 0x41, 0x54, 0x56, 0x31, 0x51, 0x30, 0x0d, 0x02, 0x41, 0x44, 0x00, 0x05, 0x03 };

	// A1+的命令模式
	private byte[] A1 = { 0x02, 0x41, 0x44, 0x30, 0x35, 0x03 };

	// 发送蓝牙电子秤的命令,只有蓝牙电子秤是命令模式下才需要
	private final byte[] RID = { 0x52 };

	// 命令的集合,后面
	private final byte[][] allCommand = { TDI300, A1, RID };

	// 命令模式发送的命令
	private byte[] command;

	// 读到数据的偏移量
	private int offset = 0;

	// 获取第几个allCommand
	private int num = 0;

	// 最大能读范围
	private int MAX_RECE = rece.length - 1;

	// 判断是否命令模式
	private boolean isCommandMode = true;

	// 是否显示蓝牙协议数据 默认不显示
	private boolean isDisplay = false;

	// 定时器
	private Runnable runable;

	// 定时器用的handler
	private Handler handler = new Handler();

	// 解析数据对象
	private ResolveData resolveData;

	// 蓝牙输入流
	private InputStream mInputStream;

	// 蓝牙输出流
	private OutputStream mOutputStream;

	// 连接蓝牙对象
	private ConnectManager mBlueToothConnect;
	
	/**
	 * 蓝牙重量管理构造方法
	 * 
	 * @param machineCode
	 *            蓝牙mac地址
	 * @param blueDataInterface
	 *            显示蓝牙重量接口
	 */
	public BluetoothWeightManager(Context context, String bluetoothAddress, BluetoothDataListener blueDataListener) {
		super(context);
		this.bluetoothAddress = bluetoothAddress;
		resolveData = new ResolveData(blueDataListener);
	}

	/**
	 * 连接蓝牙线程并获取数据
	 * 
	 */
	@Override
	public void run() {
		super.run();
		try {
			// 没有打开蓝牙设备直接退出
			if (!BluetoothUtil.getAdapter().isEnabled()) {
				resolveData.postResolveData(R.id.no_open);
			} else {
				resolveData.postResolveData(R.id.connecting);
				mBlueToothConnect = ConnectManager.getinstance(context);
				mBlueToothConnect.connectBluetooth(bluetoothAddress);
				if (Build.MODEL.equals("420") || mBlueToothConnect.isConnect()) {
					// 默认连接上了给一个初始值0
					resolveData.postResolveData(R.id.connect_success);
					mInputStream = mBlueToothConnect.getmInputStream();
					mOutputStream = mBlueToothConnect.getmOutputStream();
					getWeightData();
				} else {
					resolveData.postResolveData(R.id.connect_fail);
					closeBluetooth();
				}
			}
		} catch (Exception e) {
			resolveData.postResolveData(R.id.connect_fail);
			KlLoger.debug("蓝牙连接失败", e);
			closeBluetooth();
			e.printStackTrace();
		}
	}

	/**
	 * 获取蓝牙数据
	 * 
	 * @throws Exception
	 */
	private void getWeightData() throws Exception {

		runTimes();
		while (isRead) {
			readBluetoothData();
			if (resolveData.resolveBlueData(rece, offset))
				offset = 0;
		}
	}

	/**
	 * 读蓝牙数据
	 * 
	 * @throws InterruptedException
	 */
	private void readBluetoothData() throws InterruptedException {
		try {
			sendCommand(command);
			sendCommand(command);
			// 读取蓝牙延时,该延时需要根据合适情况调试
			// BluetoothWeightManager.sleep(70);
			// 这一步必须要加上,如果不加上蓝牙是断开不了的
			// 当MAX_RECE等于offset的时候调用read后再关闭socket
			if (offset == MAX_RECE)
				offset = 0;
			offset += mInputStream.read(rece, offset, MAX_RECE - offset);
			// 判断是否是命令模式
			if (offset > 0)
				isCommandMode = false;
			if (isDisplay)
				resolveData.postReadData(new String(rece, 0, offset, "utf-8"));
		} catch (IOException e) {
			resolveData.postResolveData(R.id.read_fail);
		}
	}

	/**
	 * 发送蓝牙命令,用于命令模式蓝牙电子秤
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendCommand(byte[] command) throws IOException, InterruptedException {
		if (command != null) {
			BluetoothWeightManager.sleep(80);
			mOutputStream.write(command);
			mOutputStream.flush();
		}
	}

	/**
	 * 判断蓝牙是否读到数据定时器,默认3秒<br>
	 * 如果3秒没有读到数据就会发送命令获取数据
	 */
	private void runTimes() {
		runable = new Runnable() {
			@Override
			public void run() {
				try {
					checkCommand();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		handler.postDelayed(runable, 3000);
	}

	/**
	 * 检查蓝牙电子秤是否是命令模式,每两秒轮训一次,假如不是命令模式直接关闭定时器
	 */
	private void checkCommand() throws Exception {
		try {
			if (isCommandMode && num < allCommand.length) {
				sendCommand(allCommand[num++]);
				handler.postDelayed(runable, 2000);
			} else {
				if (!isCommandMode && num >= 1 && num < allCommand.length) {
					command = allCommand[num--];
					sendCommand(command);
					num = 0;
				}
				handler.removeCallbacks(runable);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭蓝牙
	 */
	public synchronized void closeBluetooth() {
		if (mBlueToothConnect == null)
			return;
		
		mBlueToothConnect.setConnectStatus(false);
		if (mBlueToothConnect.isConnectting()) {
			mBlueToothConnect.waitForCancelConnect();
		} else {
			mBlueToothConnect.cancelConnect();
		}
		isRead = false;
	}
	
	public synchronized void cancelConnect() {
		if (mBlueToothConnect != null) {
			mBlueToothConnect.cancelConnect();
		}
		isRead = false;
	}

	/**
	 * 多少秒后关闭蓝牙
	 * 
	 * @param seconds
	 *            秒
	 */
	public void closeBluetoothByTime(int seconds) {
		if (mBlueToothConnect != null)
			mBlueToothConnect.cancelConnectByTime(seconds);
		isRead = false;
	}

	/**
	 * 设置是否显示协议数据 默认不显示
	 * 
	 * @param isDisplay
	 *            true 显示 false 不显示
	 */
	public void setDisplayProtocolData(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}
