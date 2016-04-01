package com.dataace.api.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dataace.api.AppConfig;
import com.dataace.api.bean.ErrorResponse;
import com.dataace.api.bean.MultipleRecordResponse;
import com.dataace.api.bean.Response;
import com.dataace.api.bean.mongo.Company;
import com.dataace.api.service.ICompanyService;
import com.dataace.api.util.StringUtils;


@Controller
@RequestMapping(value = "/company")
public class CompanyController {
	
	private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
    @Resource
    private ICompanyService companyService;
    
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
		Company company = companyService.getById(id);
		Response result = new Response();
		result.setData(company);
		result.setCurrentRequestUrl(currentRequestUrl);
		result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/"+appConfig.getProject()+"/company/field-description");
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
		String cityName = parameterMap.get("city");
		String districtName = parameterMap.get("district");
		String cellName = parameterMap.get("company_name");
 		String pageNoStr =parameterMap.get("page_no");
 		
 		int  pageSize = appConfig.getCellPageSize();
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
		criterias.put("cityName", cityName);
		criterias.put("districtName", districtName);
		criterias.put("name", cellName);
		
		long totalRecords = companyService.count(criterias);
		//long maxPageNo = totalRecords%pageSize==0?totalRecords/pageSize:totalRecords/pageSize+1;
		
		List<Company> companys = companyService.list(criterias,pageSize,pageNo);
		MultipleRecordResponse result = new MultipleRecordResponse();
		result.setCurrentRequestUrl(currentRequestUrl);
		result.setTotalNum(totalRecords);
		result.setPageNo(pageNo);
		result.setData(companys);
		result.setReturnNum(companys.size());
		result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/"+appConfig.getProject()+"/company/field-description");
		return result;
    	
    }
	
	
	 @RequestMapping(value="/field-description",method = RequestMethod.GET)
	 @ResponseBody
	 public Map<String,String> fieldDescription(){
	        Map<String,String> map = new HashMap<String,String>();
	        map.put("id", "小区ID,每条小区记录的唯一标识");
	        map.put("dataSource", "数据来源");
	        map.put("cityName", "城市名");
	        map.put("districtName", "区域名");	       
	        map.put("areaName", "片区名");
	        map.put("cellName", "小区名");
	        map.put("address", "小区地址");
	        map.put("longitude ", "小区经度");
	        map.put("latitude ", "小区纬度");
	        map.put("propertyCompany", "物业公司");
	        map.put("usedType", "建筑用途");
	        map.put("developerCompany", "开发商");
	        map.put("secondHouseNum", "小区挂盘量");
	        
	        
	        return map;
	  }
	 
	 @RequestMapping("/test")
	 public ModelAndView test(){
	        ModelAndView modelandview=new ModelAndView("HelloWorld");
	        modelandview.addObject("message","Hi Surendra");
	        return modelandview;
	  }
	
}
