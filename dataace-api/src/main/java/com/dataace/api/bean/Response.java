package com.dataace.api.bean;

public class Response {
	
	private int status;
	private String currentRequestUrl;
	private String fieldDescriptionUrl;
	private String errorMsg;
	
	
	private Object data;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
	
	public String getFieldDescriptionUrl() {
		return fieldDescriptionUrl;
	}

	public void setFieldDescriptionUrl(String fieldDescriptionUrl) {
		this.fieldDescriptionUrl = fieldDescriptionUrl;
	}
	
	public String getCurrentRequestUrl() {
		return currentRequestUrl;
	}

	public void setCurrentRequestUrl(String currentRequestUrl) {
		this.currentRequestUrl = currentRequestUrl;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
