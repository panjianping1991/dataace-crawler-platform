package com.dataace.crawler.persist.bean;

import java.util.List;

import com.dataace.crawler.annotation.MongoBean;
import com.dataace.crawler.persist.MongoCollection;

@MongoBean(collection = MongoCollection.BLACKNAME)
public class BlackName {
	
	private String id;
	private String idCard;
	private String name;
	private String qq;
	private String email;
	
	private Gender gender;
	private String mobile;
	private String address;
	
	private String updateTime;
	private Uploader uploader;
	
	List<Overdue> overdues;
	
	
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<Overdue> getOverdues() {
		return overdues;
	}
	public void setOverdues(List<Overdue> overdues) {
		this.overdues = overdues;
	}
	public Uploader getUploader() {
		return uploader;
	}
	public void setUploader(Uploader uploader) {
		this.uploader = uploader;
	}
	
	
	
}
