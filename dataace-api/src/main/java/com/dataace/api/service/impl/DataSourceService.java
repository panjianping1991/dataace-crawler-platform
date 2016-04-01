package com.dataace.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.dataace.api.bean.mongo.DataSource;
import com.dataace.api.bean.mongo.MongoCollection;
import com.dataace.api.service.IDataSourceService;
import com.mongodb.DBObject;
import com.mongodb.MongoException;


@Service
public class DataSourceService implements IDataSourceService{

	 @Resource
	 private MongoTemplate mongoTemplate;
	  
	  
	@Override
	public List<DataSource> list(Map<String, Object> criterias, int pageSize,
			int pageNo) {
		Query query = new Query();
		if(null!=criterias)
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	 
		query.limit(pageSize);
		query.skip(pageSize*(pageNo-1)<0?0:pageSize*(pageNo-1));
		final List<DataSource> dataSources = new ArrayList<DataSource>();
		 mongoTemplate.executeQuery(query, MongoCollection.DATA_SOURCE.getName(), new DocumentCallbackHandler(){

			@Override
			public void processDocument(DBObject dbObject)
					throws MongoException, DataAccessException {
				
				String id = dbObject.get("_id")==null?null:dbObject.get("_id").toString();
				String value=dbObject.get("value")==null?null:dbObject.get("value").toString();
				DataSource dataSource = new DataSource(id,value);
				dataSources.add(dataSource);
				
			}
			 
		 });
		return dataSources;
	}

	@Override
	public DataSource getById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		final DataSource dataSource = new DataSource();
		mongoTemplate.executeQuery(query, MongoCollection.DATA_SOURCE.getName(), new DocumentCallbackHandler(){

				@Override
				public void processDocument(DBObject dbObject)
						throws MongoException, DataAccessException {
					
					String id = dbObject.get("_id")==null?null:dbObject.get("_id").toString();
					String value=dbObject.get("value")==null?null:dbObject.get("value").toString();
					dataSource.setCode(id);
					dataSource.setValue(value);
				}
				 
			 });
		return dataSource.getCode()==null?null:dataSource;
	}

	@Override
	public long count(Map<String, Object> criterias) {
		Query query = new Query();
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	  
		return mongoTemplate.count(query, MongoCollection.DATA_SOURCE.getName());
	}

}
