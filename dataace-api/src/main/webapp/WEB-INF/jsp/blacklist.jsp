<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-创投热点</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/blacklist.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>创投热点</title>


</head>
<body ng-app="myApp" ng-controller="customersCtrl">


<form class="search" >
	<input type="search" placeholder="请输入姓名/手机/身份证" name="search" ng-focus="getCurrFocus($event)" >
</form>



<div class="weui_panel">
    <div class="weui_panel_hd">信用黑名单</div>
    <div class="weui_panel_bd" >
        <div class="weui_media_box weui_media_text" ng-repeat="blackname in blacknames">
           <div class="weui_media_box weui_media_text">
             <h4 class="weui_media_title">{{blackname.overdues[0].remarks}}</h4>
          
            <p class="weui_media_desc">姓名:{{blackname.name}}</p>
             <p class="weui_media_desc">身份证号:{{blackname.idCard}}</p>
             <p class="weui_media_desc">手机号:{{blackname.mobile}}</p>
            <p class="weui_media_desc">借款时间:{{blackname.overdues[0].loanDate}}&nbsp;&nbsp;&nbsp;逾期天数:{{blackname.overdues[0].overdueDays}}</p>
            <p class="weui_media_desc">借入本金:{{blackname.overdues[0].loanAmount}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;逾期本息:{{blackname.overdues[0].overdueMoney}}</p>
           <p class="weui_media_desc">数据来源:{{blackname.overdues[0].loanPlatform}}</p>
           </div>
        </div>
        
        
    </div>
</div>






<script>
var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {
  $http.get("list")
  .success(function (response) {$scope.blacknames = response.data;});
  $scope.page=1;
 
  $scope.more = function() {
	      $scope.page = $scope.page+1;
		  $http.get("list?page_no="+$scope.page).success(function (response) {$scope.blacknames=$scope.blacknames.concat(response.data);});
	     
  };
  
  $scope.getCurrFocus = function($event) {
	   // $scope.currFocus = target.getAttribute('name');
	   $event.target.setAttribute('placeholder','');
	   
	  };
});
</script>







</body>
</html>