package stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collection;
import java.util.Calendar;

import model.Usuario;
import model.UserStatus;

@Stateless
public class UsuarioServiceBean implements UsuarioService {

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emlocal) {
    entityManager = emlocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Usuario create(Usuario usr){
    entityManager.persist(usr);
    return usr;
  }

  public Usuario update(int id, Usuario modifiedUser) {
    Usuario user = find(id);

    if (user != null){
      user.setUsuario(modifiedUser.getUsuario());
      user.setNombre(modifiedUser.getNombre());
      user.setApellido(modifiedUser.getApellido());
      user.setDni(modifiedUser.getDni());
      user.setDireccion(modifiedUser.getDireccion());
      user.setTelefono(modifiedUser.getTelefono());
      user.setEmail(modifiedUser.getEmail());
      user.setSuperUsuario(modifiedUser.isSuperUsuario());
      return user;
    }

    return null;
  }

  public Usuario remove(int id, UserStatus downStatus) {
    Usuario usr = find(id);

    if (usr != null) {
      usr.setFechaBaja(Calendar.getInstance());
      usr.setEstado(downStatus);
    }

    return usr;
  }

  public Usuario updatePassword(int id, String oldPassword, String newPassword) {
    Usuario user = find(id);
    String encryptedOldPassword = DigestUtils.md5Hex(oldPassword);
    String newEcnryptedPassword = null;

    /*
     * Si la clave antigua provista por el usuario y
     * encriptada coincide con la clave ya existente
     * entonces se hace el cambio de clave, en caso
     * contrario, no se hace el cambio de clave
     *
     */
    if ((encryptedOldPassword.compareTo(user.getPassword())) == 0) {
      newEcnryptedPassword = DigestUtils.md5Hex(newPassword);
      user.setPassword(newEcnryptedPassword);
      return user;
    }

    return null;
  }

  public Usuario find(int id) {
    return entityManager.find(Usuario.class, id);
  }

  public Usuario findByUserName(String username) {
    Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :username");
    query.setParameter("username", username);
    return (Usuario) query.getSingleResult();
  }

  public Collection<Usuario> findAllUsuariosExcept(String username) {
    Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.usuario <> :username");
    query.setParameter("username", username);
    return (Collection<Usuario>) query.getResultList();
  }

  public Collection<Usuario> findAll() {
    Query query = entityManager.createQuery("SELECT u FROM Usuario u ORDER BY u.id");
    return (Collection<Usuario>) query.getResultList();
  }

  public Collection<Usuario> findAllUsuariosActivos() {
    Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.estado = 'ACTIVO'");
    return (Collection<Usuario>) query.getResultList();
  }

  /**
   * @param  usr
   * @param  pwd
   * @return el usuario contiene el usuario
   * y la clave provistos
   */
  public Usuario validarUsuario(String usr, String pwd) {
    String pwdEncrypt = DigestUtils.md5Hex(pwd);

    Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE (UPPER(u.usuario) = UPPER(:usr)) AND UPPER(u.password) = :pw");
    query.setParameter("usr", usr);
    query.setParameter("pw", pwdEncrypt.toUpperCase());

    return (Usuario) query.getSingleResult();
  }

}
