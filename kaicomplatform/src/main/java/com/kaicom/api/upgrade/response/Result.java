package com.kaicom.api.upgrade.response;

public class Result<D> {
	private D data;
	
	
	public Result(D data)
	{
		this.data = data;
	}
	
	public D getData() {
		return data;
	}
}
