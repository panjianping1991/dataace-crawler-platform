package com.dataace.crawler.util;

public class URLUtil {
	
	/**
	 *
	 * @param href  the anchor's attribute
	 * @param page  the page which contains the  anchor element
	 * @return
	 * @throws Exception
	 */
	public static String getAbsoluteUrl(String href,String currUrl) throws Exception{
		if(null==href||href.isEmpty()||href.trim().isEmpty()||null==currUrl){
			throw new Exception("Illegal Argument! href and currUrl can't be null or empty");
		}

		href =href.trim();

		if(href.toLowerCase().startsWith("http")){
			return href;
		}else {
		    String documentUrl=null;
			String domain = currUrl.substring(0,currUrl.indexOf("/",10));
			 if(href.charAt(0)=='/'){
                 documentUrl = domain+href;
             }else{
            	 if(currUrl.contains("?")){
                	 currUrl=currUrl.substring(0,currUrl.indexOf("?"));
                 }
                 if(href.indexOf("./")==0){
                     href=href.replaceFirst("./", "");
                 }

                 while(href.indexOf("../")==0){
                	 currUrl=currUrl.substring(0,currUrl.lastIndexOf("/"));
                     href=href.substring(3);
                 }
           
                 documentUrl = currUrl.substring(0,currUrl.lastIndexOf("/")+1)+href;
             }
			 return documentUrl;
		}
	}

}
