package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
// import javax.persistence.NoResultException;

import model.SolarRadiation;
import model.Latitude;
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

  /**
   * Recupera de la base de datos subyacente la radiacion solar
   * extraterrestre (Ra) correspondiente al mes y latitud
   * dados
   *
   * La latitud va de 0 a -70 grados decimales porque en
   * la base de datos estan cargadas las radiaciones
   * solares extraterrestres del hemisferio sur
   *
   * @param  month [1 ... 2]
   * @param  latitude [0 .. -70]
   * @return radiacion solar extraterrestre [MJ / metro cuadrado * dia]
   */
  public SolarRadiation findSolarRadiation(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT s FROM SolarRadiation s WHERE s.month = :month AND s.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (SolarRadiation) query.getSingleResult();
  }

}
