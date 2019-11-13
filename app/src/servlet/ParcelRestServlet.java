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

import model.Parcel;

import stateless.ParcelServiceBean;
import stateless.Page;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

@Path("/parcel")
public class ParcelRestServlet {

  // inject a reference to the ParcelServiceBean slsb
  @EJB ParcelServiceBean service;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  /*
  * Los metodos findAllParcels() y findAll() son
  * necesarios para la paginacion
  */

  @GET
  @Path("/findAllParcels")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllParcels() throws IOException {
    Collection<Parcel> parcels = service.findAll();
    return mapper.writeValueAsString(parcels);
  }

  @GET
  public String findAll(@QueryParam("page") Integer page, @QueryParam("cant") Integer cant, @QueryParam("search") String search) throws IOException {
    Map<String, String> map = new HashMap<String, String>();

    // convert JSON string to Map
    map = mapper.readValue(search, new TypeReference<Map<String, String>>(){});

    Page<Parcel> parcels = service.findByPage(page, cant, map);
    return mapper.writeValueAsString(parcels);
  }

  @GET
  @Path("/findAllActive")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllActive() throws IOException {
    Collection<Parcel> activeParcels = service.findAllActive();
    return mapper.writeValueAsString(activeParcels);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Parcel parcel = service.find(id);
    return mapper.writeValueAsString(parcel);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    Parcel newParcel = mapper.readValue(json, Parcel.class);
    newParcel = service.create(newParcel);
    return mapper.writeValueAsString(newParcel);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Parcel parcel = service.remove(id);
    return mapper.writeValueAsString(parcel);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, @QueryParam("name") String name, @QueryParam("area") int area, @QueryParam("latitude") double latitude,
  @QueryParam("longitude") double longitude, @QueryParam("active") boolean active) throws IOException {

    Parcel modifiedParcel = new Parcel();
    modifiedParcel.setName(name);
    modifiedParcel.setArea(area);
    modifiedParcel.setLatitude(latitude);
    modifiedParcel.setLongitude(longitude);
    modifiedParcel.setActive(active);

    return mapper.writeValueAsString(service.modify(id, modifiedParcel));
  }

}
