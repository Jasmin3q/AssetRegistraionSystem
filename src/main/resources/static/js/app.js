var app = angular.module('assetregistrationsystem', [ 'ngRoute', 'ngResource' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/list-all-assets', {
		templateUrl : '/template/listassets.html',
		controller : 'listAssetController'
	}).when('/register-new-asset',{
		templateUrl : '/template/assetregistration.html',
		controller : 'registerAssetController'
	}).when('/update-asset/:id',{
		templateUrl : '/template/assetupdation.html' ,
		controller : 'assetsDetailsController'
	}).otherwise({
		redirectTo : '/home',
		templateUrl : '/template/home.html',
	});
});