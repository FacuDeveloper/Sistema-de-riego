var app = angular.module('app',['ngRoute', 'Pagination', 'ui.bootstrap', 'leaflet-directive']);

// ui.bootstrap es necesario para la busqueda que se hace cuando se ingresan caracteres
// Pagination es necesario para la paginacion

app.config(['$routeProvider', function(routeprovider) {
	routeprovider

	/* Ground */
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

	/* Field */
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

	.when('/field/:fieldId/parcel/:action',{
		templateUrl:'partials/field/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	.when('/field/:fieldId/parcel/:action/:id',{
		templateUrl:'partials/field/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	/* De otra manera */
	.otherwise({
		templateUrl: 'partials/404.html'
	})
}])
