import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;

import stateless.et.ETc;

public class ETcTest {

  /*
   * Bloque de codigo fuente de prueba
   * unitaria para el calculo de la ETo
   * de la localidad Puerto Madryn
   * con los datos climaticos del dia
   * 31/10/19
   */
  @Ignore
  public void testGetEto() {
    System.out.println("Prueba unitaria de obtención de evapotranspiración del cultivo de referencia");
    System.out.println();

    // [°C]
    double temperatureMin =  13.27;
    double temperatureMax = 25.18;

    // Presion atmosferica en milibar
    double pressure = 1004.42;

    // Velocidad del viento a 10 metros de altura, en metros por segundo
    double windSpeed = 9.21;

    // Punto de rocío
    double dewPoint = -10.25;

    // Nubosidad
    double cloudCover = 0.84;

    // Radiacion solar extraterrestre diaria
    double extraterrestrialSolarRadiation = 34.7;

    // Insolacion maxima diaria
    double maximumInsolation = 13.2;

    // System.out.println("La ETo es: " + ETo.getEto(temperatureMin, temperatureMax, pressure, windSpeed, dewPoint, extraterrestrialSolarRadiation, maximumInsolation, cloudCover) + " (mm/día)");
  }

  @Test
  public void methodTest() {

  }

}