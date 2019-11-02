/*
* Esta clase es una clase cliente que tiene como simple responsabilidad
* brindar el código fuente necesario para realizar llamadas a
* la API REST (servicios web) del clima llamada Dark Sky y estas
* llamadas son necesarias para obtener el pronostico de una ubicacion
* geografica en una fecha dada, y es necesario obtener el
* pronostico porque contiene los datos necesarios para
* el calculo de la evapotranspiracion, la cual nos indica
* la cantidad de agua que va a evaporar un cultivo dado y
* al saber este dato sabremos la cantidad de agua
* que tendremos que reponer al cultivo mediante el riego
*/

package climate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import weatherApiClasses.ForecastResponse;

public class ClimateClient {

  /*
   * Variable de clase
   */
  private static ClimateClient climateClient;

  /*
   * Variables de instancia
   */
  private final String INCOMPLETE_WEATHER_URL;
  private final String QUERY_STRING;
  private Gson gson;

  /**
   * Metodo constructor privado para implementar
   * el patron de diseño Singleton
   */
  private ClimateClient() {
    /*
     * URL incompleto del servicio web a utilizar
     * para recuperar los datos climaticos en base
     * a una fecha y a unas coordenadas geograficas
     */
    INCOMPLETE_WEATHER_URL = "https://api.darksky.net/forecast/63dca785e324c5ffadd993bb157b1e1a/";

    /*
     * Cadena de consulta
     *
     * Esta cadena de consulta establece que el resultado
     * que devuelva la invocacion de la API del clima
     * llamada Dark Sky no tiene que contener los bloques
     * de datos currently, minutely, hourly y alerts, y
     * tambien establece las unidades de medidas, las cuales
     * en este caso son las SI
     */
    QUERY_STRING = "?exclude=currently,minutely,hourly,alerts&units=si";

    /*
     * Biblioteca creada por Google para convertir
     * formato JSON a Java y viceversa
     */
    gson = new Gson();
  }

  /**
   * Permite crear una unica instancia de
   * tipo ClimateClient (debido a que esta clase
   * tiene implementado el patron de diseño
   * Singleton) y permite obtener la referencia
   * a esa unica instancia de tipo ClimateClient
   *
   * @return referencia a un unico objeto de tipo ClimateClient
   * porque esta clase tiene implementado el patron de diseño
   * Singleton
   */
  public static ClimateClient getInstance() {
    if (climateClient == null) {
      climateClient = new ClimateClient();
    }

    return climateClient;
  }

  /**
   * Obtiene el pronostico para una fecha y unas coordenadas geograficas
   * dadas
   *
   * @param  latitude  [grados decimales]
   * @param  longitude [grados decimales]
   * @param  time      [UNIX TIMESTAMP]
   * @return pronostico obtenido en la fecha y en las coordenadas geograficas
   * dadas
   */
  public ForecastResponse getForecast(double latitude, double longitude, long time) {
    /*
     * Contendra el resultado de la llamada
     * a la API del clima utilizada (en este caso
     * es Dark Sky) en el formato devuelto por la
     * misma, el cual probablemente sea JSON
     */
    String resultApiCall = null;

    try {
      URL url = new URL(getCompleteWeatherUrl(latitude, longitude, time));

      HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
      httpUrlConnection.setRequestMethod("GET");
      httpUrlConnection.setRequestProperty("Accept", "application/json");

      /*
       * Si el codigo de respuesta del servidor sobre el cual
       * se invoca el servicio web deseado es distinto de 200
       * (ok, sin problemas) hay un problema, con lo cual
       * se lanza una excepcion
       */
      if (httpUrlConnection.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP Error code : " + httpUrlConnection.getResponseCode());
      }

      InputStreamReader inputStreamReader = new InputStreamReader(httpUrlConnection.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      resultApiCall = bufferedReader.readLine();

      /*
       * De esta forman se liberan estos
       * recursos utilizados lo que probablemente
       * libere el espacio de memoria que esten
       * utilizando estas variables
       */
      httpUrlConnection.disconnect();
      inputStreamReader.close();
    } catch (Exception e) {
      System.out.println("Exception in ClimateClient: " + e);
    }

    /*
     * Convierte de formato JSON a formato Java (en este case una clase
     * POJO), el pronostico obtenido en la fecha dada para las
     * coordenadas geograficas dadas
     */
    return gson.fromJson(resultApiCall, ForecastResponse.class);
  }

  /**
   * Este bloque de codigo fuente (metodo) retorna el URL completo
   * con la latitud, la longitud y la fecha dada en formato UNIX TIMESTAMP
   * para invocar a la API del clima llamada Dark Sky
   *
   * @param  latitude [grados decimales]
   * @param  longitude [grados decimales]
   * @param  time [UNIX TIMESTAMP]
   * @return URL completo para la invocacion de la API del clima utilizada (Dark Sky)
   */
  private String getCompleteWeatherUrl(double latitude, double longitude, long time) {
    return INCOMPLETE_WEATHER_URL + latitude + "," + longitude + "," + time + QUERY_STRING;
  }

}
