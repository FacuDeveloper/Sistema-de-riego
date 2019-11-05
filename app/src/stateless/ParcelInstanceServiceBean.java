package stateless;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.ParcelInstance;
import model.Parcel;
import model.Cultivo;

@Stateless
public  class ParcelInstanceServiceBean {

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

  public ParcelInstance create(ParcelInstance newParcelInstance) {
    getEntityManager().persist(newParcelInstance);
    return newParcelInstance;
  }

  /**
   * Elimina un suelo mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado el suelo, en caso contrario nulo
   */
  // public ParcelInstance remove(int id) {
  //   ParcelInstance choosenParcelInstance = find(id);
  //
  //   if (choosenParcelInstance != null) {
  //     getEntityManager().remove(choosenParcelInstance);
  //     return choosenParcelInstance;
  //   }
  //
  //   return null;
  // }

  /**
   * Actualiza o modifica la entidad asociada al id dado
   *
   * @param  id
   * @param  modifiedParcelInstance
   * @return un valor no nulo en caso de modificar la entidad solicitada
   * mediante el id, en caso contrario retorna un valor nulo
   */
  public ParcelInstance modify(int id, ParcelInstance modifiedParcelInstance) {
    ParcelInstance choosenParcelInstance = find(id);

    if (choosenParcelInstance != null) {
      choosenParcelInstance.setSeedDate(modifiedParcelInstance.getSeedDate());
      choosenParcelInstance.setHarvestDate(modifiedParcelInstance.getHarvestDate());
      choosenParcelInstance.setCrop(modifiedParcelInstance.getCrop());
      choosenParcelInstance.setParcel(modifiedParcelInstance.getParcel());
      return choosenParcelInstance;
    }

    return null;
  }

  public ParcelInstance find(int id) {
    return getEntityManager().find(ParcelInstance.class, id);
  }

  public Collection<ParcelInstance> findAll() {
    Query query = getEntityManager().createQuery("SELECT p FROM ParcelInstance p ORDER BY p.id");
    return (Collection<ParcelInstance>) query.getResultList();
  }

  /**
   * @param  parcel
   * @return registro historico, de una parcela dada, que
   * tiene fecha de siembra y que no tiene fecha de cosecha
   * para un cultivo, en caso contrario retorna el valor
   * nulo
   */
  public ParcelInstance findCurrentParcelInstance(Parcel parcel) {
    /*
     * Selecciona el registro historico de una parcela dada, que
     * tiene fecha de siembra y que no tiene fecha de cosecha
     * del cultivo sembrado, y este registro es el registro
     * historico actual de la parcela dada
     */
    Query query = entityManager.createQuery("SELECT r FROM ParcelInstance r JOIN r.parcel p WHERE (r.seedDate <> NULL AND r.harvestDate = NULL AND p = :parcel)");
    query.setParameter("parcel", parcel);

    ParcelInstance resultingParcelInstancce = null;

    try {
      resultingParcelInstancce = (ParcelInstance) query.getSingleResult();
    } catch(Exception e) {

    }

    return resultingParcelInstancce;
  }

}
