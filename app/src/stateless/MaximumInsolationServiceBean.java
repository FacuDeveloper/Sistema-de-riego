package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
// import javax.persistence.NoResultException;

import javax.ejb.EJB;

import model.MaximumInsolation;
import model.Latitude;
import model.Month;

@Stateless
public  class MaximumInsolationServiceBean {

  // inject a reference to the MonthServiceBean
  @EJB MonthServiceBean monthService;

  // inject a reference to the LatitudeServiceBean
  @EJB LatitudeServiceBean latitudeService;

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

  /**
   * Recupera de la base de datos subyacente la maxima
   * insolacion diaria (N) correspondiente al mes y la
   * latitud dados
   *
   * La latitud va de 0 a -70 grados decimales porque
   * en la base de datos estan cargadas las insolaciones
   * maximas diarias del hemisferio sur
   *
   * @param  month [1 .. 12]
   * @param  latitude [0 .. -70]
   * @return insolacion maxima diaria [MJ/metro cuadrado * dia]
   */
  public MaximumInsolation find(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT m FROM MaximumInsolation m WHERE m.month = :month AND m.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (MaximumInsolation) query.getSingleResult();
  }

  /**
   * @param  numberMonth [0 .. 11]
   * @param  latitude    [grados decimales]
   * @return promedio de la insolacion maxima en caso de que la
   * latitud sea impar, en caso contrario retorna la insolacion
   * maxima sin promediarla
   */
  public double getInsolation(int numberMonth, double latitude) {
    Latitude previousLatitude = null;
    Latitude nextLatitude = null;
    int intLatitude = (int) latitude;
    MaximumInsolation previousInsolation = null;
    MaximumInsolation nextInsolation = null;

    /*
     * Los meses en la clase Calendar van desde cero
     * a once, por este motivo, si el parametro numberMonth
     * es obtenido de un objeto de tipo Calendar, se le tiene
     * que sumar un uno para poder obtener un mes de la
     * base de datos, los cuales en la misma van desde
     * uno a doce
     */
    Month month = monthService.find(numberMonth + 1);

    /*
     * Si la latitud es impar se recuperan las latitudes
     * aledañas a la latitud impar y a partir de estas
     * dos latitudes se recuperan sus correspondientes
     * insolaciones maximes con las cuales se calcula
     * y retorna la insolacion maxima promedio
     */
    if ((intLatitude % 2) != 0) {
      previousLatitude = latitudeService.find(intLatitude + 1);
      nextLatitude = latitudeService.find(intLatitude - 1);

      previousInsolation = find(month, previousLatitude);
      nextInsolation = find(month, nextLatitude);

      return ((previousInsolation.getInsolation() + nextInsolation.getInsolation()) / 2.0);
    }

    /*
     * Si la latitud no es impar se retorna
     * la insolacion maxima sin promediarla
     */
    return find(month, latitudeService.find(intLatitude)).getInsolation();
  }

}
