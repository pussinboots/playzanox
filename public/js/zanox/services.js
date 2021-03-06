'use strict';

/* Services */

angular.module('productServices', ['ngResource'], function($provide) {

	  $provide.factory('Product', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programs' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query", "update", "save"] );

  			return resource;
		});
		
		$provide.factory('Profile', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/profiles/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true, uri: 'GET/profiles/'},
		    	update: {method:'PUT', params:{}, encoding:true, uri: 'PUT/profiles/'}		    	
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query","update"] , ["GET/profiles/","PUT/profiles/"]);

  			return resource;
		});

	    $provide.factory('Balance', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/payments/balances/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query"] , ["GET/payments/balances/"]);

  			return resource;
		});	
		
		$provide.factory('BankAccounts', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/payments/bankaccounts/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query"] , ["GET/payments/bankaccounts/"]);

  			return resource;
		});
		
		$provide.factory('Adspace', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/adspaces/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query"] , ["GET/adspaces/"]);

  			return resource;
		});
		
		$provide.factory('ProgramApplication', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programapplications/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query"] , ["GET/programapplications/"]);

  			return resource;
		});
		$provide.factory('Report', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/reports/basic/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query"] , ["GET/reports/basic/"]);

  			return resource;
		});
		
	  $provide.factory('Products', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programs/program/:programId' , {}, {
		    	get : {method:'GET', params:{}, isArray:false, encoding:true}
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["get", "update", "save"] );

  			return resource;
		});
		
	  $provide.factory('Connect', function($resource){
		  return $resource('/assets/rest/connect' , {}, {
		    	get : {method:'GET', params:{}, isArray:false}
		    });
		});
		
	  $provide.factory('Menue', function($resource){
		  return $resource('/assets/global/menue.json' , {}, {
		    	query : {method:'GET', params:{}, isArray:false}
		    });
		});
	});
