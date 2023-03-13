var app = angular.module('assetregistrationsystem', [ 'ngRoute', 'ngResource' ]);

app.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl : '/template/home.html',
		controller : 'homeController'
	})
	.when('/list-all-assets', {
		templateUrl : '/template/listassets.html',
		controller : 'listAssetController'
	})
	.when('/register-new-asset',{
		templateUrl : '/template/assetregistration.html',
		controller : 'registerAssetController'
	})
	.when('/update-asset/:id',{
		templateUrl : '/template/assetupdation.html' ,
		controller : 'assetsDetailsController'
	})
	.when('/login', {
		templateUrl : '/login/login.html',
		controller : 'loginController'
	})
	.when('/logout', {
		templateUrl : '/login/login.html',
		controller : 'logoutController'
	})
	.otherwise({
		redirectTo : '/login',
	});
});

app.config(['$httpProvider', function($httpProvider) {
	// $httpProvider.interceptors.push('AuthInterceptor');
	  $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  }]);