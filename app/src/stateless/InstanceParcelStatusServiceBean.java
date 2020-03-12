package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.InstanceParcelStatus;

import java.util.List;

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
    return entityManager.find(InstanceParcelStatus.class, id);
  }

  public List<InstanceParcelStatus> findAll() {
    Query query = entityManager.createQuery("SELECT s FROM InstanceParcelStatus s ORDER BY s.id");
    return (List<InstanceParcelStatus>) query.getResultList();
  }


}
