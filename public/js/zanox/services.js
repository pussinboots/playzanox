'use strict';

/* Services */

angular.module('productServices', ['ngResource'], function($provide) {

	  $provide.factory('Product', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programs' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true},
		    });
		    
		    resource = TokenHandler.wrapActions( resource, ["query", "update", "save"] );

  			return resource;
		});
		
		$provide.factory('Profile', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/profiles/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true, uri: 'GET/profiles/'},
		    });
		    
		    resource = TokenHandler.wrapSignatureActions( resource, ["query"] , ["GET/profiles/"]);

  			return resource;
		});

	    $provide.factory('Balance', function($resource, TokenHandler){
		    var resource = $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/payments/balances/' , {}, {
		    	query : {method:'GET', params:{}, isArray:false, encoding:true},
		    });
		    
		    resource = TokenHandler.wrapSignatureActions( resource, ["query"] , ["GET/payments/balances/"]);

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
	});
