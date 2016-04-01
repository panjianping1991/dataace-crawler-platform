package com.dataace.crawler.template;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dataace.crawler.download.Request;

public class TemplateConfigHolder {
	
	private Map<String,TemplateConfig> templateConfigMap = new HashMap<String,TemplateConfig>();
	
	private static final String DEFAULT_TEMPLATE_KEY="0000";
	
	public void addTemplateConfig(TemplateConfig templateConfig){
		templateConfigMap.put(templateConfig.getTemplateId(), templateConfig);
	}
	
	public TemplateConfig getTemplateConfig(Request request){
		if(null==request.getTemplateId()){
			return new TemplateConfig(DEFAULT_TEMPLATE_KEY);
		}
		TemplateConfig templateConfig = templateConfigMap.get(request.getTemplateId());
		if(null==templateConfig){
			templateConfig = new TemplateConfig(DEFAULT_TEMPLATE_KEY);
		}
		return templateConfig;
	}
	
	public Set<String> getAllJarPaths(){
		Set<String> paths = new HashSet<String>();
		for(TemplateConfig templateConfig:templateConfigMap.values()){
			paths.addAll(templateConfig.getJarPaths());
		}
		return paths;
	}

}
