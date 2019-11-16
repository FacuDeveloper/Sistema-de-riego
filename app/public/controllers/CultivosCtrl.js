app.controller(
	"CultivosCtrl",
	["$scope","$location","$route","CultivoSrv",
	function($scope, $location, $route, service) {
		console.log("Cargando CultivosCtrl...")

		function findAllCultivos(){
			service.findAllCultivos( function(error, cultivos){
				if(error){
					alert("Ocurrió un error: " + error);
					return;
				}
				$scope.cultivos = cultivos;
			})
		}

		// Esto es necesario para la paginacion
		var $ctrl = this;

		$scope.service = service;
		$scope.listElement = []
		$scope.cantPerPage = 20
		// Esto es necesario para la paginacion

		$scope.delete = function(id){
			// console.log("Eliminando: " + id)
			service.removeCultivo(id, function(error, cultivo){

				if(error){
					console.log(error);
					return;
				}
				$location.path("/cultivos");
				$route.reload();
			});
		}

		findAllCultivos();
	}]);
