package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.DayFifteenMonth;

@Stateless
public  class DayFifteenMonthServiceBean {

  /*
   * Instance variables
   */
  @PersistenceContext(unitName="SisRiegoDB")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager localEntityManager){
    entityManager = localEntityManager;
  }

  public DayFifteenMonth create(DayFifteenMonth dayNumberFifteen) {
    entityManager.persist(dayNumberFifteen);
    return dayNumberFifteen;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public DayFifteenMonth getDayNumber(int dayNumber) {
    Query query = entityManager.createQuery("SELECT d FROM DayFifteenMonth d WHERE d.dayNumberFifteen = :dayNumber");
    query.setParameter("dayNumber", dayNumber);
    return (DayFifteenMonth) query.getSingleResult();
  }

}
