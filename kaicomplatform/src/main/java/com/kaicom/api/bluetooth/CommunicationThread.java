package com.kaicom.api.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.kaicom.api.bluetooth.weight.BluetoothWeightParser;
import com.kaicom.api.bluetooth.weight.ScalesK3190A12Parser;
import com.kaicom.api.bluetooth.weight.ScalesK3190A7Parser;
import com.kaicom.api.bluetooth.weight.ScalesK3190APlusParser;
import com.kaicom.api.bluetooth.weight.ScalesTDI300Parser;
import com.kaicom.api.bluetooth.weight.ScalesXK3190A27EParser;
import com.kaicom.api.bluetooth.weight.ScalesXK3190A27PlusEParser;
import com.kaicom.api.log.KlLoger;

/**
 * 蓝牙通信线程
 * @author scj
 *
 */
public class CommunicationThread extends Thread {

	private final BluetoothService mService;
	private final BluetoothSocket mBtSocket;
	private final InputStream mInputStream;
	private final OutputStream mOutputStream;

	private BluetoothWeightParser mWeightParser;
	private volatile boolean mQuit = false;
	private final int MAX_LENGTH = 64;

	private boolean confirmScales = false; // 是否确定蓝牙秤
	private int counter = 0; // 计数器

	public CommunicationThread(BluetoothService service, BluetoothSocket socket) {
		mBtSocket = socket;
		mService = service;

		InputStream tmpInputStream = null;
		OutputStream tmpOutputStream = null;
		try {
			tmpInputStream = socket.getInputStream();
			tmpOutputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mInputStream = tmpInputStream;
		mOutputStream = tmpOutputStream;
	}

	@Override
	public void run() {
		if (mInputStream == null || mOutputStream == null) {
			KlLoger.debug("mInputStream is null");
			changeState(BluetoothState.CONNECT_BREAK);
			return;
		}

		// 检查秤类型
//		checkScalesType();
//
//		if (!confirmScales)
//			return;

		while (!mQuit) {
			try {
				sendCommand();
			} catch (IOException e) {
				break;
			}
			receiveData();
		}
		cancel();
	}

	private void checkScalesType() {
		if (mWeightParser == null) {
			mWeightParser = new ScalesK3190A7Parser();
			confirmScales = false;
			
		} else {
			confirmScales = true;
		}
		int count = 0;
		while (count < 5000) {
			count++;
			try {
				sleep(1);
			} catch (InterruptedException e1) {
			}
			try {
				sendCommand();
			} catch (IOException e) {
				break;
			}
			boolean hasData = checkAndReceiveData();

			boolean confirm = mWeightParser.isConfirmProtocol();
			if (confirm) {
				confirmScales = true;
				break;
			}

			if (!hasData)
				break;

		}
	}

	private void sendCommand() throws IOException {
		try {
			if (mWeightParser != null)
				mWeightParser.sendCommandFor(mOutputStream);
		} catch (Exception e) {
			KlLoger.error("sendCommand", Log.getStackTraceString(e));
			cancel();
			changeState(BluetoothState.CONNECT_BREAK);
			throw new IOException("sendCommand");
		}
	}

	private boolean checkAndReceiveData() {
		int length = availableLengthWithScales();
		if (length < 0) {
			return false;
		}
		receiveData(length);
		return true;
	}

	private void receiveData() {
		int length = 14;
		receiveData(length);
	}

	private void receiveData(int length) {
		try {
			byte[] buffer = new byte[MAX_LENGTH];
			int offset = 0;
//			System.out.println("while.read");
			while (offset < length) {
				int len = mInputStream.read(buffer, 0 + offset, MAX_LENGTH - offset);
				if (len < 0)
					break;
//				System.out.println("mInputStream.read");
				offset += len;
				if (offset > 20) {
					// 防止越界
					if (offset >= MAX_LENGTH)
						offset = MAX_LENGTH;
					break;
				}
			}

			if (offset == 0) {
				return;
			}
			byte[] rece = new byte[offset];
			System.arraycopy(buffer, 0, rece, 0, offset);
			
			postReceiveData(rece);
		} catch (IOException e) {
			KlLoger.error("receiveData", Log.getStackTraceString(e));
			changeState(BluetoothState.CONNECT_BREAK);
			cancel();
		}
	}

	private int availableLengthWithScales() {
		int length = available();

		if (length > 0) {
			counter = 0;
		} else {
			counter++;
		}

		// 未确定蓝牙秤时， 切换解析器
		if (!confirmScales && counter > 500) {
			changeAndTestWeightParser();
		}

		// 如果一定时间内没读到数据, 提示秤类型选择有问题
		if (counter > 1000) {
			cancel();
			changeState(BluetoothState.PARSE_ERROR);
			length = -1;
		}

		return length;
	}

	private int available() {
		int length = 0;
		try {
			length = mInputStream.available();
		} catch (IOException e) {
			KlLoger.error("available", Log.getStackTraceString(e));
		}
		return length;
	}

	private void postReceiveData(byte[] buffer) {
		if (mWeightParser != null) {
			String data = null;
			try {
				data = mWeightParser.parseData(buffer);
			} catch (BluetoothException e) {
				e.printStackTrace();
				if (confirmScales) {
					changeState(BluetoothState.PARSE_ERROR);
					cancel();
				} else {
					changeState(BluetoothState.CHANGE_SCALES);
					changeAndTestWeightParser();
				}
			}
			mService.postReceiveStringData(data);
		} else {
			mService.postReceiveData(buffer);
		}
	}

	private void changeAndTestWeightParser() {
		if (mWeightParser == null) {
			mWeightParser = new ScalesK3190A7Parser();
		} else if (mWeightParser instanceof ScalesK3190A7Parser) {
			mWeightParser = new ScalesK3190A12Parser();
		} else if (mWeightParser instanceof ScalesK3190A12Parser) {
			mWeightParser = new ScalesK3190APlusParser();
		} else if (mWeightParser instanceof ScalesK3190APlusParser) {
			mWeightParser = new ScalesTDI300Parser();
		} else if (mWeightParser instanceof ScalesXK3190A27PlusEParser) {
			mWeightParser = new ScalesXK3190A27PlusEParser();
		} else if (mWeightParser instanceof ScalesXK3190A27EParser) {
			mWeightParser = new ScalesXK3190A27EParser();
		} else {
			changeState(BluetoothState.UNKNOW_SCALES);
			cancel();
		}
		counter = 0;
	}

	public synchronized void write(byte[] buffer) {
		try {
			mOutputStream.write(buffer);
			mOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			cancel();
			changeState(BluetoothState.CONNECT_BREAK);
		}
	}

	private void changeState(BluetoothState state) {
		if (!mQuit)
			mService.setState(state);
	}

	synchronized void cancel() {
		mQuit = true;
		try {
			mBtSocket.close();
		} catch (IOException e) {
			BluetoothUtils.disableBluetooth();
			BluetoothUtils.enableBluetooth();
		}
	}
	
	public boolean isQuit() {
		return mQuit;
	}

	public synchronized void setWeightParser(BluetoothWeightParser parser) {
		if (parser != null)
			mWeightParser = parser;
	}

	public synchronized BluetoothWeightParser getWeightParser() {
		return mWeightParser;
	}

}
