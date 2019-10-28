package stateless;

import java.util.Collection;
import java.util.Calendar;
import java.util.Map;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import model.Cultivo;
//import model.TipoCultivo;

@Stateless
public class CultivoServiceBean implements CultivoService {
  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager em;

  public void setEntityManager(EntityManager emLocal){
    em = emLocal;
  }

  public EntityManager getEntityManager() {
    return em;
  }

  /**
  * Persiste en la base da datos la instancia de Cultivo recibida
  * @param cul
  * @return referencia a un objeto de tipo Cultivo
  */
  public Cultivo create(Cultivo cul) {
    getEntityManager().persist(cul);
    return cul;
  }

  /**
   * Elimina de la base de datos subyacente el cultivo
   * que tiene el identificador dado
   *
   * @param  id [identificador]
   * @return referencia a cultivo en caso de que haya sido eliminado,
   * referencia a nada (nulo) en caso contrario
   */
  public Cultivo remove(int id) {
    Cultivo cul= find(id);

    if (cul != null) {
      getEntityManager().remove(cul);
      return cul;
    }

    return null;
  }

  /**
  * Modificar los valores de una Cultivo a partir de los cambios de otra Cultivo instanciada
  * @param int id Id de el cultivo a modificar
  * @param Cultivo Cultivo Instancia de el cultivo con los valores actualizados
  * @return Cultivo se retorna el cultivo de la base de datos con las modificaciones, o null si no se encontro Cultivosio correspondiente al id
  */
  public Cultivo change(int id, Cultivo cultivo) {
    Cultivo cul = find(id);

    if (cul != null) {
      cul.setKcInicial(cultivo.getKcInicial());
      cul.setKcMedio(cultivo.getKcMedio());
      cul.setKcFinal(cultivo.getKcFinal());
      cul.setNombre(cultivo.getNombre());
      cul.setEtInicial(cultivo.getEtInicial());
      cul.setEtDesarrollo(cultivo.getEtDesarrollo());
      cul.setEtMedia(cultivo.getEtMedia());
      cul.setEtFinal(cultivo.getEtFinal());
      return cul;
    }

    return null;
  }

  public Cultivo find(int id) {
    return getEntityManager().find(Cultivo.class, id);
  }

  public double getKc(Cultivo crop, Calendar seedTime, Calendar currentDate) {
    /*
     * Numero del dia en el cual comienza la etapa inicial
     * sumada a la etapa de desarrollo del cultivo dado
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas
     */
    int initialDayFirstStage = 1;

    /*
     * Numero del dia en el cual comienza la segunda etapa
     * del cultivo dado
     */
    int initialDaySecondStage = crop.getEtInicial() + crop.getEtDesarrollo() + 1;

    /*
     * Dias de vida que ha vivido el cultivo dado
     * desde su fecha de siembra hasta la fecha actual
     */
    int daysLife = getDaysLife(seedTime, currentDate);

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre uno
     * y el maximo de la cantidad de dias resultante de la
     * suma de los dias de la etapa inicial y de la etapa de
     * desarrollo, este metodo retorna el coeficiente inicial (kc)
     * del cultivo dado
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas
     */
    if ((initialDayFirstStage <= daysLife) && (daysLife <= (crop.getEtInicial() + crop.getEtDesarrollo()))) {
      return crop.getKcInicial();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre el dia
     * del comienzo de la etapa media y el maximo de dias que dura la
     * etapa media (el cual resulta de sumar los dias que duran las
     * etapas inicial, desarrollo y media), este metodo retorna el
     * coeficiente medio (kc) del cultivo dado
     */
    if ((initialDaySecondStage <= daysLife) && (daysLife <= (crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia()))) {
      return crop.getKcMedio();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado no esta entre
     * los dias que dura la etapa inicial (dias de la etapa incial
     * mas dias de la etapa de desarrollo), ni entre los dias que
     * dura la etapa media, entonces esta entre el dia de comienzo
     * de la etapa final y el maximo de dias de la misma etapa, con
     * lo cual este metodo retorna el coeficiente del cultivo (kc)
     * final
     */
    return crop.getKcFinal();
  }

  private int getDaysLife(Calendar seedTime, Calendar currentDate) {
    return (currentDate.get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR));
  }

  public double getKc(Cultivo crop, Calendar seedTime) {
    /*
     * Numero del dia en el cual comienza la etapa inicial
     * sumada a la etapa de desarrollo del cultivo dado
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas
     */
    int initialDayFirstStage = 1;

    /*
     * Numero del dia en el cual comienza la etapa media
     * del cultivo dado
     */
    int initialDaySecondStage = crop.getEtInicial() + crop.getEtDesarrollo() + 1;

    /*
     * Dias de vida que ha vivido el cultivo dado
     * desde su fecha de siembra hasta la fecha actual
     */
    int daysLife = getDaysLife(seedTime);

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre uno
     * y el maximo de la cantidad de dias resultante de la
     * suma de los dias de la etapa inicial y de la etapa de
     * desarrollo, este metodo retorna el coeficiente inicial (kc)
     * del cultivo dado
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas
     */
    if ((initialDayFirstStage <= daysLife) && (daysLife <= (crop.getEtInicial() + crop.getEtDesarrollo()))) {
      return crop.getKcInicial();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre el dia
     * del comienzo de la etapa media y el maximo de dias que dura la
     * etapa media (el cual resulta de sumar los dias que duran las
     * etapas inicial, desarrollo y media), este metodo retorna el
     * coeficiente medio (kc) del cultivo dado
     */
    if ((initialDaySecondStage <= daysLife) && (daysLife <= (crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia()))) {
      return crop.getKcMedio();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado no esta entre
     * los dias que dura la etapa inicial (dias de la etapa incial
     * mas dias de la etapa de desarrollo), ni entre los dias que
     * dura la etapa media, entonces esta entre el dia de comienzo
     * de la etapa final y el maximo de dias de la misma etapa, con
     * lo cual este metodo retorna el coeficiente del cultivo (kc)
     * final
     */
    return crop.getKcFinal();
  }

  /**
   * Calcula la diferencia entre la fecha de siembra de un cultivo
   * dado y la fecha actual, operacion que produce como resultado
   * la cantidad de dias de vida que ha transcurrido el cultivo dado
   *
   * @param  seedTime [fecha de siembra de un cultivo dado]
   * @return cantidad de dias de vida de un cultivo dado desde su
   * fecha de siembra
   */
  private int getDaysLife(Calendar seedTime) {
    return (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR));
  }

  /**
  * Busca en la base de datos la lista completa de Cultivos
  * @return Collection<Cultivo> se retorna la lista de Cultivos
  */
  public Collection<Cultivo> findAll() {
    Query query = getEntityManager().createQuery("SELECT c FROM Cultivo c ORDER BY e.id");
    return (Collection<Cultivo>) query.getResultList();
  }

  public Page<Cultivo> findByPage(Integer page, Integer cantPerPage, Map<String, String> parameters) {
    // Genero el WHERE dinámicamente
    StringBuffer where = new StringBuffer(" WHERE 1=1");
    if (parameters != null)
    for (String param : parameters.keySet()) {
      Method method;
      try {
        method = Cultivo.class.getMethod("get" + capitalize(param));
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
    .createQuery("SELECT COUNT(e.id) FROM " + Cultivo.class.getSimpleName() + " e" + where.toString());

    // Pagino
    Query query = getEntityManager().createQuery("FROM " + Cultivo.class.getSimpleName() + " e" + where.toString());
    query.setMaxResults(cantPerPage);
    query.setFirstResult((page - 1) * cantPerPage);
    Integer count = ((Long) countQuery.getSingleResult()).intValue();
    Integer lastPage = (int) Math.ceil((double) count / (double) cantPerPage);

    // Armo respuesta
    Page<Cultivo> resultPage = new Page<Cultivo>(page, count, page > 1 ? page - 1 : page,
    page > lastPage ? page + 1 : lastPage, lastPage, query.getResultList());
    return resultPage;
  }

  private String capitalize(final String line) {
    return Character.toUpperCase(line.charAt(0)) + line.substring(1);
  }

  public Cultivo findByName(String cropName) {
    Query query = getEntityManager().createQuery("SELECT c FROM Cultivo c WHERE c.nombre = :cropName");
    query.setParameter("cropName", cropName.toUpperCase());
    return (Cultivo) query.getSingleResult();
  }

  /*
   * NOTE: Despues de la demostracion los metodos que tiene
   * Calendar seedTime, Calendar currentDate tienen que ser borrados
   */

}
