/*
 * Esta clase tiene como simple responsabilidad utilizar
 * los datos climaticos de los pronosticos obtenidos
 * en una fecha dada y en unas coordenadas geograficas
 * dadas, para crear y proveer o servir (a clases clientes)
 * objetos de tipo registro del clima
 */

package stateless;

import model.ClimateLog;

public class ClimateLogService {

  /*
   * Variable de clase
   */
  private static ClimateLogService climateLogService;

  /*
   * Variables de instancia
   */
  private ClimateLog climateLog;

  /**
   * Metodo constructor privado para implementar
   * el patron de diseño Singleton
   */
  private ClimateLogService() {
    /*
     * Variable utilizada para crear, con los datos
     * climaticos que necesitamos del pronostico
     * obtenido de la llamada a la API Dark
     * Sky, el registro climatico asociado a
     * dicho pronostico
     *
     * Cada registro climatico despues de ser
     * creado sera almacenado en la base de datos
     * subyacente
     */
    climateLog = new ClimateLog();
  }

  /**
   * Permite crear una unica instancia de
   * tipo ClimateLogService (debido a que esta clase
   * tiene implementado el patron de diseño
   * Singleton) y permite obtener la referencia
   * a esa unica instancia de tipo ClimateLogService
   *
   * @return referencia a un unico objeto de tipo ClimateLogService
   * porque esta clase tiene implementado el patron de diseño
   * Singleton
   */
  public static ClimateLogService getInstance() {
    if (climateLogService == null) {
      climateLogService = new ClimateLogService();
    }

    return climateLogService;
  }

  /**
   * Provee un registro del clima que contiene todos los datos
   * climaticos que necesitamos del pronostico obtenido, de la
   * llamada a la API del clima Dark Sky, en la fecha y las
   * coordenadas geograficas dadas
   *
   * @param  latitude  [grados decimales]
   * @param  longitude [grados decimales]
   * @param  time      [UNIX TIMESTAMP]
   * @return registro del clima que contiene los datos que necesitamos
   * del pronostico obtenido en la fecha y en las coordenadas geograficas
   * dadas
   */
  public ClimateLog getClimateLog(double latitude, double longitude, long time) {
    /*
     * Carga el objeto de tipo registro del clima que
     * esta siendo referenciado por la variable de tipo
     * por referencia ClimateLog que contiene su referencia, con los
     * datos climaticos que necesitamos del pronostico
     * obtenido de la llamada a la API del clima Dark Sky
     */
    climateLog.load(ClimateClient.getInstance().getForecast(latitude, longitude, time));
    return climateLog;
  }

}
