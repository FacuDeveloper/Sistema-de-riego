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

import model.Cultivo;

import java.lang.Math;

@Stateless
public class CultivoServiceBean implements CultivoService {

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emLocal){
    entityManager = emLocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
  * Persiste en la base da datos la instancia de Cultivo recibida
  * @param crop
  * @return referencia a un objeto de tipo Cultivo
  */
  public Cultivo create(Cultivo crop) {
    entityManager.persist(crop);
    return crop;
  }

  /**
   * Elimina de forma logica de la base de datos subyacente el cultivo
   * que tiene el identificador dado
   *
   * @param  id [identificador]
   * @return referencia a cultivo en caso de que haya sido eliminado,
   * referencia a nada (nulo) en caso contrario
   */
  public Cultivo remove(int id) {
    Cultivo crop = find(id);

    if (crop != null) {
      crop.setActivo(false);
      return crop;
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
    Cultivo crop = find(id);

    if (crop != null) {
      crop.setKcInicial(cultivo.getKcInicial());
      crop.setKcMedio(cultivo.getKcMedio());
      crop.setKcFinal(cultivo.getKcFinal());
      crop.setNombre(cultivo.getNombre());
      crop.setEtInicial(cultivo.getEtInicial());
      crop.setEtDesarrollo(cultivo.getEtDesarrollo());
      crop.setEtMedia(cultivo.getEtMedia());
      crop.setEtFinal(cultivo.getEtFinal());
      crop.setAgotamientoCritico(cultivo.getAgotamientoCritico());
      crop.setActivo(cultivo.getActivo());
      return crop;
    }

    return null;
  }

  public Cultivo find(int id) {
    return entityManager.find(Cultivo.class, id);
  }

  /**
   * @return coleccion con todas los cultivos que estan activos
   */
  public Collection<Cultivo> findAllActive() {
    Query query = entityManager.createQuery("SELECT c FROM Cultivo c WHERE c.activo = TRUE ORDER BY c.id");
    return (Collection<Cultivo>) query.getResultList();
  }

  /**
   * *** NOTA ***
   * Este metodo es unicamente para la clase de prueba
   * unitaria KcTest, con lo cual no sera utilizado
   * en la version final del sistema, sino que es
   * unicamente para usarlo en una prueba untiaria
   * con el fin de verificar su correcto funcionamiento
   * *** FIN DE NOTA ***
   *
   * Devuelve el kc (coeficiente del cultivo) de un
   * cultivo dado en funcion de la etapa de vida
   * en la que se encuentre la cantidad de dias
   * que ha vivido desde su fecha de siembra
   * hasta la fecha actual
   *
   * @param  crop
   * @param  seedDate [fecha de siembra del cultivo dado]
   * @return kc (coeficiente del cultivo) de un cultivo
   * dado correspondiente a la etapa de vida en la
   * que se encuentre
   */
  public double getKc(Cultivo crop, Calendar seedDate, Calendar currentDate) {
    int daysLife = 0;

    /*
     * Si la fecha de siembra y la fecha actual son del mismo
     * año se calcula la diferencia de dias entre ambas fechas
     * sin tener en cuenta el año debido a que pertenecen al
     * mismo año y dicha diferencia es la cantidad de dias
     * de vida que ha vivido el cultivo desde su fecha
     * de siembra hasta la fecha actual
     */
    if (seedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
      daysLife = currentDate.get(Calendar.DAY_OF_YEAR) - seedDate.get(Calendar.DAY_OF_YEAR);
      return calculateKc(crop, daysLife);
    }

    /*
     * Si entre la fecha de siembra y la fecha actual hay un año
     * de diferencia (lo que significa que no son del mismo año
     * pero el año de la fecha actual esta a continuacion
     * del año de la fecha de siembra) se calcula la diferencia
     * de dias entre ambas de la siguiente forma:
     *
     * Cantidad de dias de vida = Numero del dia del año de la fecha
     * actual + (365 - numero del dia del año de la fecha de siembra + 1)
     */
    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) == 1) {
      daysLife = currentDate.get(Calendar.DAY_OF_YEAR) + (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1);
      return calculateKc(crop, daysLife);
    }

    /*
     * NOTE: Este calculo esta mal pero no tan mal, y esto se lo puede
     * ver en la clase de prueba unitaria llamada KcTest cuando al
     * tomate se le pone una fecha de siembra con el año 1995 y una
     * fecha actual con el año 2020 dando la diferencia en dias entre
     * ambas fechas distinta a la diferencia en dias entre ambas
     * fechas en una calculadora online de dias
     *
     * Para ver lo que dice el parrafo anterior, ejecutar la prueba
     * mencionada
     *
     * Si entre la fecha de siembra y la fecha actual hay mas de un año
     * de diferencia (lo que significa que no son del mismo año y que
     * entre ambas fechas hay mas de un año de distancia) se calcula
     * la diferencia de dias entre ambas fechas de la siguiente forma:
     *
     * Cantidad de dias de vida = (Año de la fecha actual - año de la
     * fecha de siembra) * 365 - (365 - Numero del dia en el año de la
     * fecha de siembra + 1) - (365 - Numero del dia en el año de la fecha actual)
     *
     * Se multiplica daysLife por 365 para evitar posibles errores, y ademas
     * si la diferencia entre ambas fechas es de mas de un año no tiene sentido
     * calcular la cantidad de dias de vida del cultivo dado porque hasta donde
     * se sabe ninguno cultivo mas de un año
     */
    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) > 1) {
      daysLife = ((Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR))) * 365) - (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1) - (365 - currentDate.get(Calendar.DAY_OF_YEAR));
      return calculateKc(crop, (daysLife * 365));
    }

    return calculateKc(crop, daysLife);
  }

  /**
   * Devuelve el kc (coeficiente del cultivo) de un
   * cultivo dado en funcion de la etapa de vida
   * en la que se encuentre la cantidad de dias
   * que ha vivido desde su fecha de siembra
   * hasta la fecha actual
   *
   * @param  crop
   * @param  seedDate [fecha de siembra del cultivo dado]
   * @return kc (coeficiente del cultivo) de un cultivo
   * dado correspondiente a la etapa de vida en la
   * que se encuentre
   */
  public double getKc(Cultivo crop, Calendar seedDate) {
    int daysLife = 0;
    Calendar currentDate = Calendar.getInstance();

    /*
     * TODO: Ver si se pueden refactorizar los return
     */

    /*
     * Si la fecha de siembra y la fecha actual son del mismo
     * año se calcula la diferencia de dias entre ambas fechas
     * sin tener en cuenta el año debido a que pertenecen al
     * mismo año y dicha diferencia es la cantidad de dias
     * de vida que ha vivido el cultivo desde su fecha
     * de siembra hasta la fecha actual
     */
    if (seedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
      daysLife = currentDate.get(Calendar.DAY_OF_YEAR) - seedDate.get(Calendar.DAY_OF_YEAR);
      return calculateKc(crop, daysLife);
    }

    /*
     * Si entre la fecha de siembra y la fecha actual hay un año
     * de diferencia (lo que significa que no son del mismo año
     * pero el año de la fecha actual esta a continuacion
     * del año de la fecha de siembra) se calcula la diferencia
     * de dias entre ambas fechas de la siguiente forma:
     *
     * Cantidad de dias de vida = Numero del dia del año de la fecha
     * actual + (365 - Numero del dia del año de la fecha de siembra + 1)
     */
    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) == 1) {
      daysLife = currentDate.get(Calendar.DAY_OF_YEAR) + (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1);
      return calculateKc(crop, daysLife);
    }


    /*
     * NOTE: Este calculo esta mal pero no tan mal, y esto se lo puede
     * ver en la clase de prueba unitaria llamada KcTest cuando al
     * tomate se le pone una fecha de siembra con el año 1995 y una
     * fecha actual con el año 2020 dando la diferencia en dias entre
     * ambas fechas distinta a la diferencia en dias entre ambas
     * fechas en una calculadora online de dias
     *
     * Para ver lo que dice el parrafo anterior, ejecutar la prueba
     * mencionada
     *
     * Si entre la fecha de siembra y la fecha actual hay mas de un año
     * de diferencia (lo que significa que no son del mismo año y que
     * entre ambas fechas hay mas de un año de distancia) se calcula
     * la diferencia de dias entre ambas fechas de la siguiente forma:
     *
     * Cantidad de dias de vida = (Año de la fecha actual - año de la
     * fecha de siembra) * 365 - (365 - Numero del dia en el año de la
     * fecha de siembra + 1) - (365 - Numero del dia en el año de la fecha actual)
     *
     * Se multiplica daysLife por 365 para evitar posibles errores, y ademas
     * si la diferencia entre ambas fechas es de mas de un año no tiene sentido
     * calcular la cantidad de dias de vida del cultivo dado porque hasta donde
     * se sabe ninguno cultivo mas de un año
     */
    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) > 1) {
      daysLife = ((Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR))) * 365) - (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1) - (365 - currentDate.get(Calendar.DAY_OF_YEAR));
      return calculateKc(crop, (daysLife * 365));
    }

    return calculateKc(crop, daysLife);
  }

  /**
   * Calcula el kc (coeficiente del cultivo) de un cultivo
   * dado verificando si la cantidad de dias de vida del mismo
   * esta dentro de una de sus tres etapas de vida
   *
   * Si la cantidad de dias de vida del cultivo dado no
   * estan dentro de ninguna de sus tres etapas de vida, este
   * metodo retorna como resultado un kc = 0.0
   *
   * La cantidad de dias de vida que ha vivido un cultivo
   * se calcula como la diferencia entre la fecha actual
   * y su fecha de siembra
   *
   * @param  crop
   * @param  daysLife [dias de vida del cultivo dado]
   * @return coeficiente del cultivo (kc) dado en funcion
   * de la etapa de vida en la que se encuentre, en caso
   * de que no este dentro de ninguna de sus tres etapas
   * de vida, este metodo retorna un kc = 0.0
   */
  private double calculateKc(Cultivo crop, int daysLife) {
    double zeroKc = 0.0;

    /*
     * Numero del dia en el cual comienza la etapa inicial
     * sumada a la etapa de desarrollo del cultivo dado
     */
    int initialDayInitialStage = 1;

    /*
     * Numero del dia en el cual comienza la segunda etapa
     * (la etapa media) de vida del cultivo dado
     */
    int initialDayMiddleStage = crop.getEtInicial() + crop.getEtDesarrollo() + 1;

    /*
     * Numero del dia en el cual comienza la tercera etapa
     * (etapa final) de vida del cultivo dado
     */
    int initialDayFinalStage = crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia() + 1;

    /*
     * El limite superior o ultimo dia de la etapa inicial
     * de un cultivo es igual a la suma de los dias de su
     * etapa incial mas los dias de su etapa de desarrollo
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas y que se toma la etapa
     * inicial como la suma de los dias que dura la etapa inicial mas
     * los dias que dura la etapa de desarrollo, quedando la etapa
     * incial como la etapa incial mas la etapa de desarrollo
     */
    int upperLimitFirstStage = crop.getEtInicial() + crop.getEtDesarrollo();

    /*
     * El limite superior o ultimo dia de la etapa media
     * de un cultivo es igual a la suma de los dias de su
     * etapa inicial mas los dias de su etapa de desarrollo
     * mas los dias de su etapa media
     */
    int upperLimitSecondStage = crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia();

    /*
     * El limite superior o ultimo dia de la etapa final
     * de un cultivo es igual a la suma de los dias de su
     * etapa incial mas los dias de su etapa de desarrollo
     * mas los dias de su etapas media mas los dias de su
     * etapa final
     */
    int upperLimitThirdStage = crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia() + crop.getEtFinal();

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre
     * entre el dia del comienzo de la etapa inicial y el maximo
     * de dias (o limite superior) que dura la misma (recordar que la etapa inicial
     * es la suma de la cantidad de dias que dura la etapa incial
     * mas la suma de la cantidad de dias que dura la etapa de
     * desarrollo), este metodo retorna el coeficiente incial (kc)
     * del cultivo dado
     *
     * Dicho en otras palabras, si la cantidad de dias de vida
     * del cultivo dado esta entre uno y el maximo de la cantidad
     * de dias resultante de la suma de los dias de la etapa inicial
     * y de la etapa de desarrollo, este metodo retorna el coeficiente
     * inicial (kc) del cultivo dado
     *
     * Entre los integrantes del equipo de desarrollo se ha establecido
     * que las etapas inicial y desarrollo son una sola, y es por esto
     * que se suman los dias que duran ambas, y por ende la etapa inicial
     * es la etapa inicial mas la etapa de desarrollo
     */
    if ((initialDayInitialStage <= daysLife) && (daysLife <= upperLimitFirstStage)) {
      return crop.getKcInicial();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre el dia
     * del comienzo de la etapa media y el maximo de dias (o limite superior) que dura la
     * misma (el cual resulta de sumar los dias que duran las
     * etapas inicial, desarrollo y media), este metodo retorna el
     * coeficiente medio (kc) del cultivo dado
     */
    if ((initialDayMiddleStage <= daysLife) && (daysLife <= upperLimitSecondStage)) {
      return crop.getKcMedio();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado esta entre el dia
     * del comienzo de la etapa final y el maximo de dias (o limite superior) que dura la
     * misma (el cual resulta de sumar los dias que duran las etapas
     * incial, desarrollo, media y final), este metodo retorna el
     * coeficiente final (kc) del cultivo dado
     */
    if ((initialDayFinalStage <= daysLife) && (daysLife <= upperLimitThirdStage)) {
      return crop.getKcFinal();
    }

    /*
     * Si la cantidad de dias de vida del cultivo dado no esta
     * dentro de ninguna de las tres etapas del mismo, este
     * metodo retorna un coeficiente del cultivo (kc) igual a 0.0
     */
    return zeroKc;
  }

  /**
   * Calcula la fecha de cosecha de un cultivo a partir de su fecha
   * de siembra
   *
   * @param  seedDate [fecha de siembra]
   * @param  crop     [cultivo]
   * @return fecha de cosecha del cultivo dado
   */
  public Calendar calculateHarvestDate(Calendar seedDate, Cultivo crop) {
    /*
     * Fecha de cosecha
     *
     * Recordar que el metodo getInstance() retorna
     * una referencia a un objeto de tipo Calendar
     * que tiene la fecha actual del sistema, por lo
     * tanto, a esta fecha de cosecha hay que establecerle
     * el año de la fecha de siembra luego de la sentencia
     * de control if
     */
    Calendar harvestDate = Calendar.getInstance();

    /*
     * La cantidad total de dias de vida del cultivo dado
     * es igual a la suma de la duracion en dias de cada una
     * de sus etapas
     */
    int totalDaysLife = crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtMedia() + crop.getEtFinal();

    /*
     * Si el numero del dia de siembra en el año mas la cantidad
     * total de dias de vida del cultivo dado es mayor a 365
     * (por ende, la fecha de siembra y la fecha de cosecha no
     * estan en el mismo año), la fecha de cosecha es igual a la fecha
     * de siembra mas la cantidad total de dias de vida del cultivo
     * dado menos 365
     */
    if ((seedDate.get(Calendar.DAY_OF_YEAR) + totalDaysLife) > 365) {
      harvestDate.set(Calendar.DAY_OF_YEAR, ((seedDate.get(Calendar.DAY_OF_YEAR) + totalDaysLife) - 365));
      harvestDate.set(Calendar.YEAR, seedDate.get(Calendar.YEAR) + 1);
      return harvestDate;
    }

    /*
     * Si el numero del dia de siembra en el año mas la cantidad
     * total de dias de vida del cultivo dado no es mayor a 365
     * (por ende, la fecha de siembra y la fecha de cosecha estan
     * en el mismo año), la fecha de cosecha es igual a la fecha
     * de siembra mas la cantidad total de dias de vida del cultivo
     * dado, y tanto la fecha de siembra como la fecha de cosecha
     * estan en el mismo en el mismo año
     */
    harvestDate.set(Calendar.DAY_OF_YEAR, (seedDate.get(Calendar.DAY_OF_YEAR) + totalDaysLife));
    harvestDate.set(Calendar.YEAR, seedDate.get(Calendar.YEAR));
    return harvestDate;
  }

  /**
   * Busca en la base de datos la lista completa de Cultivos
   * @return Collection<Cultivo> se retorna la lista de Cultivos
   */
  public Collection<Cultivo> findAll() {
    Query query = entityManager.createQuery("SELECT c FROM Cultivo c ORDER BY c.id");
    return (Collection<Cultivo>) query.getResultList();
  }

  public Cultivo findByName(String cropName) {
    Query query = entityManager.createQuery("SELECT c FROM Cultivo c WHERE c.nombre = :cropName");
    query.setParameter("cropName", cropName.toUpperCase());
    return (Cultivo) query.getSingleResult();
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
    Query query = entityManager.createQuery("FROM " + Cultivo.class.getSimpleName() + " e" + where.toString());
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

}
