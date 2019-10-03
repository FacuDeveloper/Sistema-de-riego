package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.Forecast;

@Stateless
public  class ForecastServiceBean {

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

  public Forecast create(Forecast newForecast) {
    getEntityManager().persist(newForecast);
    return newForecast;
  }

  // public Forecast remove(int id) {
  //   Forecast forecast = find(id);
  //
  //   if (forecast != null) {
  //     getEntityManager().remove(forecast);
  //     return forecast;
  //   }
  //
  //   return null;
  // }

  public Forecast find(int id) {
    return getEntityManager().find(Forecast.class, id);
  }

  public Collection<Forecast> findAll() {
    Query query = getEntityManager().createQuery("SELECT f FROM Forecast f ORDER BY f.id");
    return (Collection<Forecast>) query.getResultList();
  }

}
