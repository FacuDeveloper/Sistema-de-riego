app.controller(
  "ParcelCtrl",
  ["$scope", "$location", "$route", "$routeParams", "ParcelSrv",
  function($scope, $location, $route, $params, servicio) {

    console.log("ParcelCtrl loaded with action: "+ $params.action
    + " and Parcel: " + $params.fieldId);

    var back_path = "field/view/"+$params.fieldId;

    if(['add','edit','view','delete'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path(back_path);
    }

    function find(fieldId, parcelId){
      servicio.find(fieldId, parcelId, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;
      });
    }

    $scope.save = function(){
      servicio.addParcelField(
        $params.fieldId,
        $scope.data,
        function(error, data){
          if(error){
            console.log(error);
            return;
          }
          $scope.data = data;
          $location.path(back_path);
        });
      }

      $scope.delete = function(){
        servicio.removeParcelField(
          $params.fieldId,
          $params.id,
          function(error, data){
            if(error){
              console.log(error);
              return;
            }
            $scope.data = data;
            $location.path(back_path);
          });
        }

        $scope.update = function(){
          servicio.modifyParcelField(
            $params.fieldId,
            $scope.data,
            function(error, data){
              if(error){
                console.log(error);
                return;
              }
              $scope.data = data;
              $location.path(back_path);
            });
          }

          $scope.cancel = function(){
            $location.path(back_path);
          }

          $scope.action = $params.action;
          $scope.fieldId = $params.fieldId;

          if($params.action == 'delete'){
            $scope.delete();
            $location.path(back_path);
            $route.reload();
          }

          if($params.action == 'edit' || $params.action == 'view'){
            find($params.fieldId, $params.id);
          }

        }]);
