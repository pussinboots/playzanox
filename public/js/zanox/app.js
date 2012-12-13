'use strict';

/* App Module */
//'ui.directives.date',
angular.module('zanox', ['DatePicker', 'productFilters', 'productServices', 'AwesomeChartJS', 'HighchartJs' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/dashboard', { templateUrl : 'partials/zanox/dashboard.html', controller : DashBoardCtrl })
	        .when('/products', { templateUrl : 'partials/zanox/product-list.html', controller : ProductListCtrl })
	        .when('/profile', { templateUrl : 'partials/zanox/profile.html', controller : ProfileCtrl })
	        .when('/adspace', { templateUrl : 'partials/zanox/adspace.html', controller : AdspaceCtrl })
	        .when('/balance', { templateUrl : 'partials/zanox/balance.html', controller : BalanceCtrl })
	        .when('/account', { templateUrl : 'partials/zanox/account.html', controller : BankAccountsCtrl })
	        .when('/report', { templateUrl : 'partials/zanox/reporthichart.html', controller : HiChartCtrl })
	        .when('/reportawe', { templateUrl : 'partials/zanox/report.html', controller : MyCtrl })
	        .when('/programapplication', { templateUrl : 'partials/zanox/programapplication.html', controller : ProgramApplicationsCtrl })
	        .when('/product/:productId', { templateUrl : 'partials/zanox/product-detail.html', controller : ProductDetailCtrl })
	        .otherwise({ redirectTo : '/dashboard' });
} ]).config([ '$locationProvider', function($locationProvider) {

} ]).run(function($rootScope, Menue, $compile) {
	$rootScope.apikey = { "session" : { "connectId" : "580599047DF8F5311043", "secretKey" : "newURI", "sessionExpires" : "asdad", "sessionKey" : "asd" }};
	
    $rootScope.global = Menue.query();
    $rootScope.show = function(data) {
    	alert( 'post stuff in textbox: ' + data);
    }
}).factory('TokenHandler', function($rootScope) {
	var tokenHandler = {};

	tokenHandler.getConnectId = function() {
		return $rootScope.apikey.session.connectId;
	};

	tokenHandler.getSecretKey = function() {
		return $rootScope.apikey.session.secretKey;
	};

	tokenHandler.getTime = function() {
		return new Date();
	};

	tokenHandler.getNonce = function() {
		return new Date().getTime() + '' + new Date().getTime()
	};

	tokenHandler.getSignature = function(uri, time, nonce) {
		var phrase = uri + time.toGMTString() + nonce;
		console.log(phrase);
		var hash = CryptoJS.HmacSHA1(phrase, this.getSecretKey(), { asString : true });
		hash = CryptoJS.enc.Base64.stringify(hash);
		return (hash);
	};

	// wraps given actions of a resource to send auth token
	// with every request
	tokenHandler.wrapActions = function(resource, actions) {
		// copy original resource
		var wrappedResource = resource;
		// loop through actions and actually wrap them
		for ( var i = 0; i < actions.length; i++) {
			tokenWrapper(wrappedResource, actions[i]);
		}
		;
		// return modified copy of resource
		return wrappedResource;
	};

	/*// wraps resource action to send request with auth token
	var tokenWrapper = function(resource, action) {
		// copy original action
		resource['_' + action] = resource[action];
		// create new action wrapping the original
		// and sending token
		resource[action] = function(data, success, error) {
			return resource['_' + action](
			// call action with provided data and
			// appended access_token
			angular.extend({}, data || {}, { connectId : tokenHandler.getConnectId() }), success, error);
		};
	};*/

	// wraps given actions of a resource to send auth token
	// with every request
	tokenHandler.wrapActions = function(resource, actions, uris) {
	    if (!uris) {
	    	uris=[];
	    }
		// copy original resource
		var wrappedResource = resource;
		// loop through actions and actually wrap them
		for ( var i = 0; i < actions.length; i++) {
			tokenWrapper(wrappedResource, actions[i], uris[i]);
		}
		;
		// return modified copy of resource
		return wrappedResource;
	};
	
	var getAuthParams = function(uri) {
		if (uri) {
			var no = tokenHandler.getNonce();
			var ti = tokenHandler.getTime();
			console.log('nonce ' + no);
			console.log('secret key ' + tokenHandler.getSecretKey());
			console.log('ti ' + ti.toGMTString());
			console.log('signature ' + tokenHandler.getSignature(uri, ti, no));
			return { connectId : tokenHandler.getConnectId(), nonce : no, date : ti.toGMTString(), signature : tokenHandler.getSignature(uri, ti, no) }
		} else {
			return { connectId : tokenHandler.getConnectId()}
		}
	}

	// wraps resource action to send request with auth token
	var tokenWrapper = function(resource, action, uri) {
		// copy original action
		resource['_' + action] = resource[action];
		// create new action wrapping the original
		// and sending token

		resource[action] = function(data, header, success, error) {
			/*var no = tokenHandler.getNonce();
			var ti = tokenHandler.getTime();
			console.log('nonce ' + no);
			console.log('secret key ' + tokenHandler.getSecretKey());
			console.log('ti ' + ti.toGMTString());
			console.log('signature ' + tokenHandler.getSignature(uri, ti, no));*/
			return resource['_' + action](
			// call action with provided data and
			// appended access_token
			angular.extend({}, data || {}, getAuthParams(uri)),
			{d:'asd'},
			success, error);
		};
	};

	return tokenHandler;
});