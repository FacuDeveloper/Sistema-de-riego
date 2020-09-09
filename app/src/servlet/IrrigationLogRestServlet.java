package servlet;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.IrrigationLog;
import model.Parcel;
import model.ClimateLog;
import model.InstanciaParcela;

import stateless.IrrigationLogServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;
import stateless.InstanciaParcelaService;
import stateless.CultivoService;
import stateless.SolarRadiationServiceBean;
import stateless.MaximumInsolationServiceBean;

import java.util.Collection;
import java.util.Calendar;

import irrigation.WaterMath;

import util.UtilDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Path("/irrigationLog")
public class IrrigationLogRestServlet {

  // inject a reference to the IrrigationLogServiceBean slsb
  @EJB IrrigationLogServiceBean service;

  // inject a reference to the ClimateLogServiceBean slsb
  @EJB ClimateLogServiceBean climateLogServiceBean;

  // inject a reference to the ParcelServiceBean slsb
  @EJB ParcelServiceBean serviceParcel;

  // inject a reference to the InstanciaParcelaService slsb
  @EJB InstanciaParcelaService serviceInstanceParcel;

  // inject a reference to the CultivoService slsb
  @EJB CultivoService serviceCrop;

  // inject a reference to the SolarRadiationServiceBean
  @EJB SolarRadiationServiceBean solarService;

  // inject a reference to the MaximumInsolationServiceBean
  @EJB MaximumInsolationServiceBean insolationService;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllIrrigationLogs() throws IOException {
    Collection<IrrigationLog> irrigationLogs = service.findAll();
    return mapper.writeValueAsString(irrigationLogs);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    IrrigationLog irrigationLog = service.find(id);
    return mapper.writeValueAsString(irrigationLog);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    IrrigationLog newIrrigationLog = mapper.readValue(json, IrrigationLog.class);
    newIrrigationLog = service.create(newIrrigationLog);
    updateWaterAccumulatedToday(newIrrigationLog.getParcel());
    return mapper.writeValueAsString(newIrrigationLog);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException {
    IrrigationLog modifiedIrrigationLog = mapper.readValue(json, IrrigationLog.class);
    return mapper.writeValueAsString(service.modify(id, modifiedIrrigationLog));
  }

  /**
   * Actualiza la cantidad de agua acumulada en el registro
   * climatico del dia de hoy haciendo uso de algunos
   * valores del registro climatico del dia de ayer (la Eto,
   * la Etc y la cantidad de agua de lluvia) y de la cantidad
   * total de agua de riego utilizada en el dia de hoy
   *
   * @param parcel
   */
  private void updateWaterAccumulatedToday(Parcel parcel) {
    /*
     * Fecha inmediatamente anterior a la fecha actual
     * del sistema, esta fecha se la utiliza para recuperar
     * de la base de datos subyacente la radiacion solar
     * y la insolacion maxima, ambos valores del dia de ayer,
     * los cuales a su vez se usan, junto a la parcela y al
     * coeficiente del cultivo en desarrollo, para recuperar
     * el registro climatico del dia de ayer
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    /*
     * Se tiene que recordar que la aplicacion permite calcular
     * el agua de riego solamente para aquella instancia de parcela
     * que esta en el estado 'En desarrollo', y solo puede haber una
     * instancia de parcela en dicho estado, sabiendo esto se entiende
     * que si la ejecucion de la aplicacion llego hasta aca es porque
     * hay una instancia de parcela en el estado 'En desarrollo' (con
     * lo cual hay un cultivo en desarrollo), y, por ende, el metodo
     * findInDevelopment() en esta situacion nunca debera retornar el
     * valor nulo
     *
     * Se recupera la instancia de parcela que esta en desarrollo para
     * recuperar su cultivo, el cual es necesario para recuperar su
     * coeficiente, el cual a su vez es necesario para poder recuperar
     * el registro climatico del dia de ayer
     */
    InstanciaParcela instanceInDevelopment = serviceInstanceParcel.findInDevelopment(parcel);

    /*
     * Como se comento anteriormente, la radiacion solar y la
     * insolacion maxima (ambos valores del dia de ayer) se recuperan,
     * de la base de datos subyacente, para poder recuperar el
     * registro climatico del dia de ayer
     */
    double yesterdaySolarRadiation = solarService.getRadiation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());
    double yesterdayInsolation = insolationService.getInsolation(yesterdayDate.get(Calendar.MONTH), parcel.getLatitude());

    /*
     * El coeficiente del cultivo que esta en desarrollo es
     * necesario para recuperar el registro climatico del dia
     * de ayer
     */
    double cropCoefficient = serviceCrop.getKc(instanceInDevelopment.getCultivo(), instanceInDevelopment.getFechaSiembra());

    ClimateLog yesterdayClimateLog = climateLogServiceBean.retrieveYesterdayClimateLog(parcel, yesterdaySolarRadiation, yesterdayInsolation, cropCoefficient);

    /*
     * Se recupera la cantidad total de agua de riego en el dia
     * de hoy de la parcela dada para calcular, en funcion de dicha
     * cantida de agua y de algunos valores del registro climatico
     * del dia de ayer, la cantidad de agua acumulada en el dia de hoy
     */
    double totalIrrigationWaterToday = service.getTotalWaterIrrigationToday(parcel);
    double waterAccumulatedToday = WaterMath.calculateWaterAccumulatedToday(yesterdayClimateLog, totalIrrigationWaterToday);

    /*
     * Por ultimo se actualiza la cantidad de agua acumulada en
     * el dia de hoy del registro climatico del dia de hoy asociado
     * a la parcela dada
     *
     * Es importante tener en cuenta que si no existe el registro
     * climatico del dia de hoy la aplicacion no podra actualizar
     * dicho registro
     */
    climateLogServiceBean.updateWaterAccumulatedToday(parcel, waterAccumulatedToday);
  }

}
