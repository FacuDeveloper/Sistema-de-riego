var app = angular.module('app',['ngRoute', 'Pagination', 'ui.bootstrap', 'leaflet-directive']);

// ui.bootstrap es necesario para la busqueda que se hace cuando se ingresan caracteres
// Pagination es necesario para la paginacion

app.config(['$routeProvider', function(routeprovider) {
	routeprovider

	/* Cultivos */
	.when('/cultivos',{
		templateUrl:'partials/cultivos-list.html',
		controller: 'CultivosCtrl'
	})
	.when('/cultivo/:action',{
		templateUrl:'partials/cultivo-form.html',
		controller: 'CultivoCtrl'
	})
	.when('/cultivo/:action/:id',{
		templateUrl:'partials/cultivo-form.html',
		controller: 'CultivoCtrl'
    })

	/* Parcel */
	.when('/parcel',{
		templateUrl:'partials/parcel-list.html',
		controller: 'ParcelsCtrl'
	})

	.when('/parcel/:action',{
		templateUrl:'partials/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	.when('/parcel/:action/:id',{
		templateUrl:'partials/parcel-form.html',
		controller: 'ParcelCtrl'
	})

	/* Instancias de parcelas (registro historico de parcela) */
	.when('/instanciasparcelas',{
		templateUrl:'partials/instanciasparcelas-list.html',
		controller: 'InstanciasParcelasCtrl'
	})
	.when('/instanciaparcela/:action',{
		templateUrl:'partials/instanciaparcela-form.html',
		controller: 'InstanciaParcelaCtrl'
	})
	.when('/instanciaparcela/:action/:id',{
		templateUrl:'partials/instanciaparcela-form.html',
		controller: 'InstanciaParcelaCtrl'
	})

	/* Informes estadisticos */
	.when('/statisticalReport',{
		templateUrl:'partials/statisticalReports-list.html',
		controller: 'StatisticalReportsCtrl'
	})
	.when('/statisticalReport/:action',{
		templateUrl:'partials/statisticalReport-form.html',
		controller: 'StatisticalReportCtrl'
	})
	.when('/statisticalReport/:action/:id',{
		templateUrl:'partials/statisticalReport-form.html',
		controller: 'StatisticalReportCtrl'
	})

	/* De otra manera */
	.otherwise({
		templateUrl: 'partials/404.html'
	})
}])
