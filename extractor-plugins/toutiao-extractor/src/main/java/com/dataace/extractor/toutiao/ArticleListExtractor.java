package com.dataace.extractor.toutiao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
			article.setCoummentCount(comments);
			article.setRecommendCount(recommends);
			article.setImpressionCount(impressions);
			article.setArticleCreateTime(createTime*1000);
			article.setArticleDisplayTime(displayTime*1000);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(article.getArticleCreateTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
		// TODO Auto-generated method stub
		return null;
	}

}
