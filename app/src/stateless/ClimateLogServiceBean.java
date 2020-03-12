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
    entityManager.persist(newClimateLog);
    return newClimateLog;
  }

  public ClimateLog find(int id) {
    return entityManager.find(ClimateLog.class, id);
  }

  /**
   * @param  givenDate
   * @param  givenParcel
   * @return registro climatico de la parcela dada en
   * la fecha dada
   */
  public ClimateLog find(Calendar givenDate, Parcel givenParcel) {
    Query query = entityManager.createQuery("SELECT r FROM ClimateLog r WHERE r.date = :date AND r.parcel = :parcel");
    query.setParameter("date", givenDate);
    query.setParameter("parcel", givenParcel);

    return (ClimateLog) query.getSingleResult();
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
    Query query = entityManager.createQuery("SELECT r FROM ClimateLog r WHERE (r.date = :date AND r.parcel = :parcel)");
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

  /**
   * Establece el agua acumulada del registro climatico
   * del dia de hoy de una parcela dada
   *
   * @param givenDate
   * @param givenParcel
   * @param waterAccumulated [milimetros]
   */
  public void updateWaterAccumulatedToday(Calendar givenDate, Parcel givenParcel, double waterAccumulated) {
    Query query = entityManager.createQuery("UPDATE ClimateLog c SET c.waterAccumulated = :waterAccumulated WHERE (c.date = :givenDate AND c.parcel = :givenParcel)");
    query.setParameter("givenDate", givenDate);
    query.setParameter("givenParcel", givenParcel);
    query.setParameter("waterAccumulated", waterAccumulated);
    query.executeUpdate();
  }

}
