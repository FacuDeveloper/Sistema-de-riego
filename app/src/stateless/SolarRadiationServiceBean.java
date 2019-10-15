package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
// import javax.persistence.NoResultException;

import model.SolarRadiation;
import model.Latitude;
// import model.DayFifteenMonth;
import model.Month;

@Stateless
public  class SolarRadiationServiceBean {

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

  public SolarRadiation findSolarRadiation(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT s FROM SolarRadiation s WHERE s.month = :month AND s.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (SolarRadiation) query.getSingleResult();
  }

}
