(function() {

	var app = angular.module("conSearchApp", [], function($httpProvider) {
		// Use x-www-form-urlencoded Content-Type
		$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';

		/**
		 * The workhorse; converts an object to
		 * x-www-form-urlencoded serialization.
		 * 
		 * @param {Object}
		 *            obj
		 * @return {String}
		 */
		var param = function(obj) {
			var query = '', name, value, fullSubName, subName, subValue, innerObj, i;

			for (name in obj) {
				value = obj[name];

				if (value instanceof Array) {
					for (i = 0; i < value.length; ++i) {
						subValue = value[i];
						fullSubName = name + '[' + i + ']';
						innerObj = {};
						innerObj[fullSubName] = subValue;
						query += param(innerObj) + '&';
					}
				} else if (value instanceof Object) {
					for (subName in value) {
						subValue = value[subName];
						fullSubName = name + '[' + subName
								+ ']';
						innerObj = {};
						innerObj[fullSubName] = subValue;
						query += param(innerObj) + '&';
					}
				} else if (value !== undefined
						&& value !== null)
					query += encodeURIComponent(name) + '='
							+ encodeURIComponent(value) + '&';
			}

			return query.length ? query.substr(0,
					query.length - 1) : query;
		};

		// Override $http service's default transformRequest
		$httpProvider.defaults.transformRequest = [ function(data) {
			return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
		} ];
	});

	app.controller("searchController", function($scope, $http) {
		$scope.searchText = "";
		$scope.conventions = "";
		$scope.startDate = "";
		$scope.endDate = "";
		$scope.keywords = "";
		$scope.changeSearch = function() {
			if ($scope.searchText.length >= 2) {
				var req = new XMLHttpRequest();
				req.open('GET', '/useless', false);
				req.send(null);
				var csrfToken = req.getResponseHeader('X-CSRF-TOKEN');
				
				$http.post("/conventions", {
					searchText : $scope.searchText,
					_csrf : csrfToken
				}).success(function(data, status, headers, config) {
					$scope.conventions = data.conventions;
					$scope.startDate = data.startDate;
					$scope.endDate = data.endDate;
					$scope.keywords = data.keywords;
				}).error(function(data, status, headers, config) {
					$scope.conventions = "";
					$scope.startDate = "";
					$scope.endDate = "";
					$scope.keywords = "";
				});
			} else {
				$scope.conventions = "";
				$scope.conventions = "";
				$scope.startDate = "";
				$scope.endDate = "";
				$scope.keywords = "";
			}
		}

	});

	$(document).ready(
			function() {
				$("#conSearchText").Watermark(
						"Search by name, category, and/or location");
			});
})();