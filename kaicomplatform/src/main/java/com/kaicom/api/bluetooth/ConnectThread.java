package com.kaicom.api.bluetooth;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * 蓝牙连接线程
 * @author scj
 */
class ConnectThread extends Thread {

	public final UUID UUID_DEVICE = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
	private BluetoothSocket mmSocket;
	private final BluetoothDevice mBtDevice;
	
	private final BluetoothService mService;
	// 重新连接次数
	final int reconnectCount = 3;
	
	private volatile boolean mQuit;
	private boolean connectting;

	public ConnectThread(BluetoothService service, BluetoothDevice device) {
		mBtDevice = device;
		mService = service;
		
		connectting = true;
		createSocket();
	}

	private boolean createSocket() {
		try {
			mmSocket = mBtDevice.createRfcommSocketToServiceRecord(UUID_DEVICE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mmSocket != null;
	}
	
	@Override
	public void run() {
		if (mmSocket == null) {
			mService.alertToast("UUID连接蓝牙失败");
			mService.setState(BluetoothState.CONNECT_FAIL);
			return;
		}
		
		// 蓝牙在搜索设备时通常会连接失败, 取消搜索
		cancelDiscovery();

		if (mQuit) {
			connectting = false;
			return;
		}
		
		if (connect()) {
			mService.connectSuccess(mmSocket, mBtDevice);
			connectting = false;
			return;
		}
		
		if (reconnect()) {
			mService.connectSuccess(mmSocket, mBtDevice);
		} else {
			if (!mQuit) 
				mService.connectFail();
		}
		connectting = false;
	}

	private BluetoothAdapter cancelDiscovery() {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
//		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
//		}
		return btAdapter;
	}

	private boolean reconnect() {
		boolean connected = false;
		int count = 0;
		
		while (needRetry(count, connected)) {
			if (mQuit)
				return false;
			
			count++;
			close();
			
			// 最后一次重连，重启蓝牙设备
			if (count == reconnectCount) {
				BluetoothAdapter.getDefaultAdapter().disable();
				sleep(1200);
				BluetoothAdapter.getDefaultAdapter().enable();
			}
			
			sleep(4000);
			
			if (mQuit)
				return false;
			boolean success = createSocket();
			
			if (!success) {
				mService.alertToast("UUID连接蓝牙失败");
				mService.setState(BluetoothState.CONNECT_FAIL);
			}
			connected = connect();
		}
		return connected;
	}

	private void sleep(int milliSeconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliSeconds);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * 是否重试
	 * @param count
	 * @param connected
	 * @return
	 */
	private boolean needRetry(int count, boolean connected) {
		return count < reconnectCount && !connected;
	}

	private boolean connect() {
		try {
			if (mmSocket != null) {
				mmSocket.connect();
				return true;
			}
		} catch (IOException e) {
			Log.e("ConnectThread", "蓝牙连接异常");
		}
		return false;
	}
	
	public synchronized boolean isConnectting() {
		return connectting;
	}
	
	private synchronized void close() {
		if (mmSocket != null) {
			try {
				mmSocket.close();
			} catch (IOException e) {
				BluetoothUtils.disableBluetooth();
				BluetoothUtils.enableBluetooth();
			}
		}
	}

	synchronized void cancel() {
		close();
		mQuit = true;
	}
	
}
