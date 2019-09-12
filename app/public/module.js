var app = angular.module('app',['ngRoute', 'Pagination', 'ui.bootstrap', 'leaflet-directive']);

// ui.bootstrap es necesario para la busqueda que se hace cuando se ingresan caracteres
// Pagination es necesario para la paginacion

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

	.when('/field',{
		templateUrl:'partials/field/field-list.html',
		controller: 'FieldsCtrl'
	})

	.when('/field/:action',{
		templateUrl:'partials/field/field-form.html',
		controller: 'FieldCtrl'
	})

	.when('/field/:action/:id',{
		templateUrl:'partials/field/field-form.html',
		controller: 'FieldCtrl'
	})

	.otherwise({
		templateUrl: 'partials/404.html'
	})
}])
