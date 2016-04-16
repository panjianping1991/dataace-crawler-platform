package com.dataace.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.dataace.api.service.IBlackNameService;
import com.dataace.crawler.persist.MongoCollection;
import com.dataace.crawler.persist.bean.BlackName;
import com.dataace.crawler.persist.bean.Gender;
import com.dataace.crawler.persist.bean.Overdue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Service
public class BlackNameService implements IBlackNameService{
	
private final Logger logger = LoggerFactory.getLogger(BlackNameService.class);
	
    @Resource
    private MongoTemplate mongoTemplate;

	@Override
	public List<BlackName> list(Map<String, Object> criterias, int pageSize,
			int pageNo) {
		Query query = new Query();
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	 
		query.with(new Sort(Direction.DESC, "updateTime"));
		query.limit(pageSize);
		
		query.skip(pageSize*(pageNo-1)<0?0:pageSize*(pageNo-1));
		final List<BlackName> blackNames = new ArrayList<BlackName>();
		 mongoTemplate.executeQuery(query, MongoCollection.BLACKNAME.getName(), new DocumentCallbackHandler(){

			@Override
			public void processDocument(DBObject dbObject)
					throws MongoException, DataAccessException {
				BlackName blackName = new BlackName();	
				buildBlackName(dbObject, blackName);
				blackNames.add(blackName);
				
			}
			 
		 });
		return blackNames;
	}

	@Override
	public BlackName getById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		final BlackName blackName = new BlackName();
		mongoTemplate.executeQuery(query, com.dataace.crawler.persist.MongoCollection.BLACKNAME.getName(), new DocumentCallbackHandler(){

				@Override
				public void processDocument(DBObject dbObject)
						throws MongoException, DataAccessException {
					buildBlackName(dbObject, blackName);									
				}
				 
			 });
		return blackName.getId()==null?null:blackName;
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
		return mongoTemplate.count(query, MongoCollection.BLACKNAME.getName());
	}
	
	public void buildBlackName(DBObject dbObject,BlackName blackName){
		String id = dbObject.get("_id").toString();
		String dataSource = dbObject.get("dataSource").toString();
		String idCard = dbObject.get("idCard").toString();
		String mobile = dbObject.get("mobile").toString();
		String name = dbObject.get("name").toString();
		String gender = dbObject.get("gendar")==null?null:dbObject.get("gendar").toString();
		String email = dbObject.get("email")==null?null:dbObject.get("email").toString();
		
		String overdues = dbObject.get("overdues")==null?null:dbObject.get("overdues").toString();
		if(null!=overdues){
			
			Gson gson = new Gson();
			blackName.setOverdues((List<Overdue>)gson.fromJson(overdues, new TypeToken<List<Overdue>>(){}.getType()));			
		}
		long updateTime = Long.parseLong(dbObject.get("updateTime").toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(updateTime);
		blackName.setUpdateTime(format.format(c.getTime()));
		
		blackName.setId(id);
		blackName.setIdCard(idCard);
		blackName.setMobile(mobile);
		blackName.setName(name);
		blackName.setEmail(email);
		if(null!=gender){
			blackName.setGender(Gender.valueOf(gender));
		}
		
		
	}

}
