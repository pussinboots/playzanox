'use strict';

/* Controllers */

function ProductListCtrl($scope, Product) {
  $scope.products = Product.query();
  $scope.orderProp = 'age';
  
  $scope.update = function(){
	  $scope.products = Product.query({q:$scope.query});
	  $scope.orderProp = 'age';
//      return $scope.one * $scope.query;
      
  };
}

//PhoneListCtrl.$inject = ['$scope', 'Phone'];



function ProductDetailCtrl($scope, $routeParams, Products) {
  $scope.product = Products.get({programId: $routeParams.productId});
}

//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
