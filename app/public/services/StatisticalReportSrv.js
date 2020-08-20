app.service(
  "StatisticalReportSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/statisticalReport").then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.find = function(id, callback){
		    $http.get("rest/statisticalReport/" + id).then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.delete = function(id, callback){
        $http.delete("rest/statisticalReport/" + id)
        .then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.findStatisticalReportByParcelName = function(name, callback) {
        $http.get("rest/statisticalReport/findStatisticalReportByParcelName/" + name).then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
        };

        this.generateStatisticalReport = function(name, callback) {
          $http.get("rest/statisticalReport/generateStatisticalReport/" + name).then(
            function(result) {
              callback(false, result.data);
            },
            function(error) {
              callback(error);
            });
          };

    }
  ]);
