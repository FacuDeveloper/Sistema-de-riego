package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
// import javax.persistence.NoResultException;

import model.MaximumInsolation;
import model.Latitude;
import model.Month;

@Stateless
public  class MaximumInsolationServiceBean {

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

  public MaximumInsolation findMaximumInsolation(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT m FROM MaximumInsolation m WHERE m.month = :month AND m.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (MaximumInsolation) query.getSingleResult();
  }

}
