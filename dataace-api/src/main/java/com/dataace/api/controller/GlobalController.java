package com.dataace.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataace.api.AppConfig;

@Controller

public class GlobalController {
	
	private final Logger logger = LoggerFactory.getLogger(GlobalController.class);
    @Resource
    private AppConfig appConfig;
    
    
	 @RequestMapping(value="/404",method = RequestMethod.GET)
	 @ResponseBody
	 public Map<String,String> fieldDescription(){
		      Map<String,String> map = new HashMap<String,String>();
		      map.put("errorMsg", "无效请求");
		      map.put("api-document", "http://"+appConfig.getHost()+"/dataace-api/document/");		        
		      return map;
     }
	 
	 
	 @RequestMapping(value="/document",method = RequestMethod.GET)
	 public String document(Model model,ServletResponse response){	
		 logger.info("content-type:"+response.getContentType()+",charactorEncoding:"+response.getCharacterEncoding());
		
		      model.addAttribute("host", appConfig.getHost());
		      return "document";
     }

}
