package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.Report;

@Stateless
public  class ReportServiceBean {

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

  public Report create(Report newReport) {
    entityManager.persist(newReport);
    return newReport;
  }

  public Report find(int id) {
    return entityManager.find(Report.class, id);
  }

  /**
   * @param int id que indentifica a un informe estadistico
   * @return referencia a un objeto de tipo Report si se
   * lo elimina, en caso contrario retorna nulo
   */
  public Report remove(int id){
    Report report = find(id);

    if (report != null) {
      entityManager.remove(report);
      return report;
    }

    return null;
  }

  /**
   * @return retorna una coleccion con todas los informes
   * estadisticos
   */
  public Collection<Report> findAll() {
    Query query = entityManager.createQuery("SELECT r FROM Report r ORDER BY r.id");
    return (Collection<Report>) query.getResultList();
  }

  public Collection<Report> findReportsByParcelName(String parcelName) {
    return null;
  }

  public Report generateReport(String parcelName) {
    return null;
  }

}
