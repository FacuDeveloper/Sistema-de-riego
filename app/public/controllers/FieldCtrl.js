app.controller(
  "FieldCtrl",
  ["$scope", "$location", "$routeParams", "FieldSrv",
  function($scope, $location, $params, service) {

    var base64icon = "data:image/png;base64,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";

    // Variable necesaria para poder cargar el icono del marcador
    var icondata = { iconUrl: base64icon, iconAnchor: [19, 19], };

    $scope.markers = new Array();

    console.log("FieldCtrl loaded with action: " + $params.action)

    if(['new','edit','view'].indexOf($params.action) == -1){
      alert("Acción inválida: " + $params.action);
      $location.path("/field");
    }

    function find(id){
      service.find(id, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;

        // Elimina marcador existente en el arreglo
        $scope.markers.pop();

        /*
        Agrega un nuevo marcado al arreglo con las coordendas geograficas del dato
        (en este caso es un campo en particular) recuperado, logrando de esta forma
        ver el marcador cuando el usuario selecciona la opcion de visualizacion del
        campo que ha elegido
        */
        $scope.markers.push({
          lat: $scope.data.latitude,
          lng: $scope.data.longitude,
          message: "¡Soy un marcador y tu campo está acá!",
          icon: icondata
        });

        console.log($scope.data);
      });
    }

    $scope.save = function(){

      /*
      Las coordendas geograficas del marcador colocado son cargadas
      en los atributos latitud y longitud del campo a crear
      */
      $scope.data.latitude = $scope.markers[0].lat;
      $scope.data.longitude = $scope.markers[0].lng;

      service.save($scope.data, function(error, data){
        if(error){
          console.log(error);
          return;
        }
        $scope.data = data;
        $location.path("/field")
      });
    }

    $scope.update = function(){
      // console.log("Antes de la modificacion");
      // console.log("Latitud: " + $scope.data.latitude);
      // console.log("Longitud: " + $scope.data.longitude);

      // console.log("");

      /*
      Las coordendas geograficas del marcador desplazado por el usuario son cargadas
      en los atributos latitud y longitud del campo a modificar, el cual es seleccionado
      por el usuario
      */
      $scope.data.latitude = $scope.markers[0].lat;
      $scope.data.longitude = $scope.markers[0].lng;

      // console.log("Despues de la modificacion");
      // console.log("Latitud: " + $scope.data.latitude);
      // console.log("Longitud: " + $scope.data.longitude);

      service.update(
        $scope.data.id,
        $scope.data.name,
        $scope.data.area,
        $scope.data.latitude,
        $scope.data.longitude,
        function(error, data){
          if(error){
            console.log(error);
            return;
          }
          $scope.data = data;
          $location.path("/field")
        });
      }

      $scope.cancel = function(){
        $location.path("/field");
      }

      $scope.action = $params.action;

      if ($scope.action == 'edit' || $scope.action == 'view') {
        find($params.id);
      }

      angular.extend($scope, {
        Barcelona: {
          lat: 41.3825,
          lng: 2.176944,
          zoom: 12
        },
        Madrid: {
          lat: 40.095,
          lng: -3.823,
          zoom: 7
        },
        Madryn: {
          lat: -42.787220,
          lng: -65.066404,
          zoom: 4
        },
        position: {
          lat: 51,
          lng: 0
        },
        events: {}

      });

      $scope.$on("leafletDirectiveMap.click", function(event, args){
        var leafEvent = args.leafletEvent;
        $scope.markers.pop();
        $scope.markers.push({
          lat: leafEvent.latlng.lat,
          lng: leafEvent.latlng.lng,
          message: "¡Soy un marcador y tu campo está acá!",
          icon: icondata
        });

        // alert("Lat: " + $scope.markers[0].lat + " Long: " + $scope.markers[0].lng);
      });

    }]);
