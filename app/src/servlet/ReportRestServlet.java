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

import model.Report;

import stateless.ReportServiceBean;

import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Path("/report")
public class ReportRestServlet {

  // inject a reference to the ReportServiceBean slsb
  @EJB ReportServiceBean service;

  // Mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<Report> reports = service.findAll();
    return mapper.writeValueAsString(reports);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Report report = service.find(id);
    return mapper.writeValueAsString(report);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Report report = service.remove(id);
    return mapper.writeValueAsString(report);
  }

  @GET
  @Path("/findReportsByParcelName/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findReportsByParcelName(@PathParam("name") String parcelName) throws IOException {
    Collection<Report> reports = service.findReportsByParcelName(parcelName);
    return mapper.writeValueAsString(reports);
  }

  @GET
  @Path("/generateReport/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public String generateReport(@PathParam("name") String parcelName) throws IOException {
    Report report = service.generateReport(parcelName);
    report = service.create(report);

    return mapper.writeValueAsString(report);
  }

}
