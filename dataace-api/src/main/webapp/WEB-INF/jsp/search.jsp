<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/lib/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/search.css"/>
        
        <!-- 引入jquery-WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/css/jquery-weui.css">
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>黑名单搜索</title>
</head  >
<body ontouchstart  ng-app="myApp" ng-controller="customersCtrl">

  
  
  
  <div class="weui_tab tabbottom ">
   <div class="weui_tab_bd">
   
   <div class="searchbartop">
  <h2>失信人搜索</h2>
  <div >
  <form class="searchbar">
   <input class="search" type="search" placeholder="请输入姓名/手机号/身份证号" ng-model="placeholder" ng-focus="focus($event)" ng-blur="blur($event)" ng-keydown="keydown($event)"   name="searchbar" >
   
   </form>
  </div>
  </div>
     
   
      <div class="searchresult ">
          <a  ng-repeat="blackname in blacknames" class="seacrhtabelcell" href="./details/weixin/{{blackname.id}}">
          <div class="seacrhcelltitle">
                                姓名：  {{blackname.name}}
           </div>
           <div>
                            身份证：{{blackname.idCard}}
           </div>
           <div>
                             电话：{{blackname.mobile}}
            </div>
            </a>
     </div>
  <span  ng-hide="myvalue" ng-click="more()" ng-model="resultHeader" ng-bind-html="resultHeader| trustHtml"></span>
  
  </div>
  
   <div class="weui_tabbar ">
        <a href="javascript:;" class="weui_tabbar_item weui_bar_item_on">
          <div class="weui_tabbar_icon">
            <img src="/dataace-api/css/dist/demos/images/icon_nav_button.png" alt="">
          </div>
          <p class="weui_tabbar_label">黑名单</p>
        </a>
        <a href="javascript:;" class="weui_tabbar_item">
          <div class="weui_tabbar_icon">
            <img src="/dataace-api/css/dist/demos/images/icon_nav_msg.png" alt="">
          </div>
          <p class="weui_tabbar_label">上传名单</p>
        </a>
        <a href="test" class="weui_tabbar_item">
          <div class="weui_tabbar_icon">
            <img src="/dataace-api/css/dist/demos/images/icon_nav_article.png" alt="">
          </div>
          <p class="weui_tabbar_label">综合搜索</p>
        </a>
        <a href="javascript:;" class="weui_tabbar_item">
          <div class="weui_tabbar_icon">
            <img src="/dataace-api/css/dist/demos/images/icon_nav_cell.png" alt="">
          </div>
          <p class="weui_tabbar_label">我</p>
        </a>
      </div>
  </div>
  
<script src="/dataace-api/css/dist/lib/jquery-2.1.4.js"></script>
<script src="/dataace-api/css/dist/js/jquery-weui.js"></script>
<script>
var app = angular.module('myApp', []);

app.filter('trustHtml', function ($sce) {

    return function (input) {

        return $sce.trustAsHtml(input);

    }

});


	  app.controller('customersCtrl', function($scope, $http) {
		  $http.get("list")
		  .success(function (response) {$scope.blacknames = response.data;});
		  $scope.page=1;
		  
		  $scope.myvalue=false
		  $scope.more = function() {
			  $http.get("list")
			  .success(function (response) {$scope.blacknames = response.data;});
			  $scope.myvalue=!$scope.myvalue;
	  }
		  
		  $scope.focus = function($event) {
			   // $scope.currFocus = target.getAttribute('name');
			   $event.target.setAttribute('placeholder','');
			   
			  };
			  
			  
			  
			  
			  
			  $scope.blur = function($event) {
				   
				    if(!$scope.placeholder){
				    	$event.target.setAttribute('placeholder','请输入姓名/手机/身份证');
				    	
				    }else{
				    	 var keywordsRegex =  /^((\d{11})|(\d{17}(\d|X))|(\S{2,4}))$/;
				    	 
				    	 if($scope.placeholder.match(keywordsRegex)){
				    		 
				    		 $http.get("list?keywords="+encodeURI($scope.placeholder))
					    	  .success(function (response) {
					    		  $scope.blacknames = response.data;
					    	      if(!$scope.blacknames|$scope.blacknames.length==0){
					    	    	  $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录点击文字返回";
					    	      }
					    	  });
				    		 
					    	  
				    	 }else{
				    		 $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录---点击文字返回";
				    	 }
				    	
				    }
				   
				    
				    	 
				    
				   
				  };
				  $scope.keydown = function($event) {
					   
					  
					  if($event.keyCode==13){
						  if(!$scope.placeholder){
						    	$event.target.setAttribute('placeholder','请输入姓名/手机/身份证');
						    	
						    }else{
						    	 var keywordsRegex =  /^((\d{11})|(\d{17}(\d|X))|(\S{2,4}))$/;
						    	 
						    	 if($scope.placeholder.match(keywordsRegex)){
						    		 
						    		 $http.get("list?keywords="+encodeURI($scope.placeholder))
							    	  .success(function (response) {
							    		  $scope.blacknames = response.data;
							    	      if(!$scope.blacknames|$scope.blacknames.length==0){
							    	    	  $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录点击文字返回";
							    	      }
							    	  });
						    		 
							    	  
						    	 }else{
						    		 $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录点击文字返回";
						    	 }
						    	
						    }
						   
					  }
					   
					    
					    	 
					    
					   
					  };

			  
		  
		});
	  

</script>

</body>
</html>