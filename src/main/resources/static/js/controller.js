app.controller('listAssetController',
	function($scope, $http, $location, $route) {

		$http({
			method : 'GET',
			url : 'http://localhost:9090/api/asset/'
		}).then(function(response) {
			$scope.assets = response.data;
		});

		$scope.editAsset = function(assetId) {
			$location.path("/update-asset/" + assetId);
		}

		$scope.deleteAsset = function(assetId) {
			$http({
				method : 'DELETE',
				url : 'http://localhost:9090/api/asset/' + assetId
			})
					.then(
							function(response) {
								$location.path("/list-all-assets");
								$route.reload();
							});
		}
	});

app.controller('registerAssetController', function($scope, $http, $location,
		$route) {

	$scope.submitAssetForm = function() {
		$http({
			method : 'POST',
			url : 'http://localhost:9090/api/asset/',
			data : $scope.asset,
		}).then(function(response) {
			$location.path("/list-all-assets");
			$route.reload();
		}, function(errResponse) {
			$scope.errorMessage = errResponse.data.errorMessage;
		});
	}

	$scope.resetForm = function() {
		$scope.asset = null;
	};
});

app.controller('assetsDetailsController',
	function($scope, $http, $location, $routeParams, $route) {

	$scope.assetId = $routeParams.id;

	$http({
		method : 'GET',
		url : 'http://localhost:9090/api/asset/' + $scope.assetId
	}).then(function(response) {
		$scope.asset = response.data;
	});

	$scope.submitAssetForm = function() {
		$http({
			method : 'PUT',
			url : 'http://localhost:9090/api/asset/'+ $scope.assetId,
			data : $scope.asset,
		}).then(function(response) {
			$location.path("/list-all-assets");
			$route.reload();
		},function(errResponse) {
			$scope.errorMessage = "Error while updating asset - Error Message: '"
					+ errResponse.data.errorMessage;
		});
	}

		$scope.resetForm = function() {
		$scope.asset = null;
	};
});