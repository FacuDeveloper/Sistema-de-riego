package servlet;

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

import model.Cultivo;

import stateless.CultivoService;
import stateless.Page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/cultivo")
public class CultivoRestServlet {

  // inject a reference to the CultivoService slsb
  @EJB CultivoService service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/findAllCultivos")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllCultivos() throws IOException {
    Collection<Cultivo> cultivos = service.findAll();
    return mapper.writeValueAsString(cultivos);
  }

  @GET
  public String findAll(@QueryParam("page") Integer page, @QueryParam("cant") Integer cant, @QueryParam("search") String search) throws IOException {
    Map<String, String> map = new HashMap<String, String>();

    // convert JSON string to Map
    map = mapper.readValue(search, new TypeReference<Map<String, String>>(){});

    Page<Cultivo> cultivos = service.findByPage(page, cant, map);
    return mapper.writeValueAsString(cultivos);
  }

  @GET
  @Path("/findAllActiveCrops")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllActive() throws IOException {
    Collection<Cultivo> activeCrops = service.findAllActive();
    return mapper.writeValueAsString(activeCrops);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Cultivo cultivo = service.find(id);
    return mapper.writeValueAsString(cultivo);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException  {
    Cultivo cultivo = mapper.readValue(json,Cultivo.class);
    cultivo = service.create(cultivo);
    return mapper.writeValueAsString(cultivo);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Cultivo cultivo = service.remove(id);
    return mapper.writeValueAsString(cultivo);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException  {
    Cultivo cultivo = mapper.readValue(json,Cultivo.class);
    cultivo = service.modify(id, cultivo);
    return mapper.writeValueAsString(cultivo);
  }

}
