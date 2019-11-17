package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.InstanceParcelStatus;

@Stateless
public class InstanceParcelStatusServiceBean {

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emLocal){
    entityManager = emLocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * @param  id [identificador]
   * @return referencia a objeto de tipo InstanceParcelStatus que tiene
   * el identificador provisto
   */
  public InstanceParcelStatus find(int id){
    return getEntityManager().find(InstanceParcelStatus.class, id);
  }

}
