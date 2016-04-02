package com.dataace.api.bean.weixin;

public class Media {

	private byte[] data;
	private String name;
	
	public Media(byte[] data,String name){
		this.data=data;
		this.name=name;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
