package stateless;

import java.util.Collection;
import java.util.function.Predicate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Field;
import model.Parcel;

@Stateless
public class ParcelServiceBean {
  @PersistenceContext(unitName="irrigation")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emLocal){
    entityManager = emLocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Parcel create(Parcel parcel) {
    entityManager.persist(parcel);
    return parcel;
  }

  public Parcel find(int id){
    return entityManager.find(Parcel.class, id);
  }

  /**
   * Elimina una parcela mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado la parcela, en caso contrario nulo
   */
  public Parcel remove(int id) {
    Parcel parcel = find(id);

    if (parcel != null) {
      getEntityManager().remove(parcel);
      return parcel;
    }

    return null;
  }

  public Collection<Parcel> findAllParcels() {
    Query query = entityManager.createQuery("SELECT p FROM Parcel p");
    return (Collection<Parcel>) query.getResultList();
  }

  // TODO: Documentar todo en esta clase
  public Parcel modify(int id, Parcel modifiedParcel) {
    Parcel choosenParcel = find(id);

    choosenParcel.setIdentificationNumber(modifiedParcel.getIdentificationNumber());
    choosenParcel.setArea(modifiedParcel.getArea());

    return choosenParcel;
  }

  /**
   * Busca las tareas que coincidan con el nombre dado
   *
   * Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
   *
   * @param  taskName
   * @return coleccion de tareas que coinciden con el nombre dado
   */
  // public Collection<Parcel> findByName(String taskName) {
  //   StringBuffer queryStr = new StringBuffer("SELECT t FROM Parcel t ");
  //
  //   if (taskName != null) {
  //     queryStr.append(" WHERE UPPER(t.name) LIKE :name ");
  //   }
  //
  //   Query query = entityManager.createQuery(queryStr.toString());
  //
  //   if (taskName != null) {
  //     query.setParameter("name", "%" + taskName.toUpperCase() + "%");
  //   }
  //
  //   Collection<Parcel> parcels = (Collection<Parcel>) query.getResultList();
  //   return parcels;
  // }

}
