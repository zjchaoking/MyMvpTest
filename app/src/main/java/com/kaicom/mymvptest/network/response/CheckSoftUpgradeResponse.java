package com.kaicom.mymvptest.network.response;

import com.kaicom.mymvptest.entities.CheckSoftUpgradeEntity;

public class CheckSoftUpgradeResponse extends BaseResponse{
	private CheckSoftUpgradeEntity result;

	public CheckSoftUpgradeEntity getResult() {
		return result;
	}

	public void setResult(CheckSoftUpgradeEntity result) {
		this.result = result;
	}
}
