package com.dataace.crawler.download;

import java.util.Map;

public class Request {
	
	private String templateId;
	private int level = 0;
	private String url;
	private Map<String,String> params;
	private Map<String,String> headers;
	private HttpMethod httpMethod = HttpMethod.GET;
	private String decode="utf-8";
	private int retryTimes=0;
	private Map<String,Object> extras;
	private String body;
	
	
	
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Request(String url){
		this.url = url;
	}	
	
	public String getTemplateId() {
		return templateId;
	}


	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getDecode() {
		return decode;
	}

	public void setDecode(String decode) {
		this.decode = decode;
	}

	public Map<String, Object> getExtras() {
		return extras;
	}

	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}

	@Override
	public String toString() {
		return "Request [templateId=" + templateId + ", level=" + level
				+ ", url=" + url + ", httpMethod=" + httpMethod + ", decode="
				+ decode + ", retryTimes=" + retryTimes + "]";
	}
	
	

}
