package com.dataace.crawler;

import java.util.HashMap;
import java.util.Map;

import com.dataace.crawler.download.DownloaderRoute;
import com.dataace.crawler.download.HttpMethod;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.download.Response;

public class RenrenDownloadTest {
	
	public static void main(String[] args) throws Exception {

		String url = "http://browse.renren.com/sAjax.do?ref_search=&q=%E6%AD%A6%E6%B1%89%E7%A7%91%E6%8A%80%E5%A4%A7%E5%AD%A6&p=%5B%7B%22t%22%3A%22univ%22%2C%22name%22%3A%22%E6%AD%A6%E6%B1%89%E7%A7%91%E5%A4%A7%22%2C%22id%22%3A%2211022%22%2C%22year%22%3A%222008%22%7D%5D&s=0&u=895539910&act=search&offset=0&sort=0";
		Request request = new Request(url);
		request.setHttpMethod(HttpMethod.POST);
		request.setBody("id=0q=武汉科技大学&ajax=1&s=0&p=[{\"t\":\"univ\",\"name\":\"武汉科大\",\"id\":\"11022\",\"year\":\"2008\"}]&ss=false&requestToken=-1220290957&_rtk=8304d4cd");
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("id", "0");
		//request.setParams(parameters);
		Map<String,String> headers = new HashMap<String,String>();
		//headers.put("Content-Type","application/x-www-form-urlencoded; charset=GBK");
		request.setHeaders(headers);
		headers.put("Cookie","anonymid=in2xuirbj617f8; depovince=GW; _r01_=1; JSESSIONID=abcAP8xB5gBqv9Ek7yEqv; __utma=151146938.1501138792.1460799258.1460799258.1460799258.1; __utmc=151146938; __utmz=151146938.1460799258.1.1.utmcsr=renren.com|utmccn=(referral)|utmcmd=referral|utmcct=/; ick=f177ea2f-6eca-4443-b6d4-51ec476dd8f8; newbie=1; XNESSESSIONID=2b2a56dbe3df; jebecookies=10f53939-d90c-43d2-82fe-33401080c92a|||||; ick_login=ec947f5e-3a6a-4a6c-b194-983ee1459175; _de=289A5642400344B681505879CD9109B25BF7EAB63017E04A; p=726cbfac620bb1fce10b412900b3093f0; first_login_flag=1; ln_uact=panjianping1991@163.com; ln_hurl=http://head.xiaonei.com/photos/0/0/men_main.gif; t=0313f281c7b9567934095621f9df1f0a0; societyguester=0313f281c7b9567934095621f9df1f0a0; id=895539910; xnsid=e18bcb22; jebe_key=43baf334-7e25-445a-9aa2-fd59e45a1b0b%7C9981c97c8f79c1d483912a04f81f37f1%7C1460799971352%7C1%7C1460800464652; ver=7.0; loginfrom=null; wp=0; wp_fold=0");
		request.setDecode("utf-8");
//		String content=DownloaderRoute.getDownloader(request).download(request);
		
//		String url = "http://www.renren.com/252101045/profile?v=info_timeline";
//		Request request = new Request(url);
//		request.setHttpMethod(HttpMethod.GET);
//		//request.setBody("id=0q=武汉科技大学&ajax=1&s=0&p=[{\"t\":\"univ\",\"name\":\"武汉科大\",\"id\":\"11022\",\"year\":\"2008\"}]&ss=false&requestToken=-1220290957&_rtk=8304d4cd");
//		Map<String,String> parameters = new HashMap<String,String>();
//		parameters.put("id", "0");
//		//request.setParams(parameters);
//		Map<String,String> headers = new HashMap<String,String>();
//		headers.put("Cookie","anonymid=in2xuirbj617f8; depovince=GW; _r01_=1; JSESSIONID=abcAP8xB5gBqv9Ek7yEqv; __utma=151146938.1501138792.1460799258.1460799258.1460799258.1; __utmc=151146938; __utmz=151146938.1460799258.1.1.utmcsr=renren.com|utmccn=(referral)|utmcmd=referral|utmcct=/; ick=f177ea2f-6eca-4443-b6d4-51ec476dd8f8; newbie=1; XNESSESSIONID=2b2a56dbe3df; jebecookies=10f53939-d90c-43d2-82fe-33401080c92a|||||; ick_login=ec947f5e-3a6a-4a6c-b194-983ee1459175; _de=289A5642400344B681505879CD9109B25BF7EAB63017E04A; p=726cbfac620bb1fce10b412900b3093f0; first_login_flag=1; ln_uact=panjianping1991@163.com; ln_hurl=http://head.xiaonei.com/photos/0/0/men_main.gif; t=0313f281c7b9567934095621f9df1f0a0; societyguester=0313f281c7b9567934095621f9df1f0a0; id=895539910; xnsid=e18bcb22; jebe_key=43baf334-7e25-445a-9aa2-fd59e45a1b0b%7C9981c97c8f79c1d483912a04f81f37f1%7C1460799971352%7C1%7C1460800464652; ver=7.0; loginfrom=null; wp=0; wp_fold=0");
//		request.setHeaders(headers);
//		request.setDecode("utf-8");
		Response response=DownloaderRoute.getDownloader(request).download(request);
		
		System.out.println(response.getHeaders().get("Cookie"));
	}

}
