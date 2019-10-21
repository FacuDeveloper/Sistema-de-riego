/*
 * ETo: Evapotranspiracion del cultivo de referencia
 */

package stateless;

import java.lang.Math;

public class ETo {

  public double getEto() {
    return 0;
  }

  /**
   * Pendiente de la curva de presion de saturacion de vapor (letra griega delta mayuscula)
   *
   * Los valores de la pendiente de la curva de presion de
   * saturacion de vapor para distintas temperaturas promedio
   * se pueden ver en el Anexo A2.4 de la pagina 214 del libro
   * FAO 56
   *
   * La ecuacion de la pendiente de la curva de presion de saturacion
   * de vapor es la ecuacion numero 13 de la pagina 36 del libro
   * FAO 56
   *
   * @param  averageAirTemperature [°C]
   * @return pendiente de la cutva de presion de saturacion de vapor [kPa / °C]
   */
  public double slopeVaporSaturationPressureCurve(double averageAirTemperature) {
    double exp = (17.27 * averageAirTemperature) / (averageAirTemperature + 237.3);
    double numerator = 4098 * (0.6108 * Math.pow(Math.E, exp));
    double denominator = Math.pow(averageAirTemperature + 237.3, 2);
    return numerator / denominator;
  }

  /**
   * Temperatura media calculada a partir de la suma de la temperatura minima
   * con la temperatura maxima
   *
   * La ecuacion de la temperatura es la ecuacion numero 9
   * de la pagina numero 32 del libro FAO 56
   *
   * @param  minTemperature [°C]
   * @param  maxTemperature [°C]
   * @return temperatura media [°C]
   */
  public double averageAirTemperature(double minTemperature, double maxTemperature) {
    return (minTemperature + maxTemperature) / 2;
  }

  /**
   * Constante psicrometrica (letra griega gamma)
   *
   * Los valores de la constante psicrometrica para distintas
   * presiones atmosfericas se pueden ver en el cuadro A2.2
   * del Anexo dos de la pagina 212 del libro FAO 56
   *
   * La ecuacion de la constante psicrometrica es la ecuacion
   * numero 8 de la pagina numero 31 del libro FAO 56
   *
   * @param  atmosphericPressure en kilo pascales
   * @return constante psicrometrica [kPa / °C]
   */
  public double psychometricConstant(double atmosphericPressure) {
    /*
     * Calor latente de vaporizacion [MJ / Kg], este
     * valor constante esta representado por la letra
     * griega lambda
     */
    double lambda = 2.45;

    /*
     * Calor especifico a presion constante [MJ / Kg * °C], este
     * valor constante esta representado por la palabra cp
     */
    double cp = 0.001013;

    /*
     * Cociente del peso molecular de vapor de agua/aire seco, este
     * valor esta representado por la letra griega epsilon
     */
    double epsilon = 0.622;

    return (cp * atmosphericPressure) / (epsilon * lambda);
  }

  /**
   * Velocidad del viento corregida a dos metros de altura (u2)
   *
   * Este bloque de codigo fuente hace uso del factor de
   * conversion (calculado por el bloque de codigo fuente
   * llamado conversionFactorToTwoMetersHigh) para convertir
   * la velocidad del viento mediada a una altura dada a
   * velocidad del viento a la elevacion de dos metros
   * sobre la superficie del suelo
   *
   * La ecuacion para corregir la velocidad del viento
   * a dos metros de altura es la ecuacion numero 47
   * de la pagina numero 56 del libro FAO 56
   *
   * @param  uz velocidad del viento medida a z metros sobre la superficie [m / s]
   * @param  z altura en metros
   * @return velocidad del viento a dos metros sobre la superficie [m / s]
   */
  public double windSpeedTwoMetersHigh(double uz, double z) {
    return uz * (4.87 / conversionFactorToTwoMetersHigh(z));
  }

  /**
   * Factor de conversion para calcular u2
   *
   * Los factores de conversion de la velocidad del viento
   * para distintas alturas z se pueden ver en el cuadro
   * A2.9 del Anexo dos de la pagina 220 del libro FAO 56
   *
   * La ecuacion del factor de conversion es la ecuacion
   * numero 47 de la pagina numero 56 del libro FAO 56
   *
   * @param  z altura en metros
   * @return factor de conversion para convertir la velocidad
   * del viento medida a una altura dada a velocidad del viento
   * a la elevacion estandar de dos metros sobre la superficie del suelo
   */
  public double conversionFactorToTwoMetersHigh(double z) {
    return (4.87 / Math.log(67.8 * z - 5.42));
  }

  /**
   * Presion de saturacion de vapor e°(T)
   *
   * La presion de saturacion de vapor para distintas
   * temperaturas del aire se pueden ver en el cuadro
   * A2.3 del Anexo dos de la pagina 213 del libro FAO 56
   *
   * La ecuacion de la presion de saturacion de vapor es la
   * ecuacion numero 11 de la pagina numero 12 del libro
   * FAO 56
   *
   * @param  airTemperature [°C]
   * @return presion de saturacion de vapor a la temperatura del aire [kPa]
   */
  public double steamSaturationPressure(double airTemperature) {
    double exp = (17.27 * airTemperature) / (airTemperature + 237.3);
    return (0.6108 * Math.pow(Math.E, exp));
  }

  /**
   * Presion media de vapor de la saturacion (es)
   *
   * La ecuacion de la presion media de vapor de la saturacion
   * es la ecuacion numero 12 de la pagina numero 36 del libro
   * FAO 56
   *
   * @param  minTemperature [°C]
   * @param  maxTemperature [°C]
   * @return presion media de vapor de saturacion [kPa]
   */
  public double averageSaturationVaporPressure(double minTemperature, double maxTemperature) {
    return (steamSaturationPressure(minTemperature) + steamSaturationPressure(maxTemperature)) / 2;
  }

  /**
   * Presion real de vapor (ea) derivada de la temperatura
   * del punto de rocio
   *
   * La ecuacion de la presion real de vapor derivada
   * de la temperatura del punto de rocio es la
   * ecuacion numero 15 de la pagina 37 del libro
   * FAO 56
   *
   * @param  dewPoint punto de rocio [°C]
   * @return presion real de vapor derivada de la temperatura
   * del punto de rocio [kPa]
   */
  public double actualVaporPressure(double dewPoint) {
    return steamSaturationPressure(dewPoint);
  }

  /**
   * Raciacion neta (Rn)
   *
   * La ecuacion de la radiacion neta es la ecuacion
   * numero 40 de la pagina numero 53 del libro FAO 56
   *
   * La radiacion neta es la diferencia entre la radiacion
   * neta de onda corta (Rns) y la radiacion neta de onda larga (Rnl)
   *
   * @param  extraterrestrialSolarRadiation radiacion solar extraterrestre (Ra)
   * @param  dewPoint punto de rocio [°C]
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @param  maximumInsolation duracion maxima de insolacion (N)
   * @param  cloudCover nubosidad (n)
   * @return radiacion neta [MJ / m cuadrado * dia]
   */
  public double netRadiation(double extraterrestrialSolarRadiation, double dewPoint, double minTemperature, double maxTemperature, double maximumInsolation, double cloudCover) {
    double solarRadiation = solarRadiation(extraterrestrialSolarRadiation, maximumInsolation, cloudCover);
    double netShortWaveRadiation = netShortWaveRadiation(solarRadiation);
    double netLongWaveRadiation = netLongWaveRadiation(extraterrestrialSolarRadiation, dewPoint, minTemperature, maxTemperature, solarRadiation);

    return (netShortWaveRadiation - netLongWaveRadiation);
  }

  /**
   * Radiacion neta de onda larga (Rnl)
   *
   * La ecuacion de la radiacion neta de onda larga (Rnl)
   * es la ecuacion numero 39 de la pagina 52 del libro
   * FAO 56
   *
   * @param  extraterrestrialSolarRadiation radiacion solar extraterrestre (Ra)
   * @param  dewPoint punto de rocio [°C]
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @param  solarRadiation radiacion solar (Rs) [MJ / m cuadrado * dia]
   * @return radiacion neta de onda larga [MJ / m cuadrado * dia]
   */
  public double netLongWaveRadiation(double extraterrestrialSolarRadiation, double dewPoint, double minTemperature, double maxTemperature, double solarRadiation) {
    double firstTerm = getSigmaResult(minTemperature, maxTemperature);

    /*
     * NOTE: Quizas se puede reemplazar la funcion actualVaporPressure
     * por el resultado del mismo
     */

     /*
      * Este segundo termino de la ecuacion 39 hace uso
      * del valor de la presion real de vapor (ea) derivada
      * del punto de rocio
      */
    double secondTerm = 0.34 - (0.14 * Math.sqrt(actualVaporPressure(dewPoint)));

    /*
     * Este tercer termino de la ecuacion 39 hace uso de
     * la radiacion solar (Rs) y de la radiacion solar en
     * un dia despejado (Rso)
     */
    double thirdTerm = ((1.35 * solarRadiation) / solarRadiationClearDay(extraterrestrialSolarRadiation)) - 0.35;

    return (firstTerm * secondTerm * thirdTerm);
  }

  /**
   * Radiacion neta solar o de onda corta (Rns)
   *
   * La ecuacion de la radiacion neta solar es la
   * ecuacion numero 38 de la pagina 51 del libro
   * FAO 56
   *
   * @param  solarRadiation radiacion solar (Rs) [MJ / m cuadrado * dia]
   * @return radiacion neta solar o de onda corta [MJ / m cuadrado * dia]
   */
  public double netShortWaveRadiation(double solarRadiation) {
    /*
     * Coeficiente de reflexion del cultivo, este
     * valor en la formula de la radiacion neta
     * de onda corta esta representado por la
     * letra griega alfa (Ω)
     */
    double cropReflectionCoefficient = 0.23;

    return ((1 - cropReflectionCoefficient) * solarRadiation);
  }

  /**
   * Constante de Stefan Boltzmann multiplicada por un valor de
   * temperatura en grados Kelvin
   *
   * La ecuacion de Stefan Boltzmann se puede ver en la
   * ecuacion numero 39 de la pagina 52 del libro FAO 56
   *
   * La constante de Stefan Boltzmann esta en MJ / m cuadrado * dia
   *
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @return valor que surge (en MJ / m cuadrado * dia) del resultado del producto entre
   * la constante de Stefan Boltzmann y el promedio de las
   * temperaturas maxima y minima en grados Kelvin
   */
  public double getSigmaResult(double minTemperature, double maxTemperature) {
    /*
     * En ambos casos, convierte la temperatura dada en °C a
     * grados Kelvin y luego la eleva a la cuarta potencia
     */
    double minKelvinTemperature = Math.pow(toKelvin(minTemperature), 4);
    double maxKelvinTemperature = Math.pow(toKelvin(maxTemperature), 4);

    /*
     * Comunmente, en la ecuacion de Stefan Boltzmann se utiliza el
     * promedio de la temperatura maxima del aire elevada a la cuarta
     * potencia y de la temperatura minima del aire elevada a la cuarta
     * potencia para periodos de 24 horas (a diario)
     */
    return (4.903E-9 * ((minKelvinTemperature + maxKelvinTemperature) / 2.0));
  }

  /**
   * Convierte la temperatura en °C a grados Kelvin
   *
   * @param  temperature temperatura [°C]
   * @return temperatura en grados Kelvin
   */
  private double toKelvin(double temperature) {
    return (temperature + 273.16);
  }

  /**
   * Radiacion solar en un dia despejado (Rso)
   *
   * La ecuacion de la radiacion solar en un dia
   * despejado es la ecuacion numero 37 de la
   * pagina 51 del libro FAO 56
   *
   * @param  extraterrestrialSolarRadiation radiacion solar extraterrestre (Ra)
   * @return radiacion solar en un dia despejado [MJ / m cuadrado * dia]
   */
  public double solarRadiationClearDay(double extraterrestrialSolarRadiation) {
    /*
     * Elevacion de la estacion sobre el nivel del mar [m]
     *
     * Este valor en la ecuacion esta representado
     * por la letra z
     */
    double elevation = 10.0;

    return ((0.75 + (0.00002 * elevation)) * extraterrestrialSolarRadiation);
  }

  /**
   * Radiacion solar (Rs)
   *
   * La ecuacion de la radiacion solar es la ecuacion
   * numero 35 de la pagina 50 del libro FAO 56
   *
   * @param  extraterrestrialSolarRadiation radiacion solar extraterrestre (Ra)
   * @param  maximumInsolation duracion maxima de insolacion (N)
   * @param  cloudCover nubosidad (n)
   * @return radiacion solar [MJ / m cuadrado * dia]
   */
  public double solarRadiation(double extraterrestrialSolarRadiation, double maximumInsolation, double cloudCover) {
    /*
     * En la ecuacion de la radiacion solar, la nubosidad,
     * dada por la variable cloudCover, es la letra n,
     * la duracion maxima de insolacion, dada por la variable
     * maximumInsolation, es la letra N y la radiacion
     * solar extraterrestre, dada por la variable
     * extraterrestrialSolarRadiation, es la palabra Ra
     */
    double relativeDurationInsolation = (cloudCover / maximumInsolation);
    return (((0.25 + 0.50) * relativeDurationInsolation) * extraterrestrialSolarRadiation);
  }

}
