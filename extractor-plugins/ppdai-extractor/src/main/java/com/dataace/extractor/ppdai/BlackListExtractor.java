package com.dataace.extractor.ppdai;

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
import org.jsoup.select.Elements;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.BlackName;
import com.dataace.crawler.util.Params;
import com.dataace.crawler.util.URLUtil;

public class BlackListExtractor implements Extractor<BlackName>{
	
	private static final Logger logger =  LogManager.getLogger(BlackListExtractor.class);

	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("black_table")){
			return false;
		}
		return true;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		Elements eles = doc.select(".black_table >tbody >tr >td > input[value=查看详情]");
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		List<Request> requests = new ArrayList<Request>();
		for(int i=0;i<eles.size();i++){
			String onclick = eles.get(i).attr("onclick");
			String urlRegex = ".*(/blacklistdetail/\\w+)[^\\w].*";
			Pattern pattern = Pattern.compile(urlRegex);
			Matcher matcher = pattern.matcher(onclick);
			logger.info("onclick:"+onclick);
			if(matcher.find()){
				String url = matcher.group(1);
				try {
					
					url = URLUtil.getAbsoluteUrl(url, originalUrl);
					Request request = new Request(url);
					Map<String,String> headers = new HashMap<String,String>();
					headers.put("Host", "www.ppdai.com");
					headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
					request.setHeaders(headers);
					requests.add(request);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("url:"+url);
			}
			
			
		}
		return requests;
	}

}
