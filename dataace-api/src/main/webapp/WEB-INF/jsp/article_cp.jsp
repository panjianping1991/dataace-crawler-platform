<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-创投热点</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>创投热点</title>


</head>
<body>

<div class="weui_panel weui_panel_access">
    <div class="weui_panel_hd">创投热点</div>
    <div class="weui_panel_bd">
    <div ng-app="myApp" ng-controller="customersCtrl"> 
    <input type="hidden" type="text" ng-model="page">
        <a href="{{article.articleUrl}}" class="weui_media_box weui_media_appmsg"   ng-repeat="article in articles">
            
            
            <div class="weui_panel_bd">
            
                
                
                
                <div class="weui_media_hd">
                <img class="weui_media_appmsg_thumb" src="{{article.imageUrl}}" alt="{{article.imageUrl}}">
               </div>
                <div class="weui_media_bd">
               <h4 class="weui_media_title">{{article.title}}</h4>
               <p class="weui_media_desc">{{ article.originalDataSource }}&nbsp;&nbsp;{{article.articleDisplayTimeStr}}</p>
               <br>
                <p class="weui_media_desc">{{ article.abst }}</p>
                </div>
            </div>
        </a>
        <a class="weui_panel_ft"  ng-click="more()",href="javascript:void(0);">查看更多</a>
    </div>
        
    </div>
    
</div>

<%-- <div ng-app="myApp" ng-controller="customersCtrl"> 

<article  class="weui_article" ng-repeat="article in articles">
    <h1>{{article.title}}</h1>
    <section>
        <!--<h2 class="title">章标题</h2>  -->
        <section>
            <h3>{{article.originalDataSource}}&nbsp;{{article.articleDisplayTimeStr}}</h3>
            <p>{{ article.abst }}</p>
        </section>
    </section>
</article>



</div>--%>


  



<script>
var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {
  $http.get("list")
  .success(function (response) {$scope.articles = response.data;});
  $scope.page=1;
 
  $scope.more = function() {
	      $scope.page = $scope.page+1;
		  $http.get("list?page_no="+$scope.page).success(function (response) {$scope.articles=$scope.articles.concat(response.data);});
	  
  }
});
</script>







</body>
</html>