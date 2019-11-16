app.controller(
  "CultivoCtrl",
  ["$scope", "$location", "$route", "$routeParams","$filter", "CultivoSrv",
  function($scope, $location, $route, $params,$filter, servicio) {

    console.log("CultivoEditCtrl Cargando accion: "+$params.action)

    if(['new','edit','view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/cultivos");
    }

    function findCultivoId(id){
      servicio.findCultivoId(id, function(error, cultivo){
        if(error){
          console.log(error);
          return;
        }
        $scope.cultivo = cultivo;
      });
    }

    $scope.save = function(){
      if(!$scope.CultivoForm.$invalid) {
        servicio.createCultivo($scope.cultivo, function(error, cultivo){
          if(error){
            console.log(error);
            return;
          }
          $scope.cultivo = cultivo;
          $location.path("/cultivos");
        });
      } else {
        alert("NO SE PUEDE GUARDAR")
      }
    }

    $scope.changeCultivo =function (cultivo){
      servicio.changeCultivo($scope.cultivo, function(error, cultivo){
        if(error){
          console.log(error);
          return;
        }
        $scope.cultivo.numCultivo = cultivo.numCultivo;
        $scope.cultivo.kcInicial = cultivo.kcInicial;
        $scope.cultivo.kcMedio = cultivo.kcMedio;
        $scope.cultivo.kcFinal = cultivo.kcFinal;
        $scope.cultivo.nombre = cultivo.nombre;
        $scope.cultivo.tipoCultivo = cultivo.tipoCultivo;
        $scope.cultivo.etInicial = cultivo.etInicial;
        $scope.cultivo.etDesarrollo = cultivo.etDesarrollo;
        $scope.cultivo.etMedia = cultivo.etMedia;
        $scope.cultivo.etFinal = cultivo.etFinal;
        $location.path("/cultivos")
        $route.reload();
      });
    }

    $scope.cancel = function(){
      $location.path("/cultivos");
    }

    $scope.action = $params.action;

    if ($scope.action == 'edit' || $scope.action == 'view') {
      findCultivoId($params.id);
    }
    if ($scope.action == 'view') {
      $scope.bloquear = true;
    }
    else {
      $scope.bloquear = false;
    }

  }]);
