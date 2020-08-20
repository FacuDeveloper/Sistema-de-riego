package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

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

  /**
   * @param  parcelName
   * @return coleccion de los informes estadisticos que conocen
   * a la parcela que tiene el nombre provisto
   */
  public Collection<StatisticalReport> findStatisticalReportByParcelName(String parcelName) {
    Query query = entityManager.createQuery("SELECT s FROM StatisticalReport s WHERE s.parcel.name = :parcelName ORDER BY s.date");
    query.setParameter("parcelName", parcelName);

    return (Collection<StatisticalReport>) query.getResultList();
  }

  public StatisticalReport generateStatisticalReport(String parcelName) {
    return null;
  }

  /**
   * @param  idParcel [identificador de una parcela]
   * @return coleccion que contiene los nombres de todos los
   * cultivos que mas fueron plantados en la parcela que tiene
   * el identificador provisto
   */
  public Collection<String> getMorePlantedCrops(int idParcel) {

    /*
     * Sub consultas
     *
     * "(SELECT COUNT(FK_CULTIVO) AS COUNTER FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel
     * + " GROUP BY FK_PARCELA, FK_CULTIVO) AS RESULT_TABLE"
     *
     * Cuenta la cantidad de veces que fueron plantados los cultivos en una parcela dada
     *
     * "(SELECT MAX(RESULT_TABLE.COUNTER) FROM (SELECT COUNT(FK_CULTIVO) AS COUNTER FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel
     * + " GROUP BY FK_PARCELA, FK_CULTIVO) AS RESULT_TABLE)"
     *
     * Selecciona los contadores maximos, lo que significa que selecciona la cantidad de veces de aquellos cultivos
     * que mas veces fueron plantados en la parcela dada
     *
     * Consulta
     *
     * "SELECT FK_CULTIVO, CULTIVO.NOMBRE FROM INSTANCIA_PARCELA INNER JOIN CULTIVO ON FK_CULTIVO = CULTIVO_ID WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel
     * + " GROUP BY FK_PARCELA, FK_CULTIVO, CULTIVO.NOMBRE HAVING COUNT(FK_CULTIVO) = (SELECT MAX(RESULT_TABLE.COUNTER) FROM (SELECT COUNT(FK_CULTIVO) AS COUNTER FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel
     * + " GROUP BY FK_PARCELA, FK_CULTIVO) AS RESULT_TABLE)"
     *
     * Selecciona el ID y el nombre del cultivo o de los cultivos que mas veces fueron plantados en la parcela dada
     *
     * Lo que hace esta consulta es agrupar, los registros resultantes de la union entre las tablas INSTANCIA_PARCELA y CULTIVO, por parcela,
     * cultivo y nombre de cultivo, y de estas agrupaciones filtra aquellos grupos de registros que contienen los cultivos que mas veces fueron
     * plantados en la parcela dada (esto lo hace la sentencia HAVING), y a partir de este resultado selecciona el ID y el nombre de cada cultivo,
     * pero de cada cultivo que mas veces fue plantado en la parcela dada
     */

    String firstPartQuery = "SELECT FK_CULTIVO, CULTIVO.NOMBRE FROM INSTANCIA_PARCELA INNER JOIN CULTIVO ON FK_CULTIVO = CULTIVO_ID WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel;
    String secondPartQuery = " GROUP BY FK_PARCELA, FK_CULTIVO, CULTIVO.NOMBRE HAVING COUNT(FK_CULTIVO) = (SELECT MAX(RESULT_TABLE.COUNTER) FROM (SELECT COUNT(FK_CULTIVO) AS COUNTER FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = " + idParcel;
    String thirdPartQuery = " GROUP BY FK_PARCELA, FK_CULTIVO) AS RESULT_TABLE)";

    String stringQuery = firstPartQuery + secondPartQuery + thirdPartQuery;
    Query query = entityManager.createNativeQuery(stringQuery);

    return converToStringCollection(query.getResultList());
  }

  public Collection<String> getLeastPlantedCrops(int idParcel) {
    return null;
  }

  /**
   * Recibe como parametro el arreglo bidimensional que contiene
   * el resultado de una consulta nativa (en SQL) y extrae de el,
   * el nombre del cultivo, el cual esta en la posicion 1 de cada
   * arreglo unidimensional referenciado por la lista
   *
   * @param  resultMatrix [arreglo bidimensional que contiene el resultado
   * de una sentencia SQL]
   * @return coleccion que contiene los nombres de todos los
   * cultivos contenidos en el arreglo bidimensional provisto
   */
  private Collection<String> converToStringCollection(List<Object[]> resultMatrix) {
    Collection<String> collection = new ArrayList<>();

    for (Object[] current : resultMatrix) {
      /*
       * El nombre del cultivo esta en la posicion 1,
       * de cada arreglo unidimensional referenciado por
       * la lista, porque en la sentencia SELECT aparece
       * despues de selecciona el ID del cultivo
       */
      collection.add(((String) current[1]));
    }

    return collection;
  }


}
