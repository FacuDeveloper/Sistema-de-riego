app.controller(
  "ParcelsCtrl",
  ["$scope","$location","$route","ParcelSrv",
  function($scope, $location, $route, service) {
    console.log("ParcelsCtrl loaded...")

    function findAll(){
  		service.findAll( function(error, data){
  			if(error){
  				alert("Ocurrió un error: " + error);
  				return;
  			}
  			$scope.data = data;
  		})
  	}

    /* Esto ess necesario para la paginacion */
    var $ctrl = this;

    $scope.service = service;
    $scope.listElement = []
    $scope.cantPerPage = 20
    /* Esto ess necesario para la paginacion */

    $scope.delete = function(id){
      console.log("Deleting: " + id)

  		service.delete(id, function(error, data){
  			if(error){
          console.log(error);
  				return;
  			}
  			$location.path("/parcel");
        $route.reload()
  		});
  	}

  	findAll();
  }]);
