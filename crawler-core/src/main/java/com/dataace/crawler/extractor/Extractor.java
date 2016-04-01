package com.dataace.crawler.extractor;

import java.util.List;
import java.util.Map;

import com.dataace.crawler.download.Request;

public interface Extractor<E> {

	public boolean isFailed(String content,Map<String,Object> extras);
	
	public List<E> extract(String content,Map<String,Object> extras);
	
	public List<Request> split(String content,Map<String,Object> extras);

}
