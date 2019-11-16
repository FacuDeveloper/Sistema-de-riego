app.service(
  "IrrigationLogSrv",
  [ "$http",
    function($http){

      this.save = function(data, callback){
        $http.post("rest/irrigationLog", data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

    }
  ]);
