app.controller(
  "InstanciaParcelaCtrl",
  ["$scope", "$location", "$route", "$routeParams","$filter", "InstanciaParcelaSrv","CultivoSrv", "ParcelSrv",
  function($scope,$location, $route, $params, $filter, servicio, servicioCultivo, parcelService) {
    console.log("InstanciaParcelaCtrl Cargando accion]: "+$params.action)

    /*
    Constantes utilizadas para comprobar si lo que
    viene de la aplicacion del lado del servidor, a la
    hora de crear o modificar una instante de parcela, es
    un error de fechas
     */
    const dateCrossoverError = 'DateCrossoverError';
    const dateOverlayError = 'DateOverlayError';

    if(['new','edit','view'].indexOf($params.action) == -1) {
      alert("Acción inválida: " + $params.action);
      $location.path("/instanciasparcelas");
    }

    function findInstanciaParcelaId(id) {
      servicio.findInstanciaParcelaId(id, function(error, inst) {
        if(error) {
          console.log(error);
          return;
        }

        $scope.instanciaParcela = angular.copy(inst);
        if ($scope.instanciaParcela.fechaSiembra != null) {
          $scope.instanciaParcela.fechaSiembra = new Date($scope.instanciaParcela.fechaSiembra);
        }

        if ($scope.instanciaParcela.fechaCosecha != null) {
          $scope.instanciaParcela.fechaCosecha = new Date($scope.instanciaParcela.fechaCosecha);
        }

      });
    }

    $scope.save = function() {
      /*
      Comprueba que los campos no esten vacios e impide
      que se ingresen los campos vacios
       */
      if (isNull($scope.instanciaParcela.fechaSiembra)) {
        alert("La fecha de siembra debe estar definida");
        return;
      }

      if (isNull($scope.instanciaParcela.parcel)) {
        alert("La parcela debe estar definida");
        return;
      }

      if (isNull($scope.instanciaParcela.cultivo)) {
        alert("El cultivo debe estar definido");
        return;
      }

      servicio.create($scope.instanciaParcela, function(error, data) {
        if(error) {
          alert(error.statusText);
          return;
        }

        /*
        Si la respuesta de parte del servidor al momento de crear
        una instancia de parcela es un error de fechas entonces
        se muestra un mensaje con el error que sucedio y no se
        persiste la nueva instancia de parcela
         */
        if (dateCrossoverError === data.name || dateOverlayError === data.name) {
          alert(data.description);
          return;
        }

        $scope.instanciaParcela = data;
        $location.path("/instanciasparcelas");
        $route.reload();
      });

      $location.path("/instanciasparcelas");
      $route.reload();
    }

    $scope.modify = function(instanciaParcela) {
      /*
      Comprueba que los campos de fecha no esten vacios
      e impide que se ingresen los campos vacios
       */
      if (isNull($scope.instanciaParcela.fechaSiembra) || isNull($scope.instanciaParcela.fechaCosecha)) {
        alert("Las fechas deben estar definidas");
        return;
      }

      servicio.modify($scope.instanciaParcela, function(error, data) {
        if(error) {
          console.log(error);
          return;
        }

        /*
        Si la respuesta de parte del servidor al momento de modificar
        una instancia de parcela es un error de fechas entonces
        se muestra un mensaje con el error que sucedio y no se
        persiste la instancia de parcela modificada
         */
        if (dateCrossoverError === data.name || dateOverlayError === data.name) {
          alert(data.description);
          return;
        }

        $scope.instanciaParcela.id = data.id;
        $scope.instanciaParcela.fechaSiembra = data.fechaSiembra;
        $scope.instanciaParcela.fechaCosecha = data.fechaCosecha;
        $scope.instanciaParcela.parcel = data.parcel;
        $scope.instanciaParcela.cultivo = data.cultivo;
      });

      $location.path("/instanciasparcelas")
      $route.reload();
    }

    $scope.cancel = function() {
      $location.path("/instanciasparcelas");
    }

    function findAllActiveCrops() {
      servicioCultivo.findAllActiveCrops( function(error, cultivos) {
        if(error) {
          alert("OCURRIO UN ERROR: " + error);
          return;
        }
        $scope.cultivos = cultivos;
      })
    }

    function findAllActive() {
      parcelService.findAllActive( function(error, parcelas) {
        if(error) {
          alert("OCURRIO UN ERROR: " + error);
          return;
        }
        $scope.parcelas = parcelas;
      })
    }

    function isNull(givenValue) {
      if (givenValue == null) {
        return true;
      }

      return false;
    }

    $scope.action = $params.action;

    if ($scope.action == 'new' || $scope.action == 'edit'|| $scope.action == 'view') {
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
