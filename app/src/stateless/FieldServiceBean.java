package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Map;

import model.Field;
import model.Parcel;

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

  public Field addParcelField(int fieldId, Parcel parcel) {
    Field choosenField = find(fieldId);
    choosenField.addParcel(parcel);
    return choosenField;
  }

  public Field removeParcelField(int fieldId, Parcel parcel) {
    Field choosenField = find(fieldId);
    choosenField.removeParcel(parcel);
    return choosenField;
  }

  public Page<Field> findByPage(Integer page, Integer cantPerPage, Map<String, String> parameters) {
    // Genero el WHERE dinámicamente
    StringBuffer where = new StringBuffer(" WHERE 1=1");
    if (parameters != null)
      for (String param : parameters.keySet()) {
        Method method;
        try {
          method = Field.class.getMethod("get" + capitalize(param));
          if (method == null || parameters.get(param) == null || parameters.get(param).isEmpty()) {
            continue;
          }
          switch (method.getReturnType().getSimpleName()) {
          case "String":
            where.append(" AND UPPER(e.");
            where.append(param);
            where.append(") LIKE UPPER('%");
            where.append(parameters.get(param));
            where.append("%')");
            break;
          default:
            where.append(" AND e.");
            where.append(param);
            where.append(" = ");
            where.append(parameters.get(param));
            break;
          }
        } catch (NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }

    // Cuento el total de resultados
    Query countQuery = getEntityManager()
        .createQuery("SELECT COUNT(e.id) FROM " + Field.class.getSimpleName() + " e" + where.toString());

    // Pagino
    Query query = getEntityManager().createQuery("FROM " + Field.class.getSimpleName() + " e" + where.toString());
    query.setMaxResults(cantPerPage);
    query.setFirstResult((page - 1) * cantPerPage);
    Integer count = ((Long) countQuery.getSingleResult()).intValue();
    Integer lastPage = (int) Math.ceil((double) count / (double) cantPerPage);

    // Armo respuesta
    Page<Field> resultPage = new Page<Field>(page, count, page > 1 ? page - 1 : page,
        page > lastPage ? page + 1 : lastPage, lastPage, query.getResultList());
    return resultPage;
  }

  private String capitalize(final String line) {
    return Character.toUpperCase(line.charAt(0)) + line.substring(1);
  }

}
