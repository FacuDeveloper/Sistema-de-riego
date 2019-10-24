/*
 * Clase de prueba unitaria creada para probar la clase ClimateClient que
 * contiene codigo fuente para realizar llamadas a la
 * API del clima Dark Sky
 *
 * Al usar la clase ClimateLogService para servir
 * los registros del clima puede parecer que no se
 * esta probando el codigo fuente de la clase ClimateClient
 * pero en realidad sí se lo esta probando porque la
 * clase ClimateLogService contiene una variable estatica
 * del tipo ClimateClient (clase) con la cual se realizan
 * llamadas a la API del clima Dark Sky, con lo cual
 * se esta probando (de fondo) que el codigo fuente
 * de la clase ClimateClient esta funcionando como
 * tiene que funcionar
 */

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import java.util.Calendar;

import stateless.ClimateLogService;

import model.ClimateLog;

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

    ClimateLogService climateLogService = ClimateLogService.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Puerto Madryn para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    ClimateLog climateLog = climateLogService.getClimateLog(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(climateLog);

    System.out.println(climateLog);
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

    ClimateLogService climateLogService = ClimateLogService.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Buenos Aires CABA para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    ClimateLog climateLog = climateLogService.getClimateLog(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(climateLog);

    System.out.println(climateLog);
    System.out.println();
  }

  /*
  * Bloque de codigo fuente para la
  * prueba untaria del modulo de obtencion
  * de datos climaticos utlizando las
  * coordenadas geograficas de Viedma
  * en la fecha 30/10/19
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
    * la fecha 30/10/19 se le tiene que pasar
    * como parametro la fecha 31/10/19 en formato
    * UNIX TIMESTAMP, la cual en dicho formato
    * es 1572912000
    */
    long dateUnixTimeStamp = 1572480000;
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

    ClimateLogService climateLogService = ClimateLogService.getInstance();

    System.out.println("Prueba unitaria de registro climático sobre Viedma para la fecha " +
    (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR)));
    System.out.println();

    ClimateLog climateLog = climateLogService.getClimateLog(latitude, longitude, dateUnixTimeStamp);
    assertNotNull(climateLog);

    System.out.println(climateLog);
    System.out.println();
  }

}
