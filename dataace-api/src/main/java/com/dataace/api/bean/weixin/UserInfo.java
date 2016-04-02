package com.dataace.api.bean.weixin;

public class UserInfo {

	private String subscribe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 
	private String openId;//用户的标识，对当前公众号唯一 
	private String nickName;//用户的昵称 
	private String sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 
	private String city;//用户所在城市 
	private String country;//用户所在国家 
	private String procince;//用户所在省份
	private String language;//用户的语言，简体中文为zh_CN 
	private String headImgUrl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 
	private Long subscribeTime;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProcince() {
		return procince;
	}
	public void setProcince(String procince) {
		this.procince = procince;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Long getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(Long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	
	
}
