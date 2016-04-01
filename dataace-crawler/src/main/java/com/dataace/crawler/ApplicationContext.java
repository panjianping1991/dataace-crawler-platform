package com.dataace.crawler;



import com.dataace.crawler.template.TemplateConfigHolder;

public class ApplicationContext {
	
	private static ApplicationContext applicationContext;
	private TemplateConfigHolder templateConfigHolder = new TemplateConfigHolder();
	
	public static ApplicationContext getInstance(){
		if(null==applicationContext){
			synchronized(ApplicationContext.class){
				if(null==applicationContext){
					applicationContext = new ApplicationContext();
				}
			}
		}
		return applicationContext;
	}
	


	public TemplateConfigHolder getTemplateConfigHolder() {
		return templateConfigHolder;
	}
	
	
	
	

}
