package com.kaicom.api;

import android.os.Build;

import com.kaicom.api.util.StringUtil;

import kaicom.android.app.KaicomJNI;
import kaicom.android.app.KaicomJNI.ScanCallBack;

import static kaicom.android.app.KaicomJNI.GPIO_OFF;
import static kaicom.android.app.KaicomJNI.GPIO_ON;

/**
 * <h4>KaicomJNI代理类</h4> 主要是方便替换KaicomJNI，以实现删掉KaicomJNI放手机上运行等功能
 * 
 * @author scj
 * 
 */
public class KaicomJNIProxy {

	private KaicomJNI kaicomJNI;

	public KaicomJNIProxy(KaicomJNI kaicomJNI) {
		this.kaicomJNI = kaicomJNI;
	}

	public interface ScanCallBackProxy extends KaicomJNI.ScanCallBack {
	}

	/**
	 * 设置机器唯一码
	 * 
	 * @param machineCode
	 */
	public void setMachineCode(String machineCode) {
		kaicomJNI.SetMachineCode(machineCode);
	}

	/**
	 * 获得机器唯一码
	 * 
	 * @return 可能为null
	 */
	public String getMachineCode() {
		String code = kaicomJNI.GetMachineCode();
		return StringUtil.trim(code);
	}

	public void setmScanCB(ScanCallBack callBack) {
		kaicomJNI.setmScanCB(callBack);
	}

	/**
	 * 开启扫描头
	 */
	public void setScannerOn() {
		kaicomJNI.SetScannerOn();
	}

	/**
	 * 关闭扫描头
	 */
	public void setScannerOff() {
		kaicomJNI.SetScannerOff();
	}

	/**
	 * 开始扫描
	 */
	public void setScannerStart() {
		kaicomJNI.SetScannerStart();
	}

	/**
	 * 停止扫描
	 */
	public void setScannerStop() {
		kaicomJNI.SetScannerStop();
	}

	/**
	 * 连续扫描
	 */
	public void setScannerRetriger() {
		kaicomJNI.SetScannerRetriger();
	}

	/**
	 * 是否正在扫描
	 * 
	 * @return
	 */
	public boolean isScanning() {
		return kaicomJNI.GetScannerIsScanning();
	}

	/**
	 * 设置扫描头超时时间
	 */
	public void setScannerTimeout(int sec) {
		kaicomJNI.SetScannerTimerOut(sec);
	}

	/**
	 * 设置系统时间
	 * 
	 * @param time
	 *            long值字符串
	 */
	public void setSystemTime(String time) {
		kaicomJNI.SetSystemTime(time);
	}

	public String getLibVer() {
		try {
			return kaicomJNI.getlibVer();
		} catch (Exception e) {
		}
		return Build.MODEL;
	}

	/**
	 * 关闭usb调试
	 */
	public void usbDebugOff() {
		kaicomJNI.TurnOffUSBDebug();
	}

	/**
	 * 开启usb调试
	 */
	public void usbDebugOn() {
		kaicomJNI.TurnOnUSBDebug();
	}

	/**
	 * apk静默安装, 安装后自动重启
	 * 
	 * @param apkPath
	 *            绝对路径
	 */
	public void installApkWithSilence(String apkPath) {
		kaicomJNI.TurnOnInstallManager();
		kaicomJNI.KaicomInstallApkWithSilence(apkPath, true);
	}

	/**
	 * 不允许安装第三方apk
	 */
	public void turnOffInstallManager() {
		kaicomJNI.TurnOffInstallManager();
	}
	
	/**
	 * 允许安装第三方apk
	 */
	public void turnOnInstallManager() {
		kaicomJNI.TurnOnInstallManager();
	}
	
	/**
	 * 设置apk安装界面
	 * @param action 0x00 恢复系统默,0x5A 两个都屏蔽,0xA 屏蔽安装界面的"取消"按钮，保留"安装", 0x50 屏蔽安装完成界面的"完成"按钮，保留"打开"
	 */
	public void cfgInstallManager(int action){
		kaicomJNI.CfgInstallManager(action);
	}
	

	// 右红灯和 左红灯定义（520 / 510 /585P）
	public static final int WDT520_LED_RR = 0X80;
	public static final int WDT520_LED_LR = 0X30;
	// 右红灯和左红灯定义（585）
	public static final int WDT585_LED_RR = 0X30;
	public static final int WDT585_LED_LR = 0X33;
	// 右蓝灯 和 右绿灯定义
	public static final int WDT_LED_RB = 0X50;
	public static final int WDT_LED_RG = 0X60;

	public void setGPIORightGreen(boolean open) {
		kaicomJNI.SetGPIOStatus(WDT_LED_RG, open ? GPIO_ON : GPIO_OFF);
	}

	public void setGPIOLeftRed(boolean open) {
		kaicomJNI.SetGPIOStatus(WDT520_LED_LR, open ? GPIO_ON : GPIO_OFF);
	}

	public void setGPIORightRed(boolean open) {
		kaicomJNI.SetGPIOStatus(WDT520_LED_RR, open ? GPIO_ON : GPIO_OFF);
	}

	public void turnOffStatusBarExpand() {
		kaicomJNI.TurnOffStatusBarExpand();
	}

	public void turnOnStatusBarExpand() {
		kaicomJNI.TurnOnStatusBarExpand();
	}

	public void turnOffScreenLock() {
		if (getStatusScreenLock() == 1) {
			kaicomJNI.TurnOffScreenLock();
		}
	}

	public void turnOnScreenLock() {
		kaicomJNI.TurnOnScreenLock();
	}

	public void enableTouchScreen() {
		if (getStatusTouchScreen() != 1) {
			kaicomJNI.Enable_touch_screen();
		}
	}

	public void disableTouchScreen() {
		kaicomJNI.Disable_touch_screen();
	}

	public int getStatusTouchScreen() {
		return kaicomJNI.GetStatusTouch_screen();
	}

	public int getStatusScreenLock() {
		return kaicomJNI.GetStatusScreenLock();
	}

	public void cfgAnyKeyWakeUp() {
		kaicomJNI.CfgAnyKeyWakeUp();
	}

	public void CfgPowerKeyWakeUp() {
		kaicomJNI.CfgPowerKeyWakeUp();
	}
	
	public void TurnOnOffAdbroot(boolean isOn){
		kaicomJNI.TurnOnOffAdbroot(isOn);
	}
	
	public void TurnOnOffBrowser(boolean isOn){
		kaicomJNI.TurnOnOffBrowser(isOn);
	}
	
	public void disableKeypad(){
		kaicomJNI.disableKeypad();
	}
	
	public void enableKeypad(){
		kaicomJNI.enableKeypad();
	}
	
	public void disableKeypadEnableCustom(String customKey){
		kaicomJNI.disableKeypadEnableCustom(customKey);
	}
	public void disableHomeTouchScreen() {
		kaicomJNI.Disable_home_touch_screen();
	}
	public void enableHomeTouchScreen(){
		kaicomJNI.Enable_home_touch_screen();
	}
}
