package com.dataace.crawler.persist.bean;

import com.dataace.crawler.annotation.MongoBean;
import com.dataace.crawler.persist.MongoCollection;

@MongoBean(collection = MongoCollection.ARTICLE)
public class Article {

	private String id;
	private String abst;
	private int coummentCount;
	private int recommendCount;
	private long articleCreateTime;
	private long articleDisplayTime;
	private String articleCreateTimeStr;
	private String articleDisplayTimeStr;

	private String imageUrl;
	private int impressionCount;
	private String keywords;
	private String title;
	private String dataSource;
	private String articleUrl;
	private String content;
	private String tag;
	
	private String originalDataSource;
	private String orginalUrl;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbst() {
		return abst;
	}
	public void setAbst(String abst) {
		this.abst = abst;
	}
	public int getCoummentCount() {
		return coummentCount;
	}
	public void setCoummentCount(int coummentCount) {
		this.coummentCount = coummentCount;
	}

	
	public int getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}
	public long getArticleCreateTime() {
		return articleCreateTime;
	}
	public void setArticleCreateTime(long articleCreateTime) {
		this.articleCreateTime = articleCreateTime;
	}
	public long getArticleDisplayTime() {
		return articleDisplayTime;
	}
	public void setArticleDisplayTime(long articleDisplayTime) {
		this.articleDisplayTime = articleDisplayTime;
	}
	
	
	public String getArticleCreateTimeStr() {
		return articleCreateTimeStr;
	}
	public void setArticleCreateTimeStr(String articleCreateTimeStr) {
		this.articleCreateTimeStr = articleCreateTimeStr;
	}
	public String getArticleDisplayTimeStr() {
		return articleDisplayTimeStr;
	}
	public void setArticleDisplayTimeStr(String articleDisplayTimeStr) {
		this.articleDisplayTimeStr = articleDisplayTimeStr;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getImpressionCount() {
		return impressionCount;
	}
	public void setImpressionCount(int impressionCount) {
		this.impressionCount = impressionCount;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOriginalDataSource() {
		return originalDataSource;
	}
	public void setOriginalDataSource(String originalDataSource) {
		this.originalDataSource = originalDataSource;
	}
	public String getOrginalUrl() {
		return orginalUrl;
	}
	public void setOrginalUrl(String orginalUrl) {
		this.orginalUrl = orginalUrl;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
	
}
