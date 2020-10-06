app.controller(
  "ReportCtrl",
  ["$scope", "$location", "$route", "$routeParams", "$filter", "ReportSrv",
  function($scope, $location, $route, $params, $filter, servicio) {

    if(['view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/report");
    }

    function find(id){
      servicio.find(id, function(error, data){
        if(error){
          console.log(error);
          return;
        }

        $scope.data = angular.copy(data);
        $scope.data.date = new Date($scope.data.date);
      });
    }

    $scope.cancel = function(){
      $location.path("/report");
    }

    $scope.action = $params.action;

    if ($scope.action == 'view') {
      find($params.id);
    }

  }]);
