app = angular.module('navigation.controller',[]);
app.controller('NavigationController', ['$scope','$location','$http','$cookies', 
  function($scope,$location,$http,$cookies) {
  $scope.Model = null;
  $scope.profile = function(){
    $location.path('/account/profile');
  }
  // $scope.$on('requestLogin', function(event, args) {
  //   $scope.isLogin = args.status;
  // });
  $scope.logout = function(){
    $http.post('/account/logout', {}).success(function(data){
      if(data.status==true){
        location.reload();
        $location.path('/');
      }
    });
  }
  $scope.init = function(){
    $http.post('/account/getUser', {}).success(function(data){
      if(data.status == true){
        $scope.Model = data.data;
      }
    });
  }
  $scope.init();

}]);