package com.dataace.crawler.download.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
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
import org.apache.http.entity.StringEntity;
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
import com.dataace.crawler.download.Response;
import com.dataace.crawler.util.StringUtil;
import com.dataace.crawler.util.URLUtil;


public class HttpClientDownloader implements Downloader{
	
	private static final Logger logger =  LogManager.getLogger(HttpClientManager.class);
	
	public  Response download(Request request) throws Exception{
		
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
	
		
	public static Response download(HttpRequestBase httpRequest,String encode) throws Exception{
	
		
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
				 throw new Exception("Unable to download url: " + httpRequest.getURI().getPath()+",statusCode:"+statusCode+",msg:"+response.getStatusLine().getReasonPhrase());
			 }else{
				 //String contentType = response.getFirstHeader("Content-Type").toString();
				 //logger.debug("contentType:"+contentType);
				     Response resp = new Response();
				     Map<String,String> responseHeaders = new HashMap<String,String>();
				     resp.setHeaders(responseHeaders);
					 try{
						 Header[] headers =response.getAllHeaders(); 
						 if(null!=headers){
							 for(Header header:headers){
								 responseHeaders.put(header.getName(), header.getValue());
							 }
						 }
						
						 HttpEntity entity = response.getEntity();						 
						 content = EntityUtils.toString(entity,encode);		
						 resp.setBody(content);
						 return resp;
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
		 return null;
		 
	}
	
	
	
	public static void setRequestHeaders(HttpUriRequest httpRequest,Map<String,String> reqHeaders){
		 httpRequest.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
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
		  if(null!=body&&!StringUtil.isEmpty(body)){
			 // StringEntity entity = new StringEntity(body); 
			  ((HttpPost)request).setEntity(new ByteArrayEntity(body.getBytes(encode)));
		  }else if(null!=params){
	    	 List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	       	for(String paramName:params.keySet()){
	    
	    		 nvps.add(new BasicNameValuePair(paramName, params.get(paramName)));	 	 	    
	    	  }
	    
	    	((HttpPost)request).setEntity(new UrlEncodedFormEntity(nvps,encode));
	      }
	
	
	 
}
	
	
	
	public static void main(String[] args) throws Exception {
		
		
		
		String url = "http://www.p2p12580.com/blacklist_search.asp";
		Request request = new Request(url);
		request.setHttpMethod(HttpMethod.POST);
		//request.setBody("id=0");
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("id", "0");
		request.setParams(parameters);
		Map<String,String> headers = new HashMap<String,String>();
		//headers.put("Content-Type","application/x-www-form-urlencoded; charset=GBK");
		request.setHeaders(headers);
		request.setDecode("gbk");
		Response response=DownloaderRoute.getDownloader(request).download(request);
		
		System.out.println(response.getBody());
			 
	}

	
	

}


