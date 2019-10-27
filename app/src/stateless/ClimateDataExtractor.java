/*
 * Extractor de datos climaticos
 *
 * Esta clase representa el modulo encargado de obtener
 * y almacenar de forma automatica los datos climaticos
 * para cada parcela en el sistema a las 00:00 horas de
 * cada nuevo dia
 *
 * Los datos climaticos son necesarios para determinar
 * la evapotranspiracion del cultivo en cada parcela
 * y la evapotranspiracion nos indica la cantidad de
 * agua que va a evaporar un cultivo y por ende, nos indica
 * la cantidad de agua que se le tiene que reponer al
 * cultivo dado, mediante el riego
 */

package stateless;

import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.EJB;

import javax.persistence.Query;

import java.util.Collection;
import java.util.Calendar;

import model.ClimateLog;
import model.Parcel;

@Stateless
public class ClimateDataExtractor {

  // inject a reference to the ParcelServiceBean
  @EJB ParcelServiceBean parcelService;

  // inject a reference to the ClimateLogServiceBean
  @EJB ClimateLogServiceBean climateLogServiceBean;

  /**
   * Hora de ejecucion: 00:00:00 AM
   *
   * @param second="0"
   * @param minute="0"
   * @param hour="0"
   */
  // @Schedule(second="0", minute="0", hour="0", persistent=false)
  // @Schedule(second="*/5", minute="*", hour="*", persistent=false)
  public void execute() {
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

    for (Parcel currentParcel : parcels) {
      latitude = currentParcel.getLatitude();
      longitude = currentParcel.getLongitude();

      /*
       * Convierte el tiempo en milisegundos a segundos
       * porque el formato UNIX TIMESTAMP utiliza el tiempo
       * en segundos y se realiza esta conversion porque la API del
       * clima llamada Dark Sky recibe fechas con el formato mencionado
       */
      unixTime = (Calendar.getInstance().getTimeInMillis() / 1000);

      /*
       * Recupera un registro del clima haciendo uso de las
       * coordenadas geograficas de las parcelas y de la fecha
       * actual en formato UNIX TIMESTAMP
       */
      climateLog = climateLogService.getClimateLog(latitude, longitude, unixTime);
      climateLog.setParcel(currentParcel);

      /*
       * Crea el registro historico recuperado estando
       * asociado a la parcela actualmente usada por
       * la sentencia de repeticion
       */
      climateLogServiceBean.create(climateLog);
    }

  }

}
