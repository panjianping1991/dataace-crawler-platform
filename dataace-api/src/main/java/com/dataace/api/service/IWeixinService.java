package com.dataace.api.service;

import com.dataace.api.bean.weixin.WeixinMenu;

public interface IWeixinService {
	
	public void createMenu() throws Exception;
	
	public WeixinMenu getMenu();
	
	public boolean deleteMenu() throws Exception;

}
