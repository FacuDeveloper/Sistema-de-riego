app.service("InstanciaParcelaSrv", ["$http", function($http) {

  this.findAll = function(callback) {
    $http.get("rest/instanciaParcela").then(
      function(result) {
        callback(false, result.data);
      },
      function(error) {
        callback(error);
      });
    };

    this.findInstancesParcelByParcelName = function(name, callback) {
      $http.get("rest/instanciaParcela/findInstancesParcelByParcelName/" + name).then(
        function(result) {
          callback(false, result.data);
        },
        function(error) {
          callback(error);
        });
      };

    this.findInstanciaParcelaId = function(id, callback) {
      $http.get("rest/instanciaParcela/" + id).then(
        function(result) {
          callback(false, result.data);
        },
        function(error) {
          callback(error);
        });
      };

      this.create = function(data, callback) {
        $http.post("rest/instanciaParcela", data)
        .then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
        };

    this.removeInstanciaParcela = function(id, callback) {
      $http.delete("rest/instanciaParcela/" + id).then(
        function(result) {
          callback(false, result.data);
        },
        function(error) {
          callback(error);
        });
      };

    this.modify = function(instanciaParcela, callback) {
      $http.put("rest/instanciaParcela/" + instanciaParcela.id, instanciaParcela)
      .then(
        function(result) {
          callback(false, result.data);
        },
        function(error) {
          callback(error);
        });
      };

      this.dateOverlayInModification = function(instanciaParcela, callback) {
        $http.put("rest/instanciaParcela/dateOverlayInModification", instanciaParcela)
        .then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
        };

    this.calcularRiego = function(id, callback){
      $http.get("rest/instanciaParcela/suggestedIrrigation/" + id).then(
        function(result){
          callback(false, result.data);
        },
        function(error){
          callback(error);
        });
    }

    this.findCurrentParcelInstance = function(idParcel, callback){
      $http.get("rest/instanciaParcela/findCurrentParcelInstance/" + idParcel).then(
        function(result){
          callback(false, result.data);
        },
        function(error){
          callback(error);
        });
    }

    this.checkStageCropLife = function(cultivo, fechaSiembra, fechaCosecha, callback) {

      if ((fechaSiembra != null) && (fechaCosecha != null)) {
        let nuevaFechaSiembra = fechaSiembra.getFullYear() + "-" + fechaSiembra.getMonth() + "-" + fechaSiembra.getDate();
        let nuevaFechaCosecha = fechaCosecha.getFullYear() + "-" + fechaCosecha.getMonth() + "-" + fechaCosecha.getDate();

        $http.get("rest/instanciaParcela/checkStageCropLife/" + cultivo.id + "?fechaSiembra=" + nuevaFechaSiembra + "&fechaCosecha=" + nuevaFechaCosecha)
        .then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });

        }

      };

      /*
      Comprueba si hay superposicion entre la fecha de
      siembra y la fecha de cosecha de la nueva instancia
      de parcela
       */
      this.overlapSeedDateHarvest = function(data, callback){
        $http.post("rest/instanciaParcela/overlapSeedDateHarvest", data)
        .then(
          function(result){
            callback(false, result.data);
          },
          function(error){
            callback(error);
          });
      }

      /*
      Comprueba si hay superposicion de fechas entre la
      nueva instancia de parcela y las demas instancias
      de parcela, todas estas y la primera de la misma
      parcela
       */
      this.dateOverlayInCreation = function(data, callback){
        $http.post("rest/instanciaParcela/dateOverlayInCreation", data)
        .then(
          function(result){
            callback(false, result.data);
          },
          function(error){
            callback(error);
          });
      }

    // this.checkStageCropLife = function(instanciaParcela, callback) {
    //   $http.get("rest/instanciaParcela/checkStageCropLife/" + instanciaParcela)
    //   .then(
    //     function(result) {
    //       callback(false, result.data);
    //     },
    //     function(error) {
    //       callback(error);
    //     });
    //   };

}]);
