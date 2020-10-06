app.service(
  "ReportSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/report").then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.find = function(id, callback){
		    $http.get("rest/report/" + id).then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.delete = function(id, callback){
        $http.delete("rest/report/" + id)
        .then(
			    function(result){
				    callback(false, result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.findReportsByParcelName = function(name, callback) {
        $http.get("rest/report/findReportsByParcelName/" + name).then(
          function(result) {
            callback(false, result.data);
          },
          function(error) {
            callback(error);
          });
        };

        this.generateReport = function(name, callback) {
          $http.get("rest/report/generateReport/" + name).then(
            function(result) {
              callback(false, result.data);
            },
            function(error) {
              callback(error);
            });
          };

    }
  ]);
