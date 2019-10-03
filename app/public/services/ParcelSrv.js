app.service(
  "ParcelSrv",
  [ "$http",
    function($http){

	    this.findAll = function(callback){
		    $http.get("rest/parcel/findAllParcels").then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.searchByPage = function(search, page, cant, callback) {
				$http.get('rest/parcel?page=' + page + '&cant=' + cant+ "&search="+JSON.stringify(search))
						.then(function(res) {
							return callback(false, res.data)
						}, function(err) {
							return callback(err.data)
						})
			}

      this.find = function(id, callback){
		    $http.get("rest/parcel/" + id).then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.save = function(data, callback){
        $http.post("rest/parcel", data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.update = function(id, identificationNumber, area, latitude, longitude, callback){
        console.log("Actualizando: " + id + " - " + identificationNumber);
        $http({
          method:"PUT",
          url:"rest/parcel/"+id,
          params:{"identificationNumber": identificationNumber, "area": area, "latitude": latitude, "longitude": longitude} })
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.delete = function(id, callback){
        $http.delete("rest/parcel/" + id)
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
