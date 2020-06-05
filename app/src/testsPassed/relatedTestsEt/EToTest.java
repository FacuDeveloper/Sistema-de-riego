import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import et.Eto;

import stateless.ParcelServiceBean;

import model.ClimateLog;
import model.Parcel;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EToTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static ParcelServiceBean parcelService;

  @BeforeClass
  public static void preTest() {
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * del bloque de codigo para el calculo de la
   * ETo para la localidad Uccle (Bruselas, Belgica)
   * con los datos climaticos del dia 6/7/19, los
   * cuales son provistos por el ejemplo de la pagina
   * 72 del libro FAO numero 56
   */
  @Test
  public void testETo() {
    /*
     * [°C]
     */
    double temperatureMin = 12.3;
    double temperatureMax = 21.5;

    /*
     * Presion atmosferica [P]
     *
     * El valor de presion atmosferica tiene que
     * estar en hectopascales porque el servicio climatico
     * que utilizamos nos la provee en la unidad de
     * medida mencionada, con lo cual tuvimos
     * que implementar un bloque de codigo fuente
     * que reciba la presion atmosferica en la
     * unidad de medida mencionada y la convierta
     * en kilopascales, que es la unidad de medida
     * en la cual la presion atmosferica es utilizada
     * en la formula de la ETo
     *
     * La presion atmosferica del ejemplo del libro
     * esta en kilopascales pero para que el metodo
     * getEto() devuelva el resultado correcto se le
     * tiene que pasar la presion atmosferica en hectopascales
     * para que el bloque de codigo encargado de la conversion
     * la convierta de hectopascales a kilopascales
     */
    double pressure = 1001;

    /*
     * Velocidad del viento [metros por segundo]
     *
     * Este valor tiene que estar en la unidad
     * de medidad metros por segundo porque la
     * formula de la ETo utiliza la velocidad del
     * viento en metros por segundo
     *
     * Para ver la formula que utiliza la velocidad
     * del viento dirigase a la pagina numero 56 del
     * libro FAO numero 56
     *
     * El ejemplo de la pagina numero 72 del libro
     * FAO numero 56 da este valor en metros por
     * segundo
     */
    double windSpeed = 2.78;

    /*
     * Duracion real de la insolacion (n) [horas]
     *
     * Para ver la formula de la radiacion solar (Rs)
     * dirigase a la pagina numero 50 del libro FAO 56
     */
    double cloudCover = 9.25;

    /*
     * Duracion maxima posible de insolacion (N) [horas]
     */
    double maximumInsolation = 16.1;

    /*
     * Radiacion solar extraterrestre (Ra) [MJ/metro cuadrado * dia]
     */
    double extraterrestrialSolarRadiation = 41.09;

    /*
     * Punto de rocio [°C]
     *
     * Este valor es cero porque el ejemplo de la
     * pagina numero 72 del libro FAO numero 56
     * no utiliza la temperatura del punto de rocio
     * para calcular la ETo (evapotranspiracion
     * del cultivo de referencia)
     */
    double dewPoint = 0.0;

    Calendar currentDate = Calendar.getInstance();
    Parcel givenParcel = parcelService.find(1);

    ClimateLog givenClimateLog = new ClimateLog();
    givenClimateLog.setDate(currentDate);
    givenClimateLog.setTimezone("América/Argentina/Buenos Aires");
    givenClimateLog.setPrecipIntensity(0.0);
    givenClimateLog.setPrecipProbability(0.0);
    givenClimateLog.setDewPoint(dewPoint);
    givenClimateLog.setPressure(pressure);
    givenClimateLog.setWindSpeed(windSpeed);
    givenClimateLog.setCloudCover(cloudCover);
    givenClimateLog.setTemperatureMin(temperatureMin);
    givenClimateLog.setTemperatureMax(temperatureMax);
    givenClimateLog.setRainWater(0.0);
    givenClimateLog.setWaterAccumulated(0.0);
    givenClimateLog.setEto(0.0);
    givenClimateLog.setEtc(0.0);
    givenClimateLog.setParcel(givenParcel);

    /*
     * Para que esta prueba arroje el mismo valor
     * de ETo que esta en el ejemplo de la pagina numero
     * 72 del libro FAO numero 56 se tienen que
     * asignar los numeros 1.997 y 1.409 a las
     * variables es (presion media de vapor de saturacion)
     * y ea (presion real de vapor) respectivamente
     */
    // double result = Eto.getEto(temperatureMin, temperatureMax, pressure, windSpeed, dewPoint, extraterrestrialSolarRadiation, maximumInsolation, cloudCover);

    double result = Eto.getEto(givenClimateLog, extraterrestrialSolarRadiation, maximumInsolation);

    System.out.println("La ETo es: " + result + " (mm/día)");
    assertEquals(3.8, result, 0.1);
  }

  /**
  * Prueba unitaria del bloque de codigo fuente que calcula
  * la pendiente de la curva de presion de saturacion
  * de vapor (letra griega delta mayuscula)
  *
  * Dicho bloque de codigo fuente se prueba tambien haciendo
  * uso del bloque de codigo fuente que calcula la temperatura
  * media haciendo uso de la temperatura minima y la
  * temperatura maxima
  *
  * El bloque de codigo fuente a ser probado tiene como
  * parametro la temperatura media del aire en °C
  */
  @Ignore
  public void testSlopeVaporSaturationPressureCurve() {
    System.out.println("Prueba unitaria de pendiente de la curva de presión de saturacion de vapor");
    System.out.println();

    System.out.println("Para una T media = 28.5 delta vale " + String.format("%.3f", Eto.slopeVaporSaturationPressureCurve(28.5)));
    System.out.println("Para una T media = 30.5 delta vale " + String.format("%.3f", Eto.slopeVaporSaturationPressureCurve(30.5)));
    System.out.println("Para una T media = 40.0 delta vale " + String.format("%.3f", Eto.slopeVaporSaturationPressureCurve(40.0)));
    System.out.println("Para una T media = 44.5 delta vale " + String.format("%.3f", Eto.slopeVaporSaturationPressureCurve(44.5)));
    System.out.println("Para una T media = 48.5 delta vale " + String.format("%.3f", Eto.slopeVaporSaturationPressureCurve(48.5)));
    System.out.println();
  }

  /**
  * Prueba unitaria del bloque de codigo fuente que calcula
  * la constante psicrometrica (letra griega gamma)
  *
  * El bloque de codigo fuente a ser probado tiene como
  * parametro la presion atmosferica en milibares
  */
  @Ignore
  public void testPsychometricConstant() {
    System.out.println("Prueba unitaria de la constante psicrometrica");
    System.out.println();

    System.out.println("Para una presión atmosférica de 1013 milibar (0 metros de altitud) gamma vale " + Eto.psychometricConstant(pressureMiliBarToKiloPascals(1013)));
    System.out.println("Para una presión atmosférica de 899 milibar (1000 metros de altitud) gamma vale " + Eto.psychometricConstant(pressureMiliBarToKiloPascals(899)));
    System.out.println("Para una presión atmosférica de 795 milibar (2000 metros de altitud) gamma vale " + Eto.psychometricConstant(pressureMiliBarToKiloPascals(795)));
    System.out.println("Para una presión atmosférica de 701 milibar (3000 metros de altitud) gamma vale " + Eto.psychometricConstant(pressureMiliBarToKiloPascals(701)));
    System.out.println("Para una presión atmosférica de 616 milibar (4000 metros de altitud) gamma vale " + Eto.psychometricConstant(pressureMiliBarToKiloPascals(616)));
    System.out.println();
  }

  /**
  * Prueba unitaria del bloque de codigo fuente que corrige la
  * velocidad del viento a dos metros de altura
  *
  * El bloque de codigo fuente a ser probado tiene
  * como parametro la altura en metros
  */
  @Ignore
  public void testConversionFactorToTwoMetersHigh() {
    System.out.println("Prueba unitaria del factor de conversion");
    System.out.println();

    // System.out.println("Para una altura z = 1.0 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(1.0));
    // System.out.println("Para una altura z = 2.0 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(2.0));
    // System.out.println("Para una altura z = 3.0 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(3.0));
    // System.out.println("Para una altura z = 4.0 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(4.0));
    // System.out.println("Para una altura z = 6.0 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(6.0));
    // System.out.println("Para una altura z = 10.5 metros sobre la superficie del suelo el factor de conversion vale " + Eto.conversionFactorToTwoMetersHigh(10.5));
    // System.out.println();
  }

  /**
  * Prueba unitaria del bloque de codigo fuente que calcula
  * la presion de saturacion de vapor e°(T)
  *
  * El bloque de codigo fuente a ser probado tiene como parametro la
  * temperatura del aire en °C
  */
  @Ignore
  public void testSteamSaturationPressure() {
    System.out.println("Prueba unitaria de la presion de saturacion de vapor");
    System.out.println();

    System.out.println("Para una temperatura del aire T = 1.0 e°(T) vale " + Eto.steamSaturationPressure(1.0));
    System.out.println("Para una temperatura del aire T = 10.5 e°(T) vale " + Eto.steamSaturationPressure(10.5));
    System.out.println("Para una temperatura del aire T = 20.5 e°(T) vale " + Eto.steamSaturationPressure(20.5));
    System.out.println("Para una temperatura del aire T = 30.5 e°(T) vale " + Eto.steamSaturationPressure(30.5));
    System.out.println("Para una temperatura del aire T = 40.0 e°(T) vale " + Eto.steamSaturationPressure(40.0));
    System.out.println("Para una temperatura del aire T = 48.5 e°(T) vale " + Eto.steamSaturationPressure(48.5));
    System.out.println();
  }

  /**
  * Prueba unitaria del bloque de codigo fuente
  * que convierte las coordenadas geograficas en grados
  * decimales a radianes
  */
  // @Ignore
  // public void testDecimalDegreesToRadians() {
  // System.out.println("Prueba unitaria de conversion de grados decimales a radianes");
  // System.out.println();

  // Latitud de Bankok, Tailandia
  // System.out.println("Para una latitud 13.73 (grados decimales) la misma en radianes es " + Eto.latitudeDecimalDegreesToRadians(13.73));

  // Rio de Janeiro, Brasil
  //   System.out.println("Para una latitud -22.90 (grados decimales) la misma en radianes es " + Eto.latitudeDecimalDegreesToRadians(-22.90));
  //
  //   System.out.println();
  // }

  @Test
  public void methodTest() {

  }

  /**
  * Convierte la presión atmosferica dada en milibares a kilopascales
  *
  * @param  miliBarPressure
  * @return presion atmosferica [kPa]
  */
  private double pressureMiliBarToKiloPascals(double miliBarPressure) {
    return (miliBarPressure * 0.1);
  }

  /**
  * Convierte la velocidad del viento dada en km / h a m / h
  *
  * @param  windSpeed [km / h]
  * @return velocidad del viento [m / s]
  */
  private double windSpeedToMetersPerSecond(double windSpeed) {
    double meters = 1000;
    double seconds = 3600;
    return windSpeed * (meters / seconds);
  }

  /**
  * Prueba unitaria para el bloque de codigo fuente
  * que calcula el producto entre la constante de
  * Stefan Boltzmann y el promedio de las temperaturas
  * maximas y minimas elevadas a la cuarta potencia
  * en grados Kelvin
  */
  // @Test
  // public void testGetSigmaResult() {
  //   System.out.println("Resultado de la ecuacion de Stefan Boltzmann: " + Eto.getSigmaResult(19.1, 25.1));
  // }

}
