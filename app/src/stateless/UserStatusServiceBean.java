package stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ejb.Stateless;

import model.UserStatus;

@Stateless
public class UserStatusServiceBean {

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emlocal) {
    entityManager = emlocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * @param  id
   * @return referencia a un objeto de tipo UserStatus que
   * contiene uno de los siguientes estados: Alta, Baja
   */
  public UserStatus find(int id) {
    return entityManager.find(UserStatus.class, id);
  }

}
