package com.dataace.extractor.rong36kr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;

public class Rong36KrRegionExtractor implements Extractor<Map<String, Object>>{

	 private static final Logger logger =  LogManager.getLogger(Rong36KrRegionExtractor.class);
	 
	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("CITY_DATA")){
			return false;
		}
		return true;
	}

	public List<Map<String, Object>> extract(String content,
			Map<String, Object> extras) {
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		String regex = "window\\.CITY_DATA=([\\s\\S]+)\\,5:\\[f";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if(m.find()){
			String regionJson = m.group(1);
			JSONArray jsonArray = new JSONArray(regionJson);
			Map<String,String> regionMap = new HashMap<String,String>();
			for(int i=0;i<jsonArray.length();i++){
				
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				regionMap.put(jsonObject.get("id").toString(), jsonObject.getString("name"));
				
			}
			extras.put("regionMap", regionMap);
			logger.info("jsonSize:"+jsonArray.length());
			
          List<Request> requests = new ArrayList<Request>();		
		  String urlString = "https://rong.36kr.com/api/dict/industry?type=e";
		  Request request = new Request(urlString);
		  requests.add(request);			
		 return requests;
		}
		return null;
	}

}
