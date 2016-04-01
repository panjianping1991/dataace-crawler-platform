package com.dataace.api.bean.mongo;

public class DataSource {
	
	private String code;
	private String value;
	
	public DataSource(){
		
	}
	
	public DataSource(String code, String value) {
		this.code = code;
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
