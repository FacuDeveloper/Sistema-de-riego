package servlet;

import javax.ejb.EJB;
import javax.ws.rs.GET;
// import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
// import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
// import javax.ws.rs.QueryParam;
// import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
// import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.StatisticalReport;

import stateless.StatisticalReportServiceBean;

import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Path("/statisticalReport")
public class StatisticalReportRestServlet {

  // inject a reference to the StatisticalReportServiceBean slsb
  @EJB StatisticalReportServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<StatisticalReport> reports = service.findAll();
    return mapper.writeValueAsString(reports);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    StatisticalReport statisticalReport = service.find(id);
    return mapper.writeValueAsString(statisticalReport);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    StatisticalReport statisticalReport = service.remove(id);
    return mapper.writeValueAsString(statisticalReport);
  }

  @GET
  @Path("/findStatisticalReportByParcelName/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findStatisticalReportByParcelName(@PathParam("name") String name) throws IOException {
    Collection<StatisticalReport> reports = service.findStatisticalReportByParcelName(name);
    return mapper.writeValueAsString(reports);
  }

  @GET
  @Path("/generateStatisticalReport/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String generateStatisticalReport(@PathParam("name") String name) throws IOException {
    StatisticalReport report = service.generateStatisticalReport(name);
    report = service.create(report);

    return mapper.writeValueAsString(report);
  }

}
