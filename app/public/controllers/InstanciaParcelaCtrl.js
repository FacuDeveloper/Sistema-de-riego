app.controller(
  "InstanciaParcelaCtrl",
  ["$scope", "$location", "$route", "$routeParams","$filter", "InstanciaParcelaSrv","CultivoSrv", "ParcelSrv",
  function($scope,$location, $route, $params, $filter, servicio, servicioCultivo, parcelService) {
    console.log("InstanciaParcelaCtrl Cargando accion]: "+$params.action)

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

        // console.log(inst);

        $scope.instanciaParcela = angular.copy(inst);
        if ($scope.instanciaParcela.fechaSiembra != null) {
          $scope.instanciaParcela.fechaSiembra = new Date($scope.instanciaParcela.fechaSiembra);
        }

        //($filter('date')
        if ($scope.instanciaParcela.fechaCosecha != null) {
          $scope.instanciaParcela.fechaCosecha = new Date($scope.instanciaParcela.fechaCosecha);
          // $scope.instanciaParcela.fechaCosecha = new Date($filter('date')($scope.instanciaParcela.fechaCosecha, "yyyy-MM-dd HH:mm:ss Z"));
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

      /*
      Si ya hay un registro historico de parcela de la parcela dada
      en el estado "En desarrollo", entonces no se tiene que crear
      el nuevo registro historico de parcela
       */
      servicio.findCurrentParcelInstance($scope.instanciaParcela.parcel.id, function(error, instanciaParcela) {
        /*
        Todo este bloque de codigo sin el bloque de codigo llamado
        findCurrentParcelInstance (el de la clase InstanciaParcelaServiceBean, Java)
        no sirve, ya que este bloque de codigo solo muestra el cartel que esta mas
        abajo y no hace nada mas que eso, mientras que el metodo Java mencionado
        utilizado en el metodo create de la InstanciaParcelaRestServlet permite
        determinar si se tiene que persistir o no la nueva instancia de parcela
         */

        if(error) {
          alert(error.statusText);
          return;
        }

        $scope.instanciaParcela = instanciaParcela;

        if ($scope.instanciaParcela != null) {
          alert("No está permitido crear una instancia de parcela habiendo otra en el estado 'En desarrollo' para la misma parcela");
          return;
        }

      });

      /*
      Si el ultimo registro historico de parcela esta en el estado
      "Finalizado" pero la fecha de siembra del nuevo registro
      historico de parcela esta detras de la fecha de cosecha del
      registro historico de parcela anterior, entonces no se tiene
      que crear el nuevo registro historico de parcela
       */

      servicio.createInstanciaParcela($scope.instanciaParcela, function(error, instanciaParcela) {
        // console.log("paso por crear instancia");
        if(error) {
          alert(error.statusText);
          return;
        }

        // console.log("paso por crear instancia luego del error");
        $scope.instanciaParcela = instanciaParcela;
        $location.path("/instanciasparcelas");
      });

    }

    $scope.changeInstanciaParcela = function(instanciaParcela) {
      /*
      Comprueba que los campos de fecha no esten vacios
      e impide que se ingresen los campos vacios
       */
      if (isNull($scope.instanciaParcela.fechaSiembra) || isNull($scope.instanciaParcela.fechaCosecha)) {
        alert("Las fechas deben estar definidas");
        return;
      }

      /*
      Si la fecha de siembra y la fecha de cosecha estan cruzadas
      o superpuestas no se realiza la modificacion
       */
      if ((firstDateAfterSecondDate($scope.instanciaParcela.fechaSiembra, $scope.instanciaParcela.fechaCosecha)) == true) {
        alert("La fecha de cosecha tiene que estar después de la fecha de siembra");
        return;
      }

      /*
      Comprobar que la diferencia de dias entre la fecha de siembra y
      la fecha de cosecha no sea mayor que la etapa de vida del cultivo
      dado
       */
      // servicio.checkStageCropLife($scope.instanciaParcela.cultivo, $scope.instanciaParcela.fechaSiembra, $scope.instanciaParcela.fechaCosecha, function(error, instanciaParcela) {
      //   if(error) {
      //     alert(error.statusText);
      //     return;
      //   }
      //
      //   let result = instanciaParcela;
      //   let totalDaysLife = $scope.instanciaParcela.cultivo.etInicial + $scope.instanciaParcela.cultivo.etDesarrollo + $scope.instanciaParcela.cultivo.etMedia + $scope.instanciaParcela.cultivo.etFinal;
      //
      //   if (result != null) {
      //     alert("La diferencia en días entre ambas fechas es mayor a la cantidad de días de la etapa de vida (" + totalDaysLife + ") del cultivo seleccionado");
      //     return;
      //   }
      //
      // });

      servicio.changeInstanciaParcela($scope.instanciaParcela, function(error, instanciaParcela) {
        if(error) {
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

    /*
    Comprueba si la primera fecha esta despues de
    la segunda fecha, es decir, si estan cruzadas o
    superpuestas
     */
    function firstDateAfterSecondDate(primeraFecha, segundaFecha) {
      /*
      Si la primera fecha es mayor o igual que la segunda fecha
      retorna verdadero, en caso contrario retorna falso
       */
      if (Date.parse(primeraFecha) >= Date.parse(segundaFecha)) {
        return true;
      }

      return false;
    }

    // function firstDateBeforeSecondDate(firstDate, secondDate) {
    //   /*
    //   Si la primera fecha es menor o igual que la segunda fecha
    //   retorna verdadero, en caso contrario retorna falso
    //    */
    //   if (Date.parse(secondDate) >= Date.parse(firstDate)) {
    //     return true;
    //   }
    //
    //   return false;
    // }

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
