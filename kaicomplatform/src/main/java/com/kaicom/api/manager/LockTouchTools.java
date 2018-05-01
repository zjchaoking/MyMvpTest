package com.kaicom.api.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaicom.api.util.SystemUtil;
import com.kaicom.fw.R;

/**
 * 锁触摸屏工具类<p>
 * 调用以下两个方法即可
 * <li>调用锁屏/解锁功能
 * <pre>
 	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // F4锁屏
        if (keyCode == KeyCodeConstant.KEY_F4) {
			LockTouchTools.lockTouch(this);
			return true;
		}
        return super.onKeyUp(keyCode, event);
    }
 * </pre>
 * 
 * <li>状态显示
 * <pre>
	@Override
    protected void onResume() {
    	super.onResume();
    	LockTouchTools.showOrHideLockedView(this);
    }
 * </pre>
 * 注：510上测试（锁屏图片刚出现时，触摸图片）可能出现ANR问题
 * @author scj
 *
 */
public final class LockTouchTools {

	private static View mLockView;
	private static boolean isAttached = false;

	/**
	 * 锁屏/解锁
	 * <p>
	 * <li>如果当前界面未锁屏，则执行锁屏功能(将在界面上显示一个锁的图片, 此时触摸屏不可用)
	 * <li>如果当前界面已锁，则执行解锁功能(移除锁屏图片)
	 * @param context
	 */
	public static void lockTouch(Context context) {
		if (!isLocked()) {
			SystemUtil.disableTouchScreen();
			showLockedView(context);
		} else {
			SystemUtil.enableTouchScreen();
			hideLockedView(context);
		}
	}
	
	/**
	 * 锁屏/解锁
	 * <p>
	 * <li>如果当前界面未锁屏，则执行锁屏功能(将在界面上显示一个锁的图片, 此时触摸屏不可用)
	 * <li>如果当前界面已锁，则执行解锁功能(移除锁屏图片)
	 * @param context
	 * @param notice 提示文字信息
	 */
	public static void lockTouch(Context context, String notice) {
		if (!isLocked()) {
			SystemUtil.disableTouchScreen();
			showLockedView(context, notice);
		} else {
			SystemUtil.enableTouchScreen();
			hideLockedView(context);
		}
	}
	
	/**
	 * 显示or隐藏锁图片
	 */
	public static void showOrHideLockedView(Context context) {
		if (isLocked())
			showLockedView(context);
		else
			hideLockedView(context);
	}

	/**
	 * 判断是否已经锁屏
	 * @return
	 */
	public static boolean isLocked() {
		// 1 代表触摸屏未锁，0代表已锁
		int state = SystemUtil.getStatusTouchScreen();
		return state == 0;
	}

	/**
	 * 显示锁屏图片
	 * @param context
	 */
	public static void showLockedView(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		if (mLockView == null) {
			mLockView = LayoutInflater.from(context).inflate(
					R.layout.screen_lock_layout, null);
			mLockView.setFocusable(false);
			mLockView.setEnabled(false);
		}
		if (isAttached) {
			try {
				windowManager.removeView(mLockView);
			} catch (Exception e) {
			}
		}

		WindowManager.LayoutParams wmParams = getLayoutParams();
		windowManager.addView(mLockView, wmParams);
		isAttached = true;
	}
	
	/**
	 * 显示锁屏图片及文字
	 * @param context
	 * @param notice
	 */
	public static void showLockedView(Context context, String notice) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		if (mLockView == null) {
			mLockView = LayoutInflater.from(context).inflate(
					R.layout.screen_lock_layout, null);
			TextView tv = (TextView)mLockView.findViewById(R.id.tvLockScreen);
			tv.setText(notice);
			mLockView.setFocusable(false);
			mLockView.setEnabled(false);
		}
		if (isAttached) {
			try {
				windowManager.removeView(mLockView);
			} catch (Exception e) {
			}
		}

		WindowManager.LayoutParams wmParams = getLayoutParams();
		windowManager.addView(mLockView, wmParams);
		isAttached = true;
	}

	/**
	 * 隐藏锁屏图片
	 * @param context
	 */
	public static void hideLockedView(Context context) {
		if (mLockView == null)
			return;

		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		if (isAttached) {
			try {
				windowManager.removeView(mLockView);
			} catch (Exception e) {
			}
		}
		isAttached = false;
	}

	private static WindowManager.LayoutParams getLayoutParams() {
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.format = PixelFormat.RGBA_8888;
		return wmParams;
	}

}
