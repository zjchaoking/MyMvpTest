package com.kaicom.api.bluetooth;

import com.kaicom.api.view.toast.ToastTools;

public abstract class DiscoveryActionListener implements DiscoveryListener {
	@Override
	public void onStartDiscovery() {
		ToastTools.showToast("开始搜索蓝牙设备");
	}

	@Override
	public void onFinishDiscovery(int size) {
		ToastTools.showToast("蓝牙设备搜索完毕");
	}
}