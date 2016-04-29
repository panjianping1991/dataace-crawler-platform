package com.dataace.crawler.download;

public interface Downloader {
	
   public Response download(Request request) throws Exception;
   
}
