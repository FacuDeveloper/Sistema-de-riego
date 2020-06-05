/*
 * ETc: Evapotranspiracion del cultivo bajo condiciones estandar
 */

package et;

import model.ClimateLog;

public class Etc {

  /**
   * Calcula la evapotranspiracion del cultivo bajo condiciones
   * estandar, la cual nos indica la cantidad de agua que va a evaporar
   * un cultivo dado bajo condiciones estandar dado el coeficiente del
   * cultivo y unos factores climaticos
   *
   * El registro climatico dado contiene los valores de los
   * siguientes fenomenos climaticos:
   * - Temperatura minima [°C]
   * - Temperatura maxima [°C]
   * - Presion atmosferica [kPa]
   * - Velocidad del viento [metros/segundo]
   * - Punto de rocio [°C]
   * - Nubosidad (n) [%]
   *
   * @param  cropCoefficient                [(kc) adimensional)]
   * @param  givenClimateLog
   * @param  extraterrestrialSolarRadiation [(Ra) MJ/metro cuadrado * dia)]
   * @param  maximumInsolation              [(n) horas]
   * @return cantidad de agua que va a evaporar un cultivo dado [mm/dia]
   */
  public static double getEtc(double cropCoefficient, ClimateLog givenClimateLog, double extraterrestrialSolarRadiation, double maximumInsolation) {
    return (cropCoefficient * Eto.getEto(givenClimateLog, extraterrestrialSolarRadiation, maximumInsolation));
  }

  // Constructor method
  private Etc() {

  }

}
