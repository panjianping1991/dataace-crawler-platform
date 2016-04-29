<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>DATAACE-上传失信人名单</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="/dataace-api/css/weui.css"/>
         <link rel="stylesheet" href="/dataace-api/css/blackdetail.css"/>
           <link rel="stylesheet" href="/dataace-api/css/submit_black.css"/>
        <script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
<title>失信人信息</title>


</head>



<body>
<div class="main sg_scroll_main" ng-app="myApp"  ng-controller="customersCtrl">
	<div id="mask" class="mask"></div>
    <div style="display: block;" class="sg_loading_bar loading_start loading_stop">
    <span class="sg_loading_point"></span>
    </div>
    <div class="sg_view_mask" style="display: none;"></div>
    
    <div class="sg_layout p_fdd" >
    
    <div class="top_line">
		<a class="srch_back" ng-click="goback();"></a>上传失信人信息
	</div>
	<div scroll_height="-74" class="content sg_scroll_fix">
	
    <form class="pure-form">
    <fieldset class="pure-group">
        <input type="text" class="pure-input-1-2" ng-model="user.name" placeholder="失信人姓名">
        <input type="text" class="pure-input-1-2" ng-model="user.certId"  placeholder="失信人身份证">
        <input type="text" class="pure-input-1-2" ng-model="user.mobile"  placeholder="失信人手机号">
        
    </fieldset>

 <fieldset class="pure-group">
        <input type="text" class="pure-input-1-2" ng-model="user.loanDate"  placeholder="借款时间">
         <input type="text" class="pure-input-1-2" ng-model="user.overdueDays"  placeholder="逾期天数">
            <input type="text" class="pure-input-1-2" ng-model="user.overdueMoney"  placeholder="逾期本息">
         <input type="text" class="pure-input-1-2" ng-model="user.loanPlatform"  placeholder="放贷平台">
        <textarea class="pure-input-1-2" ng-model="user.remarks"  placeholder="备注"></textarea>
    </fieldset>


    <fieldset class="pure-group">
        <input type="text" class="pure-input-1-2" ng-model="uploader.name"  placeholder="上传者姓名">
       <input type="text" class="pure-input-1-2" ng-model="uploader.mobile"  placeholder="上传者联系电话">
       <input type="text" class="pure-input-1-2" ng-model="uploader.email" placeholder="上传者联系邮箱">
    </fieldset>

    <button type="submit" ng-click= "submit();" class="pure-button pure-input-1-2 pure-button-primary">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</button>
</form>

	
	</div>
	</div></div>
 





<script>


var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http,$location) {
	
	
	
	  $scope.submit = function() {
		  var userName = $scope.user.name;
		  var certId = $scope.user.certId;
		  var mobile = $scope.user.mobile;
		  var loanDate = $scope.user.loanDate;
		  var overdueDays = $scope.user.overdueDays;
		  var overdueMoney = $scope.user.overdueMoney;
		  var loanPlatform = $scope.user.loanPlatform;
		  var uploaderEmail = $scope.uploader.email;
		  var uploaderUser = $scope.uploader.user;
		  var uploaderMobile = $scope.uploader.mobile;
		  var remarks = $scope.user.remarks;
		 /** $http.get("upload?userName="+userName).success(function (response) {
				
			});**/
			
		    $http({  
		        method:'post',  
		        url:'add',  
		        data:{userName:userName,certId:certId,mobile:mobile,loanDate:loanDate,overdueDays:overdueDays,
		        	overdueMoney:overdueMoney,loanPlatform:loanPlatform,uploaderEmail:uploaderEmail,uploaderUser:uploaderUser,
		        	uploaderMobile:uploaderMobile,remarks:remarks},  
		        headers:{'Content-Type': 'application/json'}
		        
		     }).success(function(req){  
		            console.log(req);  
		     }) ;
		   
		  };
		  
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