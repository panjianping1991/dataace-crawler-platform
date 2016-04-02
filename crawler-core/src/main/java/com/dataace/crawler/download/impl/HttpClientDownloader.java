package com.dataace.crawler.download.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataace.crawler.download.Downloader;
import com.dataace.crawler.download.DownloaderRoute;
import com.dataace.crawler.download.HttpClientManager;
import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.util.URLUtil;


public class HttpClientDownloader implements Downloader{
	
	private static final Logger logger =  LogManager.getLogger(HttpClientManager.class);
	
	public  String download(Request request) throws Exception{
		
		logger.info("【Request】:{}",request);
		HttpRequestBase httpRequest = null;
		if(HttpMethod.POST==request.getHttpMethod()){
			httpRequest = new HttpPost(request.getUrl());
			setRequestHeaders(httpRequest, request.getHeaders());
			setRequestBody(httpRequest, request.getParams(),request.getBody(),request.getDecode());
		}else{
			 httpRequest = new HttpGet(request.getUrl());	
			 setRequestHeaders(httpRequest, request.getHeaders());
		}
	    
        return download(httpRequest,request.getDecode());		 
	}
	
		
	public static String download(HttpRequestBase httpRequest,String encode) throws Exception{
	
		
         String content=null;
		 CloseableHttpClient httpClient = HttpClientManager.getHttpClient();
		 
		 
		 CloseableHttpResponse response = null;
		 try{
			   RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();			   			
			  httpRequest.setConfig(requestConfigBuilder.build());
			 			
			 response = httpClient.execute(httpRequest);	
			 int statusCode = response.getStatusLine().getStatusCode();
			 while(statusCode/100==3){
				 String newUrlString=response.getFirstHeader("Location").getValue();
				 newUrlString = URLUtil.getAbsoluteUrl(newUrlString, httpRequest.getURI().getPath());
				 logger.info("【Redirect】:【"+httpRequest.getRequestLine()+"】->【"+newUrlString+"】");
				 if(null!=response){
					 response.close();
				 }
				 response = httpClient.execute(httpRequest);
				
				 statusCode = response.getStatusLine().getStatusCode();					
			 }
			 logger.info("【Response Code】:【"+httpRequest.getRequestLine()+"】【"+statusCode+"】");
			 if(statusCode!=200){
				 if(null!=response){
					 response.close();				 
				 }
				 httpRequest.releaseConnection();
				 throw new Exception("Unable to download url: " + httpRequest.getURI().getPath()+",statusCode:"+statusCode);
			 }else{
				 //String contentType = response.getFirstHeader("Content-Type").toString();
				 //logger.debug("contentType:"+contentType);
				 
					 try{
						 HttpEntity entity = response.getEntity();						 
						 content = EntityUtils.toString(entity,encode);						 						 
					 }catch(Exception e){
						 throw new Exception("unable to download url:"+httpRequest.getURI().getPath(),e);
					 }finally{
						 if(null!=response){
							 response.close();
						 }
					 }
			 }
		 }catch(Exception e){
			 logger.error(e.getMessage(),e);
			 if(null!=response){
				 response.close();
			 }
		 }
		 return content;
		 
	}
	
	
	
	public static void setRequestHeaders(HttpUriRequest httpRequest,Map<String,String> reqHeaders){
		 if(null!=reqHeaders&&null!=reqHeaders.keySet()){
			 for(String key:reqHeaders.keySet()){
				 String value=reqHeaders.get(key);
				 if(null!=value){
					 logger.debug("set reqHeader, name:"+key+",value:"+value);
					 httpRequest.addHeader(key, value);
				 }
			 }
		 }
	 }
	 
	 private static void setRequestBody(HttpRequest request,Map<String,String> params,String body,String encode) throws UnsupportedEncodingException{
		  if(null!=body){
			  HttpEntity entity = new ByteArrayEntity(body.getBytes(encode)); 
			  ((HttpPost)request).setEntity(entity);
		  }else if(null!=params){
	    	 List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	       	for(String paramName:params.keySet()){
	    
	    		 nvps.add(new BasicNameValuePair(paramName, params.get(paramName)));	 	 	    
	    	  }
	    
	    	((HttpPost)request).setEntity(new UrlEncodedFormEntity(nvps,encode));
	      }
	
	
	 
}
	
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		String url = "http://www.baidu.com";
		String content=DownloaderRoute.getDownloader(new Request(url)).download(new Request(url));
		
		System.out.println(content);
			 
	}

	
	

}


