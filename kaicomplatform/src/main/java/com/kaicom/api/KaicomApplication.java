package com.kaicom.api;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.anrwatchdog.ANRWatchDog.ANRListener;
import com.kaicom.api.log.CrashHandler;
import com.kaicom.api.log.KlLoger;
import com.kaicom.api.log.LogManager;
import com.kaicom.api.log.upload.LogUploader;
import com.kaicom.api.manager.ModelRecognizerTools;
import com.kaicom.api.manager.PdaHelperManager;
import com.kaicom.api.manager.sound.SoundManager;
import com.kaicom.api.requirement.RequirementConfig;
import com.kaicom.api.scan.ScanManager;
import com.kaicom.api.util.SystemUtil;
import com.kaicom.fw.R;
import com.kaicom.pdahelper.PdaHelperPreference;
import com.kaicom.pdahelper.PdaHelperSendData;
import com.kaicom.process.data.TransData;

import de.greenrobot.dao.AbstractDaoSession;
import kaicom.android.app.KaicomJNI;

/**
 * Kaicom基础平台Application类
 * <p/>
 * <p>使用greendao拷贝下面的代码到KaicomApplication的子类中
 * <pre class="prettyprint">
 * private static DaoMaster daoMaster;
 * private static DaoSession daoSession;
 * <p/>
 * public DaoMaster getDaoMaster() {
 * if (daoMaster == null) {
 * OpenHelper helper = new DaoMaster.DevOpenHelper(app, "xxx.db", null);
 * daoMaster = new DaoMaster(helper.getWritableDatabase());
 * }
 * return daoMaster;
 * }
 *
 * @author scj
 * @Override public DaoSession getDaoSession() {
 * if (daoSession == null) {
 * if (daoMaster == null) {
 * getDaoMaster();
 * }
 * daoSession = daoMaster.newSession();
 * }
 * return daoSession;
 * }
 * @Override public SQLiteDatabase getDatabase() {
 * return getDaoSession().getDatabase();
 * }
 * </pre>
 */
public abstract class KaicomApplication extends Application {

    public static KaicomJNI kaicomJNI;
    public static KaicomApplication app;
    /**
     * 引入配置文件.
     */
    public RequirementConfig requirementConfig;
    /**
     * 机器码.
     */
    public String machineCode;
    /**
     * KaicomJNI代理类<br>
     * 注：需要使用KaicomJNI中的功能时优先使用该类
     */
    public static KaicomJNIProxy kaicomJNIProxy;


    /**
     * 监听ANR线程
     */
    private ANRWatchDog watchDog;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppInstance(this);
        getKaicomJNIProxy();
    }

    /**
     * 返回单实例.
     *
     * @return HsApplication
     */
    public static KaicomApplication getInstance() {
        return app;
    }

    private static void initAppInstance(KaicomApplication instance) {
        app = instance;
    }

    /**
     * 初始化app在其主进程中进行
     */
    protected void setupApp() {
        getKaicomJNIProxy();
        machineCode = kaicomJNIProxy.getMachineCode();
        CrashHandler.getInstance().init(this); // crash监听
        listenANR(); // 监听ANR
        clientSetupApp();
    }

    /**
     * 客户端主进程中初始化，例如：服务，客户端上传端口，日志, 巴枪助手等<br>
     * 例如:<br>
     * bindUploadService();  // 绑定服务<br>
     * String port = StoFilePreference.getInstance().getUpgradePort();<br>
     * startPdaHelper(null, Integer.parseInt(port), model, "GZSto"); 巴枪助手初始化<br>
     * SpeechSoundManager.getInstance().initSpeechService();// 讯飞语音<br>
     * setLogInitializeArgs("gzStoLog", "gzStoLog"); //日志 <br>
     * uploadCrash("gz_sto", StoFilePreference.getInstance().getSiteCode(),<br>
     * StoFilePreference.getInstance().getJobNumber()); // 异常日志上传<br>
     */
    protected abstract void clientSetupApp();

    /**
     * 数据初始化 在其异步线程中处理包含（系统配置，声音池资源，自动清理日志，数据库创建及升级）
     * 其他耗时数据处理应放入异步中处理
     */
    protected void initData() {
        initSystemSetting(); // 初始化系统设置
        SoundManager.getInstance().initSoundPool(); // 初始化声音池

        LogManager.getLogger().autoClear(); // 自动清理日志
        CrashHandler.getInstance().autoClear(); // 自动清理crash日志

        initDb(); // 初始化数据库

        asyncInitData();
    }

    /**
     * 异步任务初始化数据处理包含（清理无用的数据，及其他 耗时数据处理）<br>
     * 注：<br>
     * 该方法是在线程中处理的，请注意一切需要更新UI的操作请不要在这里调用
     */
    protected abstract void asyncInitData();

    /**
     * 开启监听线程，监听ANR问题
     */
    private void listenANR() {
        watchDog = new ANRWatchDog(4500).setANRListener(new ANRListener() {

            @Override
            public void onAppNotResponding(ANRError error) {
                KlLoger.error("ANR", error);
            }
        });
        watchDog.setReportMainThreadOnly();
        watchDog.start();
    }

    /**
     * 退出监听
     */
    private void unlistenANR() {
        if (watchDog != null && !watchDog.isInterrupted()) {
            try {
                watchDog.interrupt();
            } catch (Exception e) {
            }
        }
    }

    public synchronized KaicomJNIProxy getKaicomJNIProxy() {
        if (kaicomJNIProxy == null) {
            kaicomJNIProxy = ModelRecognizerTools.getKaicomJNIProxy(app);
        }
        return kaicomJNIProxy;
    }

    // 持有MainActivity对象，用以实现App的重启和退出
    MainActivity mainActivity;

    void setupApp(MainActivity activity) {
        mainActivity = activity;
        setupApp();
    }

    /**
     * 初始化系统设置(默认实现屏蔽下拉栏和滑动锁)
     */
    protected void initSystemSetting() {
        SystemUtil.turnOffStatusBarExpand();
        SystemUtil.turnOffScreenLock();
    }

    // 初始化数据库
    protected void initDb() {
        getDaoSession();
        getDatabase();
        getSqliteDatabase();
    }

    /**
     * 退出应用<br>
     * 客户程序退到桌面时，需要调用此方法来释放资源
     */
    public void exit() {
        unlistenANR();
        try {
            // 恢复下拉栏
            SystemUtil.turnOnStatusBarExpand();
            SystemUtil.turnOnScreenLock();

            // 释放声音资源
            SoundManager.getInstance().release();
            // 释放扫描资源
            ScanManager.getInstance().release();
            // 关闭数据库
            closeDb();
        } catch (Exception e) {
        }

        if (mainActivity != null)
            mainActivity.finish();
        System.exit(0);
    }

    public abstract AbstractDaoSession getDaoSession();

    public abstract SQLiteDatabase getDatabase();

    public SQLiteDatabase getSqliteDatabase() {
        return null;
    }

    protected void closeDb() {
        // 清除数据库缓存
        clearDbCache();
        // 关闭数据库
        SQLiteDatabase db = getDatabase();
        if (db != null)
            db.close();

        SQLiteDatabase sqlDb = getSqliteDatabase();
        if (sqlDb != null)
            sqlDb.close();
    }

    /**
     * 清空数据库缓存
     */
    public void clearDbCache() {
        AbstractDaoSession daoSession = getDaoSession();
        if (daoSession != null)
            daoSession.clear();
    }

    /**
     * 返回在引导界面的图片资源数组<br>
     * 如果返回null，则app不会经过引导界面<br>
     * 注：图片最好不要超过6张
     *
     * @return
     */
    public abstract int[] getGuideImgs();

    /**
     * 返回客户第一个activity（登录界面or唯一码界面）
     *
     * @return
     */
    public abstract Class<?> getFirstActivity();

    /**
     * MainActivity的背景
     *
     * @return 为null时采用默认布局
     */
    public int getWelcomeBackgroundRes() {
        return R.layout.activity_main;
    }

    /**
     * 获得进度条
     *
     * @return
     */
    public int getWelcomeProgressBar() {
        return R.id.update_progressBar;
    }

    /**
     * 设置Crash TAG
     *
     * @param tag 标签-全局捕获异常的文件头
     */
    public void setCrashTag(String tag) {
        CrashHandler.setTag(tag);
    }

    /**
     * 开启Crash全局托管
     */
    public void setCrashMode() {
        CrashHandler.getInstance().init(this);
    }

    /**
     * 设置日志的初始化参数
     *
     * @param tag      标签
     * @param customer 客户名  如：ttk
     */
    public void setLogInitializeArgs(String tag, String customer) {
        LogManager.getLogger().initLogConfig(tag, customer);
    }

    /**
     * 设置debug模式, 默认true<br>
     * 如果设置成false，调用KlLog.debug方法将不会写文件
     *
     * @param isDebug 是否是debug模式
     */
    public void setLogDebug(boolean isDebug) {
        LogManager.getLogger().setDebug(isDebug);
    }

    /**
     * 取系统配置.
     *
     * @return ParamConfig
     */
    public RequirementConfig getRequireConfig() {
        return this.requirementConfig;
    }

    /**
     * @return the app
     */
    public static KaicomApplication getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public static void setApp(KaicomApplication app) {
        KaicomApplication.app = app;
    }

    /**
     * @return the requirementConfig
     */
    public RequirementConfig getRequirementConfig() {
        return requirementConfig;
    }

    /**
     * @param requirementConfig the requirementConfig to set
     */
    public void setRequirementConfig(RequirementConfig requirementConfig) {
        this.requirementConfig = requirementConfig;
    }

    /**
     * @return the machineCode
     */
    public String getMachineCode() {
//		if (TextUtils.isEmpty(machineCode)) {
//			return kaicomJNIProxy.getMachineCode();
//		}
        return kaicomJNIProxy.getMachineCode();
    }

    /**
     * @param machineCode the machineCode to set
     */
    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    /**
     * 启动巴枪助手做升级检测， 巴枪助手的ip、端口、机器类型及客户名<br>
     * 例:<pre>startPdaHelper("122.224.143.206", 9999, "520", "TTK");</pre>
     *
     * @param ip        可以为空，默认地址122.224.143.206
     * @param port      端口
     * @param modelType 机型  可以为空，为空时自动判断机型(不建议)
     * @param customer  必填 客户名(apk中的客户名称)
     */
    public void startPdaHelper(final String ip, final int port, final String modelType, final String customer) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                if (!TextUtils.isEmpty(ip))
                    PdaHelperPreference.getInstance(app).setPDAHelperIp(ip);
                if (port > 0)
                    PdaHelperPreference.getInstance(app).setPDAHelperPort(port);
                if (!TextUtils.isEmpty(modelType))
                    PdaHelperPreference.getInstance(app).setModelType(modelType);

                Intent intent = new Intent("com.kaicom.KailiHelper.request");
                intent.putExtra("ACTION_DATA", new TransData());
                sendBroadcast(intent);

                new PdaHelperSendData(app).initPdaHelper(new PdaHelperManager(),
                        customer);
            }
        });

    }

    /**
     * 自动上传crash文件, 上传到服务器的名称为：customer_siteId_jobId_crash_date.zip
     *
     * @param customer 客户名
     * @param siteId   网点编号
     * @param jobId    工号
     */
    public void uploadCrash(String customer, String siteId, String jobId) {
        new LogUploader(customer).autoUploadCrash(customer, siteId, jobId);
    }



}
