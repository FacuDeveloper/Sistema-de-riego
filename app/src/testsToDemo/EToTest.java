import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import stateless.et.ETo;
import stateless.ClimateLogService;

// import stateless.SolarRadiationServiceBean;
// import stateless.MaximumInsolationServiceBean;
// import stateless.LatitudeServiceBean;
// import stateless.MonthServiceBean;

import model.ClimateLog;
// import model.Latitude;
// import model.Month;

// import javax.persistence.EntityManager;
// import javax.persistence.EntityManagerFactory;
// import javax.persistence.Persistence;

public class EToTest {

  // private static EntityManager entityManager;
  // private static EntityManagerFactory entityMangerFactory;
  // private static SolarRadiationServiceBean solarService;
  // private static MaximumInsolationServiceBean insolationService;
  // private static LatitudeServiceBean latitudeService;
  // private static MonthServiceBean monthService;

  // @BeforeClass
  // public static void preTest(){
  //   entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
  //   entityManager = entityMangerFactory.createEntityManager();
  //
  //   solarService = new SolarRadiationServiceBean();
  //   solarService.setEntityManager(entityManager);
  //
  //   insolationService = new MaximumInsolationServiceBean();
  //   insolationService.setEntityManager(entityManager);
  //
  //   latitudeService = new LatitudeServiceBean();
  //   latitudeService.setEntityManager(entityManager);
  //
  //   monthService = new MonthServiceBean();
  //   monthService.setEntityManager(entityManager);
  // }

  /*
  * Bloque de codigo fuente de prueba
  * unitaria para el calculo de la ETo
  * de la localidad Puerto Madryn
  * con los datos climaticos del dia
  * 31/10/19
  */
  // @Ignore
  // public void testGetEto() {
  //   System.out.println("Prueba unitaria de obtención de evapotranspiración del cultivo de referencia");
  //   System.out.println();
  //
  //   // [°C]
  //   double temperatureMin = 13.27;
  //   double temperatureMax = 25.18;
  //
  //   // Presion atmosferica en milibar
  //   double pressure = 1004.42;
  //
  //   // Velocidad del viento a 10 metros de altura, en metros por segundo
  //   double windSpeed = 9.21;
  //
  //   // Punto de rocío
  //   double dewPoint = -10.25;
  //
  //   // Nubosidad
  //   double cloudCover = 0.84;
  //
  //   // Radiacion solar extraterrestre diaria
  //   double extraterrestrialSolarRadiation = 34.7;
  //
  //   // Insolacion maxima diaria
  //   double maximumInsolation = 13.2;
  //
  //   System.out.println("La ETo es: " + ETo.getEto(temperatureMin, temperatureMax, pressure, windSpeed, dewPoint, extraterrestrialSolarRadiation, maximumInsolation, cloudCover) + " (mm/día)");
  // }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * del bloque de codigo para el calculo de la
   * ETo para la localidad Uccle (Bruselas, Belgica)
   * con los datos climaticos del dia 6/7/19
   */
  @Test
  public void testGetEtoUccleJulio() {
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

    System.out.println("Prueba unitaria del calculo de la ETo para Uccle (Bruselas, Belgica) con datos metereologicos medidos el 6 de Julio");
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

    System.out.println("La ETo es: " + ETo.getEto(temperatureMin, temperatureMax, pressure, windSpeed, dewPoint, extraterrestrialSolarRadiation, maximumInsolation, cloudCover) + " (mm/día)");
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

    System.out.println("Para una T media = 28.5 delta vale " + String.format("%.3f", ETo.slopeVaporSaturationPressureCurve(28.5)));
    System.out.println("Para una T media = 30.5 delta vale " + String.format("%.3f", ETo.slopeVaporSaturationPressureCurve(30.5)));
    System.out.println("Para una T media = 40.0 delta vale " + String.format("%.3f", ETo.slopeVaporSaturationPressureCurve(40.0)));
    System.out.println("Para una T media = 44.5 delta vale " + String.format("%.3f", ETo.slopeVaporSaturationPressureCurve(44.5)));
    System.out.println("Para una T media = 48.5 delta vale " + String.format("%.3f", ETo.slopeVaporSaturationPressureCurve(48.5)));
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

    System.out.println("Para una presión atmosférica de 1013 milibar (0 metros de altitud) gamma vale " + ETo.psychometricConstant(pressureMiliBarToKiloPascals(1013)));
    System.out.println("Para una presión atmosférica de 899 milibar (1000 metros de altitud) gamma vale " + ETo.psychometricConstant(pressureMiliBarToKiloPascals(899)));
    System.out.println("Para una presión atmosférica de 795 milibar (2000 metros de altitud) gamma vale " + ETo.psychometricConstant(pressureMiliBarToKiloPascals(795)));
    System.out.println("Para una presión atmosférica de 701 milibar (3000 metros de altitud) gamma vale " + ETo.psychometricConstant(pressureMiliBarToKiloPascals(701)));
    System.out.println("Para una presión atmosférica de 616 milibar (4000 metros de altitud) gamma vale " + ETo.psychometricConstant(pressureMiliBarToKiloPascals(616)));
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

    System.out.println("Para una altura z = 1.0 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(1.0));
    System.out.println("Para una altura z = 2.0 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(2.0));
    System.out.println("Para una altura z = 3.0 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(3.0));
    System.out.println("Para una altura z = 4.0 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(4.0));
    System.out.println("Para una altura z = 6.0 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(6.0));
    System.out.println("Para una altura z = 10.5 metros sobre la superficie del suelo el factor de conversion vale " + ETo.conversionFactorToTwoMetersHigh(10.5));
    System.out.println();
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

    System.out.println("Para una temperatura del aire T = 1.0 e°(T) vale " + ETo.steamSaturationPressure(1.0));
    System.out.println("Para una temperatura del aire T = 10.5 e°(T) vale " + ETo.steamSaturationPressure(10.5));
    System.out.println("Para una temperatura del aire T = 20.5 e°(T) vale " + ETo.steamSaturationPressure(20.5));
    System.out.println("Para una temperatura del aire T = 30.5 e°(T) vale " + ETo.steamSaturationPressure(30.5));
    System.out.println("Para una temperatura del aire T = 40.0 e°(T) vale " + ETo.steamSaturationPressure(40.0));
    System.out.println("Para una temperatura del aire T = 48.5 e°(T) vale " + ETo.steamSaturationPressure(48.5));
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
  // System.out.println("Para una latitud 13.73 (grados decimales) la misma en radianes es " + ETo.latitudeDecimalDegreesToRadians(13.73));

  // Rio de Janeiro, Brasil
  //   System.out.println("Para una latitud -22.90 (grados decimales) la misma en radianes es " + ETo.latitudeDecimalDegreesToRadians(-22.90));
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
  //   System.out.println("Resultado de la ecuacion de Stefan Boltzmann: " + ETo.getSigmaResult(19.1, 25.1));
  // }

}
