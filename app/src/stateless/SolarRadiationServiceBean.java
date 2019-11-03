package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
// import javax.persistence.NoResultException;

import javax.ejb.EJB;

import model.SolarRadiation;
import model.Latitude;
import model.Month;

@Stateless
public  class SolarRadiationServiceBean {

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
   * Recupera de la base de datos subyacente la radiacion solar
   * extraterrestre (Ra) correspondiente al mes y latitud
   * dados
   *
   * La latitud va de 0 a -70 grados decimales porque en
   * la base de datos estan cargadas las radiaciones
   * solares extraterrestres del hemisferio sur
   *
   * @param  month [1 ... 2]
   * @param  latitude [0 .. -70]
   * @return radiacion solar extraterrestre [MJ/metro cuadrado * dia]
   */
  public SolarRadiation find(Month month, Latitude latitude) {
    Query query = entityManager.createQuery("SELECT s FROM SolarRadiation s WHERE s.month = :month AND s.decimalLatitude = :latitude");
    query.setParameter("month", month);
    query.setParameter("latitude", latitude);
    return (SolarRadiation) query.getSingleResult();
  }

  /**
   * @param  numberMonth [0 .. 11]
   * @param  latitude    [grados decimales]
   * @return promedio de la radiacion solar en caso de que la
   * latitud sea impar, en caso contrario retorna la radiacion
   * solar sin promediarla
   */
  public double getRadiation(int numberMonth, double latitude) {
    Latitude previousLatitude = null;
    Latitude nextLatitude = null;
    int intLatitude = (int) latitude;
    SolarRadiation previousRadiation = null;
    SolarRadiation nextRadiation = null;

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
     * radiaciones solares con las cuales se calcula
     * y retorna la radiacion solar promedio
     */
    if ((intLatitude % 2) != 0) {
      previousLatitude = latitudeService.find(intLatitude + 1);
      nextLatitude = latitudeService.find(intLatitude - 1);

      previousRadiation = find(month, previousLatitude);
      nextRadiation = find(month, nextLatitude);

      return ((previousRadiation.getRadiation() + nextRadiation.getRadiation()) / 2.0);
    }

    /*
     * Si la latitud no es impar se retorna
     * el valor de la radiacion solar sin
     * promediarla
     */
    return find(month, latitudeService.find(intLatitude)).getRadiation();
  }

}
