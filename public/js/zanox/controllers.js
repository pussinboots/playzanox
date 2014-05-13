'use strict';

/* Controllers */

function debounce(fn, delay) {
  var timer = null;
  return function () {
    var context = this, args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  };
}

function ProductListCtrl($scope, Product) {
	$scope.products = Product.query({items:$scope.items});
	$scope.items = 10;  
	$scope.$watch('query', debounce(function() {
        $scope.products = Product.query({q:$scope.query,region:$scope.region, items:$scope.items});
        $scope.$apply();
    }, 1000));
}

function DashBoardCtrl($scope, $rootScope, Balance, BankAccounts, Profile) {
	var balances = Balance.query(); 
	var accounts = BankAccounts.query();
	var profile = Profile.query({}, {}, function(result) {
		var audioword = new Audio("http://imedia-ventures.com/taptospeak/test.php?q=Hello " + result.profileItem[0].firstName);
    	audioword.play();
    });
    
    $rootScope.profile = profile;
	
	$scope.dashboard = {balance:balances, account:accounts};
}

function getQueryVar(varName){
	// Grab and unescape the query string - appending an '&' keeps the RegExp
	// simple
	// for the sake of this example.
	var queryStr = unescape(window.location.search) + '&';

	// Dynamic replacement RegExp
	var regex = new RegExp('.*?[&\\?]' + varName + '=(.*?)&.*');

	// Apply RegExp to the query string
	var val = queryStr.replace(regex, "$1");

	// If the string is the same, we didn't find a match - return false
	return val == queryStr ? false : val;
}

function ConnectCtrl($scope, $routeParams, $rootScope, Connect, $location) {
	$scope.$watch('(location.search()).authtoken', function($routeParams) {

		var authtoken = getQueryVar('authtoken');
		if (authtoken) {
			Connect.get({authtoken: authtoken}, function(token) {
				if (token) {
					$rootScope.apikey = token;
					// $window.location.search = ''
					$location.path('/products').search('')
				}
			});
		}
	}, true);
}

function ProfileCtrl($scope, $rootScope, Profile) {
	$rootScope.profile = Profile.query();
	$scope.update = function(profile){
		Profile.update({profileItem:profile.profileItem[0]});
	}
}

function BalanceCtrl($scope, $rootScope, Balance) {
	$scope.balances = Balance.query();
}

function AdspaceCtrl($scope, $rootScope, Adspace) {
	$scope.adspaces = Adspace.query();
}

function BankAccountsCtrl($scope, $rootScope, BankAccounts) {
	$scope.accounts = BankAccounts.query();
}

function ProgramApplicationsCtrl($scope, $rootScope, ProgramApplication) {
	$scope.programApplications = ProgramApplication.query();
}

function MyCtrl($scope, $routeParams, Report) {
	$scope.data = [];
	$scope.report = Report.query({currency:'EUR',groupby:'month',fromdate:'2012-01-01', todate:'2012-12-02'},{},
			function(result) {
		console.log(result);
		angular.forEach(result.reportItems.reportItem, function(report) {
			$scope.data.push({label:report.month, value:report.total.clickCount, color:'#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6)});
		})
	}
	);
}

function HiChartCtrl($scope, Report) {
 	$scope.fromDate = '2012-01-01'
 	$scope.toDate = '2012-12-31'
 	$scope.update = function() {
	$scope.report = Report.query({currency:'EUR',groupby:'month',fromdate:$scope.fromDate, todate:$scope.toDate},{},
			function(result) {
		console.log(result);
		$scope.clicks = [];
		$scope.views = [];
		angular.forEach(result.reportItems.reportItem, function(report) {
			$scope.clicks.push([report.month, report.total.clickCount]);
			$scope.views.push([report.month, report.total.viewCount]);
		})
		$scope.data = [{name:'click', data:$scope.clicks},{name:'view', data:$scope.views}];
		console.log($scope.data);
	}
	);
	}
	$scope.update();
}

function ProductDetailCtrl($scope, $routeParams, Products) {
	$scope.product = Products.get({programId: $routeParams.productId});
}