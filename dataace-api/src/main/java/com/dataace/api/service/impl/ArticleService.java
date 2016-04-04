package com.dataace.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.dataace.api.service.IArticleService;
import com.dataace.crawler.persist.MongoCollection;
import com.dataace.crawler.persist.bean.Article;
import com.dataace.crawler.persist.bean.Article.Content;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Service
public class ArticleService implements IArticleService{

	private final Logger logger = LoggerFactory.getLogger(ArticleService.class);
	
    @Resource
    private MongoTemplate mongoTemplate;
    
	@Override
	public List<Article> list(Map<String, Object> criterias, int pageSize,
			int pageNo) {
		Query query = new Query();
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	 
		query.addCriteria(Criteria.where("content").exists(true));
		query.with(new Sort(Direction.DESC, "articleDisplayTime"));
		query.limit(pageSize);
		
		query.skip(pageSize*(pageNo-1)<0?0:pageSize*(pageNo-1));
		final List<Article> articles = new ArrayList<Article>();
		 mongoTemplate.executeQuery(query, MongoCollection.ARTICLE.getName(), new DocumentCallbackHandler(){

			@Override
			public void processDocument(DBObject dbObject)
					throws MongoException, DataAccessException {
				Article article = new Article();	
				buildArticle(dbObject, article);
				articles.add(article);
				
			}
			 
		 });
		return articles;
	}

	@Override
	public Article getById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		final Article article = new Article();
		mongoTemplate.executeQuery(query, com.dataace.crawler.persist.MongoCollection.ARTICLE.getName(), new DocumentCallbackHandler(){

				@Override
				public void processDocument(DBObject dbObject)
						throws MongoException, DataAccessException {
					buildArticle(dbObject, article);									
				}
				 
			 });
		return article.getId()==null?null:article;
	}

	@Override
	public long count(Map<String, Object> criterias) {
		Query query = new Query();
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	  
		return mongoTemplate.count(query, MongoCollection.ARTICLE.getName());
	}
	
	public void buildArticle(DBObject dbObject,Article article){
		String id = dbObject.get("_id").toString();
		String keywords = dbObject.get("keywords")==null?null:dbObject.get("keywords").toString();
		String imageUrl = dbObject.get("imageUrl")==null?null:dbObject.get("imageUrl").toString();
		String tag = dbObject.get("tag")==null?null:dbObject.get("tag").toString();
		String displayTime = dbObject.get("articleDisplayTimeStr")==null?null:dbObject.get("articleDisplayTimeStr").toString();
		String title = dbObject.get("title")==null?null:dbObject.get("title").toString();
		String abst = dbObject.get("abst")==null?null:dbObject.get("abst").toString();
		String content = dbObject.get("content")==null?null:dbObject.get("content").toString();
		if(null!=content){
			content=content.replace("\\\\", "");
			Gson gson = new Gson();
			article.setContent(gson.fromJson(content, Content.class));
			article.setArticleUrl("details/weixin/"+id);
		}
		Integer recommendCount =dbObject.get("recommendCount")==null?null:Integer.parseInt(dbObject.get("recommendCount").toString().trim());
		Integer commentCount  = dbObject.get("commentCount")==null?null:Integer.parseInt(dbObject.get("commentCount").toString().trim());
		Integer impressionCount  = dbObject.get("impressionCount")==null?null:Integer.parseInt(dbObject.get("impressionCount").toString().trim());
		String articleUrl = dbObject.get("articleUrl")==null?null:dbObject.get("articleUrl").toString();
		String originalDataSource = dbObject.get("originalDataSource")==null?null:dbObject.get("originalDataSource").toString();
		
		article.setId(id);
		article.setKeywords(keywords);
		article.setArticleDisplayTimeStr(displayTime);
		if(null==article.getArticleUrl()){
			article.setArticleUrl(articleUrl);
		}
		
		article.setAbst(abst);
		article.setTag(tag);
		article.setTitle(title);
		article.setImageUrl(imageUrl);
		article.setRecommendCount(recommendCount);
		article.setCommentCount(commentCount);
		article.setContentStr(new JSONObject(content).toString());
		article.setImpressionCount(impressionCount);
		article.setOriginalDataSource(originalDataSource);
		
	}

}
