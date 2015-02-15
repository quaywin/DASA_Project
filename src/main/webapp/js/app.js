"use strick";

app = angular.module('dasa', [
  'ngRoute',
  'treeControl'
  // 'home.controller',
  // 'account.controller',
  // 'navigation.controller',
  // 'ngCookies'
]);
app.config([
  '$routeProvider',
  function($routeProvider) {
    $routeProvider
    //Home
      .when('/', {
        templateUrl: 'views/home.html'
      })
      .when('/verify',{
        templateUrl: 'views/verify.html'
      })
      .when('/google',{
        templateUrl: 'views/google.html'
      })
      .when('/dropbox',{
        templateUrl: 'views/dropbox.html'
      })
      .otherwise({
        redirectTo: 'views/home.html'
      });
  }
]);
app.controller('VerifyController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.Verify = "Verifing"
  $scope.init = function(){
    var searchObject = $location.search();
    $http.post('/rest/serviceGG/verify',{code:searchObject.code}).success(function(data){
      if(data.Status == true){
        $scope.Verify = "Verify successful"
      }else{
        $scope.Verify = "Cannot verify"
      }
    });
  }
  return $scope.init();
}]);
app.controller('NavigationController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.GetAuthGG = function(){
    $http.post('/rest/serviceGG/GetAuthURL',{}).success(function(data){
      if(data.Status == true){
        var myWindow = window.open(data.Data, "Authorize", "width=500, height=400, top=300, left = 500");
        // window
      }
    });
  }
  $scope.GetAuthDB = function(){
    $http.post('/rest/serviceDB/GetAuthURL',{}).success(function(data){
      if(data.Status == true){
        var myWindow = window.open(data.Data, "Authorize", "width=500, height=400, top=300, left = 500");
        // window
      }
    });
  }
  $scope.Google= function(){
    $location.path("/google");
  }
  $scope.DropBox= function(){
    $location.path("/dropbox");
  }
}]);
app.controller('GoogleController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.init = function(){
    $http.post('/rest/serviceGG/getAbout',{}).success(function(data){
        $scope.About = data;
        $http.post('/rest/serviceGG/getDir',{}).success(function(data){
          $scope.Model = data;
          var item, _i, _len, _ref;
          $scope.Data = [];
          _ref = $scope.Model.items;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            item = _ref[_i];
            if(item.mimeType != 'application/vnd.google-apps.folder' && item.ownerNames[0] == $scope.About.user.displayName){
              $scope.Data.push(item);
            }
          }
          console.log($scope.Data);
      });
    });
    
  }
  $scope.deleteFile = function(id,index){
    $http.post('/rest/serviceGG/deleteFile/'+id,{}).success(function(data){
        $scope.Data.splice(index, 1);
    });
  }
  return $scope.init();
}]);
app.controller('DropBoxController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.init = function(){
    $http.post('/rest/serviceDB/getAbout',{}).success(function(data){
        $scope.About = data;
          $http.post('/rest/serviceDB/getDir',{}).success(function(data){
          $scope.Model = data;
          var item, _i, _len, _ref;
          $scope.Data = [];
          _ref = $scope.Model.contents;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            item = _ref[_i];
            item.name = item.path.replace('/','');
            if(item.is_dir){
              item.kind = "Folder";
            }else{
              item.kind = "File";
            }
          }
          // console.log($scope.Model.contents);
      });
    });
    
  }

  return $scope.init();
}]);
// app.run(function($rootScope, $location, $cookies) {
//   $rootScope.$on("$routeChangeStart", function(event, next, current) {
//     if ($cookies['loggedin'] != null && $cookies['loggedin'] != undefined && next.$$route != undefined) {
//       if (next.$$route.originalPath == '/account/login' || next.$$route.originalPath == '/account/signup'|| next.$$route.originalPath == '/') {
//         $location.path('/account/profile')
//       }
//     }
//   });
// });