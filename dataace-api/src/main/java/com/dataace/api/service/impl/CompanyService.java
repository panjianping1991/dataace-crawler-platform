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

import com.dataace.api.bean.mongo.Company;
import com.dataace.api.bean.mongo.MongoCollection;
import com.dataace.api.service.ICompanyService;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Service
public class CompanyService implements ICompanyService{
	
    @Resource
    private MongoTemplate mongoTemplate;



	@Override
	public Company getById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		final Company company = new Company();
		mongoTemplate.executeQuery(query, MongoCollection.COMPANY.getName(), new DocumentCallbackHandler(){

				@Override
				public void processDocument(DBObject dbObject)
						throws MongoException, DataAccessException {
					
					String id = dbObject.get("_id")==null?null:dbObject.get("_id").toString();
					String cityName=dbObject.get("cityName")==null?null:dbObject.get("cityName").toString();
					String districtName=dbObject.get("districtName")==null?null:dbObject.get("districtName").toString();
					String weixin=dbObject.get("weixin")==null?null:dbObject.get("weixin").toString();
					String companyName=dbObject.get("companyName")==null?null:dbObject.get("companyName").toString();
					String webLink=dbObject.get("webLink")==null?null:dbObject.get("webLink").toString();
					String fullName=dbObject.get("fullName")==null?null:dbObject.get("fullName").toString();
					String industry=dbObject.get("industry")==null?null:dbObject.get("industry").toString();
					String financePhase=dbObject.get("financePhase")==null?null:dbObject.get("financePhase").toString();
					String logoLink=dbObject.get("logoLink")==null?null:dbObject.get("logoLink").toString();
					String shortDescription=dbObject.get("shortDescription")==null?null:dbObject.get("shortDescription").toString();
					
					company.setId(id);
					company.setCityName(cityName);
					company.setDistrictName(districtName);
					company.setWebLink(webLink);
					company.setCompanyName(companyName);
					company.setWebLink(webLink);
					company.setFullName(fullName);
					company.setIndustry(industry);
					company.setFinancePhase(financePhase);
					company.setLogoLink(logoLink);
					company.setWeixin(weixin);
					company.setShortDescription(shortDescription);
					
					
				}
				 
			 });
		return company.getId()==null?null:company;
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
		return mongoTemplate.count(query, MongoCollection.COMPANY.getName());
		
	}

	@Override
	public List<Company> list(Map<String, Object> criterias, int pageSize, int pageNo) {
		Query query = new Query();
		for(String name:criterias.keySet()){
			 Object val = criterias.get(name);
			 if(null==val||"".equals(val.toString())){
				 continue;
			 }
			 query.addCriteria(Criteria.where(name).is(criterias.get(name)));
		 }	 
		query.limit(pageSize);
		query.skip(pageSize*(pageNo-1)<0?0:pageSize*(pageNo-1));
		final List<Company> companys = new ArrayList<Company>();
		 mongoTemplate.executeQuery(query, MongoCollection.COMPANY.getName(), new DocumentCallbackHandler(){

			@Override
			public void processDocument(DBObject dbObject)
					throws MongoException, DataAccessException {
				Company company = new Company();
				String id = dbObject.get("_id")==null?null:dbObject.get("_id").toString();
				String cityName=dbObject.get("cityName")==null?null:dbObject.get("cityName").toString();
				String districtName=dbObject.get("districtName")==null?null:dbObject.get("districtName").toString();
				String weixin=dbObject.get("weixin")==null?null:dbObject.get("weixin").toString();
				String companyName=dbObject.get("companyName")==null?null:dbObject.get("companyName").toString();
				String webLink=dbObject.get("webLink")==null?null:dbObject.get("webLink").toString();
				String fullName=dbObject.get("fullName")==null?null:dbObject.get("fullName").toString();
				String industry=dbObject.get("industry")==null?null:dbObject.get("industry").toString();
				String financePhase=dbObject.get("financePhase")==null?null:dbObject.get("financePhase").toString();
				String logoLink=dbObject.get("logoLink")==null?null:dbObject.get("logoLink").toString();
				String shortDescription=dbObject.get("shortDescription")==null?null:dbObject.get("shortDescription").toString();
				
				company.setId(id);
				company.setCityName(cityName);
				company.setDistrictName(districtName);
				company.setWebLink(webLink);
				company.setCompanyName(companyName);
				company.setWebLink(webLink);
				company.setFullName(fullName);
				company.setIndustry(industry);
				company.setFinancePhase(financePhase);
				company.setLogoLink(logoLink);
				company.setWeixin(weixin);
				company.setShortDescription(shortDescription);
				companys.add(company);
				
			}
			 
		 });
		return companys;
	}
	
	
	

}
