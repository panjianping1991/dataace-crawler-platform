package com.dataace.extractor.renren;

import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.BlackName;


public class BlackDetailExtractor implements Extractor<BlackName>{
	private static final Logger logger =  LogManager.getLogger(BlackDetailExtractor.class);
	public boolean isFailed(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		logger.info(content);
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		return null;
	}

}
