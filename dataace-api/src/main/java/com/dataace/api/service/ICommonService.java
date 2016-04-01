package com.dataace.api.service;

import java.util.List;
import java.util.Map;


public interface ICommonService<T> {

		
	/**
	 * 条件查询&分页查询
	 * @param criterias
	 * @return
	 */
	public List<T> list(Map<String, Object> criterias,int pageSize,int pageNo);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T getById(String id);
	
	/**
	 * 根据条件统计
	 * @param criterias
	 * @return
	 */
	public long count(Map<String,Object> criterias);
	

}
