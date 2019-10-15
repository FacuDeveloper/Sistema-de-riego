import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import java.util.Calendar;

import java.lang.Math;

import stateless.ETo;

public class EToTest {

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

    ETo eto = new ETo();
    System.out.println("Para una T media = 28.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(28.5)));
    System.out.println("Para una T media = 30.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(30.5)));
    System.out.println("Para una T media = 40.0 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(40.0)));
    System.out.println("Para una T media = 44.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(44.5)));
    System.out.println("Para una T media = 48.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(48.5)));

    // System.out.println("Para una T media = 1.0 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(1.0)));
    // System.out.println("Para una T media = 34.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(34.5)));
    // System.out.println("Para una T media = 40.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(40.5)));
    // System.out.println("Para una T media = 47.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(47.5)));
    //
    // /*
    //  * Calculo del delta haciendo uso del bloque de codigo
    //  * fuente que calcula la temperatura media
    //  */
    // System.out.println("Para una T media = 28.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(20, 37))));
    // System.out.println("Para una T media = 13.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(12, 15))));
    // System.out.println("Para una T media = 20.0 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(19, 21))));
    // System.out.println("Para una T media = 24.5 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(20, 29))));
    // System.out.println("Para una T media = 27.0 delta vale " + String.format("%.3f", eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(25, 29))));
    //
    // assertEquals(0.047, eto.slopeVaporSaturationPressureCurve(1.0), 0.001);
    // assertEquals(0.249, eto.slopeVaporSaturationPressureCurve(30.5), 0.001);
    // assertEquals(0.303, eto.slopeVaporSaturationPressureCurve(34.5), 0.001);
    // assertEquals(0.402, eto.slopeVaporSaturationPressureCurve(40.5), 0.001);
    // assertEquals(0.550, eto.slopeVaporSaturationPressureCurve(47.5), 0.001);
    //
    // // T media = 28.5
    // assertEquals(0.226, eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(20, 37)), 0.001);
    //
    // // T media = 13.5
    // assertEquals(0.101, eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(12, 15)), 0.001);
    //
    // // T media = 20.0
    // assertEquals(0.145, eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(19, 21)), 0.001);
    //
    // // T media = 24.5
    // assertEquals(0.184, eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(20, 29)), 0.001);
    //
    // // T media = 27.0
    // assertEquals(0.209, eto.slopeVaporSaturationPressureCurve(eto.averageAirTemperature(25, 29)), 0.001);

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

    ETo eto = new ETo();
    System.out.println("Para una presión atmosférica de 1013 milibar (0 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(1013)));
    System.out.println("Para una presión atmosférica de 899 milibar (1000 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(899)));
    System.out.println("Para una presión atmosférica de 795 milibar (2000 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(795)));
    System.out.println("Para una presión atmosférica de 701 milibar (3000 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(701)));
    System.out.println("Para una presión atmosférica de 616 milibar (4000 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(616)));

    // System.out.println("Para una presión atmosférica de 1001 milibar (100 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(1001)));
    // System.out.println("Para una presión atmosférica de 989 milibar (200 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(989)));
    // System.out.println("Para una presión atmosférica de 978 milibar (300 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(978)));
    // System.out.println("Para una presión atmosférica de 966 milibar (400 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(966)));
    // System.out.println("Para una presión atmosférica de 955 milibar (500 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(955)));
    // System.out.println("Para una presión atmosférica de 943 milibar (600 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(943)));
    // System.out.println("Para una presión atmosférica de 932 milibar (700 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(932)));
    // System.out.println("Para una presión atmosférica de 921 milibar (800 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(921)));
    // System.out.println("Para una presión atmosférica de 910 milibar (900 metros de altitud) gamma vale " + eto.psychometricConstant(pressureMiliBarToKiloPascals(910)));

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

    ETo eto = new ETo();
    System.out.println("Para una altura z = 1.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(1.0));
    System.out.println("Para una altura z = 2.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(2.0));
    System.out.println("Para una altura z = 3.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(3.0));
    System.out.println("Para una altura z = 4.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(4.0));
    System.out.println("Para una altura z = 6.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(6.0));
    System.out.println("Para una altura z = 10.5 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(10.5));

    // System.out.println("Para una altura z = 1.2 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(1.2));
    // System.out.println("Para una altura z = 1.4 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(1.4));
    // System.out.println("Para una altura z = 1.6 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(1.6));
    // System.out.println("Para una altura z = 1.8 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(1.8));
    // System.out.println("Para una altura z = 2.2 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(2.2));
    // System.out.println("Para una altura z = 2.4 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(2.4));
    // System.out.println("Para una altura z = 2.6 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(2.6));
    // System.out.println("Para una altura z = 2.8 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(2.8));
    // System.out.println("Para una altura z = 7.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(7.0));
    // System.out.println("Para una altura z = 8.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(8.0));
    // System.out.println("Para una altura z = 9.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(9.0));
    // System.out.println("Para una altura z = 9.5 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(9.5));
    // System.out.println("Para una altura z = 10.0 metros sobre la superficie del suelo el factor de conversion vale " + eto.conversionFactorToTwoMetersHigh(10.0));

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

    ETo eto = new ETo();
    System.out.println("Para una temperatura del aire T = 1.0 e°(T) vale " + eto.steamSaturationPressure(1.0));
    System.out.println("Para una temperatura del aire T = 10.5 e°(T) vale " + eto.steamSaturationPressure(10.5));
    System.out.println("Para una temperatura del aire T = 20.5 e°(T) vale " + eto.steamSaturationPressure(20.5));
    System.out.println("Para una temperatura del aire T = 30.5 e°(T) vale " + eto.steamSaturationPressure(30.5));
    System.out.println("Para una temperatura del aire T = 40.0 e°(T) vale " + eto.steamSaturationPressure(40.0));
    System.out.println("Para una temperatura del aire T = 48.5 e°(T) vale " + eto.steamSaturationPressure(48.5));

    // System.out.println("Para una temperatura del aire T = 1.5 e°(T) vale " + eto.steamSaturationPressure(1.5));
    // System.out.println("Para una temperatura del aire T = 2.0 e°(T) vale " + eto.steamSaturationPressure(2.0));
    // System.out.println("Para una temperatura del aire T = 2.5 e°(T) vale " + eto.steamSaturationPressure(2.5));
    // System.out.println("Para una temperatura del aire T = 3.0 e°(T) vale " + eto.steamSaturationPressure(3.0));
    // System.out.println("Para una temperatura del aire T = 3.5 e°(T) vale " + eto.steamSaturationPressure(3.5));
    // System.out.println("Para una temperatura del aire T = 4.0 e°(T) vale " + eto.steamSaturationPressure(4.0));
    // System.out.println("Para una temperatura del aire T = 4.5 e°(T) vale " + eto.steamSaturationPressure(4.5));
    // System.out.println("Para una temperatura del aire T = 5.0 e°(T) vale " + eto.steamSaturationPressure(5.0));
    // System.out.println("Para una temperatura del aire T = 5.5 e°(T) vale " + eto.steamSaturationPressure(5.5));
    // System.out.println("Para una temperatura del aire T = 6.0 e°(T) vale " + eto.steamSaturationPressure(6.0));
    // System.out.println("Para una temperatura del aire T = 6.5 e°(T) vale " + eto.steamSaturationPressure(6.5));
    // System.out.println("Para una temperatura del aire T = 7.0 e°(T) vale " + eto.steamSaturationPressure(7.0));
    // System.out.println("Para una temperatura del aire T = 7.5 e°(T) vale " + eto.steamSaturationPressure(7.5));
    // System.out.println("Para una temperatura del aire T = 8.0 e°(T) vale " + eto.steamSaturationPressure(8.0));

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

    // ETo eto = new ETo();

    // Latitud de Bankok, Tailandia
    // System.out.println("Para una latitud 13.73 (grados decimales) la misma en radianes es " + eto.latitudeDecimalDegreesToRadians(13.73));

    // Rio de Janeiro, Brasil
  //   System.out.println("Para una latitud -22.90 (grados decimales) la misma en radianes es " + eto.latitudeDecimalDegreesToRadians(-22.90));
  //
  //   System.out.println();
  // }

  @Test
  public void methodTest() {

  }

  /**
   * Convierte la presión atmosferica dada en milibar a kPa
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
  //   ETo eto = new ETo();
  //   System.out.println("Resultado de la ecuacion de Stefan Boltzmann: " + eto.getSigmaResult(19.1, 25.1));
  // }

  /**
   * Prueba unitaria del bloque de codigo fuente
   * que calcula la radiacion extraterrestre
   *
   * El bloque de codigo fuente a ser probado tiene como primer parametro
   * el numero del dia del año y como segundo parametro
   * la latitud en grados decimales
   */
  // @Ignore
  // public void testExtraterrestrialSolarRadiation() {
    // System.out.println("Prueba unitaria de la radiacion solar extraterrestre");
    // System.out.println();

    // ETo eto = new ETo();
    // System.out.println("La radiación solar extraterrestre en el día número:");
    // System.out.println("- 15 del año (15 de Enero) para la latitud 70° N es: " + eto.extraterrestrialSolarRadiation(15, 70));
    // System.out.println("- 15 del año (15 de Enero) para la latitud 0° N es: " + eto.extraterrestrialSolarRadiation(15, 0));
    // System.out.println("- 46 del año (15 de Febrero) para la latitud 0° N es: " + eto.extraterrestrialSolarRadiation(46, 0));
    // System.out.println("- 15 del año (15 de Enero) para la latitud 70° S es: " + eto.extraterrestrialSolarRadiation(15, -70));
    //
    // System.out.println("- 166 del año (15 de Junio) para la latitud 70° S es: " + eto.extraterrestrialSolarRadiation(166, -70));
    // System.out.println("- 349 del año (15 de Diciembre) para la latitud 70° S es: " + eto.extraterrestrialSolarRadiation(349, -70));


    // System.out.println();
    // System.out.println("Resultado: " + eto.extraterrestrialSolarRadiation(166, -70));

    // System.out.println("Radiacion solar extraterrestre para el dia 4, 64° N: " + eto.extraterrestrialSolarRadiation(4, 64));

    // double result = eto.extraterrestrialSolarRadiation(1, 70);

    // String stringResult = String.valueOf(result);

    // if (stringResult.equals("NaN")) {
    //   System.out.println("Radiacion solar extraterrestre: " + 0);
    // }

    // System.out.println("Resultado: " + String.valueOf(result));

    // System.out.println();
  // }

  /**
   * Prueba unitaria para el bloque de codigo fuente
   * que calcula la declinacion solar
   */
  // @Ignore
  // public void testSolarDecline() {
  //   ETo eto = new ETo();
  //   System.out.println("La declinacion solar para el dia: ");
  //   System.out.println("- 15 del año (15 de Enero) para la latitud 70° N es: " + eto.solarDecline(15));
  //   System.out.println("- 15 del año (15 de Enero) para la latitud 0° N es: " + eto.solarDecline(15));
  //   System.out.println("- 46 del año (15 de Febrero) para la latitud 0° N es: " + eto.solarDecline(46));
  //   System.out.println("- 15 del año (15 de Enero) para la latitud 70° S es: " + eto.solarDecline(15));
  //   System.out.println("- 166 del año (15 de Junio) para la latitud 70° S es: " + eto.solarDecline(166));
  //   System.out.println("- 349 del año (15 de Diciembre) para la latitud 70° S es: " + eto.solarDecline(349));
  //   System.out.println();
  // }

  /**
   * Prueba unitaria para el bloque de codigo fuente
   * que calcula el angulo de radiacion solar
   */
  // @Ignore
  // public void testSunsetRadiationAngle() {
    // ETo eto = new ETo();
    // System.out.println("El angulo de radiacion solar para el dia: ");
    // System.out.println("- 15 del año (15 de Enero) para la latitud 70° N es: " + eto.sunsetRadiationAngle(70, 15));
    // System.out.println("- 15 del año (15 de Enero) para la latitud 0° N es: " + eto.sunsetRadiationAngle(0, 15));
    // System.out.println("- 46 del año (15 de Febrero) para la latitud 0° N es: " + eto.sunsetRadiationAngle(0, 46));
    // System.out.println("- 15 del año (15 de Enero) para la latitud 70° S es: " + eto.sunsetRadiationAngle(-70, 15));
    // System.out.println("- 166 del año (15 de Junio) para la latitud 70° S es: " + eto.sunsetRadiationAngle(-70, 166));
    // System.out.println("- 349 del año (15 de Diciembre) para la latitud 70° S es: " + eto.sunsetRadiationAngle(-70, 349));
    // System.out.println();
  // }

}
