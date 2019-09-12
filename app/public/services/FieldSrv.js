app.service(
  "FieldSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/field").then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.find = function(id, callback){
		    $http.get("rest/field/" + id).then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.save = function(data, callback){
        $http.post("rest/field", data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.update = function(id, name, area, latitude, longitude, callback){
        console.log("Actualizando: " + id + " - " + name);
        $http({
          method:"PUT",
          url:"rest/field/"+id,
          params:{"name": name, "area": area, "latitude": latitude, "longitude": longitude} })
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.delete = function(id, callback){
        $http.delete("rest/field/" + id)
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
