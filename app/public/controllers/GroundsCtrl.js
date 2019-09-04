app.controller(
  "GroundsCtrl",
  ["$scope","$location","$route","GroundSrv",
  function($scope, $location, $route, servicio) {
    console.log("GroundsCtrl loaded...")

    function findAll(){
  		servicio.findAll( function(error, data){
  			if(error){
  				alert("Ocurrió un error: " + error);
  				return;
  			}
  			$scope.data = data;
  		})
  	}


    $scope.delete = function(id){

      console.log("Deleting: " + id)

  		servicio.delete(id, function(error, data){
  			if(error){
          console.log(error);
  				return;
  			}
  			$location.path("/ground");
        $route.reload()
  		});
  	}


  	findAll();
  }]);
