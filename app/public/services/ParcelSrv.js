app.service(
  "ParcelSrv",
  [ "$http",
    function($http){

      this.find = function(fieldId, parcelId, callback){
		    $http.get("rest/field/" + fieldId + "/parcels/" + parcelId).then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.addParcelField = function(fieldId, data, callback){
		    $http.post("rest/field/" + fieldId + "/parcels", data)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      this.removeParcelField = function(fieldId, parcelId, callback){
        $http.delete("rest/field/" + fieldId + "/parcels/" + parcelId)
        .then(
			    function(result){
				    callback(false,result.data);
			    },
    			function(error){
    				callback(error);
    			});
	    }

      // TODO: Ver si eliminar o no, en caso de buscar por numero de identificacion
      /*
       * Este invoca a la clase de servicio REST
       * llamada FindTaskRestServlet
       */
      // this.findByName = function(name) {
      //   return $http.get("rest/parcels/?taskName=" + name);
      // }

      this.modifyParcelField = function(fieldId, data, callback){
        $http.put("rest/field/" + fieldId + "/parcels", data)
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
