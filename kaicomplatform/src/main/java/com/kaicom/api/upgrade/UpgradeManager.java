package com.kaicom.api.upgrade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.upgrade.request.RequestData;
import com.kaicom.api.upgrade.response.IsUpgradeResponse;
import com.kaicom.api.upgrade.response.UpgradeFileInfo;
import com.kaicom.api.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import kaicom.android.app.manager.AlarmAlertWakeLockManager;

/**
 * <h4>升级管理类</h4>
 * <p>管理具体的升级配置及操作
 *
 * @author wxf
 */
public class UpgradeManager {

    private static final String TAG = "UpgradeManager";

    // private static UpgradeManager instance;

    public UpgradeConfig config;

    private WebService webService;

    private UpgradeStatus upgradeListener;

    private volatile Status mStatus = Status.PENDING;

    private String causeMsg = "";

    private int updateProcess = 0;

    private Context context;

    private String ip = "122.224.143.206";

    private int port = 18090;

    private IsUpgradeResponse resp;

    // 异步handler
    private static final Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            if (upgradeListener != null) {

                if (mStatus == Status.PENDING) {

                    upgradeListener.onStartUpdate();

                } else if (mStatus == Status.RUNNING) {

                    upgradeListener.onProgressUpdate(updateProcess);

                } else if (mStatus == Status.FINISHED) {

                    upgradeListener.onFinished(causeMsg);

                } else if (mStatus == Status.FAILED) {

                    upgradeListener.onFailed(causeMsg);

                } else {

                }

            } else {
                // 没有监听
            }
        }
    };

    public UpgradeManager(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        config = new UpgradeConfig();
    }

    /**
     * 升级管理类 初始化对象 默认下载成功且执行安装
     *
     * @param addr    地址
     * @param port    端口
     * @param context 上下文对象
     * @throws Exception 异常信息(网络异常)
     * @author wxf
     */
    public UpgradeManager(String addr, int port, Context context) throws Exception {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.ip = addr;
        this.port = port;
        config = new UpgradeConfig(addr, port);
        try {
            webService = new WebService(addr, port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("网络异常");
        }
    }

    /**
     * 升级管理类 初始化对象
     *
     * @param addr          地址
     * @param port          端口
     * @param context       上下文对象
     * @param isNeedInstall 是否需要安装, 如果false则不会安装但会提示是否有新版本, 提示信息为(有新版本or没有更新)
     * @throws Exception 异常信息(网络异常)
     * @author wxf
     */
    public UpgradeManager(String addr, int port, Context context, boolean isNeedInstall) throws Exception {
        // TODO Auto-generated constructor stub
        this.context = context;
        config = new UpgradeConfig(addr, port, isNeedInstall);
        try {
            webService = new WebService(addr, port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("网络异常");
        }
    }

    /**
     * 升级管理类 初始化对象
     *
     * @param addr          地址
     * @param port          端口
     * @param isAutoInstall 是否自动安装
     * @param context       上下文对象
     * @throws Exception 异常信息(网络异常)
     * @author wxf
     */
    public UpgradeManager(String addr, int port, boolean isAutoInstall,
                          Context context) throws Exception {
        // TODO Auto-generated constructor stub
        this.context = context;
        config = new UpgradeConfig(addr, port, isAutoInstall);
        try {
            webService = new WebService(addr, port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("网络异常");
        }
    }

    /**
     * 升级管理类 初始化对象
     *
     * @param addr          地址
     * @param port          端口
     * @param tempPath      下载文件临时目录
     * @param isAutoInstall 是否自动安装
     * @param context       上下文对象
     * @throws Exception 异常信息(网络异常)
     * @author wxf
     */
    public UpgradeManager(String addr, int port, String tempPath,
                          boolean isAutoInstall, Context context) throws Exception {
        // TODO Auto-generated constructor stub
        this.context = context;
        config = new UpgradeConfig(addr, port, tempPath, isAutoInstall);
        try {
            webService = new WebService(addr, port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("网络异常");
        }
    }

    // public static UpgradeManager getUpgradeManager() throws Exception {
    // if (instance == null) {
    // instance = new UpgradeManager();
    // }
    //
    // return instance;
    // }

    /**
     * 获得软件版本
     *
     * @return
     * @throws Exception
     * @author wxf
     */
    public String getPackageName() throws Exception {
        String versionName = "";
        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

            // 当前应用的版本名称
            versionName = info.versionName;

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("内部错误");
        }
        return versionName;
    }

    public void initUpgradeConfig(UpgradeConfig config) {
        this.config = config;
    }

    public void setUpgradeListener(UpgradeStatus upgradeListener) {
        this.upgradeListener = upgradeListener;
    }

    /**
     * 软件升级
     *
     * @param clientName 软件客户名称(如: STO, TTK)
     * @author wxf
     */
    public void UpgradeSoftware(final String clientName) {
        new Thread() {
            public void run() {
                // 锁定cpu不让进入待机
                AlarmAlertWakeLockManager
                        .acquireCpuWakeLock(context, "upgrade");

                UpgradeProcess(clientName);

                AlarmAlertWakeLockManager.releaseCpuLock("upgrade");

            }
        }.start();

    }

    /**
     * 升级处理
     *
     * @param clientName 客户名称
     * @author wxf
     */
    private void UpgradeProcess(String clientName) {
        try {
            resp = upgradeQuery(getReqData(clientName));

            if (resp.isUpgrade()) {
                if (config.isAutoInstall()) {
                    doUpgrade();
                } else {
                    postUpdateStatus(Status.FINISHED, "检测到有新版本");
                }
                // mStatus = Status.FINISHED;
                // causeMsg = "没有更新";
                // postUpdateStatus(Status.FINISHED, "更新完成");

            } else {
                // 没有更新
                // mStatus = Status.FINISHED;
                // causeMsg = "没有更新";

                postUpdateStatus(Status.FAILED, "当前软件没有更新");

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println(e.toString());
            mStatus = Status.FAILED;
            causeMsg = e.getMessage();
            postUpdateStatus();

        }
    }

    public void doUpgrade() throws Exception {
        if (resp == null) {
            return;
        }
        UpgradeFileInfo fileInfo = getUpgradeFileInfo(resp.getFileUrl() + "/"
                + resp.getFileName());
        if (fileInfo != null) {
            downloadFile(fileInfo, resp.getFileName());
            String filePath = FileUtil.SDPATH + resp.getFileName();
            //静默安装
            KaicomApplication.kaicomJNIProxy.installApkWithSilence(filePath);
//            installSoftware(getInstallSoftwareFilePath(resp
//                    .getFileName()));

        } else {
            postUpdateStatus(Status.FAILED, "软件更新失败");
        }
    }

    @SuppressLint("DefaultLocale")
    private RequestData getReqData(String clientName) throws Exception {
        RequestData reqData = new RequestData();
        Build bd = new Build();
        String WDTName = bd.MODEL.trim();

        reqData.setName(WDTName);

        if (WDTName.equals("prowave77_ics2")) {
            reqData.setName("585");
        }

        reqData.setNetNumber("");
        reqData.setMachineCode( KaicomApplication.kaicomJNIProxy.getMachineCode()
                .trim());
        String authorise = "http://" + ip
                + ":" + port + "/Service.asmx/Authorise";
        reqData.setEncryptedStr(authorise);
        reqData.setCurrentVersion(clientName.toUpperCase() + "-"
                + reqData.getName() + "-" + getPackageName());
        System.out.println(reqData.toString());
        return new RequestData(reqData.getName(), "", KaicomApplication.kaicomJNIProxy
                .getMachineCode().trim(), reqData.getCurrentVersion(), authorise);
//        return reqData;
    }

    /**
     * 查询是否有更新
     *
     * @param reqData
     * @return
     * @throws Exception()
     * @author wxf
     */
    public IsUpgradeResponse upgradeQuery(RequestData reqData) throws Exception {
        postUpdateStatus(Status.PENDING);
        return webService.query(reqData);

    }

    /**
     * 通过UIR取得文件信息
     *
     * @param uri
     * @return
     * @throws Exception()
     * @author wxf
     */
    public UpgradeFileInfo getUpgradeFileInfo(String uri) throws Exception {

        return webService.getDownFile(uri);

    }

    /**
     * 读取网络资源文件
     *
     * @param fileInfo
     * @param fileName
     * @throws Exception()
     * @author wxf
     */
    public void downloadFile(UpgradeFileInfo fileInfo, String fileName)
            throws Exception {
        mStatus = Status.RUNNING;

        FileOutputStream fileOutputStream = null;
        InputStream is = fileInfo.getIs();
        try {
            if (is != null) {

                File file = getInstallSoftwareFilePath(fileName);

                fileOutputStream = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int ch = -1;
                int times = 0;
                int count = 0;
                while ((ch = is.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, ch);
                    count += ch;
                    if (times == 30 || (count == fileInfo.getLength())) {
                        // Message msg = handler.obtainMessage();
                        // msg.what = PROGRESS_COUNT;
                        // msg.arg1 = (int) (count * 100 / length);
                        // msg.sendToTarget();
                        updateProcess = (int) (count * 100 / fileInfo
                                .getLength());
                        // 进度值
                        postUpdateStatus();

                        times = 0;
                        continue;
                    }
                    times++;
                }
            }
            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception ex) {
            throw new Exception("下载文件失败，请重试");
        }
        // 下载完成
        // mStatus = Status.FINISHED;
        //
        postUpdateStatus(Status.FINISHED, "下载完成");

    }

    public File getInstallSoftwareFilePath(String fileName) throws Exception {
        File file = null;
        try {
            file = new File(config.getTmpFilePath(), fileName);

        } catch (Exception e) {
            // TODO: handle exception

            throw new Exception("内部异常");
        }

        return file;
    }

    /**
     * Returns the current status of this task.
     *
     * @return The current status.
     */
    public final Status getStatus() {
        return mStatus;
    }

    private void postUpdateStatus() {
        mHandler.post(mRunnable);
    }

    private void postUpdateStatus(Status status) {
        mStatus = status;
        postUpdateStatus();
    }

    private void postUpdateStatus(Status status, String msg) {
        mStatus = status;
        causeMsg = msg;
        postUpdateStatus();
    }

    /**
     * 软件包安装
     *
     * @param file 文件
     * @author wxf
     */
    public void installSoftware(File file) {
//		Intent intent = new Intent();
//		intent.addFlags(config.getInstallType());
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.fromFile(filePath),
//				"application/vnd.android.domain-archive");
//        context.startActivity(intent);
        int ACTION_DIS_CANCEL_FINISH = 0X5A;    //都屏蔽
        KaicomApplication.kaicomJNIProxy.cfgInstallManager(ACTION_DIS_CANCEL_FINISH);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 软件包安装
     *
     * @param filePath 文件路径
     * @author wxf
     */
    public void installSoftware(String filePath) {
        Intent intent = new Intent();
        intent.addFlags(config.getInstallType());
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(filePath),
                "application/vnd.android.domain-archive");
        context.startActivity(intent);
    }

    /**
     * Indicates the current status of the task. Each status will be set only
     * once during the lifetime of a task.
     */
    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that {@link UpgradeManager} has finished.
         */
        FINISHED,
        /**
         * Indicates that {@link UpgradeManager} has failed.
         */
        FAILED,

    }

}
