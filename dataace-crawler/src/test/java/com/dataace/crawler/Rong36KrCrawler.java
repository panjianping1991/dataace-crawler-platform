package com.dataace.crawler;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.pipeline.impl.MongoPipeline;
import com.dataace.crawler.template.TemplateConfig;

public class Rong36KrCrawler {
	
	private static final Logger logger =  LogManager.getLogger(Rong36KrCrawler.class);
	
	public static void main(String[] args) {
		String workspace = "E:\\pan\\git\\dataace-crawler-platform";
		CommonCrawler.addPipeline(new MongoPipeline());
		try {
			Request request = new Request("https://rong.36kr.com/api/search");
			request.setHttpMethod(HttpMethod.POST);
			Map<String,String> params = new HashMap<String,String>();
			params.put("kw", "公司");
			params.put("page", "1");
			params.put("type", "1");
			request.setParams(params);
			Map<String,String> headers = new HashMap<String,String>();
			//headers.put("Referer", "https://rong.36kr.com/search?query=%E5%85%AC%E5%8F%B8&type=1&page=1");
			//headers.put("Cookie", "aliyungf_tc=AQAAAG+h7njxjQ4Azohoyma0HVfiATXA; kr_stat_uuid=xk25T24296187; gr_user_id=2b99c0ef-a564-4ee2-b462-b220fcb7d9c5; gr_session_id_76d36bd044527820a1787b198651e2f1=306e8742-7342-47f0-a289-dbe4e5c1b887; Hm_lvt_e8ec47088ed7458ec32cde3617b23ee3=1457771245; Hm_lpvt_e8ec47088ed7458ec32cde3617b23ee3=1457771245; XSRF-TOKEN=eyJpdiI6Imcyc0F6amhSakZiK2twRERReFFDUUE9PSIsInZhbHVlIjoiQnY4RUNZRXhwUVR1Z3Z5YjBuXC83Y1ZnUG5ERWlQMGRuVlgxc214dUtaT2dXOXVFaUdyOUswUFJWckk5U05OUTV0Qko3XC9Vd2RqVEx3QVk4ZnQ0eXg1UT09IiwibWFjIjoiMWQ3YTZjZjQ3NjZkMWVkODY4OTI1NTdhZTkwNmJmMzgyYmE3MjFlN2MzMTgwYWE4M2RlYzZiZTk3MDU3MGVjYiJ9; krchoasss=eyJpdiI6Ikswa1NlU0o3WDE5RllBcUd3TXhXQUE9PSIsInZhbHVlIjoiNDlNSXZ3cFQ2YnkzWE1ZMGNMTEhEQTVxaDEwc0JIYjFhK1BaTmZ0dnNhc2t2UzkrU2ZXdG1uT3VrVnNlOVhlTmhKUHowMUI0bWhLSEd2blgyWGJpMUE9PSIsIm1hYyI6IjlmODA4ZDQ3NGQ5YjkxNzRhYTMxNWUzYTM0Mzg1OWVlOGI3MDZjN2M4NzE1MGJjNWFkOGFhM2ZkZTFmODg0MjIifQ%3D%3D");
			//headers.put("Content-Length", "35");
			headers.put("Host", "rong.36kr.com");
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			request.setHeaders(headers);
			
			//request = new Request("https://rong.36kr.com/api/dict/industry?type=e");
			//request.setTemplateId("rong36kr");
			//request = new Request("https://krplus-cdn.b0.upaiyun.com/krplus/scripts/8ad19359.default.js");
			TemplateConfig templateConfig = CommonCrawler.registTemplateConfig(workspace+"\\dataace-crawler\\src\\main\\resources\\template\\rong36kr.xml");
			CommonCrawler.publish(templateConfig);
			//CommonCrawler.addRequest(request);
		} catch (Exception e) {
		   logger.error(e.getMessage(), e);
		}
	}

}
