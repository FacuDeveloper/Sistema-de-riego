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

import model.TypeGround;

import stateless.TypeGroundServiceBean;

import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Path("/typeGround")
public class TypeGroundRestServlet {

  // inject a reference to the TypeGroundServiceBean slsb
  @EJB TypeGroundServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<TypeGround> types = service.findAll();
    return mapper.writeValueAsString(types);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    TypeGround typeGround = service.find(id);
    return mapper.writeValueAsString(typeGround);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    TypeGround newTypeGround = mapper.readValue(json, TypeGround.class);
    newTypeGround = service.create(newTypeGround);
    return mapper.writeValueAsString(newTypeGround);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    TypeGround typeGround = service.remove(id);
    return mapper.writeValueAsString(typeGround);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException {
    TypeGround modifiedTypeGround = mapper.readValue(json, TypeGround.class);
    return mapper.writeValueAsString(service.modify(id, modifiedTypeGround));
  }

  // Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
  @GET
  public String findByTextureName(@QueryParam("textureName") String textureName) throws IOException {
    Collection<TypeGround> types = service.findByTextureName(textureName);
    return mapper.writeValueAsString(types);
  }

}
