import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;

import stateless.et.ETo;
import stateless.et.ETc;

import stateless.ClimateLogService;

import model.ClimateLog;

public class ETcTest {

  /*
   * Bloque de codigo fuente de prueba unitaria
   * del bloque de codigo para el calculo de la
   * ETc para la localidad Uccle (Bruselas, Belgica)
   * con los datos climaticos del dia 6/7/19
   */
  @Test
  public void testGetEtc() {
    double latitude = 50.7903541;
    double longitude = 4.3272857;

    /*
     * Fecha 7/7/19 en formato UNIX TIMESTAMP
     *
     * A la API del clima Dark Sky se le envia una
     * fecha inmediatamente posterior a la fecha en
     * la cual se quieren recuperar los datos climaticos
     * porque recupera los datos climaticos de la fecha
     * inmediatamente anterior a la fecha que se le
     * envia como parametro de la cadena de consulta en
     * el URL
     *
     * Esto significa que si se le envia la fecha
     * 7/7/19 en formato UNIX TIMESTAMP nos va
     * devolver como resultado los datos climaticos
     * de la fecha anterior a la fecha que se le envio (7/7/19),
     * es decir, nos va a devolver los datos climaticos
     * de la fecha 6/7/19
     */
    long unixDate = 1562457600;

    System.out.println("Prueba unitaria del calculo de la ETc del tomate en Uccle (Bruselas, Belgica) con datos metereologicos medidos el 6 de Julio");
    System.out.println("Latitud: " + latitude + " (grados decimales)");
    System.out.println("Longitud: " + longitude + " (grados decimales)");
    System.out.println();

    ClimateLogService climateLogService = ClimateLogService.getInstance();
    ClimateLog choosenLog = climateLogService.getClimateLog(latitude, longitude, unixDate);

    System.out.println("*** Datos climáticos obtenidos para Uccle en el día 6 de Julio ***");
    System.out.println(choosenLog);
    System.out.println();

    // [°C]
    double temperatureMin = choosenLog.getTemperatureMin();
    double temperatureMax = choosenLog.getTemperatureMax();

    // Presion atmosferica en milibar convertida a kPa
    double pressure = choosenLog.getPressure();

    // Velocidad del viento a 10 metros de altura, en metros por segundo
    double windSpeed = choosenLog.getWindSpeed();

    // Punto de rocío [°C]
    double dewPoint = choosenLog.getDewPoint();

    // Nubosidad, entre 0 y 1
    double cloudCover = choosenLog.getCloudCover();

    // Radiacion solar extraterrestre diaria [MJ/metros cuadrados * dia]
    double extraterrestrialSolarRadiation = 40.2;

    // Insolacion maxima diaria [horas]
    double maximumInsolation = 15.7;

    // Coeficiente del cultivo tomate
    double tomatoInitialKc = 0.6;

    double eto = 0.0;
    eto = ETo.getEto(temperatureMin, temperatureMax, pressure, windSpeed, dewPoint, extraterrestrialSolarRadiation, maximumInsolation, cloudCover);

    System.out.println("La ETo es: " + eto + " (mm/día)");
    System.out.println("Coeficiente inicial (Kc inicial) del cultivo tomate: " + tomatoInitialKc + " (adimensional)");
    System.out.println("La ETc del tomate es: " + (tomatoInitialKc * eto) + " (mm/día)");
  }

  @Test
  public void methodTest() {

  }

}
