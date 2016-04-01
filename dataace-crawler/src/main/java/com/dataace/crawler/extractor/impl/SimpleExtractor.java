package com.dataace.crawler.extractor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.MongoCollectionField;

public class SimpleExtractor implements Extractor<Map<String,Object>>{

	public List<Map<String,Object>> extract(String content,Map<String, Object> extras) {	
		  List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		  Map<String,Object> result = new HashMap<String,Object>();
		  result.put(MongoCollectionField.ID.getName(), "1");

		  data.add(result);
		  return data;
	}

	

	public List<Request> split(String content, Map<String, Object> extras) {
		return null;
	}



	public boolean isFailed(String content, Map<String, Object> extras) {
		return false;
	}

}
