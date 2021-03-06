package com.dataace.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataace.crawler.annotation.MongoBean;
import com.dataace.crawler.download.DownloaderRoute;
import com.dataace.crawler.download.Request;
import com.dataace.crawler.download.Response;
import com.dataace.crawler.exception.InvalidOperationException;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.extractor.ExtractorRoute;
import com.dataace.crawler.persist.MongoCollectionField;
import com.dataace.crawler.pipeline.Pipeline;
import com.dataace.crawler.pipeline.PipelineChain;
import com.dataace.crawler.pipeline.impl.MongoPipeline;
import com.dataace.crawler.plugin.PluginManager;
import com.dataace.crawler.template.TemplateConfig;
import com.dataace.crawler.template.TemplateXMLParser;
import com.dataace.crawler.util.CollectionUtil;
import com.dataace.crawler.util.Params;
import com.dataace.crawler.util.StringUtil;



public class CommonCrawler {
	
    private static final Logger logger =  LogManager.getLogger(CommonCrawler.class);
    
    private static BlockingTaskQueue<Request> requestQueue = new BlockingTaskQueue<Request>(1<<13);
    private static BlockingTaskQueue<Request> failureQueue = new BlockingTaskQueue<Request>(1<<12);
    private static BlockingTaskQueue<FutureTask<List<Map<String,Object>>>> futureQueue = new BlockingTaskQueue<FutureTask<List<Map<String,Object>>>>(1<<12);
    private static PipelineChain pipelineChain = new PipelineChain();
    private static final AtomicInteger processSuccessCount = new AtomicInteger(0);
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Map<String,AtomicInteger> runningPerTemplate = new ConcurrentHashMap<String,AtomicInteger>();


    private static ExecutorService executor =new ThreadPoolExecutor(30,30, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(8192), new TaskDiscardPolicy());
    
    public static TemplateConfig registTemplateConfig(String templatePath) throws IOException{
		TemplateConfig templateConfig = TemplateXMLParser.parseTemplate(templatePath);
		ApplicationContext.getInstance().getTemplateConfigHolder().addTemplateConfig(templateConfig);
		List<String> jarPaths = new ArrayList<String>();
		logger.info("jarPath:"+ApplicationContext.getInstance().getTemplateConfigHolder().getAllJarPaths());
		jarPaths.addAll(ApplicationContext.getInstance().getTemplateConfigHolder().getAllJarPaths());
		PluginManager.getInstance().updateUrlClassLoader(jarPaths);
		return templateConfig;
	}
    
    public static void publish(final TemplateConfig templateConfig) {
    	logger.info("publish a template,templateId={},scheduleInterval={} minutes",templateConfig.getTemplateId(),templateConfig.getScheduleInterval());
    	if(templateConfig.getScheduleInterval()>0){
    		scheduler.scheduleAtFixedRate(new Runnable(){

    			public void run() {
    				List<Request> requests = templateConfig.getSeedRequests();
    		    	for(Request request:requests){
    		    		try {
    						addRequest(request);
    					} catch (InvalidOperationException e) {			
    						e.printStackTrace();
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
    		    	}
    		    
    			}
        		
        	}, templateConfig.getInitDelay(), templateConfig.getScheduleInterval()*60, TimeUnit.SECONDS);
    	}else{
    		List<Request> requests = templateConfig.getSeedRequests();
	    	for(Request request:requests){
	    		try {
					addRequest(request);
				} catch (InvalidOperationException e) {			
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
    	}
    	
    	
    	
    	
    }
    
    public static void addPipeline(Pipeline pipeline){
    	pipelineChain.addPipeline(pipeline);
    }
    
    public static void  addRequest(Request request) throws InvalidOperationException, InterruptedException{
    	requestQueue.put(request);
    }
    
    public static void  addFailedRequest(Request request) throws InvalidOperationException, InterruptedException{
    	failureQueue.put(request);
    }
    
    
    static {
    	printWorkingLog(1000);
    	executeTask();
    	processResult();  	   	  	
    }
    
    public static void printWorkingLog(final long interval){
    	new Thread(){

			@Override
			public void run() {
				for(;;){
					int queueSize=((ThreadPoolExecutor)executor).getQueue().size();
					int activeThreadCount = ((ThreadPoolExecutor)executor).getActiveCount();
					long completedTaskCount = ((ThreadPoolExecutor)executor).getCompletedTaskCount();
					
					logger.info("【ThreadPool statistics】queueSize:{},activeThreadCount:{},completedTaskCount{}",queueSize,activeThreadCount,completedTaskCount);
					for(String templateId:runningPerTemplate.keySet()){
						AtomicInteger runningThread = runningPerTemplate.get(templateId);
						if(null!=runningThread){
							logger.info("【{} activeThreadCount:{}】",templateId,runningThread);
						}
					}
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						
					}
				}
				
			}
    		
    	}.start();
    }
    
    public static void executeTask(){
    	
    	   	
    	new Thread(){           
			@Override
			public void run() {
				int submitted=0;
				for(;;){
					Request request = requestQueue.poll();
					if(null==request){
						request = failureQueue.poll();								
					}
					if(null!=request){
						AtomicInteger runningThreadForTemplate = runningPerTemplate.get(request.getTemplateId());
						if(null==runningThreadForTemplate){
							runningThreadForTemplate = new AtomicInteger(0);
						}
						TemplateConfig templateConfig = ApplicationContext.getInstance().getTemplateConfigHolder().getTemplateConfig(request);
						if(runningThreadForTemplate.get()>=templateConfig.getMaxThreadsPerNode()){
							try {
								requestQueue.put(request);
							} catch (Exception e) {
								
							}
							continue;
						}
						FutureTask<List<Map<String,Object>>> future = new FutureTask<List<Map<String,Object>>>(new RequestProcessor(request));   
					    executor.execute(future);   
					    try {
					    	futureQueue.put(future);
						} catch (Exception e1) {
							logger.info(e1.getMessage());
						}
					    submitted++;
					    logger.info("submitted:"+submitted);
					   
					}else{
						logger.info("urlQueue is empty!!!");	
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							logger.error(e.getMessage(),e);
						}
					}
					
					try {
							Thread.sleep(200);
						} catch (InterruptedException e) {			
							logger.error(e.getMessage(),e);
					   }
				}
				
			}
    		
    	}.start();
    }
    
    public static void processResult(){
    	new Thread(){

			@Override
			public void run() {
				
				for(;;){
					FutureTask<List<Map<String,Object>>> future = futureQueue.poll();
					if(null!=future){
						try {
							List<Map<String,Object>> result= future.get();
							processSuccessCount.getAndDecrement();
							
							if(null!=pipelineChain){
								pipelineChain.process(result);
							}
						} catch (Exception e) {
							logger.error("failed to get the future result", e);
						}
					}else{
						logger.info("futureQueue is empty!!!");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							logger.error(e.getMessage(),e);
						}
					}
					
					try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {			
							logger.error(e.getMessage(),e);
					   }
				}
			}
    		
    	}.start();
    }
  
	
	

	
	
	public static class RequestProcessor implements Callable<List<Map<String,Object>>> {

		private Request request ;
		
		public RequestProcessor(Request request){
			this.request=request;
		}
		public void before(){
			AtomicInteger runningCount = runningPerTemplate.get(request.getTemplateId());
			if(null==runningCount){
				synchronized(RequestProcessor.class){
					if(null==runningCount){
						runningCount = new AtomicInteger(0);
						runningPerTemplate.put(request.getTemplateId(), runningCount);
					}
				}
				
			}
			runningCount = runningPerTemplate.get(request.getTemplateId());
			runningCount.incrementAndGet();
			
		}
		
		public void after(){
			AtomicInteger runningCount = runningPerTemplate.get(request.getTemplateId());
			runningCount.decrementAndGet();
		}
		public List<Map<String,Object>> call() {
			
			try{	
				before();
				String content=null;
				Response response = null;
				try{
					response = DownloaderRoute.getDownloader(request).download(request);
					if(null!=response){
						content = response.getBody();
					}
				}catch(Throwable e){
					logger.warn(e.getMessage());
				}				
				Map<String,Object> extras = request.getExtras();//use to pass parameters from parent request to sub request
				if(null==request.getExtras()){
					extras = new HashMap<String,Object>();
				}
				Extractor<?> extractor = ExtractorRoute.getExtractor(request);
				if(null==extractor){
					logger.error("failed to find the extractor for the request={}",request.getUrl());
					return null;
				}
			
				TemplateConfig templateConfig = ApplicationContext.getInstance().getTemplateConfigHolder().getTemplateConfig(request);
				if(extractor.isFailed(content, extras)){
					request.setRetryTimes(request.getRetryTimes()+1);
					if(templateConfig.getFailureQueueMaxRetryTimes()>=request.getRetryTimes()){
						addFailedRequest(request);
					}
					
				}else{
					extras.put(Params.ORIGINAL_URL.toString(), request.getUrl());
					List<?> datas = extractor.extract(content,extras);
					List<Request> splitRequests = extractor.split(content, extras);
					if(null!=splitRequests){
						for(Request splitRequest:splitRequests){
							splitRequest.setLevel(request.getLevel()+1);
							if(splitRequest.getLevel()>templateConfig.getMaxDeep()){
								logger.info("extends maxDeep,request={}",request);
								continue;
							}
							if(null!=splitRequest.getExtras()){
								extras.putAll(splitRequest.getExtras());
							}
							 
							splitRequest.setExtras(extras);
							splitRequest.setTemplateId(request.getTemplateId());
							Map<String,String> headers = splitRequest.getHeaders();
							if(!(null!=headers&&!StringUtil.isEmpty(headers.get("Cookie")))){
								String responseCookie = response.getHeaders()==null?null:response.getHeaders().get("Cookie");
								String parentCookie =request.getHeaders()==null?null:request.getHeaders().get("Cookie");
								if(responseCookie==null){
									responseCookie=parentCookie;
								}
								if(null!=responseCookie){
								   if(null==headers){
									   headers = new HashMap<String,String>();
									   splitRequest.setHeaders(headers);
								   }
								   headers.put("Cookie", responseCookie);
								   logger.info("set Cookie:{}",responseCookie);
								}
							}
							addRequest(splitRequest);
							
						}
					}
					
					List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
					if(null!=datas)
					for(Object data:datas){
						Map<String,Object> result = CollectionUtil.toMap(data);	
						MongoBean mongoBean = data.getClass().getAnnotation(MongoBean.class);

						if(null==mongoBean){				
							logger.error("please set the mongo collection for data,data="+data);
							continue;
						}

						result.put(MongoCollectionField.COLLECTION.getName(),mongoBean.collection());
						
						String dataSource = ApplicationContext.getInstance().getTemplateConfigHolder().getTemplateConfig(request).getDataSource();
						result.put("dataSource", dataSource);
						result.put("_id", dataSource+"_"+result.remove("id"));
						
						results.add(result);
					}
					return results;
				}			
				
				
			}catch(Exception e){
				logger.error("failed to process request,url="+request.getUrl(),e);
			}finally{
				after();
			}
			
			return null;
		}
        
    }
	
	public static void main(String[] args) {
		
		CommonCrawler.addPipeline(new MongoPipeline());
		try {
			CommonCrawler.addRequest(new Request("https://rong.36kr.com/search?query=%E5%85%AC%E5%8F%B8&type=1&page=1"));
		} catch (Exception e) {
		   logger.error(e.getMessage(), e);
		}
	}
}
class TaskDiscardPolicy extends DiscardPolicy{
	private static Logger logger = LogManager.getLogger(DiscardPolicy.class);
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		logger.debug("--------task queue is full,can not accept task now,poolSize:" + e.getPoolSize() + "----------");
	}
}


