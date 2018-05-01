/**
 * 
 */
package com.kaicom.api.manager;

import com.kaicom.api.preference.PlatformPreference;
import com.kaicom.api.util.DateUtil;

/**
 * 保存最后的一次的服务器时间，进行本地机器时间校验合法性<br>
 * 默认统一为时间阀值（0~30天）
 * @author wxf
 *
 */
public class LocalTimeCheckManager {

	private static LocalTimeCheckManager mTimeCheckManager;
	
	private PlatformPreference mFerence;
	
	/**
	 * 
	 */
	public LocalTimeCheckManager() {
		mFerence = PlatformPreference.getInstance();
	}
	
	/**
	 * 获取对象实例
	 * @return
	 */
	public static LocalTimeCheckManager getInstance(){
		if(null == mTimeCheckManager){
			mTimeCheckManager = new LocalTimeCheckManager();
		}
		
		return mTimeCheckManager;
	}
	
	/**
	 * 设置最后同步时间
     * @param time 时间戳
	 *
	 */
	public void setTime(String time){
		mFerence.setLastSynTime(time);
	}
	
	/**
	 * 获取最后一次保存在本地的时间
	 * @return 时间戳
	 */
	public String getTime(){
		return mFerence.getLastSynTime();
	}
	
	/**
	 * 时间合法性校验（0~30天以内）<br>
	 * 本地时间和最后一次同步时间比较
	 * @return
	 */
	public boolean check(){
		boolean ret = true;
		try{
			String lastTime = DateUtil.getDateTimeFromMillis(Long.parseLong(mFerence.getLastSynTime()));
			String lastDate = lastTime.split(" ")[0];
			
			long days = DateUtil.getIntervalDays(DateUtil.dateFormat(lastDate, "yyyy-MM-dd"), DateUtil.getToday());
			
			if(0 > days){
				ret = false;
			}else if(29 < days){
				ret = false;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
	}

	/**
	 * 时间合法性校验（0~30天以内）<br>
	 * 本地时间和最后一次同步时间比较
	 * @param date （yyyy-MM-dd）本地日期，或者是即将要手动设置的日期
	 * @return
	 */
	public boolean check(String date){
		boolean ret = true;
		try{
			String lastTime = DateUtil.getDateTimeFromMillis(Long.parseLong(mFerence.getLastSynTime()));
			String lastDate = lastTime.split(" ")[0];
			
			long days = DateUtil.getIntervalDays(DateUtil.dateFormat(lastDate, "yyyy-MM-dd"), date);
			
			if(0 > days){
				ret = false;
			}else if(29 < days){
				ret = false;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	
}
