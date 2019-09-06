package servlet;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
// import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.Ground;

import stateless.GroundServiceBean;
import stateless.Page;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

@Path("/ground")
public class GroundRestServlet {

  // inject a reference to the GroundServiceBean slsb
  @EJB GroundServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/findAllGrounds")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllGrounds() throws IOException {
    Collection<Ground> grounds = service.findAll();
    return mapper.writeValueAsString(grounds);
  }

  @GET
	public String findAll(@QueryParam("page") Integer page, @QueryParam("cant") Integer cant, @QueryParam("search") String search) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
    
		// convert JSON string to Map
		map = mapper.readValue(search, new TypeReference<Map<String, String>>(){});

		Page<Ground> grounds = service.findByPage(page, cant, map);
		return mapper.writeValueAsString(grounds);
	}

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Ground ground = service.find(id);
    return mapper.writeValueAsString(ground);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    Ground newGround = mapper.readValue(json, Ground.class);
    newGround = service.create(newGround);
    return mapper.writeValueAsString(newGround);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Ground ground = service.remove(id);
    return mapper.writeValueAsString(ground);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException {
    Ground modifiedGround = mapper.readValue(json, Ground.class);
    return mapper.writeValueAsString(service.modify(id, modifiedGround));
  }

}
