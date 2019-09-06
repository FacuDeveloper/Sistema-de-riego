app.controller(
  "GroundsCtrl",
  ["$scope","$location","$route","GroundSrv",
  function($scope, $location, $route, service) {
    console.log("GroundsCtrl loaded...")

    function findAll(){
  		service.findAll( function(error, data){
  			if(error){
  				alert("Ocurrió un error: " + error);
  				return;
  			}
  			$scope.data = data;
  		})
  	}

    var $ctrl = this;

    $scope.service = service;
    $scope.listElement = []
    $scope.cantPerPage = 20

    $scope.delete = function(id){

      console.log("Deleting: " + id)

  		service.delete(id, function(error, data){
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
