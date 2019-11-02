/*
 * ETo: Evapotranspiracion del cultivo de referencia [milímetros/día]
 */

package et;

import java.lang.Math;

public class ETo {

  /**
   * Calcula la evapotranspiracion del cultivo de referencia,
   * la cual nos indica la cantidad de agua que va a evaporar
   * el cultivo de referencia bajo unas condiciones climaticas
   *
   * La evapotranspiracion de referencia se presenta en un
   * cultivo hipotetico, cuyas caracteristicas son conocidas
   * y que corresponde a un cultivo de pasto de altura uniforme,
   * bien regado y en óptimas condiciones de crecimiento
   *
   * @param  minTemperature                 [°C]
   * @param  maxTemperature                 [°C]
   * @param  pressure                       [kPa]
   * @param  windSpeed                      [metros/segundo]
   * @param  dewPoint                       [°C]
   * @param  extraterrestrialSolarRadiation (Ra) [MJ/metro cuadrado * dia]
   * @param  maximumInsolation (N)          [horas]
   * @param  cloudCover (n)                 [%]
   * @return cantidad de agua que va a evaporar el cultivo de referencia [mm/dia]
   */
  public static double getEto(double minTemperature, double maxTemperature, double pressure, double windSpeed, double dewPoint, double extraterrestrialSolarRadiation,
    double maximumInsolation, double cloudCover) {

    // Temperatura media del aire (T) [°C]
    double averageAirTemperature = averageAirTemperature(minTemperature, maxTemperature);

    // System.out.println("Temperatura media: " + averageAirTemperature);

    // Pendiente de la curva de presion de saturacion de vapor (letra griega delta mayuscula) [kPa/°C]
    double delta = slopeVaporSaturationPressureCurve(averageAirTemperature);

    // System.out.println("El delta es: " + delta);

    // Constante psicrometrica (letra griega gamma) [kPa/°C]
    double gamma = psychometricConstant(pressureMiliBarToKiloPascals(pressure));

    // System.out.println("El gamma es: " + gamma);

    /*
     * Velocidad del viento a dos metros de altura (u2) [metros/segundo]
     *
     * Asumimos que la altura de medicion sobre la superficie es
     * de 10 metros
     */
    double u2 = windSpeedTwoMetersHigh(windSpeed);

    // System.out.println("El u2 es: " + u2);

    // Presion media de vapor de saturacion (es) [kPa]
    double es = averageSaturationVaporPressure(minTemperature, maxTemperature);
    // double es =  1.997;

    // System.out.println("La presion media de vapor de saturacion es: " + es);

    // Presion real de vapor (ea) [kPa]
    double ea = actualVaporPressure(dewPoint);
    // double ea = 1.409;

    // System.out.println("La presion real de vapor es: " + ea);

    // Deficit de presion de vapor [kPa]
    double vaporPressureDeficit = es - ea;

    // System.out.println("El deficit de presion de vapor es: " + vaporPressureDeficit);

    // Radiacion neta (Rn) [MJ/metro cuadrado * dia]
    double rn = netRadiation(extraterrestrialSolarRadiation, ea, minTemperature, maxTemperature, maximumInsolation, cloudCover);

    // System.out.println("La radiacion neta es: " + rn);

    double numerator = 0.408 * delta * rn + ((gamma * (900 / (averageAirTemperature + 273))) * u2 * (vaporPressureDeficit));

    // System.out.println("El resultado del numerador es: " + numerator);

    double denominator = delta + gamma * (1 + 0.34 * u2);

    // System.out.println("El resultado del denominador es: " + denominator);

    return (numerator / denominator);
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
   * @return pendiente de la cutva de presion de saturacion de vapor [kPa/°C]
   */
  public static double slopeVaporSaturationPressureCurve(double averageAirTemperature) {
    double exp = (17.27 * averageAirTemperature) / (averageAirTemperature + 237.3);
    double numerator = 4098 * (0.6108 * Math.pow(Math.E, exp));

    // System.out.println("Numerador delta: " + numerator);

    double denominator = Math.pow(averageAirTemperature + 237.3, 2);

    // System.out.println("Denominador delta: " + denominator);

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
  public static double averageAirTemperature(double minTemperature, double maxTemperature) {
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
   * @return constante psicrometrica [kPa/°C]
   */
  public static double psychometricConstant(double atmosphericPressure) {
    /*
     * Calor latente de vaporizacion [MJ/Kg], este
     * valor constante esta representado por la letra
     * griega lambda
     */
    double lambda = 2.45;

    /*
     * Calor especifico a presion constante [MJ/Kg * °C], este
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
   * @param  uz velocidad del viento medida a 10 metros sobre la superficie [metros/segundo]
   * @return velocidad del viento a dos metros sobre la superficie [metros/segundo]
   */
  public static double windSpeedTwoMetersHigh(double uz) {
    // System.out.println("u2: " + (uz * (4.87 / conversionFactorToTwoMetersHigh(z))));
    return (uz * conversionFactorToTwoMetersHigh());
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
   * La altura a la que se mide el viento es 10 y es por
   * esto que en la invocacion al metodo log estatico
   * de la clase log hay un 10

   * @return factor de conversion para convertir la velocidad
   * del viento medida a una altura dada a velocidad del viento
   * a la elevacion estandar de dos metros sobre la superficie del suelo
   */
  public static double conversionFactorToTwoMetersHigh() {
    // System.out.println("Ln: " + Math.log(67.8 * z - 5.42));
    // System.out.println("Dvisión: " + (4.87 / Math.log(67.8 * z - 5.42)));
    return (4.87 / Math.log(67.8 * 10 - 5.42));
  }

  // Para la prueba unitaria del factor de conversion
  // public static double conversionFactorToTwoMetersHigh(double z) {
  //   return (4.87 / Math.log(67.8 * z - 5.42));
  // }

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
  public static double steamSaturationPressure(double airTemperature) {
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
  public static double averageSaturationVaporPressure(double minTemperature, double maxTemperature) {
    // System.out.println("e°(tMin): " + steamSaturationPressure(minTemperature));
    // System.out.println("e°(tMax): " + steamSaturationPressure(maxTemperature));
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
  public static double actualVaporPressure(double dewPoint) {
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
   * @param  ea presion real de vapor [kPa]
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @param  maximumInsolation duracion maxima de insolacion (N)
   * @param  cloudCover nubosidad (n)
   * @return radiacion neta [MJ/metros cuadrados * dia]
   */
  public static double netRadiation(double extraterrestrialSolarRadiation, double ea, double minTemperature, double maxTemperature, double maximumInsolation, double cloudCover) {
    double solarRadiation = solarRadiation(extraterrestrialSolarRadiation, maximumInsolation, cloudCover);

    // System.out.println("N: " + maximumInsolation);
    // System.out.println("Radiacion solar extraterrestre: " + extraterrestrialSolarRadiation);
    // System.out.println("La radiacion solar es: " + solarRadiation);

    /*
     * Radiacion solar de onda corta (Rns)
     */
    double netShortWaveRadiation = netShortWaveRadiation(solarRadiation);

    // System.out.println("La radiacion solar de onda corta es: " + netShortWaveRadiation);

    /*
     * Radiacion solar de onda larga (Rnl)
     */
    double netLongWaveRadiation = netLongWaveRadiation(extraterrestrialSolarRadiation, ea, minTemperature, maxTemperature, solarRadiation);

    // System.out.println("La radiacion de onda larga es: " + netLongWaveRadiation);

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
   * @param  ea presion real de vapor [kPa]
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @param  solarRadiation radiacion solar (Rs) [MJ/metros cuadrados * dia]
   * @return radiacion neta de onda larga [MJ/metros cuadrados * dia]
   */
  public static double netLongWaveRadiation(double extraterrestrialSolarRadiation, double ea, double minTemperature, double maxTemperature, double solarRadiation) {
    double firstTerm = getSigmaResult(minTemperature, maxTemperature);

    // System.out.println("Valor sigma: " + firstTerm);

   /*
    * Este segundo termino de la ecuacion 39 hace uso
    * del valor de la presion real de vapor (ea) derivada
    * del punto de rocio
    */
    double secondTerm = 0.34 - (0.14 * Math.sqrt(ea));

    // System.out.println("El segundo termino es: " + secondTerm);

   /*
    * Este tercer termino de la ecuacion 39 hace uso de
    * la radiacion solar (Rs) y de la radiacion solar en
    * un dia despejado (Rso)
    */
    double thirdTerm = ((1.35 * solarRadiation) / solarRadiationClearDay(extraterrestrialSolarRadiation)) - 0.35;

    // System.out.println("El tercer termino es: " + thirdTerm);

    return (firstTerm * secondTerm * thirdTerm);
  }

  /**
   * Radiacion neta solar o de onda corta (Rns)
   *
   * La ecuacion de la radiacion neta solar es la
   * ecuacion numero 38 de la pagina 51 del libro
   * FAO 56
   *
   * @param  solarRadiation radiacion solar (Rs) [MJ/metros cuadrados * dia]
   * @return radiacion neta solar o de onda corta [MJ/metros cuadrados * dia]
   */
  public static double netShortWaveRadiation(double solarRadiation) {
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
   * La constante de Stefan Boltzmann esta en MJ/metros cuadrados * dia
   *
   * @param  minTemperature temperatura minima [°C]
   * @param  maxTemperature temperatura maxima [°C]
   * @return valor que surge (en MJ/metros cuadrados * dia) del resultado del producto entre
   * la constante de Stefan Boltzmann y el promedio de las
   * temperaturas maxima y minima en grados Kelvin
   */
  public static double getSigmaResult(double minTemperature, double maxTemperature) {
    /*
     * En ambos casos, convierte la temperatura dada en °C a
     * grados Kelvin y luego la eleva a la cuarta potencia
     */
    double minKelvinTemperature = Math.pow(toKelvin(minTemperature), 4);
    double maxKelvinTemperature = Math.pow(toKelvin(maxTemperature), 4);

    // System.out.println("Tempratura minima a Kelvin: " + minKelvinTemperature);
    // System.out.println("Temperatura maxima a Kelvin: " + maxKelvinTemperature);

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
  private static double toKelvin(double temperature) {
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
   * @return radiacion solar en un dia despejado [MJ/metros cuadrados * dia]
   */
  public static double solarRadiationClearDay(double extraterrestrialSolarRadiation) {
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
   * @return radiacion solar [MJ/metros cuadrados * dia]
   */
  public static double solarRadiation(double extraterrestrialSolarRadiation, double maximumInsolation, double cloudCover) {
    /*
     * En la ecuacion de la radiacion solar, la nubosidad,
     * dada por la variable cloudCover, es la letra n,
     * la duracion maxima de insolacion, dada por la variable
     * maximumInsolation, es la letra N y la radiacion
     * solar extraterrestre, dada por la variable
     * extraterrestrialSolarRadiation, es la palabra Ra
     */
    double relativeDurationInsolation = (cloudCover / maximumInsolation);

    // System.out.println("Valor: " + (relativeDurationInsolation * 0.50));
    // System.out.println("Valor: " + (0.25 + (0.50 * relativeDurationInsolation)));

    return ((0.25 + (0.50 * relativeDurationInsolation)) * extraterrestrialSolarRadiation);
  }

  /**
   * Convierte la presión atmosferica dada en milibares a kilopascales
   *
   * @param  miliBarPressure
   * @return presion atmosferica [kPa]
   */
  private static double pressureMiliBarToKiloPascals(double miliBarPressure) {
    return (miliBarPressure * 0.1);
  }

}
