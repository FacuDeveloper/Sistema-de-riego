package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.StatisticalReport;

@Stateless
public  class StatisticalReportServiceBean {

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

  public StatisticalReport create(StatisticalReport newStatisticalReport) {
    entityManager.persist(newStatisticalReport);
    return newStatisticalReport;
  }

  public StatisticalReport find(int id) {
    return entityManager.find(StatisticalReport.class, id);
  }

  /**
   * @param int id que indentifica a un informe estadistico
   * @return referencia a un objeto de tipo StatisticalReport si se
   * lo elimina, en caso contrario retorna nulo
   */
  public StatisticalReport remove(int id){
    StatisticalReport statisticalReport = find(id);

    if (statisticalReport != null) {
      entityManager.remove(statisticalReport);
      return statisticalReport;
    }

    return null;
  }

  /**
   * @return retorna una coleccion con todas los informes
   * estadisticos
   */
  public Collection<StatisticalReport> findAll() {
    Query query = entityManager.createQuery("SELECT s FROM StatisticalReport s ORDER BY s.id");
    return (Collection<StatisticalReport>) query.getResultList();
  }

  public StatisticalReport generateStatisticalReport(String parcelName) {
    return null;
  }

  

}
