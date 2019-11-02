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

  /**
   * Recupera de la base de datos subyacente la maxima
   * insolacion diaria (N) correspondiente al mes y la
   * latitud dados
   *
   * La latitud va de 0 a -70 grados decimales porque
   * en la base de datos estan cargadas las insolaciones
   * maximas diarias del hemisferio sur
   *
   * @param  month [1 .. 12]
   * @param  latitude [0 .. -70]
   * @return insolacion maxima diaria [MJ / metro cuadrado * dia]
   */
  public MaximumInsolation find(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT m FROM MaximumInsolation m WHERE m.month = :month AND m.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (MaximumInsolation) query.getSingleResult();
  }

}
