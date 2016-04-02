package com.dataace.crawler.template;

import java.util.List;

import com.dataace.crawler.download.Request;

public class TemplateConfig {
	
	private String templateId;
	
	/**
	 *失败后立即重试的最大次数 
	 */
	private int immediatelyMaxRetryTimes=1;
	
	/**
	 * 可以重复加入失败队列的最大次数
	 */
	private int failureQueueMaxRetryTimes=3;
	
	private int maxDeep=100;
	
	private int scheduleInterval=-1;
	
	private int initDelay = 0;
	
	private List<String> jarPaths;
	
	private List<Processor> processors;
	
	private List<Request> seedRequests;
	
	private String dataSource;
	
	
	
	public String getDataSource() {
		return dataSource;
	}


	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}


	
	public int getMaxDeep() {
		return maxDeep;
	}


	public void setMaxDeep(int maxDeep) {
		this.maxDeep = maxDeep;
	}

	

	public int getInitDelay() {
		return initDelay;
	}


	public void setInitDelay(int initDelay) {
		this.initDelay = initDelay;
	}


	public List<Request> getSeedRequests() {
		return seedRequests;
	}


	public void setSeedRequests(List<Request> seedRequests) {
		this.seedRequests = seedRequests;
	}


	public TemplateConfig(String templateId){
		this.templateId = templateId;
	}
	

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getImmediatelyMaxRetryTimes() {
		return immediatelyMaxRetryTimes;
	}

	public void setImmediatelyMaxRetryTimes(int immediatelyMaxRetryTimes) {
		this.immediatelyMaxRetryTimes = immediatelyMaxRetryTimes;
	}

	public int getFailureQueueMaxRetryTimes() {
		return failureQueueMaxRetryTimes;
	}

	public void setFailureQueueMaxRetryTimes(int failureQueueMaxRetryTimes) {
		this.failureQueueMaxRetryTimes = failureQueueMaxRetryTimes;
	}

	

	public int getScheduleInterval() {
		return scheduleInterval;
	}


	public void setScheduleInterval(int scheduleInterval) {
		this.scheduleInterval = scheduleInterval;
	}


	public List<String> getJarPaths() {
		return jarPaths;
	}


	public void setJarPaths(List<String> jarPaths) {
		this.jarPaths = jarPaths;
	}


	public List<Processor> getProcessors() {
		return processors;
	}


	public void setProcessors(List<Processor> processors) {
		this.processors = processors;
	}
	
	
	
	

}
