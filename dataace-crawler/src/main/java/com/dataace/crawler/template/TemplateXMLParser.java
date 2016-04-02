package com.dataace.crawler.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.util.StringUtil;



public class TemplateXMLParser {
	private static final Logger logger =  LogManager.getLogger(TemplateXMLParser.class);
    
    /**
     * @Description 将xml格式的文件转换成SiteTemplate对象
     * @param xmlFilePath
     * @return
     * @throws IOException
     */
    public static TemplateConfig parseTemplate(String xmlFilePath) throws IOException{
        InputStream in = null;
        //SiteTemplateBuilder templateBuilder = new SiteTemplateBuilder();
//        in = TemplateXMLParser.class.getClassLoader().getResourceAsStream(xmlFilePath);
        in = new FileInputStream(new File(xmlFilePath));
        Document doc = Jsoup.parse(in, "utf-8", "", Parser.xmlParser());
       
        String templateId = doc.select("template > id").text().trim();
        String dataSource = doc.select("template > dataSource").text().trim();
        String maxDeepStr = doc.select("template > maxDeep").text().trim();
        String scheduleIntervalStr = doc.select("template > scheduleInterval").text().trim();

      
        TemplateConfig templateConfig = new TemplateConfig(templateId);
        templateConfig.setDataSource(dataSource);
        List<Processor> processors = parseProcessors(doc);
        List<String> jarPaths = parseJarPaths(doc);
        List<Request> seedRequests = parseSeedRequests(doc);
        templateConfig.setProcessors(processors);
        templateConfig.setJarPaths(jarPaths);
        templateConfig.setSeedRequests(seedRequests);
        if(null!=maxDeepStr&&maxDeepStr.matches("\\d+")){
        	templateConfig.setMaxDeep(Integer.parseInt(maxDeepStr));
        }
        if(null!=scheduleIntervalStr&&scheduleIntervalStr.matches("\\d+")){
        	templateConfig.setScheduleInterval(Integer.parseInt(scheduleIntervalStr));
        }
        in.close();
        return templateConfig;
    }
    
    public static List<String> parseJarPaths(Document doc){
    	List<String> jarPaths = new ArrayList<String>();
    	Elements eles = doc.select("template > jarPaths >jarPath");
    	for(Element ele:eles){
    		jarPaths.add(ele.text().trim());
    	}
    	return jarPaths;
    }
    public static List<Processor> parseProcessors(Document doc){
    	Elements eles = doc.select("template > processors >processor");
    	List<Processor> processors = new ArrayList<Processor>();
    	for(Element ele:eles){
    		Processor processor = new Processor();
    		Elements urlEles = ele.select("regexs>urlRegs>urlReg");
    		List<String> urlRegs = new ArrayList<String>();
    		for(Element urlEle:urlEles){
    			urlRegs.add(urlEle.text().trim());			
    		}
    		String targetClass = ele.select("target > class").first().text().trim();
    		processor.setUrlRegs(urlRegs);
    		processor.setTargetClass(targetClass);
    		processors.add(processor);
    	}
    	return processors;
    }
    
    public static List<Request> parseSeedRequests(Document doc){
    	Elements eles = doc.select("template > seeds >request");
    	 String templateId = doc.select("template > id").text().trim();
    	List<Request> requests = new ArrayList<Request>();
    	for(Element ele:eles){
    		String url = ele.select("url").text().trim();
    		String method = ele.select("httpMethod").text().trim();
    		Map<String,String> headers = new HashMap<String,String>();
    		Elements headerEles = ele.select("httpHeaders > httpHeader");
    		if(null!=headerEles){
    			for(Element headerEle:headerEles){
    				String key=headerEle.select("key").text().trim();
    				String value = headerEle.select("value").text().trim();
    				if(!StringUtil.isEmpty(key)){
    					headers.put(key, value);
    				}				
    			}
    		}
    		
    		Map<String,String> params = new HashMap<String,String>();
    		Elements paramEles = ele.select("parameters > parameter");
    		if(null!=paramEles){
    			for(Element paramEle:paramEles){
    				String key=paramEle.select("key").text().trim();
    				String value = paramEle.select("value").text().trim();
    				if(!StringUtil.isEmpty(key)){
    					params.put(key, value);
    				}				
    			}
    		}
    		
    		Request request = new Request(url);
    		request.setTemplateId(templateId);
    		request.setHttpMethod(HttpMethod.valueOf(method.toUpperCase()));
    		request.setHeaders(headers);
    		request.setParams(params);
    		requests.add(request);
    		
    	}
    	return requests;
    }
    
    public static void main(String[] args) throws IOException {
    
    	TemplateXMLParser.parseTemplate("D:\\peter\\git5\\dataace-crawler\\src\\main\\resources\\template\\rong36kr.xml");
	}
    
  
}
