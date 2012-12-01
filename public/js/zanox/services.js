'use strict';

/* Services */

//angular
//.module('productServices', ['ngResource'])
//    .factory('Product', function($resource){

//    return $resource('/assets/proxy/:path' , {}, {
//    	query : {method:'GET', params:{path:'https://api.zanox.com/json/2011-03-01/programs', connectid:'580599047DF8F5311043'}, isArray:false},
//    	get : {method:'GET', params:{path:'https://api.zanox.com/json/2011-03-01/programs', connectid:'580599047DF8F5311043'}, isArray:false}
//    	
//    });
//    
//	    return $resource('/assets/proxy/:path' , {}, {
//	    	query : {method:'GET', params:{path:'https://api.zanox.com/json/2011-03-01/programs', connectid:'580599047DF8F5311043'}, isArray:false},
//	    });
//	});
//	.factory('Products', function($resource){
//		return $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programs/program/:programId' , {}, {
//	    	get : {method:'GET', params:{connectid:'580599047DF8F5311043'}, isArray:false}
//	    	
//	    });
//	});

angular.module('productServices', ['ngResource'], function($provide) {
	  $provide.factory('Product', function($resource){
		    return $resource('/assets/proxy/:path' , {}, {
		    	query : {method:'GET', params:{path:'https://api.zanox.com/json/2011-03-01/programs', connectid:'580599047DF8F5311043'}, isArray:false},
		    });
		});
	  $provide.factory('Products', function($resource){
		  return $resource('/assets/proxy/https://api.zanox.com/json/2011-03-01/programs/program/:programId' , {}, {
		    	get : {method:'GET', params:{connectid:'580599047DF8F5311043'}, isArray:false}
		    });
		});
	});
