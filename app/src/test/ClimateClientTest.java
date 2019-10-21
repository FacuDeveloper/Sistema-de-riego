import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import java.util.Calendar;

import stateless.ClimateClient;

import model.Forecast;

public class ClimateClientTest {

  /*
   * Bloque de codigo fuente para la
   * prueba untaria del modulo de obtencion
   * de datos climaticos utlizando las
   * coordenadas geograficas de Puerto
   * Madryn en la fecha 31/10/19
   */
  @Test
  public void testForecastPuertoMadryn() {
    double latitude = -42.7683337;
    double longitude = -65.060855;

    /*
     * La API del clima utilizada brinda los
     * datos climaticos de una locacion dada
     * en una fecha anterior a la que se le pasa
     * como parametro de consulta (QUERY_STRING,
     * clase ClimateClient), con lo cual como se
     * quiere recuperar los datos climaticos de
     * la fecha 31/10/19 se le tiene que pasar
     * como parametro la fecha 1/11/19 en formato
     * UNIX TIMESTAMP, la cual en dicho formato
     * es 1572566400
     */
    long dateUnixTimeStamp = 1572566400;
    Calendar date = Calendar.getInstance();

    /*
     * Convierte los segundos a milisegundos en formato
     * long porque este metodo utiliza la fecha dada
     * por el formato UNIX TIMESTAMP en segundos,
     * en milisegundos en formato long
     *
     * Lo que se logra con esto es convertir la fecha
     * en formato UNIX TIMESTAMP a formato de año, mes
     * y dia
     */
    date.setTimeInMillis(dateUnixTimeStamp * 1000L);

    ClimateClient climateClient = ClimateClient.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Puerto Madryn para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    Forecast forecast = climateClient.getForecast(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(forecast);

    System.out.println(forecast);
    System.out.println();
  }

  /*
   * Bloque de codigo fuente para la
   * prueba untaria del modulo de obtencion
   * de datos climaticos utlizando las
   * coordenadas geograficas de Buenos
   * Aires CABA en la fecha 31/10/19
   */
  @Test
  public void testForecastBuenosAireCaba() {
    double latitude = -34.6156625;
    double longitude = -58.5033379;

    /*
     * La API del clima utilizada brinda los
     * datos climaticos de una locacion dada
     * en una fecha anterior a la que se le pasa
     * como parametro de consulta (QUERY_STRING,
     * clase ClimateClient), con lo cual como se
     * quiere recuperar los datos climaticos de
     * la fecha 31/10/19 se le tiene que pasar
     * como parametro la fecha 1/11/19 en formato
     * UNIX TIMESTAMP, la cual en dicho formato
     * es 1572566400
     */
    long dateUnixTimeStamp = 1572566400;
    Calendar date = Calendar.getInstance();

    /*
     * Convierte los segundos a milisegundos en formato
     * long porque este metodo utiliza la fecha dada
     * por el formato UNIX TIMESTAMP en segundos,
     * en milisegundos en formato long
     *
     * Lo que se logra con esto es convertir la fecha
     * en formato UNIX TIMESTAMP a formato de año, mes
     * y dia
     */
    date.setTimeInMillis(dateUnixTimeStamp * 1000L);

    ClimateClient climateClient = ClimateClient.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Buenos Aires CABA para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    Forecast forecast = climateClient.getForecast(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(forecast);

    System.out.println(forecast);
    System.out.println();
  }

  /*
   * Bloque de codigo fuente para la
   * prueba untaria del modulo de obtencion
   * de datos climaticos utlizando las
   * coordenadas geograficas de Viedma
   * en la fecha 4/11/19
   */
  @Test
  public void testForecastViedma() {
    double latitude = -40.8249902;
    double longitude = -63.0176492;

    /*
     * La API del clima utilizada brinda los
     * datos climaticos de una locacion dada
     * en una fecha anterior a la que se le pasa
     * como parametro de consulta (QUERY_STRING,
     * clase ClimateClient), con lo cual como se
     * quiere recuperar los datos climaticos de
     * la fecha 4/11/19 se le tiene que pasar
     * como parametro la fecha 5/11/19 en formato
     * UNIX TIMESTAMP, la cual en dicho formato
     * es 1572912000
     */
    long dateUnixTimeStamp = 1572912000;
    Calendar date = Calendar.getInstance();

    /*
     * Convierte los segundos a milisegundos en formato
     * long porque este metodo utiliza la fecha dada
     * por el formato UNIX TIMESTAMP en segundos,
     * en milisegundos en formato long
     *
     * Lo que se logra con esto es convertir la fecha
     * en formato UNIX TIMESTAMP a formato de año, mes
     * y dia
     */
    date.setTimeInMillis(dateUnixTimeStamp * 1000L);

    ClimateClient climateClient = ClimateClient.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Viedma para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    Forecast forecast = climateClient.getForecast(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(forecast);

    System.out.println(forecast);
    System.out.println();
  }

}
