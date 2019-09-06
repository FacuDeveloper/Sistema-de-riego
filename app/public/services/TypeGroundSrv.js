app.service(
  "TypeGroundSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/typeGround").then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.find = function(id, callback){
		    $http.get("rest/typeGround/" + id).then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }


      this.save = function(data, callback){
        $http.post("rest/typeGround", data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.update = function(data, callback){
        console.log("Update");
        $http.put("rest/typeGround/" + data.id, data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.delete = function(id, callback){
        $http.delete("rest/typeGround/" + id)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      // Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
      this.findByTextureName = function(name) {
				return $http.get("rest/typeGround/?textureName="+name);
			}


    }
  ]);
