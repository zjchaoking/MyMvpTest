package com.kaicom.mymvptest;

import android.database.sqlite.SQLiteDatabase;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.log.CrashHandler;
import com.kaicom.api.util.FileUtil;
import com.kaicom.mymvptest.activity.LoginActivity;
import com.kaicom.mymvptest.datasource.DataSourceHelper;
import com.kaicom.mymvptest.datasource.gen.DaoMaster;
import com.kaicom.mymvptest.datasource.gen.DaoSession;

import java.io.File;

import de.greenrobot.dao.AbstractDaoSession;

/**
 * Created by jc on 2017/9/1.
 */

public class MyMvpApplication extends KaicomApplication {
    public static MyMvpApplication app;
    public static final String APP_TAG = "MY_MVP_TEST";
    public static final String LOG_PATH = FileUtil.SDPATH + APP_TAG;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    @Override
    protected void clientSetupApp() {

    }

    @Override
    protected void asyncInitData() {

    }


    @Override
    public AbstractDaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return getDaoSession().getDatabase();
    }

    @Override
    public int[] getGuideImgs() {
        return new int[0];
    }

    @Override
    public Class<?> getFirstActivity() {
        return LoginActivity.class;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            String db = "my_mvp_test.db";
            if (BuildConfig.DEBUG)
                db = LOG_PATH +File.separator+ db;
            DaoMaster.OpenHelper helper = new DataSourceHelper(app, db,
                    null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
}
