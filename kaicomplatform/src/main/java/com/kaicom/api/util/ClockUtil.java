package com.kaicom.api.util;

import android.os.SystemClock;
import android.util.Log;

/**
 * <h3>计时器工具类</h3>
 * 
 * <p>主要用于计算用时
 * 
 * @author scj
 */
public final class ClockUtil {

    private static long currentTime;

    private ClockUtil() {
        throw new RuntimeException("╮(╯▽╰)╭");
    }

    /**
     * 开始计时<p>
     */
    public static void startTimer() {
        currentTime = SystemClock.elapsedRealtime();
    }
    
    /**
     * 计算从开始计时到此方法调用时所用的时间<br>
     * 默认tag为Clock
     * @return 时间long值
     */
    public static long calcUseTime() {
        return calcUseTime("Clock");
    }

    /**
     * <p>计算从开始计时到此方法调用时所用的时间
     * 该方法会调用Log.d打印所用时间
     * @param tag Log.d
     * @return useTime 所用时间
     */
    public static long calcUseTime(String tag) {
        if (tag == null || tag.length() == 0)
            tag = "Clock";
        long useTime = SystemClock.elapsedRealtime() - currentTime;
        Log.d(tag, "所用时间:" + useTime);
        return useTime;
    }
    
    /**
     * 休眠time毫秒
     * @param time
     */
    public static void sleep(long time) {
        SystemClock.sleep(time);
    }

}
