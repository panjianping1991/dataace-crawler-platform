package com.dataace.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataace.api.AppConfig;
import com.dataace.api.bean.MultipleRecordResponse;
import com.dataace.api.bean.Response;
import com.dataace.api.bean.mongo.DataSource;
import com.dataace.api.service.IDataSourceService;


@Controller
@RequestMapping(value = "/data-source")
public class DataSourceContoller {
	
private final Logger logger = LoggerFactory.getLogger(DataSourceContoller.class);
	
    @Resource
    private IDataSourceService dataSourceService;
    
    @Resource
    private AppConfig appConfig;


    @RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public Response list(HttpServletRequest request){
    	List<DataSource> dataSources = dataSourceService.list(null, appConfig.getDefaultPageSize(), 1);
    	String currentRequestUrl = request.getRequestURL().toString();
    	MultipleRecordResponse result = new MultipleRecordResponse();
		result.setCurrentRequestUrl(currentRequestUrl);
		result.setTotalNum(dataSources.size());
		result.setPageNo(1);
		result.setData(dataSources);
		result.setReturnNum(dataSources.size());
		result.setFieldDescriptionUrl("http://"+appConfig.getHost()+"/webant-api/data-source/field-description");
		return result;
    }
    
    
    @RequestMapping(value="/field-description",method = RequestMethod.GET)
	@ResponseBody
	 public Map<String,String> fieldDescription(){
	        Map<String,String> map = new HashMap<String,String>();
	        map.put("code", "数据来源编码");
	        map.put("value", "数据来源描述");
	        
	        return map;
	  }
}
