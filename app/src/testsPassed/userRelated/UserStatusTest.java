import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.UsuarioService;
import stateless.UsuarioServiceBean;
import stateless.UserStatusServiceBean;

import java.util.Calendar;
import java.util.Collection;

import model.UserStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserStatusTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static UsuarioServiceBean serviceUser;
  private static UserStatusServiceBean serviceUserStatus;

  @BeforeClass
  public static void preTest() {
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    serviceUser = new UsuarioServiceBean();
    serviceUser.setEntityManager(entityManager);

    serviceUserStatus = new UserStatusServiceBean();
    serviceUserStatus.setEntityManager(entityManager);
  }

  /*
   * Se prueba que el metodo remove de la clase UsuarioServiceBean
   * y que la relacion existente entre las tablas USUARIO y
   * ESTADO_USUARIO funcionen correctamente
   *
   * Para ejecutar esta prueba correctamente es necesario tener
   * cargada la base de datos con los datos del archivo SQL
   * allInserts
   */
  @Test
  public void removeUserTest() {
    UserStatus inactiveStatus = serviceUserStatus.find(2);

    entityManager.getTransaction().begin();
    serviceUser.remove(2, inactiveStatus);
    entityManager.getTransaction().commit();
  }

  @Test
  public void methodTest() {

  }

  @AfterClass
  public static void postTest() {
    /*
     * Cierra las conexiones, cosa que hace
     * que se liberen los recursos utilizados
     * por el administrador de entidades y su fabrica
     */
    entityManager.close();
    entityMangerFactory.close();
  }

}
