package com.dataace.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataace.api.AppConfig;
import com.dataace.api.bean.ErrorResponse;
import com.dataace.api.bean.MultipleRecordResponse;
import com.dataace.api.bean.Response;
import com.dataace.api.service.IArticleService;
import com.dataace.api.util.StringUtils;
import com.dataace.crawler.persist.bean.Article;



@Controller
@RequestMapping(value = "/insking")
public class InskingController {

	private final Logger logger = LoggerFactory.getLogger(InskingController.class);
	
	
	   @Resource
       private IArticleService articleService;
	   
	   
	    @Resource
	    private AppConfig appConfig;
	 
	    @RequestMapping(value="/test",method = RequestMethod.GET)
		public String gettest(Model model,HttpServletRequest request) {
	    	 
	    	 
			return "test";
	    	
	    }
	    
	    @RequestMapping(value="/list",method = RequestMethod.GET)
		@ResponseBody
		public Response list(HttpServletRequest request, HttpServletResponse response) {	
		  	
			String currentRequestUrl = request.getRequestURL().toString();
			Map<String,String> parameterMap = new HashMap<String,String>();
			if(null!=request.getQueryString()&&!"".equals(request.getQueryString())){
				currentRequestUrl=currentRequestUrl+"?"+request.getQueryString();
				String[] parameters = request.getQueryString().split("&");
				
				for(String parameter:parameters){				
					 String[] kv = parameter.split("=");
					 parameterMap.put(kv[0], StringUtils.decode(kv[1], appConfig.getUrlDecoder()));
				}
			}
			

			logger.info("currentRequestUrl:"+currentRequestUrl);
			String dataSource  = parameterMap.get("data_source");
	 		String pageNoStr =parameterMap.get("page_no");
	 		
	 		int  pageSize = 20;
			int pageNo=1;
			if(null!=pageNoStr){
				if(pageNoStr.matches("\\d{1,100000}")){
					pageNo = Integer.parseInt(pageNoStr);
				}else{
					return new ErrorResponse(currentRequestUrl, "invalid page_no,page_no should be an integer value from 1 to 100000");
				}
			}
			Map<String, Object> criterias = new HashMap<String,Object>();
			criterias.put("dataSource", dataSource);
		
			
			long totalRecords = articleService.count(criterias);
			//long maxPageNo = totalRecords%pageSize==0?totalRecords/pageSize:totalRecords/pageSize+1;
			
			List<Article> articles = articleService.list(criterias,pageSize,pageNo);
			MultipleRecordResponse result = new MultipleRecordResponse();
			result.setCurrentRequestUrl(currentRequestUrl);
			result.setTotalNum(totalRecords);
			result.setPageNo(pageNo);
			result.setData(articles);
			result.setReturnNum(articles.size());
			result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/"+appConfig.getProject()+"/article/field-description");
			return result;
	    	
	    }
	    
	    
	    @RequestMapping(value="/table",method = RequestMethod.GET)
		public String gettable(Model model,HttpServletRequest request,HttpServletResponse response) {
	    	logger.info("content-type:"+response.getContentType()+",charactorEncoding:"+response.getCharacterEncoding());
	    	model.addAttribute("host", appConfig.getHost());
	    	
			return "table";
	    	
	    }
	    
	    @RequestMapping(value="/sroll",method = RequestMethod.GET)
		public String getSroll(Model model,HttpServletRequest request,HttpServletResponse response) {
	    	logger.info("content-type:"+response.getContentType()+",charactorEncoding:"+response.getCharacterEncoding());
	    	model.addAttribute("host", appConfig.getHost());
	    	
			return "sroll";
	    	
	    }
	    
	    @RequestMapping(value="/shixin",method = RequestMethod.GET)
		public String getshixin(Model model,HttpServletRequest request,HttpServletResponse response) {
	    	
	    	
			return "shixin";
	    	
	    }
	    
	
}
