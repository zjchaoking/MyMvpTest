package com.kaicom.api.bluetooth;

public class BluetoothException extends Exception {

	private static final long serialVersionUID = 151355554652114L;

	public BluetoothException() {
		super();
	}

	public BluetoothException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public BluetoothException(String detailMessage) {
		super(detailMessage);
	}

	public BluetoothException(Throwable throwable) {
		super(throwable);
	}

}
