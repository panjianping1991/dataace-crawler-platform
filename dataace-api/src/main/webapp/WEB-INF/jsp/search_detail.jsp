<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/lib/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/searchdetail.css"/>
        
        <!-- 引入jquery-WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/dist/css/jquery-weui.css">
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>失信人员信息</title>
</head>
<body ontouchstart  ng-app="myApp" ng-controller="customersCtrl">
<div class="detailinfo">
<div>姓名：{{blackname.name}}</div>
<div>身份证：{{blackname.idCard}}</div>
<div>手机：{{blackname.mobile}}</div>
<div>年龄:</div>
<div>身份:</div>
<div>性别:</div>
<div>借款时间：{{blackname.overdues[0].loanDate}}</div>
<div>借款天数：{{blackname.overdues[0].overdueDays}}天</div>
<div>借入本金：{{blackname.overdues[0].loanAmount}}元</div>
<div>逾期本息：{{blackname.overdues[0].overdueMoney}}元</div>
<div>备注：{{blackname.overdues[0].remarks}}</div>
<div>数据来源：{{blackname.overdues[0].loanPlatform}}</div>
<div>同步时间：{{blackname.updateTime}}</div>

</div>

<script src="/dataace-api/css/dist/lib/jquery-2.1.4.js"></script>
<script src="/dataace-api/css/dist/js/jquery-weui.js"></script>
<script>

var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http,$location) {
  var location = window.location+"";
  $scope.articleId=location.substring(location.indexOf("weixin")+7);
  $http.get("../"+$scope.articleId)
  .success(function (response) {$scope.blackname = response.data;});
 
  $scope.goback = function($event) {
	  history.go(-1);
	   
	  };

});

app.filter('trustHtml', function ($sce) {

    return function (input) {

        return $sce.trustAsHtml(input);

    }

});


</script>
</body>
</html>
