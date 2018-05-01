package com.kaicom.api.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.kaicom.api.KaicomApplication;

import java.util.Date;

/**
 * 平台的配置
 * @author scj
 *
 */
public final class PlatformPreference {

    private static final String FILE_NAME = "platform_preference";
    private SharedPreferences sp;

    private PlatformPreference() {
        sp = KaicomApplication.app.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    private static class PlatformPreferenceHolder {
        private static PlatformPreference filePreference = new PlatformPreference();
    }

    public static PlatformPreference getInstance() {
        return PlatformPreferenceHolder.filePreference;
    }

    /**
     * 是否崩溃过<p>
     * <li>崩溃后会app将其值改成true
     * <li>上传掉崩溃日志后改成false
     * @param isCrash
     */
    public void setCrash(boolean isCrash) {
        sp.edit().putBoolean("crash", isCrash).commit();
    }
    
    /**
     * 用于判断是否需要上传崩溃日志
     * @return 
     */
    public boolean hasCrash() {
        return sp.getBoolean("crash", false);
    }
    

    /** 设置日志自动清理天数 */
    public void setLogAutoClearDays(int days) {
        sp.edit().putInt("log_auto_clear", days).commit();
    }
    
    /** 获得日志自动清理天数 */
    public int getLogAutoClearDays() {
        return sp.getInt("log_auto_clear", 10);
    }
    
    
    /** 设置crash文件自动清理天数 */
    public void setCrashAutoClearDays(int days) {
    	sp.edit().putInt("crash_auto_clear", days).commit();
    }
    
    /** 获得crash自动清理天数 */
    public int getCrashAutoClearDays() {
        return sp.getInt("crash_auto_clear", 30);
    }
    
    
    /** app是否第一次启动 */
    public void setFirstLaunch(boolean isFirst) {
        sp.edit().putBoolean("first_launch", isFirst).commit();
    }
    
    /** 第一次启动为true */
    public boolean getFirstLaunch() {
        return sp.getBoolean("first_launch", true);
    }
    
    
    /** 设置机器唯一码 */
    public void setMachineCode(String code) {
    	sp.edit().putString("machine_code", code).commit();
    }
    
    /** 获得机器唯一码 */
    public String getMachineCode() {
    	return sp.getString("machine_code", "111111111111111");
    }
    
    /**
     * 保存最近崩溃时间(long值)
     * @param date
     */
    public void setLastCrashTime(long date) {
    	sp.edit().putLong("last_crash_time", date).commit();
    }
    
    /**
     * 获得最近崩溃时间
     * @return
     */
    public long getLastCrashTime() {
    	return sp.getLong("last_crash_time", 0);
    }
    
    /**
     * 设置最后同步时间
     * @param time 时间戳
     */
	public void setLastSynTime(String time) {
		sp.edit().putString("last_syn_Time", time).commit();
	}

	/**
	 * 获取最后同步时间
	 * @return 时间戳
	 */
	public String getLastSynTime() {
		return sp.getString("last_syn_Time", new Date().getTime() + "");
	}
    
    /**
     * 清除配置
     */
    public void clearConfig() {
        String machineCode = getMachineCode();
    	sp.edit().clear().commit();
        setFirstLaunch(false);
        setMachineCode(machineCode);
    }

}
