package servlet;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
// import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
// import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.IrrigationLog;

import stateless.IrrigationLogServiceBean;
import stateless.Page;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

@Path("/irrigationLog")
public class IrrigationLogRestServlet {

  // inject a reference to the IrrigationLogServiceBean slsb
  @EJB IrrigationLogServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/findAllIrrigationLogs")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllIrrigationLogs() throws IOException {
    Collection<IrrigationLog> irrigationLogs = service.findAll();
    return mapper.writeValueAsString(irrigationLogs);
  }

  @GET
	public String findAll(@QueryParam("page") Integer page, @QueryParam("cant") Integer cant, @QueryParam("search") String search) throws IOException {
		Map<String, String> map = new HashMap<String, String>();

		// convert JSON string to Map
		map = mapper.readValue(search, new TypeReference<Map<String, String>>(){});

		Page<IrrigationLog> irrigationLogs = service.findByPage(page, cant, map);
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

}
