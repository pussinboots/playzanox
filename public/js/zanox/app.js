'use strict';

/* App Module */

angular.module('zanox', ['productFilters', 'productServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/products', {templateUrl: 'partials/zanox/product-list.html',   controller: ProductListCtrl}).
      when('/product/:productId', {templateUrl: 'partials/zanox/product-detail.html', controller: ProductDetailCtrl}).
      otherwise({redirectTo: '/products'});
}]);
