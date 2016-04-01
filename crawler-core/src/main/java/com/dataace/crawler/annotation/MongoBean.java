package com.dataace.crawler.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.dataace.crawler.persist.MongoCollection;

@Retention(RetentionPolicy.RUNTIME)
public @interface MongoBean {
	
	MongoCollection collection();

}
