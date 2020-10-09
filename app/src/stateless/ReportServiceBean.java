package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;
import java.util.List;

import model.Report;

@Stateless
public  class ReportServiceBean {
  private final int ZERO_CROPS = 0;
  private final int MORE_THAN_ONE_CROP = 1;

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

  /**
   * @param  idParcel [ID de una parcela]
   * @return el nombre del cultivo que mas fue plantado en la
   * parcela con el ID dado, en caso contrario retorna "Cultivo no encontrado"
   */
  public String getMorePlantedCrop(int idParcel) {
    /*
     * Cuenta la cantidad de veces que se repite el ID de cada cultivo en cada grupo,
     * de instancias de parcela que estan en el estado "Finalizado", hecho por ID
     * del cultivo de una parcela dada
     */
    String subQueryOne = "(SELECT COUNT(FK_CULTIVO) AS CANT_VECES_SEMBRADO FROM INSTANCIA_PARCELA INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID WHERE INSTANCIA_PARCELA.FK_PARCELA = "
    + idParcel + " AND NOMBRE = 'Finalizado' GROUP BY FK_CULTIVO) AS REST)";

    /*
     * Selecciona el maximo del conteo en cada grupo, de instancias de parcela que estan
     * en el estado "Finalizado", hecho en base al ID del cultivo de una parcela dada
     * (esto quiere decir que primero se filtra por el ID de una parcela)
     */
    String subQueryTwo = "(SELECT MAX(CANT_VECES_SEMBRADO) AS MAX_CANT_VECES_SEMBRADO FROM " + subQueryOne;

    /*
     * Obtiene el ID de los cultivos que tienen mas ocurrencias de su ID en los grupos,
     * de instancias de parcela que estan en el estado "Finalizado", hechos en base al
     * ID del cultivo de una parcela dada (esto quiere decir que primero se filtra por
     * el ID de una parcela)
     */
    String subQueryThree = "(SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS CANT_VECES_SEMBRADO FROM INSTANCIA_PARCELA INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID WHERE INSTANCIA_PARCELA.FK_PARCELA = "
    + idParcel + " AND NOMBRE = 'Finalizado' GROUP BY FK_CULTIVO HAVING COUNT(FK_CULTIVO) = " + subQueryTwo + ")";

    /*
     * Obtiene el nombre del cultivo que mas se ha plantado en una parcela dada, pero
     * puede obtener mas de un cultivo como "el cultivo que mas se ha plantado", si esto
     * ultimo sucede no existe el cultivo que mas se ha plantado en una parcela dada
     * porque hay mas de uno que aparece como "el que mas se ha plantado"
     */
    Query query = entityManager.createNativeQuery("SELECT NOMBRE FROM CULTIVO INNER JOIN " + subQueryThree + " AS REST ON CULTIVO_ID = REST.FK_CULTIVO");

    List<Object[]> crops = query.getResultList();

    /*
     * Si la consulta SQL retorna una lista con tamaño 0, significa
     * que no hay o no hubo cultivos plantados en la parcela dada,
     * con lo cual, lo que se debe retornar es "Cultivo no encontrado"
     *
     * Si la consulta SQL retorna una lista con tamaño mayor a 1, significa
     * que hay mas de un cultivo como "el mas plantado" en la parcela dada,
     * y al haber mas de un cultivo como "el mas plantado" no existe el cultivo
     * que mas fue plantado en la parcela dada, por lo tanto, se debe retornar
     * "Cultivo no encontrado"
     */
    if ((crops.size() == ZERO_CROPS) || (crops.size() > MORE_THAN_ONE_CROP)) {
      return "Cultivo no encontrado";
    }

    /*
     * Se agrega "" + para convertir el elemento de la
     * posicion 0 que es una referencia de tipo Object
     * a una referencia de tipo String (recordar que
     * existen las variables de tipo por referencia)
     *
     * Si no se ejecuta la sentencia de seleccion anterior
     * es porque la lista crops contiene un unico elemento,
     * y al unico elemento de la lista se accede mediante
     * el indice 0
     */
    return "" + crops.get(0);
  }

}
