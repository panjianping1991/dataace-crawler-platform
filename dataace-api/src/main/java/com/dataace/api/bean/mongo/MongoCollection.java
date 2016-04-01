package com.dataace.api.bean.mongo;

public enum MongoCollection {
	
	DATA_SOURCE("data_source","数据来源表"),
    COMPANY("company_original","公司表");

	private String name;
	private String desc;
	
	MongoCollection(String name,String desc){
		this.name=name;
		this.desc=desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
