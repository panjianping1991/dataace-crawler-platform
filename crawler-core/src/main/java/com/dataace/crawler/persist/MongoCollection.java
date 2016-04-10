package com.dataace.crawler.persist;

public enum MongoCollection {
	
	COMPANY("company_original","公司信息"),
	ARTICLE("article_original","新闻"),
	INVESTOR("investor","投资人"),
	BLACKNAME("blackname_original","信用黑名单"),
	DATA_SOURCE("data_source","数据来源");
	
	private String name;
	private String description;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	MongoCollection(String name,String description){
		this.name=name;
		this.description=description;
	}

}
