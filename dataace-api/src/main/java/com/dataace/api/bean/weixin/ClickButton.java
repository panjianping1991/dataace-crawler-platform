package com.dataace.api.bean.weixin;

public class ClickButton implements IWeixinButton{

	private String type="click";
	private String name;
	private String key;
	
	public ClickButton(){
		
	}
	public ClickButton(String name,String key){
		this.name=name;
		this.key=key;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	
	
}
