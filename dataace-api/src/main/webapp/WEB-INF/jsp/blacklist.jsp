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


<div class="weui_search_bar" id="search_bar">
    <form class="weui_search_outer">
        <div class="weui_search_inner">
            <i class="weui_icon_search"></i>
            <input type="search" class="weui_search_input" id="search_input" placeholder="搜索" required/>
            <a href="javascript:" class="weui_icon_clear" id="search_clear"></a>
        </div>
        <label for="search_input" class="weui_search_text" id="search_text">
            <i class="weui_icon_search"></i>
            <span>搜索</span>
        </label>
    </form>
    <a href="javascript:" class="weui_search_cancel" id="search_cancel">取消</a>
</div>



<div class="weui_panel">
    <div class="weui_panel_hd">信用黑名单</div>
    <div class="weui_panel_bd" ng-app="myApp" ng-controller="customersCtrl">
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
	  
  }
});
</script>







</body>
</html>