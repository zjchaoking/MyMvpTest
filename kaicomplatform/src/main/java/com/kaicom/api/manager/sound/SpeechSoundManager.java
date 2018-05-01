package com.kaicom.api.manager.sound;

import static com.kaicom.api.KaicomApplication.app;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.util.Log;

import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.kaicom.api.util.ApkUtil;

/**
 * 语音播报管理类
 * <p>
 * 使用说明：<br>
 * 1、先调用c;返回true再执行2、3
 * 2、再调用SpeechSoundManager.getInstance().startSpeech(要播报的内容);
 * 3、后释放资源SpeechSoundManager.getInstance().destroy();不是每次调用2步骤就要释放,不用了语音播报就释放。
 * </p>
 * 
 * @author hb
 * 
 */
public class SpeechSoundManager {
	public static final String PREFER_NAME = "com.iflytek.setting";

	private static String TAG = "TtsDemo";
	// 语音合成对象
	private SpeechSynthesizer mTts;
	// private Toast mToast;
	public static String SPEAKER = "speaker";

	private SharedPreferences mSharedPreferences;

	private static SpeechSoundManager mSpeechManager;

	public static SpeechSoundManager getInstance() {
		if (mSpeechManager == null) {
			mSpeechManager = new SpeechSoundManager();
		}
		return mSpeechManager;
	}

	/**
	 * 检查是否安装【讯飞语音+】并获取TTS对象<br>
	 * 由于语音播报依赖于【讯飞语音+】所以没有安装就要先安装它才能语音播报<br>
	 * 【讯飞语音+】：包含了语音库,所以我们需要安装它
	 * 
	 * @return 没有安装【讯飞语音+】返回false 安装了返回true
	 */
	public boolean initSpeechService() throws Exception {
		if (mSpeechManager != null)
			mSpeechManager.destroy();

		if (ApkUtil.checkApkExist("com.iflytek.speechcloud")) {
			getTTS();
		} else {
			return false;
		}
		return true;

	}

	private void getTTS() {
		// 设置你申请的应用appid
		SpeechUtility.getUtility(app).setAppid("4d6774d0");
		mSharedPreferences = app.getSharedPreferences(PREFER_NAME,
				Activity.MODE_PRIVATE);
		// 初始化合成对象
		mTts = new SpeechSynthesizer(app, mTtsInitListener);
	}

	/**
	 * 开始语音播报播报之前必须先调用initSpeechService
	 * 
	 * @param speechContext
	 *            播报的内容
	 */
	public void startSpeech(String speechContext) throws Exception {

		if (mTts != null && speechContext != null && speechContext.length() > 0) {

			String speechStr = getSpeechContext(speechContext);
			setParam();
			// 设置参数
			mTts.startSpeaking(speechStr, mTtsListener);
		}
	}

	/**
	 * 假如speechContext = 123 ,该方法转化1 2 3; 假如不转化播报是一百二十三,转化后播报一 二 三;
	 * 
	 * @param speechContext
	 *            播报的内容
	 * @return
	 */
	private String getSpeechContext(String speechContext) {
		StringBuffer str = new StringBuffer();

		int len = speechContext.length();

		for (int i = 0; i < len; i++) {
			str.append(speechContext.substring(i, i + 1) + " ");
		}

		return str.toString();
	}

	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {

		@Override
		public void onInit(ISpeechModule arg0, int code) {
			Log.d(TAG, "InitListener init() code = " + code);
		}
	};

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
		@Override
		public void onBufferProgress(int progress) throws RemoteException {
			Log.d(TAG, "onBufferProgress :" + progress);
		}

		@Override
		public void onCompleted(int code) throws RemoteException {
			Log.d(TAG, "onCompleted code =" + code);
		}

		@Override
		public void onSpeakBegin() throws RemoteException {
			Log.d(TAG, "onSpeakBegin");
		}

		@Override
		public void onSpeakPaused() throws RemoteException {
			Log.d(TAG, "onSpeakPaused.");
		}

		@Override
		public void onSpeakProgress(int progress) throws RemoteException {
			Log.d(TAG, "onSpeakProgress :" + progress);
		}

		@Override
		public void onSpeakResumed() throws RemoteException {
			Log.d(TAG, "onSpeakResumed.");
		}
	};

	/**
	 * 语音播报参数设置<br>
	 * 可以设置播报人员,播报语速、音调、音量
	 */
	private void setParam() {
		mTts.setParameter(SpeechConstant.ENGINE_TYPE,
				mSharedPreferences.getString("engine_preference", "local"));

		// 设置发音人
		if (mSharedPreferences.getString("engine_preference", "local")
				.equalsIgnoreCase("local")) {
			mTts.setParameter(SpeechSynthesizer.VOICE_NAME, mSharedPreferences
					.getString("role_cn_preference", "xiaoyan"));
		} else {
			mTts.setParameter(SpeechSynthesizer.VOICE_NAME, mSharedPreferences
					.getString("role_cn_preference", "xiaoyan"));
		}
		// 设置语速
		mTts.setParameter(SpeechSynthesizer.SPEED,
				mSharedPreferences.getString("speed_preference", "60"));

		// 设置音调
		mTts.setParameter(SpeechSynthesizer.PITCH,
				mSharedPreferences.getString("pitch_preference", "50"));

		// 设置音量
		mTts.setParameter(SpeechSynthesizer.VOLUME,
				mSharedPreferences.getString("volume_preference", "90"));
	}
	
	// 判断手机中是否安装了讯飞语音+
	private boolean checkSpeechServiceInstall() {
		String packageName = "com.iflytek.speechcloud";
		List<PackageInfo> packages = app.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			if (packageInfo.packageName.equals(packageName)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 释放语音播报资源
	 */
	public void destroy() throws Exception {
		if (mTts != null) {
			mTts.stopSpeaking(mTtsListener);
			mTts.destory();
			mTts = null;
		}
	}
}
