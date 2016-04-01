package com.dataace.extractor.rong36kr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;



import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.Company;


public class Rong36KrCompanyIndustryExtractor implements Extractor<Company>{
	
    private static final Logger logger =  LogManager.getLogger(Rong36KrCompanyIndustryExtractor.class);
	
	public boolean isFailed(String content,Map<String,Object> extras){
		try{
			JSONObject jsonObj = new JSONObject(content);
			if(0!=jsonObj.getInt("code")){
				return true;
			}
		}catch(Throwable e){
			logger.error("failed to parse the content to json,content="+content);
			return true;
		}
		return false;
	}


	public List<Company> extract(String content,
			Map<String, Object> extras) {
		logger.info("content:"+content);
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {

		JSONObject jsonObj = new JSONObject(content);
		if(jsonObj.getInt("code")==0){
			List<Request> requests = new ArrayList<Request>();
			
			JSONArray industries= jsonObj.getJSONObject("data").getJSONArray("data");
			String[] fincephases = {"ANGEL","PRE_A","A","A_PLUS","B","B_PLUS","C","D","IPO"};
			for(int i=0;i<industries.length();i++){
				JSONObject industry = industries.getJSONObject(i);
				int industryId = industry.getInt("id");
				String industryValue = industry.getString("value");
				for(String fincephase:fincephases){
					String urlString = "https://rong.36kr.com/api/company?fincephase="+fincephase+"&fincestatus=0&industry="+industryValue+"&page=1&type=";
							Request request = new Request(urlString);
							Map<String,String> headers = new HashMap<String,String>();
							headers.put("Host", "rong.36kr.com");
							headers.put("Content-Type", "application/x-www-form-urlencoded");
							request.setHeaders(headers);
							requests.add(request);
				}
				
			}
			
			return requests;
			
		}	
		return null;
	}
		
	

}
