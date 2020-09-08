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
import model.Parcel;
import model.ClimateLog;
import model.IrrigationLog;
import model.DateError;

import stateless.InstanciaParcelaService;
import stateless.ParcelServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.IrrigationLogServiceBean;
import stateless.InstanceParcelStatusServiceBean;
import stateless.CultivoService;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;
import stateless.DateErrorServiceBean;

import irrigation.WaterMath;

import climate.ClimateLogService;

import util.UtilDate;

@Path("/instanciaParcela")
public class InstanciaParcelaRestServlet {

  // inject a reference to the InstanciaParcelaService slsb
  @EJB InstanciaParcelaService service;

  // inject a reference to the ClimateLogServiceBean slsb
  @EJB ClimateLogServiceBean climateLogServiceBean;

  // inject a reference to the CultivoService slsb
  @EJB CultivoService cropService;

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

  // inject a reference to the DateErrorServiceBean
  @EJB DateErrorServiceBean dateErrorService;

  // mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  /*
   * Variable de tipo por referencia para almacenar la
   * referencia de un objeto de tipo DateError, el cual
   * representa un error de fechas
   *
   * Hay dos errores de fechas: uno para cuando la
   * fecha de siembra y la fecha de cosecha de una
   * instancia de parcela estan cruzadas (superpuestas)
   * y otro para cuando las fechas de una instancia de
   * parcela estan superpuestas con las fechas de las
   * instancias de parcelas pertenecientes a la misma
   * parcela
   */
  private DateError dateError;

  /*
   * Constantes numericas utilizadas para recuperar
   * de la base de datos subyacente cada uno de los
   * errores de fecha que puede haber en la creacion
   * y en la modificacion de una instancia de parcela
   */
  private static final int ID_CROSSOVER_DATE_ERROR = 1;
  private static final int ID_OVERLAY_DATE_ERROR = 2;
  private Calendar harvestDate;

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

  /*
   * NOTE: No se para que se usa este metodo
   */
  @GET
  @Path("/findCurrentParcelInstance/{idParcel}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findCurrentParcelInstance(@PathParam("idParcel") int idParcel) throws IOException {
    Parcel choosenParcel = serviceParcel.find(idParcel);
    InstanciaParcela instancia = service.findInDevelopment(choosenParcel);
    return mapper.writeValueAsString(instancia);
  }

  @GET
  @Path("/findInstancesParcelByParcelName/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findInstancesParcelByParcelName(@PathParam("name") String name) throws IOException {
    Collection<InstanciaParcela> instancias = service.findInstancesParcelByParcelName(name);
    return mapper.writeValueAsString(instancias);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    InstanciaParcela newInstance = mapper.readValue(json, InstanciaParcela.class);

    /*
     * Si la fecha de cosecha de la nueva instancia de parcela
     * no ha sido definida por el usuario se la crea
     */
    if (newInstance.getFechaCosecha() == null) {
      harvestDate = cropService.calculateHarvestDate(newInstance.getFechaSiembra(), newInstance.getCultivo());
      newInstance.setFechaCosecha(harvestDate);
      newInstance.setStatus(statusService.getStatus(newInstance.getFechaSiembra(), harvestDate));
    } else {
      newInstance.setStatus(statusService.getStatus(newInstance.getFechaSiembra(), newInstance.getFechaCosecha()));
    }

    /*
     * Si la fecha de siembra y la fecha de cosecha estan
     * cruzadas, es decir, superpuestas, entonces se envia
     * de parte de la aplicacion del lado servidor un aviso
     * de error de fechas, el cual contiene una descripcion
     * para evitar el error
     */
    if (service.crossoverDates(newInstance.getFechaSiembra(), newInstance.getFechaCosecha())) {
      dateError = dateErrorService.find(ID_CROSSOVER_DATE_ERROR);
      return mapper.writeValueAsString(dateError);
    }

    /*
     * Si hay superposicion de fechas entre la nueva instancia
     * de parcela y las instancias de parcela pertenecientes
     * a la misma parcela (de la nueva instancia de parcela)
     * entonces se envia de parte de la aplicacion del lado
     * servidor un aviso de error de fechas, el cual
     * contiene una descripcion para evitar el error
     */
    if (service.overlapDates(service.findInstancesParcelByParcelName(newInstance.getParcel().getName()), newInstance)) {
      dateError = dateErrorService.find(ID_OVERLAY_DATE_ERROR);
      return mapper.writeValueAsString(dateError);
    }

    newInstance = service.create(newInstance);
    return mapper.writeValueAsString(newInstance);
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
    InstanciaParcela modifiedInstance = mapper.readValue(json, InstanciaParcela.class);
    modifiedInstance.setStatus(statusService.getStatus(modifiedInstance.getFechaSiembra(), modifiedInstance.getFechaCosecha()));

    /*
     * Si la fecha de siembra y la fecha de cosecha estan
     * cruzadas, es decir, superpuestas, entonces se envia
     * de parte de la aplicacion del lado servidor un aviso
     * de error de fechas, el cual contiene una descripcion
     * para evitar el error
     */
    if (service.crossoverDates(modifiedInstance.getFechaSiembra(), modifiedInstance.getFechaCosecha())) {
      dateError = dateErrorService.find(ID_CROSSOVER_DATE_ERROR);
      return mapper.writeValueAsString(dateError);
    }

    /*
     * Si hay superposicion de fechas entre la nueva instancia
     * de parcela y las instancias de parcela pertenecientes
     * a la misma parcela (de la nueva instancia de parcela)
     * entonces se envia de parte de la aplicacion del lado
     * servidor un aviso de error de fechas, el cual
     * contiene una descripcion para evitar el error
     *
     * Para comprobar la superposicion de fechas en la modificacion
     * de una instancia de parcela se deben usar las instancias
     * de parcela pertenecientes a la misma parcela excepto la
     * instancia de parcela a modificar como un valor contra
     * el cual comparar para determinar si hay o no superposicion
     * de fechas cuando se modifica una instancia de parcela
     */
    if (service.overlapDates(service.findInstancesExceptOne(modifiedInstance.getId(), modifiedInstance.getParcel().getName()), modifiedInstance)) {
      dateError = dateErrorService.find(ID_OVERLAY_DATE_ERROR);
      return mapper.writeValueAsString(dateError);
    }

    modifiedInstance = service.modify(id, modifiedInstance);
    return mapper.writeValueAsString(modifiedInstance);
  }

  @GET
  @Path("/suggestedIrrigation/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getSuggestedIrrigation(@PathParam("id") int id) throws IOException {
    InstanciaParcela choosenParcelInstance = service.find(id);
    Parcel parcel = choosenParcelInstance.getParcel();
    ClimateLogService climateLogService = ClimateLogService.getInstance();

    /*
     * Fecha actual del sistema
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Se solicita el registro climatico del dia de mañana
     * para obtener la precipitacion del dia de mañana, lo
     * cual se lo hace para añadir dicho valor al registro
     * de riego que sera mostrado al usuario luego de que
     * este presione el boton para calcular el agua de riego
     */
    Calendar tomorrowDate = UtilDate.getTomorrowDate();

    /*
     * Se divide el tiempo en milisegundos entre 1000 para
     * pasarlo a segundos, unidad de medida en la cual esta
     * el formato UNIX TIMESTAMP
     */
    ClimateLog tomorrowClimateLog = climateLogService.getClimateLog(parcel.getLatitude(), parcel.getLongitude(), (tomorrowDate.getTimeInMillis() / 1000));
    double tomorrowPrecipitation = tomorrowClimateLog.getRainWater();

    /*
     * Cantidad total de agua utilizada en los riegos
     * realizados en el dia de hoy, la cual es
     * necesaria para determinar el riego sugerido
     * para el dia de hoy
     */
    double totalIrrigationWaterToday = irrigationLogService.getTotalWaterIrrigationToday(parcel);

    /*
     * Fecha del dia inmediatamente anterior a la fecha
     * actual del sistema
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    double solarRadiation = solarService.getRadiation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());
    double insolation = insolationService.getInsolation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());
    double cropCoefficient = cropService.getKc(choosenParcelInstance.getCultivo(), choosenParcelInstance.getFechaSiembra());

    /*
     * Recupera el registro climatico de la parcela
     * de la fecha anterior a la fecha actual
     */
    ClimateLog yesterdayClimateLog = climateLogServiceBean.retrieveYesterdayClimateLog(parcel, solarRadiation, insolation, cropCoefficient);

    /*
     * El agua de riego para el dia de hoy se calcula en funcion
     * de:
     * - La Etc del dia de ayer
     * - La Eto del dia de ayer, en caso de que la Etc del dia de ayer sea 0
     * - La cantidad de agua de lluvia del dia de ayer
     * - La cantidad de agua acumulada del dia de ayer
     * - La cantidad total de agua de riego utilizada en el dia de hoy
     *
     * Los primeros cuatro datos son extraidos del registro climatico del
     * dia de ayer
     */
    double suggestedIrrigationToday = WaterMath.getSuggestedIrrigation(yesterdayClimateLog, totalIrrigationWaterToday);

    IrrigationLog newIrrigationLog = new IrrigationLog();
    newIrrigationLog.setDate(currentDate);
    newIrrigationLog.setSuggestedIrrigation(suggestedIrrigationToday);
    newIrrigationLog.setTomorrowPrecipitation(WaterMath.truncateToThreeDecimals(tomorrowPrecipitation));
    newIrrigationLog.setParcel(parcel);

    return mapper.writeValueAsString(newIrrigationLog);
  }

}
