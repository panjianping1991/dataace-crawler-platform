package com.dataace.crawler.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.json.JSONObject;

import com.dataace.crawler.persist.bean.Article;
import com.dataace.crawler.persist.bean.Article.Content;
import com.dataace.crawler.persist.bean.Article.Content.Section;
import com.dataace.crawler.persist.bean.Company;
import com.dataace.crawler.persist.bean.Founder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class CollectionUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> toMap(Object data) {
		Gson gson = new Gson();
	
		return JsonToMap.toMap(gson.toJson(data));
			  //return gson.fromJson(gson.toJson(data), new TypeToken<Map<String,Object>>(){}.getType());
//			JSONObject jsonObj = new JSONObject(data);
//			for(String key:JSONObject.getNames(jsonObj)){
//				result.put(key, jsonObj.get(key)==null?null:jsonObj.get(key).toString());			
//			}
//			
//		   return result;
	}
	
	public static void main(String[] args) {
		Company company = new Company();
		company.setId("RONG_36KR_"+1);
		company.setFullName("A");
		
		
        company.setDataSource("RONG_36KR");
       
	    	List<Founder> founders = new ArrayList<Founder>();
	    
	    		Founder founder = new Founder();

	    		founder.setId("found1");
	    		founder.setName("1");
	    		
	    	    founders.add(founder);
	    	
	    	company.setFounders(founders);
	    	
	    	//System.out.println(toMap(company));
	    	
	    	Article article = new Article();
	    	article.setId("TOUTIAO_12314324");
	    	article.setImpressionCount(1);
	    	Content content = article.new Content();
	    	List<Section> sections = new ArrayList<Section>();
	    	Section section = content.new Section();
	    	section.setContent("sdfsdf");
	    	sections.add(section);
	    	content.setSections(sections);
	    	article.setContent(content);
	    	Map<String,Object> map = toMap(article);
	    	for(String key:map.keySet()){
	    		System.out.println(key+":"+map.get(key).getClass());
	    		
	    	}
	    	System.out.println(map);
	    	System.out.println(map.get("id"));
	    	
	    }
	   
	 
	


}
