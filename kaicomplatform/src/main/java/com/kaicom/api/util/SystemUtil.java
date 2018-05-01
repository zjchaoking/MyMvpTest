package com.kaicom.api.util;

import static com.kaicom.api.KaicomApplication.kaicomJNIProxy;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * 系统api <br>
 * 由于系统不断更新,该类需要不断的优化
 * 
 * @author hb
 * 
 */
public final class SystemUtil {
    
    private SystemUtil() {
        throw new RuntimeException("～～ ^_^");
    }

	/**
	 * 屏蔽状态栏下拉
	 */
	public static void turnOffStatusBarExpand() {
	    kaicomJNIProxy.turnOffStatusBarExpand();
	}

	/**
	 * 恢复状态栏下拉
	 */
	public static void turnOnStatusBarExpand() {
	    kaicomJNIProxy.turnOnStatusBarExpand();
	}

	/**
	 * 关闭触摸屏滑动锁,一般是屏幕黑掉再起来不显示滑动锁
	 */
	public static void turnOffScreenLock() {
		if (getStatusScreenLock() == 1) {
		    kaicomJNIProxy.turnOffScreenLock();
		}
	}

	/**
	 * 打开触摸屏滑动锁,一般是屏幕黑掉再起来显示滑动锁
	 */
	public static void turnOnScreenLock() {
	    kaicomJNIProxy.turnOnScreenLock();
	}

	/**
	 * 使能触摸屏可以触摸事件
	 */
	public static void enableTouchScreen() {
		if (getStatusTouchScreen() != 1) {
		    kaicomJNIProxy.enableTouchScreen();
		}
	}

	/**
	 * 使能触摸屏不可以触摸事件
	 */
	public static void disableTouchScreen() {
	    kaicomJNIProxy.disableTouchScreen();
	}

	/**
	 * 获取触摸屏当前的状态<br>
	 * 
	 * @return 返回 1表示：触摸屏没有锁 ,有触摸事件。 返回 0表示：触摸屏已锁住,没有触摸事件
	 */
	public static int getStatusTouchScreen() {
		return kaicomJNIProxy.getStatusTouchScreen();
	}

	/**
	 * 获取滑动解锁当前的状态<br>
	 * 
	 * @return 返回 1表示：滑动解锁没有关闭 。 返回 0表示：滑动解锁已关闭
	 */
	public static int getStatusScreenLock() {
		return kaicomJNIProxy.getStatusScreenLock();
	}
	
	/**
	 * 设置任意键唤醒
	 * 
	 */
	public static void setAnyKeyWakeUp(){
		kaicomJNIProxy.cfgAnyKeyWakeUp();
	}
	
	/**
	 * 设置默认电源键唤醒，APP退出时应该设置为默认唤醒方式
	 */
	public static void setDefaultPowerKeyWakeUp(){
		kaicomJNIProxy.CfgPowerKeyWakeUp();
	}
	
	/**
	 * 打开ADB Root模式
	 */
	public static void turnOnAdbroot(){
		kaicomJNIProxy.TurnOnOffAdbroot(true);
	}
	
	/**
	 * 关闭ADB Root模式
	 */
	public static void turnOffAdbroot(){
		kaicomJNIProxy.TurnOnOffAdbroot(false);
	}
	
	/**
	 * 禁用系统浏览器
	 */
	public static void disableBrowser(){
		kaicomJNIProxy.TurnOnOffBrowser(false);
	}
	
	/**
	 * 开启系统浏览器
	 */
	public static void enableBrowser(){
		kaicomJNIProxy.TurnOnOffBrowser(true);
	}
	
	/**
	 * 不允许安装第三方APK
	 */
	public static void turnOffInstallManager(){
		kaicomJNIProxy.turnOffInstallManager();
	}
	
	/**
	 * 允许安装第三方APK
	 */
	public static void turnOnInstallManager(){
		kaicomJNIProxy.turnOnInstallManager();
	}
	
	/**
	 * 屏蔽apk安装界面打开与完成按钮
	 */
	public static void cfgInstallManager(){
		kaicomJNIProxy.cfgInstallManager(0x5A);
	}
	
	
	/**
	 * 屏蔽所有物理按键
	 */
	public static void disableKeypad(){
		kaicomJNIProxy.disableKeypad();
	}
	
	/**
	 * 恢复所有物理按键可用
	 */
	public static void enableKeypad(){
		kaicomJNIProxy.enableKeypad();
	}
	
	/**
	 * 屏蔽所有物理按键
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void setOnlyScanKeypadEnable(){
		
		String disableKey = android.view.KeyEvent.KEYCODE_MENU+","
				+android.view.KeyEvent.KEYCODE_DPAD_UP+","
				+android.view.KeyEvent.KEYCODE_DEL+","
				+android.view.KeyEvent.KEYCODE_DPAD_LEFT+","
				+android.view.KeyEvent.KEYCODE_DPAD_CENTER+","
				+android.view.KeyEvent.KEYCODE_DPAD_RIGHT+","
				+android.view.KeyEvent.KEYCODE_DPAD_DOWN+","
				+android.view.KeyEvent.KEYCODE_BACK+","
				+android.view.KeyEvent.KEYCODE_1+","
				+android.view.KeyEvent.KEYCODE_2+","
				+android.view.KeyEvent.KEYCODE_3+","
				+android.view.KeyEvent.KEYCODE_4+","
				+android.view.KeyEvent.KEYCODE_5+","
				+android.view.KeyEvent.KEYCODE_6+","
				+android.view.KeyEvent.KEYCODE_7+","
				+android.view.KeyEvent.KEYCODE_8+","+android.view.KeyEvent.KEYCODE_9+","
				+android.view.KeyEvent.KEYCODE_STAR+","
				+android.view.KeyEvent.KEYCODE_0+","
				+android.view.KeyEvent.KEYCODE_POUND+","
				+android.view.KeyEvent.KEYCODE_BUTTON_A+","
				+android.view.KeyEvent.KEYCODE_BUTTON_B+","
				+android.view.KeyEvent.KEYCODE_EXPLORER+","
				+android.view.KeyEvent.KEYCODE_SOFT_RIGHT+","
				+android.view.KeyEvent.KEYCODE_BUTTON_THUMBL+","
				+android.view.KeyEvent.KEYCODE_BUTTON_THUMBR+","
				+android.view.KeyEvent.KEYCODE_BUTTON_L2+","
				+android.view.KeyEvent.KEYCODE_BUTTON_R2+",";
		
		kaicomJNIProxy.disableKeypadEnableCustom(disableKey);
	}
	
	
}
