package com.dataace.api.bean.weixin;

import java.util.List;

public class WeixinSubButton implements IWeixinButton {

	private String name;
	private List<IWeixinButton> sub_button;
	
	public WeixinSubButton(){
		
	}
	public WeixinSubButton(String name,List<IWeixinButton> subButton){
		this.name=name;
		this.sub_button=subButton;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<IWeixinButton> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<IWeixinButton> sub_button) {
		this.sub_button = sub_button;
	}
	
	

}
