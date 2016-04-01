package com.dataace.crawler.util;

public class StringUtil {
	
	public static boolean isEmpty(String s){
		if(null==s||"".equals(s.trim())){
			return true;
		}
		return false;
	}

}
