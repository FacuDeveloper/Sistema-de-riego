app.service("CultivoSrv", ["$http", function($http) {
  this.findAllCultivos = function(callback) {
    // console.log("indAllCultivos");
    $http.get("rest/cultivo/findAllCultivos").then(
      function(result) {
        // console.log("entro por ok" + result.data);
        callback(false, result.data);
      },
      function(error) {
        // console.log("entro por error" + error);
        callback(error);
      });
    };

    this.findCultivoId = function(id, callback) {
      $http.get("rest/cultivo/" + id).then(
        function(result) {
          callback(false, result.data);
        },
        function(error) {
          callback(error);
        });
      };

      this.findAllActiveCrops = function(callback){
        $http.get("rest/cultivo/findAllActiveCrops").then(
          function(result){
            callback(false, result.data);
          },
          function(error){
            callback(error);
          });
        }

      this.searchByPage = function(search, page, cant, callback) {
        $http.get('rest/cultivo?page=' + page + '&cant=' + cant+ "&search=" + JSON.stringify(search))
        .then(function(res) {
          return callback(false, res.data)
        }, function(err) {
          return callback(err.data)
        })
      }

      this.createCultivo = function(data, callback) {
        $http.post("rest/cultivo/", data)
        .then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
        };

        this.removeCultivo = function(id, callback) {
          $http.delete("rest/cultivo/" + id).then(
            function(result) {
              callback(false, result.data);
            },
            function(error) {
              callback(error);
            });
          };

          this.changeCultivo = function(cultivo, callback) {
            $http.put("rest/cultivo/" + cultivo.id, cultivo)
            .then(
              function(result) {
                callback(false, result.data);
              },
              function(error) {
                callback(error);
              });
            };
          }]);
