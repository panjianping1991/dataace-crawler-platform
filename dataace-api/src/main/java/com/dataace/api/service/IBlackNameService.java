package com.dataace.api.service;


import com.dataace.crawler.persist.bean.BlackName;

public interface IBlackNameService extends ICommonService<BlackName>{
	
	public void save(BlackName blackName);

}
