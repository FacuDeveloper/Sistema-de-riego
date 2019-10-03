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

	/* Parcel */
	.when('/parcel',{
		templateUrl:'partials/parcel/parcel-list.html',
		controller: 'ParcelsCtrl'
	})

	.when('/parcel/:action',{
		templateUrl:'partials/parcel/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	.when('/parcel/:action/:id',{
		templateUrl:'partials/parcel/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	/* De otra manera */
	.otherwise({
		templateUrl: 'partials/404.html'
	})
}])
