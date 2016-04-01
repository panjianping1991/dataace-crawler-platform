<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
<link rel="stylesheet" href="/webant-api/css/document.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>爬虫数据 - 接口文档</title>
</head>

  
  <body>
    <h1>webant-api 接口文档</h1>
    <div class="cell">
    <h2>小区API</h2>
    <div class="function">
    <h3>接口功能:&nbsp;根据小区id查询</h3>
   <table cellspacing="0" cellpadding="0" border="1">
     <tr>     
         <th width="30%">请求地址</th>
         <th width="40%">示例</th>
         <th>说明</th>
     </tr>
      <tr>        
         <td>http://${host}/webant-api/cell/details/id</td>
         <td>http://${host}/webant-api/cell/details/SOUFUN_1110663137</td>
         <td>返回0条或1条小区记录</td>
     </tr>
  </table>
   <div class="response">
    <h4>响应参数</h4>
    <table cellspacing="0" cellpadding="0" border="1">
     <tr>     
         <th width="20%">名称</th>
         <th width="20%">类型</th>
         <th width="20%">描述</th>
         <th width="40%">示例</th>
     </tr>
      <tr>
         <td>currentRequestUrl</td>
         <td>String</td>
         <td>当前请求url</td>
         <td>http://${host}/webant-api/cell/details/SOUFUN_1110663137</td>
     </tr> 
     <tr>
         <td>data</td>
         <td>JSON</td>
         <td>代表小区信息的json</td>
         <td>{"id":"SOUFUN_1110663137","dataSource":null,"cityName":"天津",</br>
         "districtName":"和平","areaName":"劝业场街","cellName":"万全道117号",</br>
         "address":null,"longitude":null,"latitude":null,"propertyCompany":null,</br>
         "developerCompany":null,"usedType":null,"secondHouseNum":null}</td>
     </tr>
      <tr>
         <td>fieldDescriptionUrl</td>
         <td>String</td>
         <td>获取小区字段描述信息的请求URL</td>
         <td>http://${host}/webant-api/cell/field-description</td>
     </tr>
     <tr>
         <td>status</td>
         <td>String</td>
         <td>响应状态,0表示正常,1表示错误</td>
         <td>0</td>
     </tr>
     <tr>
         <td>errorMsg</td>
         <td>String</td>
         <td>错误描述，若status=0，则错误描述为空</td>
         <td></td>
     </tr>
     
  </table>
   </div>
   
  </div>
  <div class="function">
  <h3>接口功能:&nbsp;小区列表查询</h3>  
  <table cellspacing="0" cellpadding="0" border="1">
     <tr>              
         <th width="30%">请求地址</th>
         <th width="40%">示例</th>
         <th>说明</th>
     </tr>
      <tr>      
         <td>http://${host}/webant-api/cell/list</td>
         <td>http://${host}/webant-api/cell/list?data_source=SOUFUN&city=%E4%B8%8A%E6%B5%B7&page_no=2</td>
         <td>非ISO-8859-1编码参数需要使用utf-8进行URL Encoder，每次请求最多返回100条小区信息，可进去分页查询更多小区</td>
     </tr>
  </table>
   <h4>请求参数</h4>
  <table cellspacing="0" cellpadding="0" border="1">
     <tr>     
         <th width="18%">名称</th>
         <th width="10%">类型</th>
         <th width="10%">是否必须</th>
         <th>描述</th>
     </tr>
      <tr>
         <td>data_source</td>
         <td>String</td>
         <td>否</td>
         <td>小区来源</td>
     </tr>
     <tr>
         <td>city</td>
         <td>String</td>
         <td>否</td>
         <td>城市</td>
     </tr>
     <tr>
         <td>district</td>
         <td>String</td>
         <td>否</td>
         <td>区域</td>
     </tr>
     <tr>
         <td>section</td>
         <td>String</td>
         <td>否</td>
         <td>片区</td>
     </tr>
      <tr>
         <td>cell_name</td>
         <td>String</td>
         <td>否</td>
         <td>小区名</td>
     </tr>
     <tr>
         <td>page_no</td>
         <td>String</td>
         <td>否</td>
         <td>查询页数</td>
     </tr>
  </table>
  
   <div class="response">
    <h4>响应参数</h4>
    <table cellspacing="0" cellpadding="0" border="1">
     <tr>     
         <th width="20%">名称</th>
         <th width="20%">类型</th>
         <th width="20%">描述</th>
         <th width="40%">示例</th>
     </tr>
      <tr>
         <td>currentRequestUrl</td>
         <td>String</td>
         <td>当前请求url</td>
         <td>http://${host}/webant-api/cell/list?data_source=SOUFUN&city=%E4%B8%8A%E6%B5%B7&page_no=2</td>
     </tr> 
      <tr>
         <td>pageNo</td>
         <td>Integer</td>
         <td>当前请求页数</td>
         <td>2</td>
     </tr> 
     <tr>
         <td>totalNum</td>
         <td>Integer</td>
         <td>满足查询条件的小区总数</td>
         <td>14356</td>
     </tr> 
     <tr>
         <td>data</td>
         <td>JSON数组</td>
         <td>代表小区列表信息的json</td>
         <td></td>
     </tr>
      <tr>
         <td>fieldDescriptionUrl</td>
         <td>String</td>
         <td>获取小区字段描述信息的请求URL</td>
         <td>http://${host}/webant-api/cell/field-description</td>
     </tr>
     <tr>
         <td>status</td>
         <td>String</td>
         <td>响应状态,0表示正常,1表示错误</td>
         <td>0</td>
     </tr>
     <tr>
         <td>errorMsg</td>
         <td>String</td>
         <td>错误描述，若status=0，则错误描述为空</td>
         <td></td>
     </tr>
     
  </table>
   </div>
   
  </div>
  
  
 </div>
  </body>
</html>
