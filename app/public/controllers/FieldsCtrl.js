app.controller(
  "FieldsCtrl",
  ["$scope","$location","$route","FieldSrv",
  function($scope, $location, $route, service) {
    console.log("FieldsCtrl loaded...")

    function findAll(){
  		service.findAll( function(error, data){
  			if(error){
  				alert("Ocurrió un error: " + error);
  				return;
  			}
  			$scope.data = data;
  		})
  	}

    $scope.delete = function(id){
      console.log("Deleting: " + id)

  		service.delete(id, function(error, data){
  			if(error){
          console.log(error);
  				return;
  			}
  			$location.path("/field");
        $route.reload()
  		});
  	}

  	findAll();
  }]);
