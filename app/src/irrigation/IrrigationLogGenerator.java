/*
 * Generador de registros historicos de riego para
 * cada parcela existente en el sistema
 *
 * Esta clase es el modulo encargado de crear y almacenar
 * de forma automatica registros historicos de riego para
 * cada parcela para cada dia del año siempre y cuando no
 * exista ningun registro historico de riego en el dia
 * dado para cada parcela existente em eñ sistema
 */

package irrigation;

import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.EJB;

import java.util.Collection;
import java.util.Calendar;

import java.lang.Math;

import stateless.ParcelServiceBean;
import stateless.IrrigationLogServiceBean;
import stateless.ClimateLogServiceBean;

import model.Parcel;
import model.IrrigationLog;
import model.ClimateLog;

import climate.ClimateLogService;

import util.UtilDate;

@Stateless
public class IrrigationLogGenerator {

  // inject a reference to the ParcelServiceBean
  @EJB ParcelServiceBean parcelService;

  // inject a reference to the IrrigationLogServiceBean
  @EJB IrrigationLogServiceBean irrigationLogService;

  // inject a reference to the ClimateLogServiceBean
  @EJB ClimateLogServiceBean climateLogServiceBean;

  /**
   * Este metodo tiene la finalidad crear y almacenar
   * un unico registro historico de riego para cada
   * dia para cada parcela que no tenga ningun registro
   * historico de riego en el dia dado, lo cual hara a
   * partir de las 23:59 en la fecha actual del sistema
   * y sera en esta hora porque es al final del dia cuando
   * se puede saber si cada parcela existente en el sistema
   * tiene o no registros historicos de riego en el dia dado
   *
   * @param second="*"
   * @param minute="59"
   * @param hour="23"
   * @param persistent=false
   */
  // @Schedule(second="*", minute="59", hour="23", persistent=false)
  // @Schedule(second="*/5", minute="*", hour="*", persistent=false)
  private void execute() {
    ClimateLogService climateLogService = ClimateLogService.getInstance();
    Collection<Parcel> parcels = parcelService.findAll();
    Calendar currentDate = Calendar.getInstance();

    /*
     * Registro actual de riego, es decir,
     * registro de riego para el dia de hoy
     */
    IrrigationLog currentIrrigationLog = null;

    /*
     * Riego sugerido actual, es decir,
     * riego sugerido para el dia de hoy
     */
    double currentSuggestedIrrigation = 0.0;

    /*
     * Variables para almacenar temporalmente los
     * datos del dia de ayer
     */
    ClimateLog yesterdayClimateLog = null;
    double yesterdayEto = 0.0;
    double yesterdayEtc = 0.0;
    double yesterdayRainWater = 0.0;
    double waterAccumulatedYesterday = 0.0;

    /*
     * Fecha inmediatamente siguiente a la fecha
     * actual para recuperar el registro climatico,
     * de una parcela dada, en la fecha siguiente
     * a la fecha actual
     */
    Calendar tomorrowDate = UtilDate.getTomorrowDate();
    ClimateLog tomorrowClimateLog = null;

    /*
     * Fecha inmediatamente anterior a la fecha
     * actual para recuperar el registro climatico,
     * de una parcela dada, en la fecha anterior a
     * la fecha actual
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    for (Parcel currentParcel : parcels) {
      /*
       * Si la parcela dada no tiene asociado un registro historico
       * de riego en la fecha dada (en este caso la actual), se tiene
       * que crear uno para la fecha dada y asociarlo a la misma
       */
      if (!irrigationLogService.exist(currentDate, currentParcel)) {
        currentIrrigationLog = new IrrigationLog();

        /*
         * Establece la fecha del registro de riego
         */
        currentIrrigationLog.setDate(currentDate);

        /*
         * Recupera el registro climatico de la parcela
         * dada de la fecha inmediatamente anterior a la
         * fecha actual
         */
        // TODO: Seguridad, mas adelante refactorizar
        yesterdayClimateLog = climateLogServiceBean.find(yesterdayDate, currentParcel);
        yesterdayEto = yesterdayClimateLog.getEto();
        yesterdayEtc = yesterdayClimateLog.getEtc();
        yesterdayRainWater = yesterdayClimateLog.getRainWater();
        waterAccumulatedYesterday = yesterdayClimateLog.getWaterAccumulated();

        /*
         * Lo que esta dentro de la sentencia de seleccion se ejecuta
         * si no hay un registro historico de riego de la parcela dada
         * en la fecha dada (la de hoy, en este caso), con lo cual al
         * no haber registros historicos de riego de la parcela dada
         * en la fecha dada, la cantidad total de agua utilizada en
         * el riego en el dia de hoy es 0.0
         */
        // currentSuggestedIrrigation = WaterMath.getSuggestedIrrigation(currentParcel.getArea(), yesterdayEtc, yesterdayEto, yesterdayRainWater, waterAccumulatedYesterday, 0.0);
        // currentIrrigationLog.setSuggestedIrrigation(currentSuggestedIrrigation);

        currentSuggestedIrrigation = WaterMath.getSuggestedIrrigation(yesterdayClimateLog, 0.0);
        currentIrrigationLog.setSuggestedIrrigation(currentSuggestedIrrigation);

        /*
         * Se recupera el registro climatico del dia de mañana
         * y de el se obtiene la cantidad de agua de lluvia del
         * dia de mañana para establecerla en el registro historico
         * de riego del dia de hoy
         *
         * La precipiacion del dia de mañana en el registro
         * historico de riego solo es un dato informativo, no
         * se lo usa con otro fin mas que para informar
         */
        tomorrowClimateLog = climateLogService.getClimateLog(currentParcel.getLatitude(), currentParcel.getLongitude(), (tomorrowDate.getTimeInMillis() / 1000));
        currentIrrigationLog.setTomorrowPrecipitation(WaterMath.truncateToThreeDecimals(tomorrowClimateLog.getRainWater()));

        /*
         * Si este bloque de codigo fuente se ejecuta es porque
         * el usuario cliente del sistema nunca ha registrado
         * los riegos que ha realizado en el dia actual (si
         * es que los realizo) para la parcela dada, con lo
         * cual, el riego realizado en el dia de hoy sera
         * establecido por el sistema con el valor 0.0 de
         * forma predetermianda
         *
         * Si el usuario nunca ha ingresado su riego
         * realizado en el dia de hoy, el sistema mismo
         * lo establece en cero porque tiene que tener
         * un registro del riego que no ha realizado el
         * usuario cliente
         */
        currentIrrigationLog.setIrrigationDone(0.0);
        currentIrrigationLog.setParcel(currentParcel);

        /*
         * Se crea en la base de datos subyacente el
         * registro historico de riego asociado a la
         * parcela dada
         */
        irrigationLogService.create(currentIrrigationLog);
      } // End if

    } // End for

  }

}
