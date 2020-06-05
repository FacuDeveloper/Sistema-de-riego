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

import util.UtilDate;

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
  @Path("/findInstancesParcelByParcelName/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findInstancesParcelByParcelName(@PathParam("name") String name) throws IOException {
    Collection<InstanciaParcela> instancias = service.findInstancesParcelByParcelName(name);
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
    InstanciaParcela newInstanceParcel = mapper.readValue(json, InstanciaParcela.class);
    newInstanceParcel = service.create(newInstanceParcel);
    newInstanceParcel.setStatus(service.getStatus(newInstanceParcel.getFechaSiembra(), newInstanceParcel.getFechaCosecha(), statusService.findAll()));

    /*
     * Modifica los estados de las instancias de las parcelas,
     * todas ellas pertenecientes a la misma parcela, en base
     * a sus fechas y a la fecha actual del sistema
     */
    service.modifyStates(newInstanceParcel.getParcel().getName(), statusService.findAll());
    return mapper.writeValueAsString(newInstanceParcel);
  }

  /**
   * Comprueba si hay superposicion entre la fecha
   * de siembra y la fecha de cosecha de la instancia
   * de parcela a crear
   * @param "/overlapSeedDateHarvest"
   */
  @POST
  @Path("/overlapSeedDateHarvest")
  @Produces(MediaType.APPLICATION_JSON)
  public String overlapSeedDateHarvest(String json) throws IOException {
    InstanciaParcela newInstanceParcel = mapper.readValue(json, InstanciaParcela.class);

    /*
     * Si la fecha de siembra de la nueva instancia
     * de parcela es mayor o igual que su fecha de
     * cosecha, esta nueva instancia de parcela no
     * se tiene que crear
     */
    if ((newInstanceParcel.getFechaCosecha() != null) && ((newInstanceParcel.getFechaSiembra().compareTo(newInstanceParcel.getFechaCosecha())) >= 0)) {
      return mapper.writeValueAsString(null);
    }

    return mapper.writeValueAsString(newInstanceParcel);
  }

  /**
   * Comprueba si hay superposicion de fechas entre la
   * nueva instancia de parcela y las demas instancias
   * de parcela, todas estas y la primera de la misma
   * parcela
   * @param "/dateOverlay"
   */
  @POST
  @Path("/dateOverlayInCreation")
  @Produces(MediaType.APPLICATION_JSON)
  public String dateOverlayInCreation(String json) throws IOException {
    InstanciaParcela newInstanceParcel = mapper.readValue(json, InstanciaParcela.class);
    Calendar currentDate = Calendar.getInstance();
    Calendar harvestDate = null;

    /*
     * Instancia de parcela (registro historico de parcela) mas
     * reciente (o ultima) que esta en el estado "Finalizado"
     */
    InstanciaParcela recentFinishedInstanceParcel = service.findRecentFinished(newInstanceParcel.getParcel());

    /*
     * Instancia de parcela que esta en el estado 'En desarrollo',
     * hay que recordar que solo una instancia de parcela puede
     * estar en el estado mencionado
     */
    InstanciaParcela instanceParcelInDevelopment = service.findInDevelopment(newInstanceParcel.getParcel());

    /*
     * Si la fecha de cosecha no fue establecida por
     * el usuario al momento de crear una instancia
     * de parcela, el sistema la calcula de forma
     * automatica
     */
    if (newInstanceParcel.getFechaCosecha() == null) {
      harvestDate = cultivoService.calculateHarvestDate(newInstanceParcel.getFechaSiembra(), newInstanceParcel.getCultivo());
      newInstanceParcel.setFechaCosecha(harvestDate);
    }

    /*
     * Evita la superposicion de fechas entre las
     * instancias de parcelas y la nueva instancia
     * de parcela
     */
    if (service.dateOverlayInCreation(newInstanceParcel)) {
      return mapper.writeValueAsString(null);
    }

    return mapper.writeValueAsString(newInstanceParcel);
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
  public String modify(@PathParam("id") int id, String json) throws IOException  {
    InstanciaParcela modifiedInstanceParcel = mapper.readValue(json, InstanciaParcela.class);
    modifiedInstanceParcel = service.modify(id, modifiedInstanceParcel);

    /*
     * Coleccion que tiene todas las instancias de parcela
     * que pertenecen a la misma parcela de la nueva instancia
     * de parcela
     */
    Collection<InstanciaParcela> instances = service.findInstancesParcelByParcelName(modifiedInstanceParcel.getParcel().getName());

    /*
     * Modifica los estados de las instancias de las parcelas,
     * todas ellas pertenecientes a la misma parcela, en base
     * a sus fechas y a la fecha actual del sistema
     */
    service.modifyStates(modifiedInstanceParcel.getParcel().getName(), statusService.findAll());
    return mapper.writeValueAsString(modifiedInstanceParcel);

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
    //   instancia = service.modify(id, instancia);
    //   return mapper.writeValueAsString(instancia);
    // }

    // instancia.setStatus(getStatus(instancia.getFechaCosecha()));
    // instancia = service.modify(id, instancia);
    // return mapper.writeValueAsString(instancia);
  }

  @PUT
  @Path("/dateOverlayInModification")
  @Produces(MediaType.APPLICATION_JSON)
  public String dateOverlayInModification(String json) throws IOException  {
    InstanciaParcela modifiedInstanceParcel = mapper.readValue(json, InstanciaParcela.class);

    /*
     * Evita la superposicion de fechas entre las
     * instancias de parcelas y la instancia de
     * parcela modificada
     */
    if (service.dateOverlayInModification(modifiedInstanceParcel)) {
      return mapper.writeValueAsString(null);
    }

    return mapper.writeValueAsString(modifiedInstanceParcel);
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
    Calendar tomorrowDate = UtilDate.getTomorrowDate();

    /*
     * Solicita el registro del clima del dia de mañana
     */
    ClimateLog tomorrowClimateLog = climateLogService.getClimateLog(parcel.getLatitude(), parcel.getLongitude(), (tomorrowDate.getTimeInMillis() / 1000));
    tomorrowPrecipitation = tomorrowClimateLog.getRainWater();

    /*
     * Fecha del dia inmediatamente anterior a la fecha
     * actual del sistema
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    /*
     * Cantidad total de agua utilizada en los riegos
     * realizados en el dia de hoy, la cual es necesaria
     * para determinar el riego sugerido para el dia de
     * hoy
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
      yesterdayEto = Eto.getEto(yesterdayClimateLog, extraterrestrialSolarRadiation, maximumInsolation);

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
    yesterdayClimateLog = climateLogServiceBean.find(yesterdayDate, parcel);
    suggestedIrrigationToday = WaterMath.getSuggestedIrrigation(yesterdayClimateLog.getEtc(), yesterdayClimateLog.getEto(), yesterdayClimateLog.getRainWater(), yesterdayClimateLog.getWaterAccumulated(), totalIrrigationWaterToday);
    // suggestedIrrigationToday = WaterMath.getSuggestedIrrigation(parcel.getArea(), yesterdayClimateLog.getEtc(), yesterdayClimateLog.getEto(), yesterdayClimateLog.getRainWater(), yesterdayClimateLog.getWaterAccumulated(), totalIrrigationWaterToday);

    IrrigationLog newIrrigationLog = new IrrigationLog();
    newIrrigationLog.setDate(currentDate);
    newIrrigationLog.setSuggestedIrrigation(suggestedIrrigationToday);
    newIrrigationLog.setTomorrowPrecipitation(WaterMath.truncateToThreeDecimals(tomorrowPrecipitation));
    newIrrigationLog.setParcel(parcel);

    return mapper.writeValueAsString(newIrrigationLog);
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
