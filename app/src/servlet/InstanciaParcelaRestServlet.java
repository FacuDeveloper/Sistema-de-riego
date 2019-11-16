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
import stateless.ClimateLogServiceBean;
import stateless.IrrigationLogServiceBean;
import stateless.CultivoService;

import irrigation.WaterMath;

import java.lang.Math;

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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException  {
    InstanciaParcela instancia = mapper.readValue(json, InstanciaParcela.class);

    Calendar harvestDate = getHarvestDate(instancia);
    instancia.setFechaCosecha(harvestDate);

    instancia = service.create(instancia);
    return mapper.writeValueAsString(instancia);
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
    instancia = service.change(id, instancia);
    return mapper.writeValueAsString(instancia);
  }

  @GET
  @Path("/suggestedIrrigation/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getSuggestedIrrigation(@PathParam("id") int id) throws IOException {
    InstanciaParcela choosenParcelInstance = service.find(id);
    Parcel parcel = choosenParcelInstance.getParcel();

    /*
     * Fecha actual del sistema
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Fecha del dia inmediatamente anterior a la fecha
     * actual del sistema
     */
    Calendar yesterdayDate = getYesterdayDate();

    double suggestedIrrigationToday = 0.0;

    /*
     * Cantidad total de agua utilizada en los riegos
     * realizados en el dia de hoy
     */
    double totalIrrigationWaterToday = irrigationLogService.getTotalWaterIrrigation(parcel);

    /*
     * Recupera el registro climatico de la parcela
     * de la fecha anterior a la fecha actual
     */
    ClimateLog climateLog = climateLogServiceBean.find(yesterdayDate, parcel);
    suggestedIrrigationToday = WaterMath.getSuggestedIrrigation(climateLog.getEtc(), climateLog.getEto(), climateLog.getRainWater(), climateLog.getWaterAccumulated(), totalIrrigationWaterToday);

    IrrigationLog newIrrigationLog = new IrrigationLog();
    newIrrigationLog.setSuggestedIrrigation(suggestedIrrigationToday);
    newIrrigationLog.setDate(currentDate);
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
   * @param  instancia
   * @return fecha de cosecha de la instancia parcela dada (registro
   * historico de parcela)
   */
  private Calendar getHarvestDate(InstanciaParcela instancia) {
    return cultivoService.calculateHarvestDate(instancia.getFechaSiembra(), instancia.getCultivo());
  }

}