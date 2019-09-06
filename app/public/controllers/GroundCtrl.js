app.controller(
  "GroundCtrl",
  ["$scope", "$location", "$routeParams", "GroundSrv", "TypeGroundSrv",
  function($scope, $location, $params, service, typeGroundSrv) {

    console.log("GroundCtrl loaded with action: "+$params.action)

    if(['new','edit','view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/ground");
    }

    function find(id){
      service.find(id, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;
      });
    }

    $scope.save = function(){
      service.save($scope.data, function(error, data){
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
      service.update($scope.data, function(error, data){
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

    // Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
    $scope.findTypeGround = function(textureName){
      return typeGroundSrv.findByTextureName(textureName).
      then(function (response) {
        var typesGround = [];
        for (var i = 0; i < response.data.length; i++) {
          typesGround.push(response.data[i]);
        }
        return typesGround;
      });;
    }

      $scope.cancel = function(){
        $location.path("/ground");
      }

      $scope.action = $params.action;

      if ($scope.action == 'edit' || $scope.action == 'view') {
        find($params.id);
      }

    }]);
