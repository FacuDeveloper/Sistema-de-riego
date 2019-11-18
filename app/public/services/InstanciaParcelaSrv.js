app.service("InstanciaParcelaSrv", ["$http", function($http) {

  this.findAllInstanciasParcelas = function(callback) {
    $http.get("rest/instanciaParcela").then(
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

    this.createInstanciaParcela = function(data, callback) {
      // console.log("antes de hacer el post"+ data);
      $http.post(
        "rest/instanciaParcela",data)

        .then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
          // console.log("saleindo del de hacer el post"+ data);
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

    this.changeInstanciaParcela = function(instanciaParcela, callback) {
      $http.put("rest/instanciaParcela/" + instanciaParcela.id, instanciaParcela)
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
