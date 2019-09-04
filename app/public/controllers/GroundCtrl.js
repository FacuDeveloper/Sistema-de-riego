app.controller(
  "GroundCtrl",
  ["$scope", "$location", "$routeParams", "GroundSrv",
  function($scope, $location, $params, servicio) {

    console.log("GroundCtrl loaded with action: "+$params.action)

    if(['new','edit','view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/ground");
    }

    function find(id){
      servicio.find(id, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;
      });
    }

    $scope.save = function(){
      servicio.save($scope.data, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;
        $location.path("/ground")
      });
    }

    $scope.update = function(data){
      console.log("UPDATE");
      servicio.update($scope.data, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data.id = data.id;
        $scope.data.depth = data.depth;
        $scope.data.stony = data.stony; // pedregosidad
        $location.path("/ground")
      });
    }

      $scope.cancel = function(){
        $location.path("/ground");
      }

      $scope.action = $params.action;

      if ($scope.action == 'edit' || $scope.action == 'view') {
        find($params.id);
      }

    }]);
