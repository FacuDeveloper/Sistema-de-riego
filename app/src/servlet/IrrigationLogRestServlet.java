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

import stateless.IrrigationLogServiceBean;
import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;

import java.util.Collection;
import java.util.Calendar;

import irrigation.WaterMath;

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

    /*
     * NOTE: Esto tiene que ser activado en el despliegue
     * final de la aplicacion cuando este listo y en
     * funcionamiento el modulo que obtiene y almacena
     * los registros climaticos de cada parcela para
     * cada dia del año
     */
    // setWaterAccumulatedToday(newIrrigationLog.getParcel());
    return mapper.writeValueAsString(newIrrigationLog);
  }

  // @DELETE
  // @Path("/{id}")
  // @Produces(MediaType.APPLICATION_JSON)
  // public String remove(@PathParam("id") int id) throws IOException {
  //   IrrigationLog irrigationLog = service.remove(id);
  //   return mapper.writeValueAsString(irrigationLog);
  // }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException {
    IrrigationLog modifiedIrrigationLog = mapper.readValue(json, IrrigationLog.class);
    return mapper.writeValueAsString(service.modify(id, modifiedIrrigationLog));
  }

  /**
   * Establece la cantidad de agua acumulada en el registro
   * climatico del dia de hoy haciendo uso de la cantidad
   * de agua de lluvia del dia de ayer, de la cantidad de
   * agua acumulada del dia de ayer (agua a favor para el
   * dia de hoy), de la ETc del dia de ayer, de la ETo
   * del dia de ayer (en caso de que la ETc sea igual cero
   * debido a que ayer no haya habido un cultivo sembrado
   * en la parcela dada) y de la cantidad total de agua
   * utilizada en el riego del dia de hoy
   *
   * @param givenParcel
   */
  private void setWaterAccumulatedToday(Parcel givenParcel) {
    double yesterdayEto = 0.0;
    double yesterdayEtc = 0.0;
    double yesterdayRainWater = 0.0;
    double waterAccumulatedYesterday = 0.0;
    double totalIrrigationWaterToday = 0.0;
    double waterAccumulatedToday = 0.0;

    /*
     * Fecha actual para actualizar el atributo
     * agua acumulada del dia de hoy del registro
     * climatico del dia de hoy (por esto la fecha
     * actual) de la parcela dada
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Fecha inmediatamente anterior a la fecha actual
     * para recuperar, de la base de datos, el registro
     * climatico del dia de ayer
     */
    Calendar yesterdayDate = Calendar.getInstance();
    yesterdayDate.set(Calendar.DAY_OF_YEAR, yesterdayDate.get(Calendar.DAY_OF_YEAR) - 1);

    ClimateLog yesterdayClimateLog = climateLogServiceBean.find(yesterdayDate, givenParcel);
    yesterdayEto = yesterdayClimateLog.getEto();
    yesterdayEtc = yesterdayClimateLog.getEtc();
    yesterdayRainWater = yesterdayClimateLog.getRainWater();
    waterAccumulatedYesterday = yesterdayClimateLog.getWaterAccumulated();

    totalIrrigationWaterToday = service.getTotalWaterIrrigationToday(givenParcel);

    waterAccumulatedToday = WaterMath.getWaterAccumulatedToday(yesterdayEtc, yesterdayEto, yesterdayRainWater, waterAccumulatedYesterday, totalIrrigationWaterToday);
    climateLogServiceBean.updateWaterAccumulated(currentDate, givenParcel, waterAccumulatedToday);
  }

}
