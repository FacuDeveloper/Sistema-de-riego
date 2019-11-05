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

package climate;

import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.EJB;

import java.util.Collection;
import java.util.Calendar;

import stateless.ParcelServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;
import stateless.ParcelInstanceServiceBean;
import stateless.CultivoService;

import model.ClimateLog;
import model.Parcel;
import model.ParcelInstance;

import et.Eto;

@Stateless
public class ClimateDataExtractor {

  // inject a reference to the ParcelServiceBean
  @EJB ParcelServiceBean parcelService;

  // inject a reference to the ClimateLogServiceBean
  @EJB ClimateLogServiceBean climateLogServiceBean;

  // inject a reference to the SolarRadiationServiceBean
  @EJB SolarRadiationServiceBean solarService;

  // inject a reference to the MaximumInsolationServiceBean
  @EJB MaximumInsolationServiceBean insolationService;

  // inject a reference to the ParcelInstanceServiceBean
  @EJB ParcelInstanceServiceBean parcelInstanceService;

  // inject a reference to the CultivoService
  @EJB CultivoService cultivoService;

  /**
   * Este metodo tiene como finalidad obtener y almacenar
   * de forma automatica los datos climaticos para cada
   * parcela que no los tenga, lo cual hara cada dos
   * horas a partir de las 00:00 debido a que quizas, en
   * alguna hora del dia, la API climatica (Dark Sky) puede
   * que no este disponible al momento de que el sistema le
   * pida los datos climaticos de una parcela
   *
   * @param second="*"
   * @param minute="*"
   * @param hour="0/2" cada dos horas a partir de las doce de la
   * noche
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
    long unixTime = 0;
    ClimateLog climateLog = null;
    Calendar currenDate = Calendar.getInstance();
    double eto = 0.0;
    double etc = 0.0;
    double extraterrestrialSolarRadiation = 0.0;
    double maximumInsolation = 0.0;
    ParcelInstance parcelInstance = null;

    for (Parcel currentParcel : parcels) {

      /*
       * Si no existe un registro historico climatico
       * en la fecha actual para la parcela dada, entonces
       * se tiene que crear uno
       */
      if (!climateLogServiceBean.exist(currenDate, currentParcel)) {
        latitude = currentParcel.getLatitude();
        longitude = currentParcel.getLongitude();

        /*
         * Convierte el tiempo en milisegundos a segundos
         * porque el formato UNIX TIMESTAMP utiliza el tiempo
         * en segundos y se realiza esta conversion porque la API del
         * clima llamada Dark Sky recibe fechas con el formato mencionado
         */
        unixTime = (currenDate.getInstance().getTimeInMillis() / 1000);

        /*
         * Recupera un registro del clima haciendo uso de las
         * coordenadas geograficas de las parcelas y de la fecha
         * actual en formato UNIX TIMESTAMP
         */
        climateLog = climateLogService.getClimateLog(latitude, longitude, unixTime);
        climateLog.setParcel(currentParcel);

        extraterrestrialSolarRadiation = solarService.getRadiation(currenDate.get(Calendar.MONTH), latitude);
        maximumInsolation = insolationService.getInsolation(currenDate.get(Calendar.MONTH), latitude);

        /*
         * Con los datos climaticos recuperados se calcula la
         * evapotranspiracion del cultivo de referencia (ETo)
         */
        eto = Eto.getEto(climateLog.getTemperatureMin(), climateLog.getTemperatureMax(), climateLog.getPressure(), climateLog.getWindSpeed(),
        climateLog.getDewPoint(), extraterrestrialSolarRadiation, maximumInsolation, climateLog.getCloudCover());
        climateLog.setEto(eto);

        parcelInstance = parcelInstanceService.findCurrentParcelInstance(currentParcel);

        /*
         * Si existe (!= null) un registro historico actual de la
         * parcela dada se obtiene su cultivo y su fecha de
         * siembra para obtener el kc del cultivo, y todo
         * esto es para calcular la etc del cultivo sembrado
         *
         * En pocas palabras, si hay (!= null) un registro historico
         * actual de la parcela dada es porque la misma
         * actualmente tiene un cultivo y por ende, se
         * calcula su ETc
         *
         * En caso contrario la ETc sera cero
         */
        if (parcelInstance != null) {
          etc = cultivoService.getKc(parcelInstance.getCrop(), parcelInstance.getSeedDate()) * eto;
        } else {
          etc = 0.0;
        }

        climateLog.setEtc(etc);

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
