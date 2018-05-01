package com.kaicom.mymvptest.files;

import java.io.File;

import static com.kaicom.mymvptest.MyMvpApplication.LOG_PATH;

/**
 * Created by jc on 2017/9/4.
 */

public class AppConfigs {
    /**
     * 运动轨迹记录文件的路径常量
     */
    public static final String RECORD_PATH = "RECORD_PATH";
    /**
     * 运动轨迹记录文件的记录时长
     */
    public static final String RECORD_INTERVAL_TIME = "RECORD_INTERVAL_TIME";

    /**
     * 定位权限请求码
     */
    public static final int LOCATION_PERMISSION_CODE = 100;
    /**
     * SD卡读写权限请求码
     */
    public static final int STORAGE_PERMISSION_CODE = 101;
    /**
     * 保存运动轨迹记录文件夹
     */
    public static final String TRACE_RECORD_PATH = LOG_PATH + File.separator+"TraceRecords";
}
