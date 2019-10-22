package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.EtLog;

@Stateless
public  class EtLogServiceBean {

  /*
   * Instance variables
   */
  @PersistenceContext(unitName="SisRiegoDB")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager localEntityManager){
    entityManager = localEntityManager;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public EtLog create(EtLog newEtLog) {
    getEntityManager().persist(newEtLog);
    return newEtLog;
  }

  // public EtLog remove(int id) {
  //   EtLog etLog = find(id);
  //
  //   if (etLog != null) {
  //     getEntityManager().remove(etLog);
  //     return etLog;
  //   }
  //
  //   return null;
  // }

  public EtLog find(int id) {
    return getEntityManager().find(EtLog.class, id);
  }

  public Collection<EtLog> findAll() {
    Query query = getEntityManager().createQuery("SELECT e FROM EtLog e ORDER BY e.id");
    return (Collection<EtLog>) query.getResultList();
  }

}
