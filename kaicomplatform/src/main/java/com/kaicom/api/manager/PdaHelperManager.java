package com.kaicom.api.manager;


import com.kaicom.pdahelper.PdaHelperExternal;
import com.kaicom.pdahelper.exception.HelperException;

/**
 * 巴枪助手数据管理
 * 
 * @author hb
 * 
 */
public class PdaHelperManager implements PdaHelperExternal {

	/**
	 * 网点编号
	 */
	@Override
	public String getNetCode() throws HelperException {
		return "000000";
	}

	/**
	 * 已发数据个数
	 */
	@Override
	public int statisticsSentCount() throws HelperException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 未发数据个数
	 */
	@Override
	public int statisticsUnsentCount() throws HelperException {
		return 0;
	}

}
