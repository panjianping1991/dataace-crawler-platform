package com.dataace.api.bean;

public class Constants {

	
	public static final String APP_ID="wxd021df5496a7720d";
	public static final String APP_SECRET="35d70224fae5c122169cc762bdc15694";
	public static final String TOKEN="dataace";
	

	public static final String GET_ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+APP_SECRET;
	public static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	public static final String GET_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	public static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
	public static final String SEND_CUSTOMER_SREVICE_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	public static final String DOWNLOAD_MEDIA_URL="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	public static final String GET_USER_INFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	
	
	
	public static final String OPEN_ID="gh_7e4ffe71cb72";
	public static final String HOST="http://120.25.213.61/";


	
	
	
	
	
}
