package com.dataace.api.bean.mongo;

import java.util.List;

public class Company {
	
	private String id;
	private String dataSource;
	private String cityName;
	private String districtName;
	private String companyName;
	private String fullName;
	private String webLink;
	private String shortDescription;
	private String weixin;
	private String industry;
	private String financePhase;
	private String logoLink;
	
	private List<Founder> founders;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getWebLink() {
		return webLink;
	}
	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getFinancePhase() {
		return financePhase;
	}
	public void setFinancePhase(String financePhase) {
		this.financePhase = financePhase;
	}
	public String getLogoLink() {
		return logoLink;
	}
	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}
	public List<Founder> getFounders() {
		return founders;
	}
	public void setFounders(List<Founder> founders) {
		this.founders = founders;
	}
	
	
	
}
