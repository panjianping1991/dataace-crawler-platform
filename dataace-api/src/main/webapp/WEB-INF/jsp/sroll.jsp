<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/lib/weui.css"/>
        <!-- 引入jquery-WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/css/jquery-weui.css">
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>inskingsroll</title>
</head>
<body ontouchstart>


<div class="weui_panel">



    <div class="weui_panel_hd">创投热点</div>
    <div class="weui_panel_bd" ng-app="myApp" ng-controller="customersCtrl">
        <div class="weui_media_box weui_media_text" ng-repeat="article in articles">
        <a href="{{article.articleUrl}}" style="color:#000000">
            <h4 class="weui_media_title">{{article.title}}</h4>
            <div class="weui_media_hd" >
                <img width="120px" class="weui_media_appmsg_thumb" src="{{article.imageUrl}}" alt="{{article.imageUrl}}">
               </div>
            <p class="weui_media_desc" style="padding-top:10px;">{{ article.abst }}</p>
            <ul class="weui_media_info">
                <li class="weui_media_info_meta">{{ article.originalDataSource }}</li>
                <li class="weui_media_info_meta">{{article.articleDisplayTimeStr}}</li>
                <li class="weui_media_info_meta weui_media_info_meta_extra">其它信息</li>
            </ul>
           </a> 
        </div>
        
        
                  
    </div>
</div>

   <div class="weui-infinite-scroll">
      <div class="infinite-preloader"></div>
                 正在加载
    </div>

<script src="/dataace-api/css/dist/lib/jquery-2.1.4.js"></script>
<script src="/dataace-api/css/dist/js/jquery-weui.js"></script>



<script>
var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {
  $http.get("list")
  .success(function (response) {$scope.articles = response.data;});
  $scope.page=1;
 
 
  var loading = false;
  $(document.body).infinite().on("infinite", function() {
    if(loading) return;
    loading = true;
    setTimeout(function() {
    	   $scope.page = $scope.page+1;
		  $http.get("list?page_no="+$scope.page).success(function (response) {$scope.articles=$scope.articles.concat(response.data);});
      loading = false;
      $(document.body).destroyInfinite()
    }, 2000);
  });
  
});
</script>

</body>
</html>