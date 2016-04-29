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
<title>失信人员</title>
</head>
<body ng-app="myApp" ng-controller="customersCtrl">

<div >
  <form class="searchbar">
   <input class="search" type="search" placeholder="请输入姓名/手机/身份证" ng-model="placeholder" ng-focus="focus($event)" ng-blur="blur($event)" ng-keydown="keydown($event)"   name="searchbar" >
   <button ng-click="search()">search</button>
   </form>
  </div>


<script src="/dataace-api/css/dist/lib/jquery-2.1.4.js"></script>
<script src="/dataace-api/css/dist/js/jquery-weui.js"></script>
<script>
var app = angular.module('myApp', []);

app.controller('customersCtrl', function($scope, $http){
    

    $scope.search = function(){
        var newBook = {
                       
                        pName:"尹四金",
                        pCardNum:"",
                        pProvince:"0",
                        pCode:"1269"
                      };

        $http.post('http://shixin.court.gov.cn/findd', newBook).success(function(){
            $scope.msg = 'Data saved';
        }).error(function(data) {
            alert("failure message:" + JSON.stringify({data:data}));
        });
    }
    
    
    
    
});



</script>
</body>
</html>