<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-征信记录</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/blackdetail.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>失信人信息</title>


</head>



<body>
<div class="main sg_scroll_main" ng-app="myApp"  ng-controller="customersCtrl">
	<div id="mask" class="mask"></div>
    <div style="display: block;" class="sg_loading_bar loading_start loading_stop"><span class="sg_loading_point"></span></div><div class="sg_view_mask" style="display: none;"></div>
    
    <div class="sg_layout p_fdd" ng-model="blackname">
    
    <div class="top_line">
		<a class="srch_back" ng-click="goback();"></a>失信人信息
	</div>
	<div scroll_height="-74" class="content sg_scroll_fix">
	<div class="fdd_title">{{blackname.name}}</div>
	


<div class="fdd_info">
		<div class="entry_name">身份证号码</div>
		<div class="entry_value">{{blackname.idCard}}</div>
		<div class="entry_name">手机号</div>
		<div class="entry_value">{{blackname.mobile}}</div>
		<div class="entry_name">年龄</div>
		<div class="entry_value"></div>
		<div class="entry_name">身份</div>
		<div class="entry_value"></div>
		<div class="entry_name">性别</div>
		<div class="entry_value"></div>

	</div>

           
	<div class="fdd_info">
		<div class="entry_name">借款时间 </div>
		<div class="entry_value">{{blackname.overdues[0].loanDate}}</div>
		<div class="entry_name">逾期天数</div>
		<div class="entry_value">{{blackname.overdues[0].overdueDays}}天</div>
		<div class="entry_name">借入本金</div>
		<div class="entry_value">{{blackname.overdues[0].loanAmount}}</div>
		<div class="entry_name">逾期本息 </div>
		<div class="entry_value">{{blackname.overdues[0].overdueMoney}}元</div>
		<div class="entry_name">借款平台</div>
		<div class="entry_value">{{blackname.overdues[0].loanPlatform}}</div>
		<div class="entry_name">备注</div>
		<div class="entry_value">{{blackname.overdues[0].remarks}}</div>
	</div>

	
	</div>
	<div class="footer">
		<div class="data_resource">数据来源：{{blackname.overdues[0].loanPlatform}}&nbsp;|&nbsp;同步时间：{{blackname.updateTime}}</div>
	</div></div></div>
 





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