package org.thebetterchinese.extractor;
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
import com.dataace.crawler.persist.bean.Gender;
import com.dataace.crawler.persist.bean.Overdue;
import com.dataace.crawler.util.Params;
import com.dataace.crawler.util.URLUtil;



public class Thebetterchinese implements Extractor<BlackName> {

	private static final Logger logger =  LogManager.getLogger(Thebetterchinese.class);
	
	
	public boolean isFailed(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		if(null!=content&&content.contains("Blacklist/Lai.mvc")){
			return false;
		}
		return true;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		// TODO Auto-generated method stub
		Document doc = Jsoup.parse(content);
		Elements eles = doc.select("a.xiang_xix");
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		List<Request> requests = new ArrayList<Request>();
		for(int i=0;i<eles.size();i++){
			String href = eles.get(i).attr("href");
			//String urlRegex = ".*(/blacklistdetail/\\w+)[^\\w].*";
			// /Blacklist/Laidetails.mvc?laiId=181
			String urlRegex = "/Blacklist/Laidetails.mvc?laiId=\\d+";
			
			Pattern pattern = Pattern.compile(urlRegex);
			Matcher matcher = pattern.matcher(href);
			//logger.info("onclick:"+onclick);
			if(matcher.find()){
				String url = matcher.group(1);
				try {
					
					url = URLUtil.getAbsoluteUrl(url, originalUrl);
					Request request = new Request(url);
					Map<String,String> headers = new HashMap<String,String>();
					headers.put("Host", "http://www.thebetterchinese.com/");
					headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
					request.setHeaders(headers);
					requests.add(request);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//logger.info("url:"+url);
			}
			
			
		}
		if(!originalUrl.contains("pageNo")){
			Element lastEle = doc.select("span.info").first();
			String pageRegex = "共(\\d+)页";
			Pattern pattern = Pattern.compile(pageRegex);
			Matcher matcher = pattern.matcher(lastEle.text().trim());
			if(matcher.find()){
				int totalPage = Integer.parseInt(matcher.group(1));
				for(int i=2;i<=totalPage;i++){
					 String listUrl = "http://www.thebetterchinese.com/Blacklist/Lai.mvc?pageNo="+i+"&pageSize=10";
					Request request = new Request(listUrl);
				        	Map<String,String> headers = new HashMap<String,String>();
					headers.put("Host", "http://www.thebetterchinese.com/");
					headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
					request.setHeaders(headers);
					requests.add(request);
				}
			}
			
		}
		
		return requests;
	}

}
