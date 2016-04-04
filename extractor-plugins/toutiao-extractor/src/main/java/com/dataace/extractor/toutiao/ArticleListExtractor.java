package com.dataace.extractor.toutiao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.dataace.crawler.persist.bean.Article;
import com.dataace.crawler.util.Params;



public class ArticleListExtractor implements Extractor<Article>{

	private static final Logger logger =  LogManager.getLogger(ArticleListExtractor.class);
	 
	public boolean isFailed(String content, Map<String, Object> extras) {
		try{
			JSONObject jsonObj = new JSONObject(content);
			if("success".equals(jsonObj.getString("message"))){
				return false;
			}
		}catch(Throwable e){
			logger.error("failed to parse the content to json,content="+content);
			return true;
		}
		return true;
	}

	public List<Article> extract(String content, Map<String, Object> extras) {
		JSONObject jsonObj = new JSONObject(content);
		JSONArray jsonArray=jsonObj.getJSONArray("data");
		List<Article> articles = new ArrayList<Article>();
		for(int i=0;i<jsonArray.length();i++){
			JSONObject articleJson = jsonArray.getJSONObject(i);
			Article article = new Article();
			String abst = articleJson.getString("abstract");
			int comments = articleJson.getInt("comment_count");
			int recommends = articleJson.getInt("recommend");
			int impressions = articleJson.getInt("impression_count");
			long createTime = articleJson.getLong("create_time");
			long displayTime = articleJson.getLong("display_time");
			String imageUrl = articleJson.has("image_url")?articleJson.getString("image_url"):null;
			String keywords = articleJson.getString("keywords");
			String title = articleJson.getString("title");
	        String tag = articleJson.getString("tag");
	        String itemUrl = articleJson.getString("item_source_url");
	        String regex = "/item/(\\d+)/";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(itemUrl);
	        if(matcher.find()){
	        	String id = matcher.group(1);
	        	article.setId(id);
	        }else{
	        	logger.error("failed to get the original id,articleJson:"+articleJson);
	        	continue;
	        }
			String originalSource = articleJson.getString("source");
			String articleUrl = articleJson.getString("display_url");
			article.setAbst(abst);
			article.setCommentCount(comments);
			article.setRecommendCount(recommends);
			article.setImpressionCount(impressions);
			article.setArticleCreateTime(createTime*1000);
			article.setArticleDisplayTime(displayTime*1000);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(article.getArticleCreateTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			article.setArticleCreateTimeStr(format.format(c.getTime()));
			c.setTimeInMillis(article.getArticleDisplayTime());
			article.setArticleDisplayTimeStr(format.format(c.getTime()));
			article.setImageUrl(imageUrl);
			article.setKeywords(keywords);
			article.setTitle(title);
			article.setTag(tag);
			article.setOriginalDataSource(originalSource);
			article.setArticleUrl(articleUrl);
			articles.add(article);
		}
		return articles;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		
		JSONObject jsonObj = new JSONObject(content);
		List<Request> requests = new ArrayList<Request>();
		
		//分裂出详情页
		JSONArray jsonArray=jsonObj.getJSONArray("data");
		for(int i=0;i<jsonArray.length();i++){
			JSONObject articleJson = jsonArray.getJSONObject(i);
			String articleUrl = articleJson.getString("display_url");
			 String itemUrl = articleJson.getString("item_source_url");
		        String regex = "/item/(\\d+)/";
		        Pattern pattern = Pattern.compile(regex);
		        Matcher matcher = pattern.matcher(itemUrl);
		        if(matcher.find()){
		        	String id = matcher.group(1);
		        	Request request = new Request(articleUrl); 
		        	request.setExtras(new HashMap<String,Object>());
		        	request.getExtras().put("originalId", id);
					requests.add(request);
		        }
			
		}
		
		//分裂出下一页,下一页的新闻更旧
		JSONObject next=jsonObj.getJSONObject("next");
		Long maxBehotTime = next.getLong("max_behot_time");
		
		Calendar c = Calendar.getInstance();
		long now = c.getTimeInMillis();
		if(now-1000*maxBehotTime>1000*60*60*24){
			maxBehotTime=now/1000;
		}
		c.setTimeInMillis(maxBehotTime*1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("next maxBehotTime:{}",format.format(c.getTime()));
		String orginalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		String regex = "http://toutiao\\.com/api/article/recent/\\?source=2&count=20&category=news_tech&max_behot_time=(\\d+)&utm_source=toutiao&offset=0&[\\s\\S]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(orginalUrl);

		if(matcher.find()){
			String oldMaxBehotTimeStr = matcher.group(1);
			String url = orginalUrl.replace(oldMaxBehotTimeStr, maxBehotTime+"");
			Request request = new Request(url);
			requests.add(request);			
		}
		
		
		
		return requests;
	}

}
