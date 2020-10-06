app.controller(
	"ReportsCtrl",
	["$scope","$location","$route","ReportSrv", "ParcelSrv",
	function($scope, $location, $route, servicio, parcelSrv) {
		console.log("Cargando ReportsCtrl...")

		function findAll(){
			servicio.findAll( function(error, data){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.data = data;
			})
		}

		// Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
		$scope.findParcel = function(parcelName){
			return parcelSrv.findByName(parcelName).
			then(function (response) {
				var parcels = [];
				for (var i = 0; i < response.data.length; i++) {
					parcels.push(response.data[i]);
				}
				return parcels;
			});;
		}

		$scope.generateReport = function(){
			servicio.generateReport($scope.parcel.name, function(error, data){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.data = data;
			})
		}

		/*
		 * Trae el listado de todos los informes estadisticos que
		 * conocen a la parcela que tiene el nombre dado
		 */
		$scope.findReportsByParcelName = function(){
			servicio.findReportsByParcelName($scope.parcel.name, function(error, data){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.data = data;
			})
		}

		/*
		 * Trae el listado de todos los informes estadisticos
		 * cuando se presiona el boton "Reiniciar listado"
		 */
		$scope.reset = function(){
			servicio.findAll( function(error, data){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.data = data;
			})
		}

		findAll();
	}]);
