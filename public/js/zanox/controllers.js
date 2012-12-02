'use strict';

/* Controllers */

function ProductListCtrl($scope, Product) {
  $scope.products = Product.query({items:$scope.items});
  $scope.items = 10;
  
  $scope.update = function(){
	  $scope.products = Product.query({q:$scope.query,region:$scope.region, items:$scope.items});
  };
}


function ProductDetailCtrl($scope, $routeParams, Products) {
  $scope.product = Products.get({programId: $routeParams.productId});
}

