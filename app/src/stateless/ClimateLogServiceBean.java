package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import java.util.Collection;
import java.util.Calendar;

import model.ClimateLog;
import model.Parcel;

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

  /**
   * Comprueba si la parcela dada tiene un registro
   * climatico asociado (en la base de datos) y si lo
   * tiene retorna verdadero, en caso contrario retorna
   * falso
   *
   * @param  date
   * @param  parcel
   * @return verdadero en caso de enccontrar un registro del clima
   * con la fecha y la parcela dadas, en caso contrario retorna falso
   */
  public boolean exist(Calendar date, Parcel parcel) {
    Query query = entityManager.createQuery("SELECT r FROM ClimateLog r WHERE r.date = :date AND r.parcel = :parcel");
    query.setParameter("date", date);
    query.setParameter("parcel", parcel);

    boolean result = false;

    try {
      query.getSingleResult();
      result = true;
    } catch(NoResultException ex) {

    }

    return result;
  }

}
