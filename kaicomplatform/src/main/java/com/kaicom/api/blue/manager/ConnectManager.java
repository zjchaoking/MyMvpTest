package com.kaicom.api.blue.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.kaicom.api.blue.BluetoothUtil;

/**
 * 连接蓝牙和关闭蓝牙
 * 
 * @author hb
 * 
 */
public class ConnectManager {

	// 最大的连接次数默认3次
	private int maxLinkNum = 3;

	// 蓝牙适配器
	private BluetoothAdapter mBTAdapter;

	// 蓝牙设备
	private BluetoothDevice mBTDevice;

	// 蓝牙套接字
	public BluetoothSocket mBTSocket;

	// 连接蓝牙用的UUID
	private final UUID UUID_NUM = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	// 定时器timer
	private Timer timer;

	// 静态的对象
	private static ConnectManager mConnect;

	private boolean connectStatus = true;
	private boolean connectting; 

	/**
	 * 连接蓝牙的单例模式
	 * 
	 * @param context
	 *            上下午
	 * @param bluetoothAddr
	 *            蓝牙mac地址
	 * @return
	 * @throws Exception
	 */
	public synchronized static ConnectManager getinstance(Context context) throws Exception {
		try {
			if (mConnect == null) {
				mConnect = new ConnectManager();
				mConnect.mBTAdapter = BluetoothUtil.getAdapter();
			}
			// 移除定时器
			if (mConnect.timer != null) {
				mConnect.timer.cancel();
				mConnect.timer = null;
			}
			
			if (mConnect.isConnectting()) {
				while (mConnect.isConnectting()) {
					Thread.sleep(50);
				}
				Thread.sleep(4000);
			}
		} catch (NullPointerException e) {
			Thread.sleep(4000);
		}
		
		if (mConnect == null) {
			mConnect = new ConnectManager();
			mConnect.mBTAdapter = BluetoothUtil.getAdapter();
		}
		
		return mConnect;
	}

	public ConnectManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 用于多个蓝牙设备连接,构造函数
	 * 
	 * @param context
	 *            上下午
	 * @param addr
	 *            蓝牙mac地址
	 * @throws Exception
	 */
	public ConnectManager(Context context) throws Exception {
		// 移除定时器
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		mBTAdapter = BluetoothUtil.getAdapter();
	}

	/**
	 * 根据蓝牙地址连接蓝牙设备
	 * 
	 * @param bluetoothAddr
	 *            蓝牙mac地址
	 * @throws Exception
	 */
	public void connectBluetooth(String bluetoothAddr) throws Exception {
		int linkNum = 0;

		if (isConnect()) {
			cancelConnect();
			Thread.sleep(3000);
		}
		
		// 连接失败多次重连,默认maxLinkNum次
		while (linkNum++ < maxLinkNum && isConnectStatus()) {
			try {
				mBTDevice = mBTAdapter.getRemoteDevice(bluetoothAddr);
				mBTAdapter.cancelDiscovery();
				setConnectting(true);
				mBTSocket = mBTDevice
						.createRfcommSocketToServiceRecord(UUID_NUM);
				mBTSocket.connect();
				break;
			} catch (IOException e) {
				if (mBTSocket != null) {
					mBTSocket.close();
					mBTSocket = null;
				}
				if (linkNum >= maxLinkNum || !isConnectStatus()) {
					setConnectting(false);
					throw new IOException();
				}
				Thread.sleep(4000);
			}
		}
		setConnectting(false);
	}
	
	

	/**
	 * 检查蓝牙是否连接<br>
	 * 420巴枪调用该方法有问题
	 * 
	 * @return
	 */
	@SuppressLint("NewApi")
	public boolean isConnect() {
		try {
			if (mBTSocket != null)
				return mBTSocket.isConnected();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取蓝牙输入流
	 * 
	 * @return 返回输入流,用于读操作
	 * @throws IOException
	 */
	public InputStream getmInputStream() throws IOException {
		return mBTSocket.getInputStream();
	}

	/**
	 * 获取蓝牙输出流
	 * 
	 * @return 返回输出流,用于写操作
	 * @throws IOException
	 */
	public OutputStream getmOutputStream() throws IOException {
		return mBTSocket.getOutputStream();
	}
	
	public synchronized boolean isConnectStatus() {
		return connectStatus;
	}

	public synchronized void setConnectStatus(boolean connectStatus) {
		this.connectStatus = connectStatus;
	}
	
	public boolean isConnectting() {
		return connectting;
	}

	public void setConnectting(boolean connectting) {
		this.connectting = connectting;
	}

	/**
	 * 取消蓝牙连接
	 */
	public synchronized void cancelConnect() {
		try {
			connectStatus = false;
			if (mBTSocket != null) {
				mBTSocket.close();
				mBTSocket = null;
			}
			mConnect = null;
			closeThread = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private CloseThread closeThread;
	
	public synchronized void waitForCancelConnect() {
		if (closeThread == null) {
			closeThread = new CloseThread();
			closeThread.start();
		}
	}
	
	private class CloseThread extends Thread {
		
		@Override
		public void run() {
			while (isConnectting()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			cancelConnect();
		}
		
	}

	/**
	 * 关闭蓝牙定时多长时间关闭,用于非主线程不能new handler <br>
	 * 需要调用Looper.prepare()但定时器还是不能用<br>
	 * 所以用TimerTask()
	 * 
	 * @param seconds
	 *            秒
	 */
	public void cancelConnectByTime(int seconds) {
		if (timer == null)
			timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				cancelConnect();
			}
		}, seconds * 1000);
	}
}
