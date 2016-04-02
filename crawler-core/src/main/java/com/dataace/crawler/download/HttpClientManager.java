package com.dataace.crawler.download;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClientManager {
	private static   CloseableHttpClient httpclient = null;
	
	private static final Logger logger =  LogManager.getLogger(HttpClientManager.class);
		static{
			 SSLContext context=null;
				try {
					context = SSLContext.getInstance("TLS");
					context.init(null, new X509TrustManager[]{new X509TrustManager(){
	                public void checkClientTrusted(X509Certificate[] chain,
	                                String authType) throws CertificateException {}
	                public void checkServerTrusted(X509Certificate[] chain,
	                                String authType) throws CertificateException {}
	                public X509Certificate[] getAcceptedIssuers() {
	                        return new X509Certificate[0];
	                }}}, new SecureRandom());

					

				} catch (Exception e) {
					logger.error("failed to decorete the connection with https",e);
				}
			     Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
			                .register("http", PlainConnectionSocketFactory.INSTANCE)		                
			                .register("https", new SSLConnectionSocketFactory(context))
			                .build();
			 PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);

		     cm.setMaxTotal(100);
		     cm.setDefaultMaxPerRoute(100);
		   
		     cm.setMaxPerRoute(new HttpRoute(new HttpHost("www.baidu.com")), 10);
		      RequestConfig
		      requestConfig = RequestConfig.custom()
		      .setSocketTimeout(20000)
		      .setConnectTimeout(20000)
		      .setConnectionRequestTimeout(30000)
		      .build();//设置请求和传输超时时
		      httpclient = HttpClients.custom()
		    		  .setDefaultRequestConfig(requestConfig)
		    	 
//		    		  .setRedirectStrategy(new RedirectStrategy(){
	//
//				@Override
//				public boolean isRedirected(HttpRequest request,
//						HttpResponse response, HttpContext context)
//						throws ProtocolException {
//					
//					return false;
//				}
	//
//				@Override
//				public HttpUriRequest getRedirect(HttpRequest request,
//						HttpResponse response, HttpContext context)
//						throws ProtocolException {
//					// TODO Auto-generated method stub
//					return null;
//				}
//		    	  
//		      })
		                .setConnectionManager(cm)
		                .build();
		      
		      
		      
		  
		 }
		
		public static CloseableHttpClient getHttpClient(){
			 return httpclient;
		 }
		 
}
