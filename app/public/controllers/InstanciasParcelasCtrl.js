app.controller(
	"InstanciasParcelasCtrl",
	["$scope","$location","$route","InstanciaParcelaSrv", "IrrigationLogSrv", "ParcelSrv",
	function($scope, $location, $route, servicio, irrigationLogService, parcelSrv) {
		console.log("Cargando InstanciasParcelasCtrl...")

		function findAll(){
			servicio.findAll( function(error, instanciaParcelas){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.instanciaParcelas = instanciaParcelas;
			})
		}

		$scope.calcularRiego = function(id){
			// console.log("Calcular riego de : " + id)
			servicio.calcularRiego(id, function(error, irrigationLog){
				if(error){
					console.log(error);
					return;
				}

				/*
				Si esta sentencia no esta, no se puede ver el riego
				sugerido en el modal
				*/
				$scope.irrigationLog = irrigationLog;

				/*
				Esto impide que el modal no se desplegado
				*/
				// $location.path("/instanciasparcelas");
				// $route.reload();
			});
		}

		$scope.guardarRegistroRiego = function() {

			if ($scope.irrigationLog.irrigationDone >= 0) {
				irrigationLogService.save($scope.irrigationLog, function(error, irrigationLog){
					if(error){
						console.log(error);
						return;
					}
					$scope.irrigationLog = irrigationLog;
				});
			} else {
				alert("El riego realizado sólo puede ser mayor o igual a cero");
			}

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

		/*
		 * Trae el listado de las instancias de parcela que
		 * conocen a la parcela que tiene el nombre dado
		 */
		$scope.findInstancesParcelByParcelName = function(){
			servicio.findInstancesParcelByParcelName($scope.parcel.name, function(error, instanciaParcelas){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.instanciaParcelas = instanciaParcelas;
			})
		}

		/*
		 * Trae el listado de instancias de parcelas
		 * cuando se presiona el boton "Reiniciar listado"
		 */
		$scope.reset = function(){
			servicio.findAll( function(error, instanciaParcelas){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.instanciaParcelas = instanciaParcelas;
			})
		}

		findAll();
	}]);
