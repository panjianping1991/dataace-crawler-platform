package com.dataace.extractor.p2p12580;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.BlackName;
import com.dataace.crawler.util.Params;


public class BlackListExtractor implements Extractor<BlackName>{

	private static final Logger logger =  LogManager.getLogger(BlackListExtractor.class);
	
	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("blacklist_search")){
			return false;
		}
		return true;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		Elements trs =doc.select("span.title2").get(0).parent().parent().parent().parent().select("tr");
		for(int i=1;i<trs.size()-2;i++){
			Element tr = trs.get(i);
			if(tr.html().contains("dotted")){
				BlackName blackName = new BlackName();
				Element ele = tr.nextElementSibling();
				logger.info("#######"+ele.text());
				
			}
		}
		
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		List<Request> requests = new ArrayList<Request>();
		if(!originalUrl.contains("page=")){
			Element lastEle = doc.select("select[name=sel_page]").last().parent().select("a").get(4);
			String pageRegex = "blacklist_search.asp\\?page=(\\d+)&id=0&strKeyWord=";
			Pattern pattern = Pattern.compile(pageRegex);
			Matcher matcher = pattern.matcher(lastEle.attr("href").trim());
			if(matcher.find()){
				int totalPage = Integer.parseInt(matcher.group(1));
				for(int i=2;i<=totalPage;i++){
					String listUrl = "http://www.p2p12580.com/blacklist_search.asp?page="+i+"&id=0&strKeyWord=";
					Request request = new Request(listUrl);
					
					requests.add(request);
				}
			}
			
		}
		
		return requests;
	}

}
