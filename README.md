# dataace-crawler-platform
a light web crawler
dataace-core : common project that all the other projects depends on.
dataace-api : a web project that provide open api for querying data which crawlered by the web crawler
dataace-crawler: a web crawler framework which inluding  task schedule logic and data persist logic
dataace-plugins: a project which contains many websites' extractor

when we need to crawler a new website, we just need to do the following step:
 1、define a crawler task by create a new template file in 
"dataace-crawler-platform/dataace-crawler/src/main/resources/template/" folder, the folder already has some sample template files.
 2、publish the task which descriped by a template file to the web crawler framework, for detail, you can refer to the samples in
 "dataace-crawler-platform/dataace-crawler/src/test/java/com/dataace/crawler/" folder
 
 if you have any questions, please feel free to contact me by email panjianping1991@163.com.

