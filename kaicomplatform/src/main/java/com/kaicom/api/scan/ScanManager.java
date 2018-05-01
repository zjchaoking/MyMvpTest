package com.kaicom.api.scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.KaicomJNIProxy;
import com.kaicom.api.KaicomJNIProxy.ScanCallBackProxy;
import com.kaicom.api.log.KlLoger;
import com.kaicom.api.scan.mode.ClickMode;
import com.kaicom.api.scan.mode.LongClickMode;
import com.kaicom.api.scan.mode.ScanTrigMode;
import com.zbar.lib.CaptureActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 扫描管理
 * 
 * @author scj
 */
public class ScanManager implements ScanCallBackProxy, Handler.Callback {

	/**
	 * 扫描触发模式
	 */
	public enum TrigModeType {

		/** 单按键触发, 默认方式 */
		CLICK_MODE,

		/** 长按出光模式 */
		LONG_CLICK_MODE,

		/** 自动出光模式 */
		AUTO_SCAN_MODE;

	}

	private boolean AUTO_SCAN_FLAG = true;
	
	private static ScanManager instance = null;

	public static final int SCAN_RESULT_WHAT = 0x10;
	private Handler handler;

	private List<ScanObserver> observers = new LinkedList<ScanObserver>();
	private KaicomJNIProxy kaicomJNI;
	private boolean isOpen; // 扫描头是否打开

	/** 扫描触发方式 */
	private ScanTrigMode trigMode = ScanTrigModeFactory
			.getMode(TrigModeType.CLICK_MODE);
	private TrigModeType type = TrigModeType.CLICK_MODE;

	private ScanManager() {
		handler = new Handler(Looper.getMainLooper(), this);
		setScanTimeout(10);
	}

	public static ScanManager getInstance() {
		if (instance == null)
			instance = new ScanManager();
		return instance;
	}

	@Override
	public void onScanResults(String result) {
		Message msg = handler.obtainMessage(SCAN_RESULT_WHAT);
		msg.obj = result;
		handler.sendMessage(msg);
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what != SCAN_RESULT_WHAT || msg.obj == null)
			return true;

		try {
			String barcode = barcodeTrim(msg.obj.toString());
			// 最后一个添加的观察者才会接受到消息
			if (observers != null && observers.size() > 0) {
				observers.get(observers.size() - 1).onScan(barcode);
			}
			if (type == TrigModeType.AUTO_SCAN_MODE && AUTO_SCAN_FLAG) {
				Thread.sleep(200);
				startScan();
			}
		} catch (Exception e) {
			KlLoger.debug("ScanMangager Observer", e);
		}
		return true;
	}
	
	private String barcodeTrim(String barcode) {
    	if (TextUtils.isEmpty(barcode)) {
    		return "";
    	}
    	int start = 0, last = barcode.length() - 1;
        int end = last;
        while ((start <= end) && (barcode.charAt(start) <= ' ' || barcode.charAt(start) > '~')) {
            start++;
        }
        while ((end >= start) && (barcode.charAt(end) <= ' ' || barcode.charAt(end) > '~')) {
            end--;
        }
        return barcode.substring(start, end + 1);
    }

	@Override
	public void onScanResults(String result, int type) {
	}

	/**
	 * 注册扫描观察者
	 * 
	 * @param observer
	 */
	public void attch(ScanObserver observer) {
		if (observers == null)
			observers = new LinkedList<ScanObserver>();
		if (observers.size() == 0 && !isOpen)
			scanOn();
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * 移除扫描观察者
	 * 
	 * @param observer
	 */
	public void detach(ScanObserver observer) {
		if (observers == null)
			return;
		if (observers.size() > 0)
			observers.remove(observer);
		if (observers == null || observers.size() == 0) {
			scanOff();
		}
	}

	public void release() {
		scanOff();
		if (handler != null) {
			handler.removeMessages(SCAN_RESULT_WHAT);
			handler = null;
		}
		freeInstance();
	}

	private static void freeInstance() {
		instance = null;
	}

	public void setTrigMode(TrigModeType type) {
		this.type = type;
		trigMode = ScanTrigModeFactory.getMode(type);
	}

	public TrigModeType getTrigMode() {
		return type;
	}

	public static class ScanTrigModeFactory {

		public static ScanTrigMode getMode(TrigModeType type) {
			switch (type) {
			case CLICK_MODE:
			case AUTO_SCAN_MODE:
				return new ClickMode();
			case LONG_CLICK_MODE:
				return new LongClickMode();
			default:
				break;
			}
			return new ClickMode();
		}

	}

	/**
	 * 按键事件处理
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public boolean scanEvent(int keyCode, KeyEvent event) {
		return trigMode.event(keyCode, event);
	}

	/**
	 * 打开扫描头
	 */
	public void scanOn() {
		if (kaicomJNI == null) {
			kaicomJNI = KaicomApplication.app.getKaicomJNIProxy();
			kaicomJNI.setmScanCB(this);
		}
		kaicomJNI.setScannerOn();
		isOpen = true;
	}

	/**
	 * 关闭扫描头
	 */
	public void scanOff() {
		if (kaicomJNI != null) {
			kaicomJNI.setScannerStop();
			kaicomJNI.setScannerOff();
			kaicomJNI = null;
		}
		isOpen = false;
	}

	/**
	 * 开始扫描
	 */
	public void startScan() {
	    AUTO_SCAN_FLAG = true;
		if (kaicomJNI != null) {
			if (!isScanning()) {
				kaicomJNI.setScannerStart();
			}

		}
	}

	/**
	 * @hide
	 */
	@SuppressLint("NewApi")
	public void startCamera() {
		if (observers.size() == 0) {
			return;
		}
		Intent intent = new Intent(KaicomApplication.app, CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		KaicomApplication.app.startActivity(intent);
	}

	/**
	 * 结束扫描
	 */
	public void endScan() {
		if (kaicomJNI != null) { // && isScanning()
			kaicomJNI.setScannerStop();
		}
	}

	/**
	 * 连续扫描
	 */
	public void scanContinue() {
		if (kaicomJNI != null) {
			kaicomJNI.setScannerRetriger();
		}
	}
	
	/**
	 * 自动扫描模式开关(默认为自动扫描true)
	 * @author wxf
	 *
	 * @param flag true:标示为自动模式,false:标示不再继续出光扫描
	 */
	public void setAutoScanFlag(boolean flag){
	    AUTO_SCAN_FLAG = flag;
	}

	/**
	 * 扫描头式否打开
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return isOpen;
	}

	public boolean isScanning() {
		if (kaicomJNI != null)
			return kaicomJNI.isScanning();
		return false;
	}

	public void setScanTimeout(int sec) {
		KaicomApplication.app.getKaicomJNIProxy().setScannerTimeout(sec);
	}

}
