package com.dataace.extractor.rong36kr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.Company;
import com.dataace.crawler.persist.bean.Founder;
import com.dataace.crawler.util.Params;

public class Rong36KrCompanyListExtractor implements Extractor<Company>{
	
	private static final Logger logger =  LogManager.getLogger(Rong36KrCompanyListExtractor.class);	
	public boolean isFailed(String content,Map<String,Object> extras){
		try{
			JSONObject jsonObj = new JSONObject(content);
			if(0!=jsonObj.getInt("code")){
				return true;
			}
		}catch(Throwable e){
			logger.error("failed to parse the content to json,content="+content);
			return true;
		}
		return false;
	}


	public List<Company> extract(String content,
			Map<String, Object> extras) {
		logger.info("content:"+content);
		
		JSONObject jsonObj = new JSONObject(content);
		
		if(jsonObj.getInt("code")==0){
			List<Company> companys = new ArrayList<Company>();
			//List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
			JSONObject pageObj = jsonObj.getJSONObject("data").getJSONObject("page");
			int page = pageObj.getInt("page");
			extras.put("page", page);
			JSONArray results = pageObj.getJSONArray("data");
			@SuppressWarnings("unchecked")
			Map<String,String> regionMap = (Map<String, String>) extras.get("regionMap");
			for(int i=0;i<results.length();i++){
				JSONObject companyObject = results.getJSONObject(i).getJSONObject("company");
				String province = null;
				String city = null;
				
				
				if(!companyObject.isNull("address1")){
					province=regionMap.get(companyObject.get("address1").toString());
					if(null!=province&&province.endsWith("市")){
						city=province;
					}
				}						
				String district = null;
				if(!companyObject.isNull("address2")){
					if(null==city){
						city = regionMap.get(companyObject.get("address2").toString());
					}else{
						district= regionMap.get(companyObject.get("address2").toString());
					}
					
				}
				if(!companyObject.isNull("address3")){
					if(district==null){
						district = regionMap.get(companyObject.get("address3").toString());
					}
				
				}
				String financePhase=companyObject.getString("financePhase");
				String industry = companyObject.getString("industry");
				String intro = companyObject.getString("intro");
				String webLink = companyObject.getString("webLink");
				String weixin = companyObject.getString("weixin");
				JSONArray foundersJson = results.getJSONObject(i).getJSONArray("founder");
				//String founderId = founder.get("id").toString();
				//String founderName = founder.getString("name");
				String brief = companyObject.getString("brief");
				String fullName = companyObject.getString("fullName");
				String id = companyObject.get("id").toString();
				String logo = companyObject.getString("logo");
				String name = companyObject.getString("name");
				
				Company company = new Company();
				company.setId(id);
				company.setFullName(fullName);
				company.setCompanyName(name);
                company.setCityName(city);
                company.setDistrictName(district);
                company.setIndustry(industry);
                company.setFinancePhase(financePhase);
                company.setLogoLink(logo);
                company.setShortDescription(brief);
                company.setWebLink(webLink);
                company.setWeixin(weixin);
                

			    if(null!=foundersJson){
			    	List<Founder> founders = new ArrayList<Founder>();
			    	for(int j=0;j<foundersJson.length();j++){
			    		Founder founder = new Founder();
			    		JSONObject founderJson = foundersJson.getJSONObject(j);
			    		founder.setId(founderJson.get("id")==null?null:founderJson.get("id").toString());
			    		founder.setName(founderJson.get("name")==null?null:founderJson.get("name").toString());
			    		
			    	    founders.add(founder);
			    	}
			    	company.setFounders(founders);
			    }
			   
				companys.add(company);
				
			}
			
			return companys;
			
		}
		return null;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		if(!"1".equals(extras.get("page").toString())){
			return null;
		}
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		JSONObject jsonObj = new JSONObject(content);
		if(jsonObj.getInt("code")==0){
			JSONObject pageObj = jsonObj.getJSONObject("data").getJSONObject("page");
			int totalPage = pageObj.getInt("totalPages"); 
			List<Request> requests = new ArrayList<Request>();
			for(int i=2;i<=totalPage;i++){
				String urlString = originalUrl.replaceAll("page=\\d+&type=", "page="+i+"&type=");
				Request request = new Request(urlString);
				Map<String,String> headers = new HashMap<String,String>();
				headers.put("Host", "rong.36kr.com");
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				request.setHeaders(headers);
				requests.add(request);
			}
			return requests;
			
		}	
		return null;
	}
	
	
}
