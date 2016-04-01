package com.dataace.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	public static boolean isEmpty(String s){
		if(null==s||"".equals(s.trim())){
			return true;
		}
		return false;
	}
	
	public static String decode(String s,String decoder){
		if(null!=s){
			try {
				return URLDecoder.decode(s,decoder);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

}
