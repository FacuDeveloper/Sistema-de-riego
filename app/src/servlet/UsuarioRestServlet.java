package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Usuario;

import stateless.UsuarioService;
import stateless.UserStatusServiceBean;

import org.apache.commons.codec.digest.DigestUtils;

@Path("/usuarios")
public class UsuarioRestServlet {

  @EJB UsuarioService service;
  @EJB UserStatusServiceBean serviceUserStatus;

  ObjectMapper mapper = new ObjectMapper();

  /*
   * Constante que se utiliza para recuperar de la
   * tabla de estado de usuario, el estado activo, el
   * cual tiene el ID 1
   */
  private final int ACTIVE_STATUS = 1;

  /*
   * Constante que se utiliza para recuperar de la
   * tabla de estado de usuario, el estado inactivo, el
   * cual tiene el ID 2
   */
  private final int INACTIVE_STATUS = 2;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String findAll() throws IOException {
    Collection<Usuario> users = service.findAll();
    return mapper.writeValueAsString(users);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String find(@PathParam("id") int id) throws IOException {
    Usuario user = service.find(id);
    return mapper.writeValueAsString(user);
  }

  @GET
  @Path("/autenticacion/{usr_email}")
  @Produces(MediaType.APPLICATION_JSON)
  public String validarUsuario(@PathParam("usr_email") String usr_email, @QueryParam("pwd") String pwd) throws IOException {
    Usuario usr = service.validarUsuario(usr_email, pwd);
    return mapper.writeValueAsString(usr);
  }

  @GET
  @Path("/except/{usr}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAllUsuariosExcept(@PathParam("usr") String usr) throws IOException {
    Collection<Usuario> usuarios = service.findAllUsuariosExcept(usr);
    return mapper.writeValueAsString(usuarios);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(String json) throws IOException {
    Usuario newUser = mapper.readValue(json, Usuario.class);
    String pwdcrypt = DigestUtils.md5Hex(newUser.getPassword());

    // Establece la clave encriptada
    newUser.setPassword(pwdcrypt);
    newUser.setFechaAlta(Calendar.getInstance());
    newUser.setEstado(serviceUserStatus.find(ACTIVE_STATUS));

    newUser = service.create(newUser);
    return mapper.writeValueAsString(newUser);
  }

  @GET
  @Path("/usuario/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findByUserName(@PathParam("username") String username) throws IOException {
    Usuario user = service.findByUserName(username);
    return mapper.writeValueAsString(user);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String modify(@PathParam("id") int id, String json) throws IOException {
    Usuario modifiedUser = mapper.readValue(json, Usuario.class);
    modifiedUser = service.update(id, modifiedUser);
    return mapper.writeValueAsString(modifiedUser);
  }

  @PUT
  @Path("/updatepass/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String updatePassword(@PathParam("id") int id, @QueryParam("oldPassword") String oldPassword, @QueryParam("newPassword") String newPassword ) throws IOException {
    Usuario user = service.updatePassword(id, oldPassword, newPassword);

    // NOTE: Revisar esto
    if (user == null) {
      throw new IOException();
    }

    return mapper.writeValueAsString(user);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String remove(@PathParam("id") int id) throws IOException {
    Usuario user = service.remove(id, serviceUserStatus.find(INACTIVE_STATUS));
    return mapper.writeValueAsString(user);
  }

}
