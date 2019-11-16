app.controller(
	"InstanciasParcelasCtrl",
	["$scope","$location","$route","InstanciaParcelaSrv", "IrrigationLogSrv",
	function($scope, $location, $route, servicio, irrigationLogService) {
		console.log("Cargando InstanciasParcelasCtrl...")

		function findAllInstanciasParcelas(){
			servicio.findAllInstanciasParcelas( function(error, instanciaParcelas){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.instanciaParcelas = instanciaParcelas;
			})
		}

		$scope.calcularRiego = function(id){
			console.log("Calcular riego de : " + id)
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
			irrigationLogService.save($scope.irrigationLog, function(error, irrigationLog){
				if(error){
					console.log(error);
					return;
				}
				$scope.irrigationLog = irrigationLog;
			});
		}

		findAllInstanciasParcelas();
	}]);
