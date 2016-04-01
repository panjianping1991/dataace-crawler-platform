package com.dataace.crawler.extractor;

import java.util.List;

import com.dataace.crawler.ApplicationContext;
import com.dataace.crawler.download.Request;
//import com.dataace.crawler.extractor.impl.Rong36KrCompanyIndustryExtractor;
//import com.dataace.crawler.extractor.impl.Rong36KrCompanyListExtractor;
//import com.dataace.crawler.extractor.impl.Rong36KrKeyWordSearchExtractor;
//import com.dataace.crawler.extractor.impl.Rong36KrRegionExtractor;
import com.dataace.crawler.extractor.impl.SimpleExtractor;
import com.dataace.crawler.plugin.PluginManager;
import com.dataace.crawler.template.Processor;
import com.dataace.crawler.template.TemplateConfig;

public class ExtractorRoute {
	
	public static Extractor<?> getExtractor(Request request){
//		if(request.getUrl().equals("https://rong.36kr.com/api/dict/industry?type=e")){
//			return new Rong36KrCompanyIndustryExtractor();
//		}else if(request.getUrl().matches("[\\s\\S]*36kr.com/api/company[\\s\\S]*")){
//			return new Rong36KrCompanyListExtractor();
//		}else if(request.getUrl().matches("[\\s\\S]*36kr.com[\\s\\S]*")){
//			return new Rong36KrKeyWordSearchExtractor();
//		}else if(request.getUrl().equals("https://krplus-cdn.b0.upaiyun.com/krplus/scripts/8ad19359.default.js")){
//			//return new Rong36KrRegionExtractor();
//		}
		TemplateConfig templateConfig = ApplicationContext.getInstance().getTemplateConfigHolder().getTemplateConfig(request);
		List<Processor> processors = templateConfig.getProcessors();
		String requestUrl = request.getUrl();
		for(Processor processor:processors){
			List<String> urlRegs = processor.getUrlRegs();
			for(String urlReg:urlRegs){
				if(requestUrl.matches(urlReg)){
					Extractor<?> extractor = (Extractor<?>) PluginManager.getInstance().getClazzInstance(processor.getTargetClass());
				    return extractor;
				}
			}
		}
		
		return new SimpleExtractor();
	}

}
