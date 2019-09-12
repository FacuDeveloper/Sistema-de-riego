package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

import model.Field;

@Stateless
public  class FieldServiceBean {

  /*
   * Instance variables
   */
  @PersistenceContext(unitName="irrigation")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager localEntityManager){
    entityManager = localEntityManager;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Field create(Field newField) {
    getEntityManager().persist(newField);
    return newField;
  }

  /**
   * Elimina un campo mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado el campo, en caso contrario nulo
   */
  public Field remove(int id) {
    Field field = find(id);

    if (field != null) {
      getEntityManager().remove(field);
      return field;
    }

    return null;
  }

  /**
   * Actualiza o modifica la entidad asociada al id dado
   *
   * @param  id
   * @param  modifiedField
   * @return un valor no nulo en caso de modificar la entidad solicitada
   * mediante el id, en caso contrario retorna un valor nulo
   */
  public Field modify(int id, Field modifiedField) {
    Field choosenField = find(id);

    if (choosenField != null) {
      choosenField.setName(modifiedField.getName());
      choosenField.setLongitude(modifiedField.getLongitude());
      choosenField.setLatitude(modifiedField.getLatitude());
      choosenField.setArea(modifiedField.getArea());

      // TODO: Esto queda en revision
      // choosenField.setCountry(modifiedField.getCountry());
      // choosenField.setProvince(modifiedField.getProvince());
      return choosenField;
    }

    return null;
  }

  public Field find(int id) {
    return getEntityManager().find(Field.class, id);
  }

  public Collection<Field> findAll() {
    Query query = getEntityManager().createQuery("SELECT f FROM Field f ORDER BY f.id");
    return (Collection<Field>) query.getResultList();
  }

}
