<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-征信记录</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/blacklist.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>创投热点</title>


</head>
<body ng-app="myApp" ng-controller="customersCtrl">



<div class="header"><h1>P2P 信用黑名单</h1></div>
<div class="search_box">

<form class="search" >
	<input type="search" placeholder="请输入姓名/手机号/身份证号" ng-model="placeholder" name="search" ng-focus="focus($event)"  ng-blur="blur($event)" ng-keydown="keydown($event)">
	<button class="btn-search"><i class="icon-search"></i></button>
</form>
</div>



   <div class="main"> 
<div class="search_result">

        <div class="weui_panel_hd" ng-model="resultHeader" ng-bind-html="resultHeader| trustHtml"></div>
        <div class="weui_panel_bd" >
        <div class="weui_media_box weui_media_text" ng-repeat="blackname in searchResult">
              <h4 class="weui_media_title">{{blackname.overdues[0].remarks}}</h4>
          
 <p class="weui_media_desc"><span class="field">姓名 :{{blackname.name}}</span><span class="field"> 身份证号 :{{blackname.idCard}}</span></p>
             <p class="weui_media_desc"><span class="field">手机号 :{{blackname.mobile}}</span><span class="field">邮箱 :{{blackname.email}}</span></p>
            <p class="weui_media_desc"><span class="field">借款时间 :{{blackname.overdues[0].loanDate}}</span><span class="field">逾期天数 :{{blackname.overdues[0].overdueDays}}天</span></p>
            <p class="weui_media_desc"><span class="field">借入本金 :{{blackname.overdues[0].loanAmount}}</span><span class="field">逾期本息 :{{blackname.overdues[0].overdueMoney}}元</span></p>
           <p class="weui_media_desc"><span class="field">数据来源 :{{blackname.overdues[0].loanPlatform}}</span><span class="field">更新时间 :{{blackname.updateTime}}</span></p>
        </div>
        </div>
        
        </div>
        
        
        




<div class="weui_panel">
    <div class="weui_panel_hd">信用黑名单(最新20条)</div>
    <div class="weui_panel_bd" >
        <div class="weui_media_box weui_media_text" ng-repeat="blackname in blacknames">
       
             <h4 class="weui_media_title">{{blackname.overdues[0].remarks}}</h4>
          
            <p class="weui_media_desc"><span class="field">姓名 :{{blackname.name}}</span><span class="field"> 身份证号 :{{blackname.idCard}}</span></p>
             <p class="weui_media_desc"><span class="field">手机号 :{{blackname.mobile}}</span><span class="field">邮箱 :{{blackname.email}}</span></p>
            <p class="weui_media_desc"><span class="field">借款时间 :{{blackname.overdues[0].loanDate}}</span><span class="field">逾期天数 :{{blackname.overdues[0].overdueDays}}天</span></p>
            <p class="weui_media_desc"><span class="field">借入本金 :{{blackname.overdues[0].loanAmount}}</span><span class="field">逾期本息 :{{blackname.overdues[0].overdueMoney}}元</span></p>
           <p class="weui_media_desc"><span class="field">数据来源 :{{blackname.overdues[0].loanPlatform}}</span><span class="field">更新时间 :{{blackname.updateTime}}</span></p>
          
        </div>
        
        
    </div>
</div>

</div>




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
 
  $scope.more = function() {
	      $scope.page = $scope.page+1;
		  $http.get("list?page_no="+$scope.page).success(function (response) {$scope.blacknames=$scope.blacknames.concat(response.data);});
	     
  };
  
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
			    		  $scope.searchResult = response.data;
			    	      if(!$scope.searchResult|$scope.searchResult.length==0){
			    	    	  $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录";
			    	      }else{
			    	    	  "查询关键字:"+$scope.placeholder+"<br>查询结果 ";
			    	      }
			    	  });
		    		 
			    	  
		    	 }else{
		    		 $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录";
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
					    		  $scope.searchResult = response.data;
					    	      if(!$scope.searchResult|$scope.searchResult.length==0){
					    	    	  $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录";
					    	      }else{
					    	    	  $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 ";
					    	      }
					    	  });
				    		 
					    	  
				    	 }else{
				    		 $scope.resultHeader="查询关键字:"+$scope.placeholder+"<br>查询结果 :无记录";
				    	 }
				    	
				    }
				   
			  }
			   
			    
			    	 
			    
			   
			  };
});
</script>







</body>
</html>