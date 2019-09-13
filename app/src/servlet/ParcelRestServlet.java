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

import model.Field;
import model.Parcel;

import stateless.ParcelServiceBean;
import stateless.FieldServiceBean;

import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Path("/field/{fieldId}/parcels")
public class ParcelRestServlet {

  // inject a reference to the ParcelServiceBean slsb
  @EJB ParcelServiceBean parcelService;
  @EJB FieldServiceBean fieldService;

  //mapea lista de pojo a JSON
  ObjectMapper mapper = new ObjectMapper();

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Parcel parcel = parcelService.find(id);
    return mapper.writeValueAsString(parcel);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(@PathParam("fieldId") int fieldId, String parcelJson) throws IOException {
    Parcel parcel = mapper.readValue(parcelJson, Parcel.class);

    // Se crea la nueva parcela
    parcelService.create(parcel);

    // Se crea la parcela pasando como parametro el campo en el cual fue creada
    // porque el sistema no le agrega de forma automatica el identificador del campo
    // parcelService.create(parcel, fieldService.find(fieldId));

    // Luego se la agrega al campo sobre el cual fue creada
    Field choosenField = fieldService.addParcelField(fieldId, parcel);

    return mapper.writeValueAsString(choosenField);
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("fieldId") int fieldId, String parcelJson) throws IOException {
    Parcel modifiedParcel = mapper.readValue(parcelJson, Parcel.class);
    parcelService.modify(modifiedParcel.getId(), modifiedParcel);

    Field field = fieldService.find(fieldId);
    return mapper.writeValueAsString(field);
  }

  @DELETE
  @Path("/{parcelId}")
  public String remove(@PathParam("fieldId") int fieldId, @PathParam("parcelId") int parcelId) throws IOException {
    Parcel parcel = parcelService.find(parcelId);

    /*
     * Antes de eliminar la parcela seleccionada
     * primero se la tiene que eliminar del campo
     * que la tiene
     */
    Field choosenField = fieldService.removeParcelField(fieldId, parcel);

    /*
     * Recien de la eliminacion anterior se puede eliminar
     * la parcela seleccionada y asi de esta forma se evita
     * la excepcion de violacion de integridad referencial
     * en el caso de eliminacion.
     *
     * Excepcion que proviene de la base de datos subyacente.
     */
    parcelService.remove(parcelId);

    return mapper.writeValueAsString(choosenField);
  }

}
