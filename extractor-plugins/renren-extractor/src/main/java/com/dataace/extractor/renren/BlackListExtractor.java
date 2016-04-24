package com.dataace.extractor.renren;

import java.util.ArrayList;
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


public class BlackListExtractor implements Extractor<BlackName>{
	private static final Logger logger =  LogManager.getLogger(BlackListExtractor.class);
	public boolean isFailed(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		Elements users = doc.select("a.user_header");
		List<Request> requests = new ArrayList<Request>();
		for(Element user:users){
			String href= user.attr("href");
			String idRegex = "[\\s\\S]+searchresult_\\d+&id=(\\d+)&q[\\s\\S]+";
			Pattern pattern = Pattern.compile(idRegex);
			Matcher matcher = pattern.matcher(href);
			if(matcher.find()){
				String id = matcher.group(1);
				String userInfoDetail = "http://www.renren.com/"+id+"/profile?v=info_timeline";
				Request request = new Request(userInfoDetail);
				requests.add(request);
			}
			logger.info(href);
		}
		return requests;
	}

}
