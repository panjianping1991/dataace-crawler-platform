package com.dataace.api.bean;

public class ErrorResponse extends Response{

	public ErrorResponse(String currentRequestUrl,String errorMsg){
		this.setCurrentRequestUrl(currentRequestUrl);
		this.setStatus(1);
		this.setErrorMsg(errorMsg);
		
	}
}
