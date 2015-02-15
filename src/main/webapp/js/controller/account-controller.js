app = angular.module('account.controller',[]);
app.controller('LoginController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.Username=null;
  $scope.Password=null;
  $scope.ValidList = null;
  $scope.submitLogin = function(){
    $http.post('/account/login',{username:$scope.Username,password:$scope.Password}).success(function(data){
      if(data.status == true){
        location.reload();
      }else{
        $scope.ValidList = data.data;
      }
    });
  }
}]);
app.controller('SignUpController', 
  ['$scope','$location','$http','$rootScope', 
  function($scope,$location,$http,$rootScope) {
  $scope.Model={
    FirstName:null,
    LastName:null,
    Email:null,
    Password:null,
    RePassword:null,
    Valid:null
  };
  $scope.submit = function(){
    $http.post('/account/signup',{
      firstname:$scope.Model.FirstName,
      lastname:$scope.Model.LastName,
      username:$scope.Model.Email,
      password:$scope.Model.Password,
      repassword:$scope.Model.RePassword
    }).success(function(data){
      if(data.status == true){
        location.reload();
      }else{
        $scope.Model.Valid = data.valid;
      }
    });
  }
  // return $scope.init();
}]);
app.controller('ProfileController', ['$scope','$location', function($scope,$location) {
  $scope.Model = null;
  // return $scope.init();
}]);