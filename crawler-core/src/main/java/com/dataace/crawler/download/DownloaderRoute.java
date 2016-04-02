package com.dataace.crawler.download;

import com.dataace.crawler.download.impl.HttpClientDownloader;

public class DownloaderRoute {
	
	public static Downloader getDownloader(Request request){
		
		return new HttpClientDownloader();
		
	}

}
