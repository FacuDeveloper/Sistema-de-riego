package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.InstanceParcelStatus;

import java.util.List;
import java.util.Calendar;

@Stateless
public class InstanceParcelStatusServiceBean {

  /*
   * Los valores de estas constantes son los IDs de los
   * estados de una instancia de parcela que existen
   * en la base de datos
   */
  private static final int FINISHED = 1;
  private static final int IN_DEVELOPMENT = 2;
  private static final int ON_HOLD = 3;

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emLocal){
    entityManager = emLocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * @param  id [identificador]
   * @return referencia a objeto de tipo InstanceParcelStatus que tiene
   * el identificador provisto
   */
  public InstanceParcelStatus find(int id){
    return entityManager.find(InstanceParcelStatus.class, id);
  }

  public List<InstanceParcelStatus> findAll() {
    Query query = entityManager.createQuery("SELECT s FROM InstanceParcelStatus s ORDER BY s.id");
    return (List<InstanceParcelStatus>) query.getResultList();
  }

  /**
   * Obtiene de la base de datos el estado correspondiente a las
   * fechas de una instancia de parcela dada
   *
   * @param  seedDate
   * @param  harvestDate
   * @return la referencia a un objeto de tipo InstanceParcelStatus
   * en base a las fechas de una instancia de parcela y a la fecha
   * actual del sistema
   */
  public InstanceParcelStatus getStatus(Calendar seedDate, Calendar harvestDate) {
    // Fecha actual del sistema
    Calendar currentDate = Calendar.getInstance();

    /*
     * Si la fecha de cosecha de la instancia de parcela
     * dada es menor que la fecha actual del sistema, dicha
     * instancia tiene que tener el estado 'Finalizado' (0)
     */
    if ((harvestDate.compareTo(currentDate)) < 0) {
      return find(FINISHED);
    }

    /*
     * Si la fecha actual del sistema esta entre la fecha de
     * siembra y la fecha de cosecha de la instancia de parcela
     * dada, esta tiene que tener el estado 'En desarrollo' (1)
     */
    if ((currentDate.compareTo(seedDate) >= 0) && (currentDate.compareTo(harvestDate) <= 0)) {
      return find(IN_DEVELOPMENT);
    }

    /*
     * Si la fecha de siembra de la instancia de parcela
     * dada es mayor que la fecha actual del sistema, dicha
     * instancia tiene que tener el estado 'En espera' (2)
     */
    return find(ON_HOLD);
  }

}
