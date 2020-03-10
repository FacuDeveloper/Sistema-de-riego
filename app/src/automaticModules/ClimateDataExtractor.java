/*
 * Extractor de datos climaticos
 *
 * Esta clase representa el modulo encargado de obtener
 * y almacenar de forma automatica los datos climaticos
 * para cada parcela en el sistema para cada dia nuevo
 *
 * Los datos climaticos son necesarios para determinar
 * la evapotranspiracion del cultivo en cada parcela
 * y la evapotranspiracion nos indica la cantidad de
 * agua que va a evaporar un cultivo y por ende, nos indica
 * la cantidad de agua que se le tiene que reponer al
 * cultivo dado, mediante el riego
 */

package automaticModules;

import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.EJB;

import java.util.Collection;
import java.util.Calendar;

import stateless.ParcelServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;
import stateless.InstanciaParcelaService;
import stateless.IrrigationLogServiceBean;
import stateless.CultivoService;

import model.ClimateLog;
import model.Parcel;
import model.InstanciaParcela;

import climate.ClimateLogService;

import et.Eto;

import irrigation.WaterMath;

import util.UtilDate;

@Stateless
public class ClimateDataExtractor {

  // inject a reference to the ParcelServiceBean
  @EJB ParcelServiceBean parcelService;

  // inject a reference to the ClimateLogServiceBean
  @EJB ClimateLogServiceBean climateLogServiceBean;

  // inject a reference to the IrrigationLogServiceBean
  @EJB IrrigationLogServiceBean irrigationLogService;

  // inject a reference to the SolarRadiationServiceBean
  @EJB SolarRadiationServiceBean solarService;

  // inject a reference to the MaximumInsolationServiceBean
  @EJB MaximumInsolationServiceBean insolationService;

  // inject a reference to the InstanciaParcelaService
  @EJB InstanciaParcelaService parcelInstanceService;

  // inject a reference to the CultivoService
  @EJB CultivoService cultivoService;

  /**
   * Este metodo tiene como finalidad obtener y almacenar
   * de forma automatica los datos climaticos para cada
   * parcela que no los tenga, lo cual hara cada dos
   * horas a partir de las 00:00 debido a que quizas, en
   * alguna hora del dia, la API climatica (Dark Sky) puede
   * que no este disponible al momento de que el sistema le
   * solicite los datos climaticos para cada parcela
   *
   * @param second="*"
   * @param minute="*"
   * @param hour="0/2" cada dos horas a partir de las doce de la
   * noche
   * @param persistent=false
   */
  // @Schedule(second="*", minute="*", hour="0/2", persistent=false)
  // @Schedule(second="*/5", minute="*", hour="*", persistent=false)
  private void execute() {
    Collection<Parcel> parcels = parcelService.findAll();

    /*
     * Obtiene una referencia a un objeto del tipo
     * clase de servicio de registro climatico, para
     * obtener los registros climaticos de unas
     * coordenadas geograficas dadas en una fecha
     * dada
     */
    ClimateLogService climateLogService = ClimateLogService.getInstance();

    double latitude = 0.0;
    double longitude = 0.0;
    ClimateLog climateLog = null;

    /*
     * Fecha del dia de hoy
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Fecha del dia de ayer
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    /*
     * Convierte el tiempo en milisegundos a segundos
     * porque el formato UNIX TIMESTAMP utiliza el tiempo
     * en segundos y se realiza esta conversion porque la API del
     * clima llamada Dark Sky recibe fechas con el formato mencionado
     */
    long unixTime = (currentDate.getInstance().getTimeInMillis() / 1000);

    double eto = 0.0;
    double etc = 0.0;
    double waterAccumulatedToday = 0.0;
    double extraterrestrialSolarRadiation = 0.0;
    double maximumInsolation = 0.0;
    InstanciaParcela parcelInstance = null;

    for (Parcel currentParcel : parcels) {

      /*
       * Si no existe un registro historico climatico
       * en la fecha actual para la parcela dada, entonces
       * se tiene que crear uno
       */
      if (!climateLogServiceBean.exist(currentDate, currentParcel)) {
        latitude = currentParcel.getLatitude();
        longitude = currentParcel.getLongitude();

        /*
         * Recupera un registro del clima haciendo uso de las
         * coordenadas geograficas de las parcelas y de la fecha
         * actual en formato UNIX TIMESTAMP
         */
        climateLog = climateLogService.getClimateLog(latitude, longitude, unixTime);
        climateLog.setParcel(currentParcel);

        extraterrestrialSolarRadiation = solarService.getRadiation(currentDate.get(Calendar.MONTH), latitude);
        maximumInsolation = insolationService.getInsolation(currentDate.get(Calendar.MONTH), latitude);

        /*
         * Con los datos climaticos recuperados se calcula la
         * evapotranspiracion del cultivo de referencia (ETo)
         */
        eto = Eto.getEto(climateLog.getTemperatureMin(), climateLog.getTemperatureMax(), climateLog.getPressure(), climateLog.getWindSpeed(),
        climateLog.getDewPoint(), extraterrestrialSolarRadiation, maximumInsolation, climateLog.getCloudCover());
        climateLog.setEto(eto);

        /*
         * Busca una instancia de la parcela dada
         * que tenga un cultivo en desarrollo
         */
        parcelInstance = parcelInstanceService.findInDevelopment(currentParcel);

        /*
         * Si hay (es decir, != null) un registro historico
         * actual de la parcela dada es porque la misma
         * actualmente tiene un cultivo sembrado y
         * que aun no ha llegado a su fecha de cosecha,
         * por ende, se calcula la ETc del cultivo que
         * tiene
         *
         * En el caso en el que la parcela no tenga un
         * registro historico actual, la ETc sera cero
         * debido a que este registro se crea cuando
         * se planta un cultivo en la parcela dada,
         * con lo cual si no hay cultivo no se
         * puede calcular su ETc, y por lo tanto,
         * la misma es cero
         */
        if (parcelInstance != null) {
          etc = cultivoService.getKc(parcelInstance.getCultivo(), parcelInstance.getFechaSiembra()) * eto;
        } else {
          etc = 0.0;
        }

        climateLog.setEtc(etc);

        /*
         * Se recupera el registro climatico del dia de
         * ayer para calcular el agua acumulada para el dia
         * de hoy
         */
        ClimateLog yesterdayClimateLog = climateLogServiceBean.find(yesterdayDate, currentParcel);
        waterAccumulatedToday = WaterMath.getWaterAccumulatedToday(yesterdayClimateLog.getEtc(), yesterdayClimateLog.getEto(), yesterdayClimateLog.getRainWater(),
        yesterdayClimateLog.getWaterAccumulated(), irrigationLogService.getTotalWaterIrrigation(yesterdayDate, currentParcel));

        climateLog.setWaterAccumulated(waterAccumulatedToday);

        /*
         * Crea el registro historico recuperado estando
         * asociado a la parcela actualmente usada por
         * la sentencia de repeticion
         */
        climateLogServiceBean.create(climateLog);
      } // End if

    } // End for

  }

}
