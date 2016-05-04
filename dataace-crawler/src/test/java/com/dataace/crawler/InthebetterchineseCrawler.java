package com.dataace.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataace.crawler.pipeline.impl.MongoPipeline;
import com.dataace.crawler.template.TemplateConfig;

public class InthebetterchineseCrawler {
private static final Logger logger =  LogManager.getLogger(ToutiaoCrawler.class);
	
	public static void main(String[] args) {
		String workspace = "D:\\dataace\\dataace-crawler-platform";
		CommonCrawler.addPipeline(new MongoPipeline());
		try {	
			TemplateConfig templateConfig = CommonCrawler.registTemplateConfig(workspace+"\\dataace-crawler\\src\\main\\resources\\template\\inthebetterchinese.xml");
			CommonCrawler.publish(templateConfig);
			//CommonCrawler.addRequest(request);
		} catch (Exception e) {
		   logger.error(e.getMessage(), e);
		}
	}
}
