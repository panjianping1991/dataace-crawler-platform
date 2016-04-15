package com.dataace.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.dataace.api.service.IBlackNameService;
import com.dataace.api.util.StringUtils;
import com.dataace.crawler.persist.bean.BlackName;

@Controller
@RequestMapping(value = "/blacklist")
public class BlackNameController {

private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
    @Resource
    private IBlackNameService blackNameService;
    
    @Resource
    private AppConfig appConfig;

    
	
	@RequestMapping(value="/details/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Response getById(@PathVariable String id,HttpServletRequest request) {
		String currentRequestUrl = request.getRequestURL().toString();
		if(null!=request.getQueryString()&&!"".equals(request.getQueryString())){
			currentRequestUrl=currentRequestUrl+"?"+request.getQueryString();
		}
		if(StringUtils.isEmpty(id)){
			return new ErrorResponse(currentRequestUrl, "company id could not be empty!");
		}
		BlackName blackName = blackNameService.getById(id);
		Response result = new Response();
		result.setData(blackName);
		result.setCurrentRequestUrl(currentRequestUrl);
		result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/"+appConfig.getProject()+"/blacklist/field-description");
		return result;
    	
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
 		String keywords = parameterMap.get("keywords");
 	
 		
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
		if(null!=keywords){
			keywords = keywords.trim();
			  if(keywords.matches("\\d{11}")){
			    	criterias.put("mobile",keywords);
		 		}else if(keywords.matches("\\d{17}(\\d|X)")){
			    	criterias.put("idCard",keywords);
		 		}else if(keywords.matches("\\S{2,4}")){
			    	criterias.put("name",keywords);
		 		}
		}
	  
		
		long totalRecords = blackNameService.count(criterias);
		//long maxPageNo = totalRecords%pageSize==0?totalRecords/pageSize:totalRecords/pageSize+1;
		
		List<BlackName> blackNames = blackNameService.list(criterias,pageSize,pageNo);
		MultipleRecordResponse result = new MultipleRecordResponse();
		result.setCurrentRequestUrl(currentRequestUrl);
		result.setTotalNum(totalRecords);
		result.setPageNo(pageNo);
		result.setData(blackNames);
		result.setReturnNum(blackNames.size());
		result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/"+appConfig.getProject()+"/blacklist/field-description");
		return result;
    	
    }
	
	 @RequestMapping(value="/weixin",method = RequestMethod.GET)
	 public String document(Model model,ServletResponse response){	
		 logger.info("content-type:"+response.getContentType()+",charactorEncoding:"+response.getCharacterEncoding());
		
		      model.addAttribute("host", appConfig.getHost());
		      return "blacklist";
     }
	
}
