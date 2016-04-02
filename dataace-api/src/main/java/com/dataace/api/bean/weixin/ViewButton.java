package com.dataace.api.bean.weixin;

public class ViewButton implements IWeixinButton {

	private String type="view";
	private String name;
	private String url;
	
	public ViewButton(){
		
	}
	public ViewButton(String name,String url){
		this.name=name;
		this.url=url;
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
	public String getUrl() {
		return url;
	}
	
}
