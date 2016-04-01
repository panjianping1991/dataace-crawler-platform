package com.dataace.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:properties-config/env.properties")
public class AppConfig {
    
    @Value("${host}") String host;
    @Value("${urlDecoder}") String urlDecoder;
    @Value("${project}") String project;
    @Value("${cellPageSize}") int cellPageSize;
    @Value("${buildingPageSize}") int buildingPageSize;
    @Value("${roomPageSize}") int roomPageSize ;
    @Value("${defaultPageSize}") int defaultPageSize ;

   //To resolve ${} in @Value
  	@Bean
  	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
  		return new PropertySourcesPlaceholderConfigurer();
  	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getCellPageSize() {
		return cellPageSize;
	}

	public void setCellPageSize(int cellPageSize) {
		this.cellPageSize = cellPageSize;
	}

	public int getBuildingPageSize() {
		return buildingPageSize;
	}

	public void setBuildingPageSize(int buildingPageSize) {
		this.buildingPageSize = buildingPageSize;
	}

	public int getRoomPageSize() {
		return roomPageSize;
	}

	public void setRoomPageSize(int roomPageSize) {
		this.roomPageSize = roomPageSize;
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public String getUrlDecoder() {
		return urlDecoder;
	}

	public void setUrlDecoder(String urlDecoder) {
		this.urlDecoder = urlDecoder;
	}
  	
  	
    
    
}
