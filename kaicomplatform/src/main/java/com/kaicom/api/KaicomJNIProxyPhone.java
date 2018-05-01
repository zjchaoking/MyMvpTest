package com.kaicom.api;

import kaicom.android.app.KaicomJNI.ScanCallBack;
import android.os.Build;

import com.kaicom.api.preference.PlatformPreference;
import com.kaicom.api.util.StringUtil;

public class KaicomJNIProxyPhone extends KaicomJNIProxy {

	public KaicomJNIProxyPhone() {
		super(null);
	}
	
    /**
     * 设置机器唯一码
     * @param machineCode
     */
    public void setMachineCode(String machineCode) {
    	PlatformPreference.getInstance().setMachineCode(machineCode);
    }
    
    /**
     * 获得机器唯一码
     * @return 可能为null
     */
    public String getMachineCode() {
        String code = PlatformPreference.getInstance().getMachineCode();
        return StringUtil.trim(code);
    }
    
    public void setmScanCB(ScanCallBack callBack) {
    }
    
    /**
     * 开启扫描头
     */
    public void setScannerOn() {
    }
    
    /**
     * 关闭扫描头
     */
    public void setScannerOff() {
    }
    
    /**
     * 开始扫描
     */
    public void setScannerStart() {
    }
    
    /**
     * 停止扫描
     */
    public void setScannerStop() {
    }
    
    /**
     * 连续扫描
     */
    public void setScannerRetriger() {
    }
    
    /**
     * 是否正在扫描
     * @return
     */
    public boolean isScanning() {
    	return false;
    }
    
    /**
     * 设置扫描头超时时间
     */
    public void setScannerTimeout(int sec) {
    }
    
    /**
     * 设置系统时间
     * @param time long值字符串
     */
    public void setSystemTime(String time) {
    }
    
    public String getLibVer() {
        return Build.MODEL;
    }
    
    /**
     * 关闭usb调试
     */
    public void usbDebugOff() {
    }
    
    /**
     * 开启usb调试
     */
    public void usbDebugOn() {
    }
    
    /**
     * apk静默安装, 安装后自动重启
     * @param apkPath 绝对路径
     */
    public void installApkWithSilence(String apkPath) {
    }
    
    /**
     * 不允许安装第三方apk
     */
    public void turnOnInstallManager() {
    }

    public void setGPIORightGreen(boolean open) {
    }

    public void setGPIOLeftRed(boolean open) {
    }

    public void setGPIORightRed(boolean open) {
    }
	
    
    @Override
	public void turnOffStatusBarExpand() {
	}

    @Override
	public void turnOnStatusBarExpand() {
	}
    
    @Override
	public void turnOnScreenLock() {
	}

    @Override
	public void turnOffScreenLock() {
	}

    @Override
	public void enableTouchScreen() {
	}

    @Override
	public void disableTouchScreen() {
	}

    @Override
	public int getStatusTouchScreen() {
		return 1;
	}

    @Override
	public int getStatusScreenLock() {
		return 1;
	}

    /* (non-Javadoc)
     * @see com.kaicom.api.KaicomJNIProxy#cfgAnyKeyWakeUp()
     */
    @Override
    public void cfgAnyKeyWakeUp() {
    	
    }
    
    /* (non-Javadoc)
     * @see com.kaicom.api.KaicomJNIProxy#CfgPowerKeyWakeUp()
     */
    @Override
    public void CfgPowerKeyWakeUp() {
    	// TODO Auto-generated method stub
    	
    }
    
    /* (non-Javadoc)
     * @see com.kaicom.api.KaicomJNIProxy#TurnOnOffAdbroot(boolean)
     */
    @Override
    public void TurnOnOffAdbroot(boolean isOn) {
    	
    }
    
    @Override
    public void TurnOnOffBrowser(boolean isOn){
    	
    }
    
    @Override
    public void disableKeypad(){
    	
    }
    
    @Override
    public void enableKeypad(){
    	
    }
    
    @Override
    public void disableKeypadEnableCustom(String customKey){
    	
    }
    
    /* (non-Javadoc)
     * @see com.kaicom.api.KaicomJNIProxy#cfgInstallManager(int)
     */
    @Override
    public void cfgInstallManager(int action) {
    	
    }
    
}
