'use strict';

/* Controllers */

function ProductListCtrl($scope, Product, $rootScope, $routeParams) {

  $scope.products = Product.query({items:$scope.items});
  $scope.items = 10;
  
  $scope.update = function(){
	  $scope.products = Product.query({q:$scope.query,region:$scope.region, items:$scope.items});
  };
}

function getQueryVar(varName){
    // Grab and unescape the query string - appending an '&' keeps the RegExp simple
    // for the sake of this example.
    var queryStr = unescape(window.location.search) + '&';

    // Dynamic replacement RegExp
    var regex = new RegExp('.*?[&\\?]' + varName + '=(.*?)&.*');

    // Apply RegExp to the query string
    var val = queryStr.replace(regex, "$1");

    // If the string is the same, we didn't find a match - return false
    return val == queryStr ? false : val;
}

function ConnectCtrl($scope, $routeParams, $rootScope, Connect, $location, $window) {
$scope.$location = $location;
$scope.$window = $window;
$scope.$watch('(location.search()).authtoken', function($routeParams) {
          
        var authtoken = getQueryVar('authtoken');
        if (authtoken) {
        Connect.get({authtoken: authtoken}, function(token) {
      if (token) {
		  $rootScope.apikey = token;
		  //$window.location.search = ''
		  $location.path('/products').search('')
      }
    });
    }
    }, true);
    if ($routeParams.authtoken) {
	
    }
}

function ProfileCtrl($scope, $rootScope, Profile) {
	$scope.profile = Profile.query();
	$rootScope.profile = $scope.profile;
}

function BalanceCtrl($scope, $rootScope, Balance) {
	$scope.balances = Balance.query();
}

function BankAccountsCtrl($scope, $rootScope, BankAccounts) {
	$scope.accounts = BankAccounts.query();
}

function ProgramApplicationsCtrl($scope, $rootScope, ProgramApplication) {
	$scope.programApplications = ProgramApplication.query();
}

function MyCtrl($scope, Report) {
    $scope.report = Report.query();
    $scope.data = [];
    $scope.report = Report.query({},{},
	    function(result) {
	      console.log(result);
	       angular.forEach(result.reportItems.reportItem, function(report) {
	      	$scope.data.push({label:report.month, value:report.total.clickCount, color:'#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6)});
	      })
	    });
   
    
    //usage stats for 05/2012 from http://www.w3schools.com/browsers/browsers_stats.asp
    /*$scope.data = [
        {label:'Chrome', value:39.3, color:'#39E639'},
        {label:'Firefox', value:35.2, color:'#FFA640'},
        {label:'IE', value:18.1, color:'#3CA0D0'},
        {label:'Safari', value:4.3, color:'#A60000'},
        {label:'Opera', value:2.2, color:'#BF3030'}
    ];*/
}

function HiChartCtrl($scope, Report) {
    $scope.report = Report.query();
    $scope.report = Report.query({},{},
	    function(result) {
	      console.log(result);
	      $scope.d = [];
	       angular.forEach(result.reportItems.reportItem, function(report) {
	      	$scope.d.push([report.month, report.total.clickCount]);
	      })
	      $scope.data = {name:'click', data:$scope.d};
	      console.log($scope.data);
	    });
	    
   
    
    //usage stats for 05/2012 from http://www.w3schools.com/browsers/browsers_stats.asp
    /*$scope.data = [
        {label:'Chrome', value:39.3, color:'#39E639'},
        {label:'Firefox', value:35.2, color:'#FFA640'},
        {label:'IE', value:18.1, color:'#3CA0D0'},
        {label:'Safari', value:4.3, color:'#A60000'},
        {label:'Opera', value:2.2, color:'#BF3030'}
    ];*/
}

function ProductDetailCtrl($scope, $routeParams, Products) {
  $scope.product = Products.get({programId: $routeParams.productId});
}

