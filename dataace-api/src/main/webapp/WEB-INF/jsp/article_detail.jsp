<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-热点详情</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>创投热点</title>
<style>
img{
 width:300px;
}
</style>


</head>
<body>



<div class="weui_panel">
    <div class="weui_panel_hd">创投热点</div>
    <div class="weui_panel_bd" ng-app="myApp"  ng-controller="customersCtrl">

        
     <article class="weui_article">
          <div style="color:#CECECE;padding:10px;">
   
              {{ article.originalDataSource }}
         &nbsp;&nbsp; {{article.articleDisplayTimeStr}}
            
      </div>
    <h1>{{article.title}}</h1>
  
            
    <section>
       <!--  <h2 class="title">章标题</h2> -->
        <section  ng-repeat="section in article.content.sections">
            
            <p ng-bind-html="section.content | trustHtml"></p>
        </section>
       
    </section>
</article>
    </div>
</div>





  



<script>
var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http,$location) {
  var location = window.location+"";
  $scope.articleId=location.substring(location.indexOf("weixin")+7);
  $http.get("../"+$scope.articleId)
  .success(function (response) {$scope.article = response.data;});
 
  

});

app.filter('trustHtml', function ($sce) {

    return function (input) {

        return $sce.trustAsHtml(input);

    }

});


</script>







</body>
</html>