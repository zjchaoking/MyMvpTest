package com.kaicom.api;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ProgressBar;

import com.kaicom.api.AppInitAsyncTask.InitListener;
import com.kaicom.api.preference.PlatformPreference;
import com.kaicom.api.util.ApkUtil;

import java.util.regex.Pattern;

import static com.kaicom.api.KaicomApplication.app;

/**
 * 程序入口
 * @author scj
 *
 */
public class MainActivity extends Activity implements InitListener{
	ProgressBar updateProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		// 设置背景
		int background = app.getWelcomeBackgroundRes();
		setContentView(background);
		initWidget();
		processExtraData();
		// 创建桌面快捷方式
		ApkUtil.createShortCut(this);
	}

	/**
	 * 初始化控件
	 */
	private void initWidget() {
		try {
			updateProgressBar = (ProgressBar)this.findViewById(app.getWelcomeProgressBar());

			if(null != updateProgressBar){
				updateProgressBar.setIndeterminate(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		processExtraData();
	}

	private void processExtraData() {
		Intent intent = getIntent();
		if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
			// 退出应用
			if (intent.getBooleanExtra(ApkUtil.APK_EXIT, false)) {
				finish();
				KaicomApplication.app.exit();
			}
			// 重启应用
			else if (intent.getBooleanExtra(ApkUtil.APK_RESTART, false)) {
				welcome();
			}
		} else {
//			welcome();
			app.setupApp(MainActivity.this);
			new AppInitAsyncTask(this).execute();
		}
	}

	private void welcome() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 初始化app设置
//				app.setupApp(MainActivity.this);

				// 先检查唯一码， 未设置唯一码时，先调转到唯一码设置界面
				String machineCode = app.getKaicomJNIProxy().getMachineCode();
				if (!isMachineCode(machineCode)) {
					Intent intent = new Intent(MainActivity.this,
							UniqueCodeActivity.class);
					startActivity(intent);
					PlatformPreference.getInstance().setFirstLaunch(false);
					return;
				}

				boolean isFirst = PlatformPreference.getInstance()
						.getFirstLaunch();
				Class<?> subject = null;

				// 第一次启动且客户程序有设置引导图片进入引导界面，否则直接进入登录界面
				if (isFirst && hasGuideImgs()) {
					subject = GuideActivity.class;
				} else {
					subject = app.getFirstActivity();
				}

				if (isFirst)
					PlatformPreference.getInstance().setFirstLaunch(false);

				if (subject == null)
					return;
				Intent intent = new Intent(MainActivity.this, subject);
				startActivity(intent);
			}
		}, 200);
	}


    /**
     * 验证是否是15位机器唯一码
     * @param machineCode
     * @return
     */
    public static boolean isMachineCode(String machineCode){
        if (TextUtils.isEmpty(machineCode)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[1-3]\\d((0[1-9])|10|11|12)(\\d{11})$");
        return pattern.matcher(machineCode).matches();
    }

	private boolean hasGuideImgs() {
		int[] imgs = app.getGuideImgs();
		return imgs != null && imgs.length > 0;
	}

	@Override
	public void onBackPressed() {
		//        super.onBackPressed();
	}

	/* (non-Javadoc)
	 * @see com.kaicom.api.AppInitAsyncTask.InitListener#onExecute()
	 */
	@Override
	public void onExecute() {
		app.initData();
	}

	/* (non-Javadoc)
	 * @see com.kaicom.api.AppInitAsyncTask.InitListener#onFinish()
	 */
	@Override
	public void onFinish() {
//		updateProgressBar.incrementProgressBy(1);
		if(null != updateProgressBar){
			updateProgressBar = null;
		}
		welcome();
	}

}
