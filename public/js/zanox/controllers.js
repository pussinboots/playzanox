'use strict';

/* Controllers */

function ProductListCtrl($scope, Product, $rootScope) {
  $scope.products = Product.query({items:$scope.items});
  $scope.items = 10;
  
  $scope.update = function(){
	  $scope.products = Product.query({q:$scope.query,region:$scope.region, items:$scope.items});
  };
}

function ConnectCtrl($scope, $routeParams, $rootScope, Connect, $location) {
	Connect.get({authtoken: $routeParams.authtoken}, function(token) {
      if (token) {
		  $rootScope.apikey = token;
		  $location.path('/products')
      }
    });
}

function ProfileCtrl($scope, Profile) {
	$scope.profile = Profile.query();
}

function ProductDetailCtrl($scope, $routeParams, Products) {
  $scope.product = Products.get({programId: $routeParams.productId});
}

