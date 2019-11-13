/*
 * Esta clase tiene como simple responsabilidad utilizar
 * los datos climaticos de los pronosticos obtenidos
 * en una fecha dada y en unas coordenadas geograficas
 * dadas, para crear y proveer o servir (a clases clientes)
 * objetos de tipo registro del clima
 */

package climate;

import model.ClimateLog;

public class ClimateLogService {

  /*
   * Class variable
   */
  private static ClimateLogService climateLogService;

  /**
   * Metodo constructor privado para implementar
   * el patron de diseño Singleton
   */
  private ClimateLogService() {

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
     * Variable utilizada para crear, con los datos
     * climaticos que necesitamos del pronostico
     * obtenido de la llamada a la API Dark
     * Sky, el registro climatico asociado a
     * dicho pronostico
     *
     * Cada registro climatico despues de ser
     * creado sera almacenado en la base de datos
     * subyacente
     *
     * Lo siguiente surgio a raiz de un problema en
     * el modulo de obtencion y almacenamiento de
     * datos climaticos:
     *
     * Esto tiene que ser asi porque si se utiliza
     * una variable de instancia que se la crea
     * una sola vez, el modulo mencionado en el
     * parrafo anterior al usarla para persistir
     * registros climaticos de cada parcela, va
     * a almacenar registros climaticos de una sola
     * parcela, por mas que haya mas de una parcela
     * en la base de datos del sistema, cuando
     * deberia almacenar registros climaticos
     * para cada parcela
     *
     * Si se desea reproducir el problema mencionado,
     * utilizar la version de la confirmacion
     * "Funcion del calculo del coeficiente (kc)
     * junto con sus pruebas, todo terminado"
     */
    ClimateLog climateLog = new ClimateLog();

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
