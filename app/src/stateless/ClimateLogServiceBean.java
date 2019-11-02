package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.ClimateLog;

@Stateless
public  class ClimateLogServiceBean {

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

  public ClimateLog create(ClimateLog newClimateLog) {
    getEntityManager().persist(newClimateLog);
    return newClimateLog;
  }

  public ClimateLog find(int id) {
    return getEntityManager().find(ClimateLog.class, id);
  }

}
