app.controller(
  "InstanciaParcelaCtrl",
  ["$scope", "$location", "$route", "$routeParams","$filter", "InstanciaParcelaSrv","CultivoSrv", "ParcelSrv",
  function($scope,$location, $route, $params, $filter, servicio, servicioCultivo, parcelService) {
    console.log("InstanciaParcelaCtrl Cargando accion]: "+$params.action)

    if(['new','edit','view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/instanciasparcelas");
    }

    function findInstanciaParcelaId(id){
      servicio.findInstanciaParcelaId(id, function(error, inst){
        if(error){
          console.log(error);
          return;
        }
        console.log(inst);

        $scope.instanciaParcela = angular.copy(inst);
        if ($scope.instanciaParcela.fechaSiembra != null){
          $scope.instanciaParcela.fechaSiembra = new Date($scope.instanciaParcela.fechaSiembra);
        }
        //($filter('date')
        if ($scope.instanciaParcela.fechaCosecha != null){
          $scope.instanciaParcela.fechaCosecha = new Date($scope.instanciaParcela.fechaCosecha);
          // $scope.instanciaParcela.fechaCosecha = new Date($filter('date')($scope.instanciaParcela.fechaCosecha, "yyyy-MM-dd HH:mm:ss Z"));
        }

      });
    }

    $scope.save = function(){
      servicio.createInstanciaParcela($scope.instanciaParcela, function(error, instanciaParcela){
        console.log("paso por crear instancia");
        if(error){
          alert(error.statusText);
          return;
        }
        // console.log("paso por crear instancia luego del error");
        $scope.instanciaParcela = instanciaParcela;
        $location.path("/instanciasparcelas");
      });
    }

    $scope.changeInstanciaParcela =function (instanciaParcela){
      servicio.changeInstanciaParcela($scope.instanciaParcela, function(error, instanciaParcela){
        if(error){
          console.log(error);
          return;
        }
        $scope.instanciaParcela.id = instanciaParcela.id;
        $scope.instanciaParcela.fechaSiembra = instanciaParcela.fechaSiembra;
        $scope.instanciaParcela.fechaCosecha = instanciaParcela.fechaCosecha;
        $scope.instanciaParcela.cultivo = instanciaParcela.cultivo;
        $scope.instanciaParcela.parcel = instanciaParcela.parcel;
        $location.path("/instanciasparcelas")
        $route.reload();
      });
    }

    $scope.cancel = function(){
      $location.path("/instanciasparcelas");
    }

    // function findAllCultivos(){
    //   servicioCultivo.findAllCultivos( function(error, cultivos){
    //     if(error){
    //       alert("OCURRIO UN ERROR: " + error);
    //       return;
    //     }
    //     //alert ("paso por el cultivo de instancia");
    //     $scope.cultivos = cultivos;
    //   })
    // }

    function findAllActiveCrops(){
      servicioCultivo.findAllActiveCrops( function(error, cultivos){
        if(error){
          alert("OCURRIO UN ERROR: " + error);
          return;
        }
        $scope.cultivos = cultivos;
      })
    }

    function findAllActive(){
      parcelService.findAllActive( function(error, parcelas){
        if(error){
          alert("OCURRIO UN ERROR: " + error);
          return;
        }
        $scope.parcelas = parcelas;
      })
    }

    $scope.action = $params.action;

    if ($scope.action == 'new' || $scope.action == 'edit'|| $scope.action == 'view'){
      findAllActive();
      findAllActiveCrops();
    }

    if ($scope.action == 'edit' || $scope.action == 'view') {
      findInstanciaParcelaId($params.id);
    }

    if ($scope.action == 'view') {
      $scope.bloquear=true;
    }
    else {
      $scope.bloquear=false;
    }

  }]);
