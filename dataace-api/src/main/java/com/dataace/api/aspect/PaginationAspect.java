package com.dataace.api.aspect;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.dataace.api.AppConfig;
import com.dataace.api.service.impl.CompanyService;

@Service
public class PaginationAspect {
	
    @Resource
    private AppConfig appConfig;
    
	public void paginate(ProceedingJoinPoint point){
		
		Object[] args = point.getArgs();
		if(null!=args&&args.length==1){
			if(args[0] instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String, Object> criterias = (Map<String, Object>) args[0];
				Class<?> clz = point.getTarget().getClass();
				int pageSize = appConfig.getDefaultPageSize();
				if(clz ==CompanyService.class){
					pageSize=appConfig.getCellPageSize();
				}else {
					
				}
				criterias.put("pageSize", pageSize);
			}
		}
	}

}
