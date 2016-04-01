package com.dataace.crawler.template;

import java.util.List;

public class Processor {
	
	private List<String> urlRegs;
	private String targetClass;
	
	public List<String> getUrlRegs() {
		return urlRegs;
	}
	public void setUrlRegs(List<String> urlRegs) {
		this.urlRegs = urlRegs;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	
	

}
