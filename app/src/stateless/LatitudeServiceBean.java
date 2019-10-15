package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Latitude;

@Stateless
public  class LatitudeServiceBean {

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

  public Latitude create(Latitude latitude) {
    entityManager.persist(latitude);
    return latitude;
  }

  /**
   * Recupera, haciendo uso de un valor de latitud, de
   * la base de datos subyacente un registro de la tabla
   * de latitudes convertido en objeto de tipo Latitude
   *
   * @param  latitudeValue
   * @return latitude
   */
  public Latitude getLatitude(int latitudeValue) {
    Query query = entityManager.createQuery("SELECT l FROM Latitude l WHERE l.decimalLatitude = :latitudeValue");
    query.setParameter("latitudeValue", latitudeValue);
    return (Latitude) query.getSingleResult();
  }

}
