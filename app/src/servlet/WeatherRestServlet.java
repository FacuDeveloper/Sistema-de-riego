package servlet;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.Forecast;

import stateless.ForecastServiceBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import weatherApiClasses.Datum;
import weatherApiClasses.ForecastResponse;
import weatherApiClasses.Daily;

/*
 * Esta clase de servicio web esta hecha para probar
 * la funcion de obtencion de datos climaticos
 */

@Path("/weather")
public class WeatherRestServlet {

  private final String INCOMPLETE_WEATHER_URL = "https://api.darksky.net/forecast/63dca785e324c5ffadd993bb157b1e1a/";
  private final String QUERY_STRING = "?exclude=currently,minutely,hourly,alerts&units=ca";
  private Client client = ClientBuilder.newClient();
  private WebTarget target;

  // inject a reference to the ForecastServiceBean slsb
  @EJB ForecastServiceBean service;

  // mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Forecast forecast = service.find(id);
    return mapper.writeValueAsString(forecast);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    CoordTime coordTime = mapper.readValue(json, CoordTime.class);

    Forecast forecast = getForecast(coordTime.getLatitude(), coordTime.getLongitude(), coordTime.getTime());
    forecast = service.create(forecast);
    return mapper.writeValueAsString(forecast);
  }

  // TODO: Quizas vengas un Calendar en lugar de time para convertirlo en UNIX timestamp

  /**
   * Los datos necesarios del pronostico provisto por la API del clima
   * Dark Sky son extraidos y almacenados en una variable de tipo Forecast
   *
   * Extraemos algunos datos del pronostico provisto por la API
   * del clima porque no los necesitamos a todos
   *
   * @param  latitude
   * @param  longitude
   * @param  time
   * @return pronostico con los datos necesarios del pronostico provisto
   * por la solicitud a la API del clima Dark Sky con la ubicacion geografica
   * dada en la fecha dada (time)
   */
  public Forecast getForecast(double latitude, double longitude, long time) {
      ForecastResponse forecastResponse = requestForecast(latitude, longitude, time);

      Forecast forecast = new Forecast();

      /*
       * Se extraen los datos necesarios del pronostico
       * provisto por la API del clima Dark Sky
       */
      forecast.load(forecastResponse);

      return forecast;
  }

  /**
   * @param  latitude
   * @param  longitude
   * @param  time
   * @return pronostico solicitado a la API del clima Dark Sky para la ubicacion
   * geografica dada en la fecha dada (time)
   */
  private ForecastResponse requestForecast(double latitude, double longitude, long time) {
      target = client.target(getCompleteWeatherUrl(latitude, longitude, time));
      return target.request(MediaType.APPLICATION_JSON).get(ForecastResponse.class);
  }

  /**
   * @param  latitude
   * @param  longitude
   * @param  time
   * @return URL completo para la invocacion de la API del clima utilizada (Dark Sky)
   */
  private String getCompleteWeatherUrl(double latitude, double longitude, long time) {
    return INCOMPLETE_WEATHER_URL + latitude + "," + longitude + "," + time + QUERY_STRING;
  }

}
