package com.dataace.crawler.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.json.JSONObject;

import com.dataace.crawler.persist.bean.Company;
import com.dataace.crawler.persist.bean.Founder;

public class CollectionUtil {
	
	public static Map<String,Object> toMap(Object data) {
			Map<String,Object> result = new HashMap<String,Object>();
			JSONObject jsonObj = new JSONObject(data);
			for(String key:JSONObject.getNames(jsonObj)){
				result.put(key, jsonObj.get(key));			
			}
		   return result;
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
	    	
	    	System.out.println(toMap(company));
	    }
	   
	 
	


}
