/*
* Esta clase representa al cliente que hace una llamada a
* la API Dark Sky para obtener los datos climaticos de
* una ubicacion dada en una fecha dada
*/

package stateless;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import weatherApiClasses.ForecastResponse;

import model.Forecast;

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
  private Forecast forecast;

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

    /*
     * Variable utilizada para crear con los datos
     * climaticos que necesitamos el pronostico que
     * necesitamos, el cual va a ser almacenado en la
     * base de datos subyacente
     */
    forecast = new Forecast();
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
   * Obtiene el pronostico en base a una fecha y coordenadas geograficas
   *
   * @param  latitude en grados decimales
   * @param  longitude en grados decimales
   * @param  time en formato UNIX TIMESTAMP
   * @return el pronostico solicitado en la fecha y en las coordenadas geograficas
   * dadas
   */
  public Forecast getForecast(double latitude, double longitude, long time) {
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
      System.out.println("Exception in ClimateClient:- " + e);
    }

    ForecastResponse forecastResponse = gson.fromJson(resultApiCall, ForecastResponse.class);

    /*
     * Carga el objeto de tipo pronostico (Forecast en ingles),
     * que esta siendo referenciado por la variable de tipo
     * por referencia que contiene su referencia, con los
     * datos climaticos obtenidos de la llamada a la API
     * del clima Dark Sky
     */
    forecast.load(forecastResponse);
    return forecast;
  }

  /**
   * Este bloque de codigo fuente (metodo) retorna el URL completo
   * con la latitud, la longitud y la fecha dada en formato UNIX TIMESTAMP
   * para invocar a la API del clima llamada Dark Sky
   *
   * @param  latitude
   * @param  longitude
   * @param  time
   * @return URL completo para la invocacion de la API del clima utilizada (Dark Sky)
   */
  private String getCompleteWeatherUrl(double latitude, double longitude, long time) {
    return INCOMPLETE_WEATHER_URL + latitude + "," + longitude + "," + time + QUERY_STRING;
  }

}
