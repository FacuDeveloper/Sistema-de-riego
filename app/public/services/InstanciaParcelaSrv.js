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

}]);
