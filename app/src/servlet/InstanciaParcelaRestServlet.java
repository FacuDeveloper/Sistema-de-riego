package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.Collection;
import java.util.Calendar;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.InstanciaParcela;
import model.InstanceParcelStatus;
import model.Cultivo;
import model.Parcel;
import model.ClimateLog;
import model.IrrigationLog;

import stateless.InstanciaParcelaService;
import stateless.ParcelServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.IrrigationLogServiceBean;
import stateless.InstanceParcelStatusServiceBean;
import stateless.CultivoService;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;

import irrigation.WaterMath;

import java.lang.Math;

import java.util.Date;

import climate.ClimateLogService;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import et.Eto;

@Path("/instanciaParcela")
public class InstanciaParcelaRestServlet {

  // inject a reference to the InstanciaParcelaService slsb
  @EJB InstanciaParcelaService service;

  // inject a reference to the ClimateLogServiceBean slsb
  @EJB ClimateLogServiceBean climateLogServiceBean;

  // inject a reference to the CultivoService slsb
  @EJB CultivoService cultivoService;

  // inject a reference to the IrrigationLogServiceBean slsb
  @EJB IrrigationLogServiceBean irrigationLogService;

  // inject a reference to the InstanceParcelStatusServiceBean slsb
  @EJB InstanceParcelStatusServiceBean statusService;

  // inject a reference to the ParcelServiceBean slsb
  @EJB ParcelServiceBean serviceParcel;

  // inject a reference to the SolarRadiationServiceBean
  @EJB SolarRadiationServiceBean solarService;

  // inject a reference to the MaximumInsolationServiceBean
  @EJB MaximumInsolationServiceBean insolationService;

  // mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<InstanciaParcela> instancias = service.findAll();
    return mapper.writeValueAsString(instancias);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    InstanciaParcela instancia = service.find(id);
    return mapper.writeValueAsString(instancia);
  }

  @GET
  @Path("/findCurrentParcelInstance/{idParcel}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findCurrentParcelInstance(@PathParam("idParcel") int idParcel) throws IOException {
    Parcel choosenParcel = serviceParcel.find(idParcel);
    InstanciaParcela instancia = service.findInDevelopment(choosenParcel);
    return mapper.writeValueAsString(instancia);
  }

  @GET
  @Path("/findNewestParcelInstance/{idParcel}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findNewestParcelInstance(@PathParam("idParcel") int idParcel) throws IOException {
    Parcel choosenParcel = serviceParcel.find(idParcel);
    InstanciaParcela instancia = service.findRecentFinished(choosenParcel);
    return mapper.writeValueAsString(instancia);
  }

  // @GET
  // @Path("/checkStageCropLife/{idCrop}")
  // @Produces(MediaType.APPLICATION_JSON)
  // public String checkStageCropLife(@PathParam("idCrop") int idCrop, @QueryParam("fechaSiembra") String fechaSiembra,
  // @QueryParam("fechaCosecha") String fechaCosecha) throws IOException, ParseException {
  //   Cultivo choosenCrop = cultivoService.find(idCrop);
  //
  //   SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  //   Calendar seedDate = Calendar.getInstance();
  //   Calendar harvestDate = Calendar.getInstance();
  //
  //   /*
  //    * Fechas convertidas de String a Date
  //    */
  //   Date dateSeedDate = new Date(dateFormatter.parse(fechaSiembra).getTime());
  //   Date dateHarvestDate = new Date(dateFormatter.parse(fechaCosecha).getTime());
  //
  //   seedDate.set(dateSeedDate.getYear(), dateSeedDate.getMonth(), dateSeedDate.getDate());
  //   harvestDate.set(dateHarvestDate.getYear(), dateHarvestDate.getMonth(), dateHarvestDate.getDate());
  //
  //   /*
  //    * Si la diferencia en dias entre la fecha de siembra
  //    * y la fecha de cosecha ingresadas es mayor a la cantidad
  //    * total de dias de vida que vive el cultivo dado, retorna
  //    * el cultivo dado como "error"
  //    */
  //   if (excessStageLife(choosenCrop, seedDate, harvestDate)) {
  //     return mapper.writeValueAsString(choosenCrop);
  //   }
  //
  //   return mapper.writeValueAsString(null);
  // }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException  {
    InstanciaParcela instancia = mapper.readValue(json, InstanciaParcela.class);

    /*
     * Instancia de parcela (registro historico de parcela) mas
     * reciente que esta en el estado "Finalizado"
     */
    InstanciaParcela newestInstanceParcel = service.findRecentFinished(instancia.getParcel());

    /*
     * Si la fecha de cosecha de la instancia de parcela mas reciente
     * que esta en el estado "Finalizado" es mayor o igual que la fecha de
     * siembra de la nueva instancia de parcela, entonces no se tiene
     * que persistir la nueva instancia de parcela
     */
    if ((newestInstanceParcel != null) && ((newestInstanceParcel.getFechaCosecha().compareTo(instancia.getFechaSiembra())) >= 0)) {
      return null;
    }

    /*
     * La instancia de parcela (registro historico de parcela)
     * actual es aquella instancia de parcela que a la fecha
     * actual del sistema esta en el estado "En desarrollo"
     */
    InstanciaParcela currentParcelInstance = service.findInDevelopment(instancia.getParcel());

    /*
     * Si no hay un registro historico actual de parcela
     * entonces se procede a crear el nuevo registro historico
     * de parcela, el cual es el actual porque su cultivo al
     * estar en este nuevo registro historico actual de parcela
     * aun no ha llegado a su fecha de cosecha
     */
    if (currentParcelInstance == null) {
      /*
       * En funcion de la fecha de siembra del cultivo dado y
       * de la suma de sus dias de vida (suma de la cantidad de
       * dias que dura cada una de sus etapas), se calcula
       * la fecha de cosecha del cultivo dado
       */
      Calendar harvestDate = cultivoService.calculateHarvestDate(instancia.getFechaSiembra(), instancia.getCultivo());
      instancia.setFechaCosecha(harvestDate);
      instancia.setStatus(getStatus(harvestDate));

      instancia = service.create(instancia);
      return mapper.writeValueAsString(instancia);
    }

    return null;
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    InstanciaParcela instancia = service.remove(id);
    return mapper.writeValueAsString(instancia);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String change(@PathParam("id") int id, String json) throws IOException  {
    InstanciaParcela instancia = mapper.readValue(json,InstanciaParcela.class);

    InstanciaParcela previuosParcelInstance = service.find(instancia.getParcel(), instancia.getId() - 1);
    InstanciaParcela nextParcelInstance = service.find(instancia.getParcel(), instancia.getId() + 1);

    /*
     * Si la fecha de cosecha de la instancia de parcela anterior a la
     * que se va a modificar es mayor a la fecha de siembra de la instancia
     * de parcela que se va a modificar, no se tiene que realizar la modificacion
     */
    if ((previuosParcelInstance != null) && ((previuosParcelInstance.getFechaCosecha().compareTo(instancia.getFechaSiembra()) == 0)
    || (previuosParcelInstance.getFechaCosecha().compareTo(instancia.getFechaSiembra()) > 0))) {
      return null;
    }

    /*
     * Si la fecha de cosecha de la instancia de parcela que se va a modificar
     * es mayor que la fecha de siembra de la siguiente instancia de parcela
     * a la que se va a modificar, no se tiene que realizar la modificacion
     */
    if ((nextParcelInstance != null) && ((instancia.getFechaCosecha().compareTo(nextParcelInstance.getFechaSiembra()) == 0)
    || (instancia.getFechaCosecha().compareTo(nextParcelInstance.getFechaSiembra()) > 0))) {
      return null;
    }

    /*
     * Si la fecha de siembra y la fecha de cosecha de la instancia
     * de parcela que se va a modificar coinciden, no se tiene que
     * realizar la modificacion
     */
    if ((instancia.getFechaSiembra() != null) && (instancia.getFechaCosecha() != null) && (instancia.getFechaSiembra().compareTo(instancia.getFechaCosecha()) == 0)) {
      return null;
    }

    // if (instancia.getStatus().getName().equals("Finalizado")) {
    //   instancia = service.change(id, instancia);
    //   return mapper.writeValueAsString(instancia);
    // }

    /*
     * NOTE: Falta hacerlo funcionar
     *
     * Si la diferencia en dias entre la fecha de siembra y la fecha
     * de cosecha ingresadas no es mayor que la cantidad de dias que
     * dura la etapa de vida del cultivo dado se realiza la modificacion
     * del registro historico de parcela dado
     */
    // if (!(excessStageLife(instancia.getCultivo(), instancia.getFechaSiembra(), instancia.getFechaCosecha()))) {
    //   instancia.setStatus(getStatus(instancia.getFechaCosecha()));
    //   instancia = service.change(id, instancia);
    //   return mapper.writeValueAsString(instancia);
    // }

    instancia.setStatus(getStatus(instancia.getFechaCosecha()));
    instancia = service.change(id, instancia);
    return mapper.writeValueAsString(instancia);
  }

  @GET
  @Path("/suggestedIrrigation/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getSuggestedIrrigation(@PathParam("id") int id) throws IOException {
    InstanciaParcela choosenParcelInstance = service.find(id);
    Parcel parcel = choosenParcelInstance.getParcel();
    double suggestedIrrigationToday = 0.0;
    double tomorrowPrecipitation = 0.0;

    ClimateLogService climateLogService = ClimateLogService.getInstance();
    ClimateLog yesterdayClimateLog = null;
    double yesterdayEto = 0.0;
    double yesterdayEtc = 0.0;
    double extraterrestrialSolarRadiation = 0.0;
    double maximumInsolation = 0.0;

    /*
     * Fecha actual del sistema
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Fecha del dia de mañana para solicitar la precipitacion
     * del dia de mañana
     */
    Calendar tomorrowDate = Calendar.getInstance();
    tomorrowDate.set(Calendar.DAY_OF_YEAR, tomorrowDate.get(Calendar.DAY_OF_YEAR) + 1);

    /*
     * Solicita el registro del clima del dia de mañana
     */
    ClimateLog tomorrowClimateLog = climateLogService.getClimateLog(parcel.getLatitude(), parcel.getLongitude(), (tomorrowDate.getTimeInMillis() / 1000));
    tomorrowPrecipitation = tomorrowClimateLog.getRainWater();

    /*
     * Fecha del dia inmediatamente anterior a la fecha
     * actual del sistema
     */
    Calendar yesterdayDate = getYesterdayDate();

    /*
     * Cantidad total de agua utilizada en los riegos
     * realizados en el dia de hoy
     */
    double totalIrrigationWaterToday = irrigationLogService.getTotalWaterIrrigationToday(parcel);

    /*
     * Si el registro climatico del dia de ayer no existe en
     * la base de datos, se lo tiene que pedir y se lo tiene
     * que persistir en la base de datos subyacente
     */
    if (!(climateLogServiceBean.exist(yesterdayDate, parcel))) {
      yesterdayClimateLog = climateLogService.getClimateLog(parcel.getLatitude(), parcel.getLongitude(), (yesterdayDate.getTimeInMillis() / 1000));

      extraterrestrialSolarRadiation = solarService.getRadiation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());
      maximumInsolation = insolationService.getInsolation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());

      /*
       * Evapotranspiracion del cultivo de referencia (ETo) con las
       * condiciones climaticas del registro climatico del dia de ayer
       */
      yesterdayEto = Eto.getEto(yesterdayClimateLog.getTemperatureMin(), yesterdayClimateLog.getTemperatureMax(), yesterdayClimateLog.getPressure(), yesterdayClimateLog.getWindSpeed(),
      yesterdayClimateLog.getDewPoint(), extraterrestrialSolarRadiation, maximumInsolation, yesterdayClimateLog.getCloudCover());

      /*
       * Evapotranspiracion del cultivo bajo condiciones esntandar (ETc)
       * del cultivo dado con la ETo del dia de ayer
       */
      yesterdayEtc = cultivoService.getKc(choosenParcelInstance.getCultivo(), choosenParcelInstance.getFechaSiembra()) * yesterdayEto;

      yesterdayClimateLog.setEto(yesterdayEto);
      yesterdayClimateLog.setEtc(yesterdayEtc);
      yesterdayClimateLog.setParcel(parcel);
      climateLogServiceBean.create(yesterdayClimateLog);
    }

    /*
     * Recupera el registro climatico de la parcela
     * de la fecha anterior a la fecha actual
     */
    ClimateLog climateLog = climateLogServiceBean.find(yesterdayDate, parcel);
    suggestedIrrigationToday = WaterMath.getSuggestedIrrigation(parcel.getArea(), climateLog.getEtc(), climateLog.getEto(), climateLog.getRainWater(), climateLog.getWaterAccumulated(), totalIrrigationWaterToday);

    IrrigationLog newIrrigationLog = new IrrigationLog();
    newIrrigationLog.setDate(currentDate);
    newIrrigationLog.setSuggestedIrrigation(suggestedIrrigationToday);
    newIrrigationLog.setTomorrowPrecipitation(tomorrowPrecipitation);
    newIrrigationLog.setParcel(parcel);

    return mapper.writeValueAsString(newIrrigationLog);
  }

  /**
   * @return la fecha anterior a la fecha actual del sistema
   */
  private Calendar getYesterdayDate() {
    Calendar currentDate = Calendar.getInstance();
    Calendar yesterdayDate = Calendar.getInstance();

    /*
    * Si la fecha actual es el primero de Enero, entonces
    * la fecha anterior a la fecha actual es el 31 de Diciembre
    *
    * Si la fecha actual no es el primero de Enero, entonces
    * la fecha anterior a la fecha actual es el dia anterior
    * a la fecha actual
    */
    if (currentDate.get(Calendar.DAY_OF_YEAR) == 1) {
      yesterdayDate.set(Calendar.DAY_OF_YEAR, 365);
    } else {
      yesterdayDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) - 1);
    }

    return yesterdayDate;
  }

  /**
   * @param  harvestDate [fecha de cosecha]
   * @return el estado "En desarrollo" si la fecha de cosecha esta
   * despues de la fecha actual del sistema y el estado "Finalizado"
   * si la fecha de cosecha esta antes de la fecha actual del sistema
   * o si es igual a la misma
   */
  private InstanceParcelStatus getStatus(Calendar harvestDate) {
    Calendar yesterdayCurrentDate = Calendar.getInstance();
    yesterdayCurrentDate.set(Calendar.DAY_OF_YEAR, yesterdayCurrentDate.get(Calendar.DAY_OF_YEAR) - 1);

    /*
     * Si la fecha de cosecha del registro historico de parcela
     * esta despues de la fecha actual del sistema - un dia retorna el
     * estado "En desarrollo"
     */
    if ((harvestDate.compareTo(yesterdayCurrentDate)) > 0) {
      return statusService.find(2);
    }

    /*
     * En cambio si la fecha de cosecha del registro historico
     * de parcela esta antes de la fecha actual del sistema
     * o es igual a la misma, retorna el estado "Finalizado"
     */
    return statusService.find(1);
  }

  /**
   * @param  givenCrop
   * @param  seedDate    [fecha de siembra]
   * @param  harvestDate [fecha de cosecha]
   * @return verdadero si la diferencia en dias entre la fecha de
   * siembra y la fecha de cosecha es mayor que la cantidad de dias
   * que dura la etapa de vida del cultivo dado, en caso contrario
   * retorna falso
   */
  private boolean excessStageLife(Cultivo givenCrop, Calendar seedDate, Calendar harvestDate) {
    int differenceBetweenDates = 0;
    int totalDaysLife = givenCrop.getEtInicial() + givenCrop.getEtDesarrollo() + givenCrop.getEtMedia() + givenCrop.getEtFinal();

    /*
     * Si los años de la fecha de siembra y de la fecha de cosecha
     * son iguales, entonces la diferencia en dias entre ambas fechas
     * se calcula de forma directa
     */
    if ((seedDate.get(Calendar.YEAR)) == (harvestDate.get(Calendar.YEAR))) {
      differenceBetweenDates = harvestDate.get(Calendar.DAY_OF_YEAR) - seedDate.get(Calendar.DAY_OF_YEAR);
    }

    /*
     * Si la diferencia entre los años de la fecha de siembra y la fecha
     * de cosecha ingresadas es igual a uno, la diferencia en dias entre
     * ambas fechas se calcula de la siguiente forma:
     *
     * Diferencia en dias entre ambas fechas = (365 - numero del dia en el
     * año de la fecha de siembra + 1) - numero del dia en el año de la
     * fecha de cosecha
     */
    if ((harvestDate.get(Calendar.YEAR) - seedDate.get(Calendar.YEAR)) == 1) {
      differenceBetweenDates = (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1) + harvestDate.get(Calendar.DAY_OF_YEAR);
    }

    /*
     * Si la diferencia entre los años de la fecha de siembra y la fecha
     * de cosecha ingresadas es mayor a uno, la diferencia en dias entre
     * ambas fechas se calcula de la siguiente forma:
     *
     * Diferencia en dias entre ambas fechas = (año de la fecha de cosecha -
     * año de la fecha de siembra) * 365 - (365 - numero del dia en el año
     * de la fecha de siembra + 1) - numero del dia en el año de la fecha
     * de cosecha
     */
    if ((harvestDate.get(Calendar.YEAR) - seedDate.get(Calendar.YEAR)) > 1) {
      differenceBetweenDates = ((harvestDate.get(Calendar.YEAR) - seedDate.get(Calendar.YEAR)) * 365) - (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1) - harvestDate.get(Calendar.DAY_OF_YEAR);
    }

    /*
     * Si la diferencia en dias entre la fecha de siembra
     * y la fecha de cosecha ingresadas es mayor a la cantidad
     * total de dias de vida que vive el cultivo dado, retorna
     * verdadero
     */
    if (differenceBetweenDates > totalDaysLife) {
      return true;
    }

    return false;
  }

}
