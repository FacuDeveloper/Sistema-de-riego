var app = angular.module('app',['ngRoute', 'Pagination', 'ui.bootstrap']);

// ui.bootstrap es necesario para la busqueda que se hace cuando se ingresan caracteres
// Pagination es para la paginacion

app.config(['$routeProvider', function(routeprovider) {

	routeprovider
	.when('/ground',{
		templateUrl:'partials/ground/ground-list.html',
		controller: 'GroundsCtrl'
	})

	.when('/ground/:action/:id',{
		templateUrl:'partials/ground/ground-form.html',
		controller: 'GroundCtrl'
	})

	.when('/ground/:action',{
		templateUrl:'partials/ground/ground-form.html',
		controller: 'GroundCtrl'
	})

	.otherwise({
		templateUrl: 'partials/404.html'
	})
}])
