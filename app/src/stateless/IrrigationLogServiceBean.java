package stateless;

import java.util.Collection;
import java.util.Map;
import java.util.Calendar;

import java.lang.reflect.Method;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.IrrigationLog;

@Stateless
public  class IrrigationLogServiceBean {

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

  public IrrigationLog create(IrrigationLog irrigationLog) {
    getEntityManager().persist(irrigationLog);
    return irrigationLog;
  }

  /**
   * Elimina un suelo mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado el suelo, en caso contrario nulo
   */
  // public IrrigationLog remove(int id) {
  //   IrrigationLog irrigationLog = find(id);
  //
  //   if (irrigationLog != null) {
  //     getEntityManager().remove(irrigationLog);
  //     return irrigationLog;
  //   }
  //
  //   return null;
  // }

  /**
   * Actualiza o modifica la entidad asociada al id dado
   *
   * @param  id
   * @param  modifiedIrrigationLog
   * @return un valor no nulo en caso de modificar la entidad solicitada
   * mediante el id, en caso contrario retorna un valor nulo
   */
  public IrrigationLog modify(int id, IrrigationLog modifiedIrrigationLog) {
    IrrigationLog choosenIrrigationLog = find(id);

    if (choosenIrrigationLog != null) {
      choosenIrrigationLog.setWateringDate(modifiedIrrigationLog.getWateringDate());
      choosenIrrigationLog.setSuggestedIrrigation(modifiedIrrigationLog.getSuggestedIrrigation());
      choosenIrrigationLog.setIrrigationDone(modifiedIrrigationLog.getIrrigationDone());
      return choosenIrrigationLog;
    }

    return null;
  }

  public IrrigationLog find(int id) {
    return getEntityManager().find(IrrigationLog.class, id);
  }

  public Collection<IrrigationLog> findAll() {
    Query query = getEntityManager().createQuery("SELECT i FROM IrrigationLog i ORDER BY i.id");
    return (Collection<IrrigationLog>) query.getResultList();
  }

  public Page<IrrigationLog> findByPage(Integer page, Integer cantPerPage, Map<String, String> parameters) {
    // Genero el WHERE dinámicamente
    StringBuffer where = new StringBuffer(" WHERE 1=1");
    if (parameters != null)
      for (String param : parameters.keySet()) {
        Method method;
        try {
          method = IrrigationLog.class.getMethod("get" + capitalize(param));
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
        .createQuery("SELECT COUNT(e.id) FROM " + IrrigationLog.class.getSimpleName() + " e" + where.toString());

    // Pagino
    Query query = getEntityManager().createQuery("FROM " + IrrigationLog.class.getSimpleName() + " e" + where.toString());
    query.setMaxResults(cantPerPage);
    query.setFirstResult((page - 1) * cantPerPage);
    Integer count = ((Long) countQuery.getSingleResult()).intValue();
    Integer lastPage = (int) Math.ceil((double) count / (double) cantPerPage);

    // Armo respuesta
    Page<IrrigationLog> resultPage = new Page<IrrigationLog>(page, count, page > 1 ? page - 1 : page,
        page > lastPage ? page + 1 : lastPage, lastPage, query.getResultList());
    return resultPage;
  }

  private String capitalize(final String line) {
    return Character.toUpperCase(line.charAt(0)) + line.substring(1);
  }

}
