package com.dataace.extractor.toutiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.Article;
import com.dataace.crawler.persist.bean.Article.Content;
import com.dataace.crawler.persist.bean.Article.Content.Section;
import com.dataace.crawler.util.Params;

public class ArticleDetailExtractor implements Extractor<Article>{

	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("article-detail")){
			return false;
		}
		return true;
	}

	public List<Article> extract(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		
		
		
			Elements eles = doc.select(".article-content > div > p");
			
			if(null!=eles&&eles.size()>0){
				List<Article> articles = new ArrayList<Article>();
				Article article = new Article();
				article.setId(extras.get("originalId").toString());
				Content articleContent = article.new Content();
				List<Section> sections = new ArrayList<Section>();
				for(Element ele:eles){
					Section section = articleContent.new Section();
					section.setContent( ele.html());
					sections.add(section);
				}
				articleContent.setSections(sections);
				article.setContent(articleContent);
				articles.add(article) ;
				return articles;
			}
	
		
		
		
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		return null;
	}

}
