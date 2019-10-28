package servlet;

import javax.ejb.EJB;

import javax.ws.rs.GET;
// import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.ClimateLog;
import model.Cultivo;
import model.Latitude;
import model.Month;
import model.SolarRadiation;
import model.MaximumInsolation;

import stateless.CultivoService;
import stateless.ClimateLogService;
import stateless.et.ETc;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;
import stateless.LatitudeServiceBean;
import stateless.MonthServiceBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;

import java.io.IOException;

@Path("/client")
public class IrrigationWaterRestServlet {

  // inject a reference to the CultivoService slsb
  @EJB CultivoService serviceCultivo;

  // inject a reference to the ExtraterrestrialSolarRadiation slsb
  @EJB SolarRadiationServiceBean solarService;

  // inject a reference to the MaximumInsolationServiceBean slsb
  @EJB MaximumInsolationServiceBean insolationService;

  // inject a reference to the LatitudeServiceBean slsb
  @EJB LatitudeServiceBean latitudeService;

  // inject a reference to the MonthServiceBean slsb
  @EJB MonthServiceBean monthService;

  // mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/{latitude}/{longitude}/{cropName}/{seedTime}/{amountWater}")
  @Produces(MediaType.APPLICATION_JSON)
  public String irrigationWaterOut(@PathParam("latitude") double latitude, @PathParam("longitude") double longitude, @PathParam("cropName") String cropName,
  @PathParam("seedTime") long seedTime, @PathParam("amountWater") double amountWater) throws IOException {
    Cultivo choosenCrop = null;

    try {
      choosenCrop = serviceCultivo.findByName(cropName);
    } catch(Exception e) {
      return mapper.writeValueAsString("No existe un cultivo llamado " + cropName);
    }

    if (seedTime < 0) {
      return mapper.writeValueAsString("La fecha en formato UNIX no debe ser negativa");
    }

    if (amountWater < 0) {
      return mapper.writeValueAsString("La cantidad de agua de riego del día de ayer no debe ser negativa");
    }

    ClimateLogService climateLogService = ClimateLogService.getInstance();

    /*
     * La fecha del dia de ayer es necesaria para
     * obtener los datos climaticos del dia de ayer
     */
    Calendar yesterday = Calendar.getInstance();
    yesterday.set(Calendar.DAY_OF_YEAR, yesterday.get(Calendar.DAY_OF_YEAR) - 1);

    /*
     * Se convierte el tiempo en milisegundos a segundos porque
     * la API del clima Dark Sky utiliza la fecha para la obtencion
     * de datos climaticos en formato UNIX
     */
    long unixTime = (yesterday.getTimeInMillis() / 1000);

    /*
     * Registro climatico del dia de ayer en funcion
     * de las coordenadas geograficas recibidas, obtenido
     */
    ClimateLog yesterdayClimateLog = climateLogService.getClimateLog(latitude, longitude, unixTime);

    /*
     * Fecha de siembra convertida de formato UNIX
     * a formato Calendar
     */
    Calendar calendarSeedTime = Calendar.getInstance();
    calendarSeedTime.setTimeInMillis(seedTime * 1000L);

    // String date = yesterday.get(Calendar.DAY_OF_MONTH) + "-" + (yesterday.get(Calendar.MONTH) + 1) + "-" + yesterday.get(Calendar.YEAR);

    double resultAmountWater = getIrrigationWater(choosenCrop, calendarSeedTime, yesterdayClimateLog, amountWater, latitude);
    return mapper.writeValueAsString(resultAmountWater);
    // return mapper.writeValueAsString(date);
  }

  private double getIrrigationWater(Cultivo crop, Calendar seedTime, ClimateLog climateLog, double amountWater, double latitude) {
    /*
     * Se obtiene el coeficiente del cultivo (Kc)
     * para poder calcular la evapotranspiracion del
     * cultivo bajo condiciones estandar
     */
    double kc = serviceCultivo.getKc(crop, seedTime);

    /*
     * Se obtiene la radiacion solar y la duracion
     * de insolacion maxima en funcion del mes
     * actual y de la latitud enviada como
     * parametro
     *
     * Estos datos son obtenidos para poder
     * calcular la ETc
     */
    double solarRadiation = getExtraterrestrialSolarRadiation(latitude);
    double maximumInsolation = getMaximumInsolation(latitude);

    /*
     * Calcula la evapotranspiracion del cultivo
     * bajo condiciones estandar ETc = Kc * ETo
     * con los datos climaticos recuperados y
     * con el coeficiente del cultivo enviado
     * como parametro
     */
    double etc = ETc.getEtc(kc, climateLog.getTemperatureMin(), climateLog.getTemperatureMax(), climateLog.getPressure(),
    climateLog.getWindSpeed(), climateLog.getDewPoint(), solarRadiation, maximumInsolation, climateLog.getCloudCover());

    /*
     * Se suma la cantidad de agua (si la hubo) utilizada
     * en el riego de ayer mas la cantidad total de agua
     * de lluvia del dia de ayer (si la hubo)
     */
    double totalWater = amountWater + climateLog.getTotalRainWater();

    /*
     * Variable que contiene el resultado de la
     * llamada a este servicio web
     */
    double resultAmountWater = 0;

    /*
     * Si la cantidad total de agua (agua de
     * lluvia mas agua de riego, si huno una
     * de ellas o ambas) es igual a la
     * cantidad de agua que se va a evaporar,
     * la cantidad de agua de riego que se tiene
     * que utilizar para el dia de hoy es la
     * cantidad de agua dada por la evapotranspiracion
     * del cultivo bajo condiciones estandar (ETc)
     *
     * Si la cantidad total de agua (agua de
     * lluvia mas agua de riego, si hubo una
     * de ellas o ambas) es menor a la cantidad
     * de agua que se va evaporar, la cantidad
     * de agua de riego que se tiene que utilizar
     * para el dia de hoy es la cantidad de agua
     * dada por la evapotranspiracion del cultivo
     * bajo condiciones estandar (ETc)
     */
    if ((totalWater == etc) || (totalWater < etc)) {
      resultAmountWater = etc;
    }

    if (totalWater > etc) {
      resultAmountWater = totalWater - etc;
    }

    return resultAmountWater;
  }

  private double getExtraterrestrialSolarRadiation(double latitude) {
    /*
     * Convierte de double a entero lo que
     * provoca que tome unicamente la parte
     * entera
     */
    int intLatitude = (int) latitude;

    Latitude previousLatitude = null;
    Latitude nextLatitude = null;

    /*
     * Radiacion solar promedio
     */
    double averageSolarRadiation = 0.0;

    /*
     * Radiaciones solares aledañas a la
     * radiacion solar de la latitud que fue
     * pasada como parametro
     */
    double previousSolarRadiation = 0.0;
    double nextSolarRadiation = 0.0;

    /*
     * Se obtiene el numero del mes actual sumadole
     * un uno al numero de mes de la clase Calendar
     * porque en dicha clase los meses empiezan desde
     * 0 y no desde 1
     */
    int numberCurrentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    Month currentMonth = monthService.find(numberCurrentMonth);

    /*
     * Si la latitud es impar se tienen
     * que recuperar de la base de datos
     * las latitudes aledañas a la que fue pasada
     * como parametro y partir de estas y del
     * mes actual recuperar las radiaciones solares
     * correspondientes y calcular el promedio de ambas
     */
    if ((intLatitude % 2) != 0) {
      previousLatitude = latitudeService.getLatitude(intLatitude - 1);
      nextLatitude = latitudeService.getLatitude(intLatitude + 1);

      previousSolarRadiation = solarService.findSolarRadiation(currentMonth, previousLatitude).getSolarRadiation();
      nextSolarRadiation = solarService.findSolarRadiation(currentMonth, nextLatitude).getSolarRadiation();

      averageSolarRadiation = (previousSolarRadiation + nextSolarRadiation) / 2;
      return averageSolarRadiation;
    }

    Latitude givenLatitude = latitudeService.getLatitude(intLatitude);
    SolarRadiation solarRadiation = solarService.findSolarRadiation(currentMonth, givenLatitude);
    return solarRadiation.getSolarRadiation();
  }

  private double getMaximumInsolation(double latitude) {
    /*
     * Convierte de double a entero lo que
     * provoca que tome unicamente la parte
     * entera
     */
    int intLatitude = (int) latitude;

    Latitude previousLatitude = null;
    Latitude nextLatitude = null;

    double averageMaximumInsolation = 0.0;

    /*
     * Duraciones de insolaciones maximas
     * aledañas a la duracion de la insolacion
     * maxima correspondiente a la latitud
     * pasada como parametro
     */
    double previousMaximumInsolation = 0.0;
    double nextMaximumInsolation = 0.0;

    /*
     * Se obtiene el numero del mes actual sumadole
     * un uno al numero de mes de la clase Calendar
     * porque en dicha clase los meses empiezan desde
     * 0 y no desde 1
     */
    int numberCurrentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    Month currentMonth = monthService.find(numberCurrentMonth);

    /*
     * Si la latitud es impar se tienen
     * que recuperar de la base de datos
     * las latitudes aledañas a la que fue pasada
     * como parametro
     */
    if ((intLatitude % 2) != 0) {
      previousLatitude = latitudeService.getLatitude(intLatitude - 1);
      nextLatitude = latitudeService.getLatitude(intLatitude + 1);

      previousMaximumInsolation = insolationService.findMaximumInsolation(currentMonth, previousLatitude).getMaximumInsolation();
      nextMaximumInsolation = insolationService.findMaximumInsolation(currentMonth, nextLatitude).getMaximumInsolation();

      averageMaximumInsolation = (previousMaximumInsolation + nextMaximumInsolation) / 2;
    }

    Latitude givenLatitude = latitudeService.getLatitude(intLatitude);
    MaximumInsolation maximumInsolation = insolationService.findMaximumInsolation(currentMonth, givenLatitude);
    return maximumInsolation.getMaximumInsolation();
  }

}
