package com.dataace.extractor.rong36kr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;

public class Rong36KrKeyWordSearchExtractor implements Extractor<Map<String, Object>>{
	
	   private static final Logger logger =  LogManager.getLogger(Rong36KrKeyWordSearchExtractor.class);
	
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


	public List<Map<String, Object>> extract(String content,
			Map<String, Object> extras) {
		//logger.info("content:"+content);
		
		JSONObject jsonObj = new JSONObject(content);
		if(jsonObj.getInt("code")==0){
			List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
			JSONObject data = jsonObj.getJSONObject("data");
			int page = data.getInt("page");
			extras.put("page", page);
			JSONArray results = data.getJSONArray("results");
			for(int i=0;i<results.length();i++){
				JSONObject companyObject = results.getJSONObject(i);
				String brief = companyObject.getString("brief");
				String fullName = companyObject.getString("fullName");
				String id = companyObject.getString("id");
				String logo = companyObject.getString("logo");
				String name = companyObject.getString("name");
				Map<String, Object> map = new HashMap<String, Object>();
//				map.put(MongoCollectionField.ID.getName(), DataSource.RONG_36KR+"_"+id);
//				map.put(MongoCollectionField.FULL_NAME.getName(),fullName);
//				map.put(MongoCollectionField.NAME.getName(), name);
//				map.put(MongoCollectionField.SHORT_DESCRIPTION.getName(), brief);
//				map.put(MongoCollectionField.LOGO_LINK.getName(), logo);
				maps.add(map);
				
			}
			
			return maps;
			
		}
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		if(!"1".equals(extras.get("page").toString())){
			return null;
		}
		JSONObject jsonObj = new JSONObject(content);
		if(jsonObj.getInt("code")==0){
			JSONObject data = jsonObj.getJSONObject("data");
			int companyNum = data.getInt("companyNum");
			int pageSize = data.getJSONArray("results").length();
			int totalPage = companyNum%pageSize==0?companyNum/pageSize:(companyNum/pageSize+1);
			List<Request> requests = new ArrayList<Request>();
			for(int i=2;i<=totalPage;i++){
				Request request = new Request("https://rong.36kr.com/api/search");
				request.setHttpMethod(HttpMethod.POST);
				Map<String,String> params = new HashMap<String,String>();
				params.put("kw", "公司");
				params.put("page", i+"");
				params.put("type", "1");
				request.setParams(params);
				Map<String,String> headers = new HashMap<String,String>();
				headers.put("Host", "rong.36kr.com");
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				request.setHeaders(headers);
				requests.add(request);
			}
			return requests;
			
		}	
		return null;
	}
	
	
}
