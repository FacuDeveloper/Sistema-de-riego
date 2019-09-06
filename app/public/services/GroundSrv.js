app.service(
  "GroundSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/ground/findAllGrounds").then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.searchByPage = function(search, page, cant, callback) {
				$http.get('rest/ground?page=' + page + '&cant=' + cant+ "&search="+JSON.stringify(search))
						.then(function(res) {
							return callback(false, res.data)
						}, function(err) {
							return callback(err.data)
						})
			}

      this.find = function(id, callback){
		    $http.get("rest/ground/" + id).then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }


      this.save = function(data, callback){
        $http.post("rest/ground", data)
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
        $http.put("rest/ground/" + data.id, data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      // this.update = function(id, dni, name, lastName, docketCode, callback){
      //   console.log("Actualizando: "+id+" - "+name);
      //   $http({
      //     method:"PUT",
      //     url:"rest/ground/"+id,
      //     params:{"dni": dni, "name": name, "lastName": lastName, "docketCode":docketCode} })
      //   .then(
			//     function(result){
			// 	    callback(false,result.data);
			//     },
    	// 		function(error){
    	// 			callback(error);
    	// 		});
	    // }

      this.delete = function(id, callback){
        $http.delete("rest/ground/" + id)
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
