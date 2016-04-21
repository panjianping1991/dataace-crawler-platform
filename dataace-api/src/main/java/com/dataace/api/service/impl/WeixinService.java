package com.dataace.api.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dataace.api.bean.Constants;
import com.dataace.api.bean.weixin.IWeixinButton;
import com.dataace.api.bean.weixin.ViewButton;
import com.dataace.api.bean.weixin.WeixinMenu;
import com.dataace.api.service.IWeixinService;
import com.dataace.crawler.download.Downloader;
import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.download.impl.HttpClientDownloader;

public class WeixinService implements IWeixinService{
	
	private final Logger logger = LoggerFactory.getLogger(WeixinService.class);

	@Override
	public void createMenu() throws Exception {
		IWeixinButton ctrdButton = new ViewButton("征信查询","http://www.dataace.cn/dataace-api/blacklist/weixin");
		IWeixinButton gstsButton = new ViewButton("征信录入","http://www.dataace.cn/dataace-api/company/weixin");
		IWeixinButton hyfxButton = new ViewButton("行业分析","http://www.dataace.cn/dataace-api/industry/weixin");
		
		WeixinMenu weixinMenu = new WeixinMenu();
		List<IWeixinButton> buttons = new ArrayList<IWeixinButton>(); 
		buttons.add(ctrdButton);
		buttons.add(gstsButton);
		buttons.add(hyfxButton);
		weixinMenu.setButton(buttons);
		
		JSONObject menuJson=new JSONObject(weixinMenu);
		String accessToken = getAccessToken();
		Request request = new Request(Constants.CREATE_MENU_URL+accessToken);
		request.setHttpMethod(HttpMethod.POST);
		request.setBody(menuJson.toString());
		logger.info("menuJson:"+menuJson);
		Downloader downloader = new HttpClientDownloader();
		String response = downloader.download(request);
		logger.info("response:{}"+response);
		
	}

	@Override
	public WeixinMenu getMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String getAccessToken() throws Exception{
		Downloader downloader = new HttpClientDownloader();
		Request request = new Request(Constants.GET_ACCESS_TOKEN_URL);
		String token=null;
		String content = downloader.download(request);
		token = new JSONObject(content).getString("access_token");
		return token;
		
	}
	public static void main(String[] args) throws Exception {
		WeixinService weixinService = new WeixinService();
		//weixinService.deleteMenu();
		weixinService.createMenu();
		
	}

	@Override
	public boolean deleteMenu() throws Exception {
		String accessToken = getAccessToken();
		Request request = new Request(Constants.DELETE_MENU_URL+accessToken);
		request.setHttpMethod(HttpMethod.GET);
		
		Downloader downloader = new HttpClientDownloader();
		String response = downloader.download(request);
		logger.info("response:{}"+response);
		JSONObject responseJson=new JSONObject(response);
		String status=responseJson.get("errcode").toString();
		logger.info("status:"+status);
		if("0".equals(status)){
			logger.info("delete menu successful!");
			return true;
		}
		return false;
	}

}
