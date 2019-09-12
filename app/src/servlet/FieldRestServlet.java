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

import model.Field;

import stateless.FieldServiceBean;

import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Path("/field")
public class FieldRestServlet {

  // inject a reference to the FieldServiceBean slsb
  @EJB FieldServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<Field> fields = service.findAll();
    return mapper.writeValueAsString(fields);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Field field = service.find(id);
    return mapper.writeValueAsString(field);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    Field newField = mapper.readValue(json, Field.class);
    newField = service.create(newField);
    return mapper.writeValueAsString(newField);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Field field = service.remove(id);
    return mapper.writeValueAsString(field);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, @QueryParam("name") String name, @QueryParam("area") int area, @QueryParam("latitude") double latitude,
  @QueryParam("longitude") double longitude) throws IOException {

    Field modifiedField = new Field();
    modifiedField.setName(name);
    modifiedField.setArea(area);
    modifiedField.setLatitude(latitude);
    modifiedField.setLongitude(longitude);

    return mapper.writeValueAsString(service.modify(id, modifiedField));
  }

}
