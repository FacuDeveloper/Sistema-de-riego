package stateless;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import java.util.Collection;
import java.util.Calendar;

import model.ClimateLog;
import model.Parcel;

import climate.ClimateLogService;

import util.UtilDate;

import et.Eto;

@Stateless
public  class ClimateLogServiceBean {

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

  public ClimateLog create(ClimateLog newClimateLog) {
    entityManager.persist(newClimateLog);
    return newClimateLog;
  }

  public ClimateLog find(int id) {
    return entityManager.find(ClimateLog.class, id);
  }

  /**
   * @param  givenDate
   * @param  givenParcel
   * @return registro climatico de la parcela dada en
   * la fecha dada
   */
  public ClimateLog find(Calendar givenDate, Parcel givenParcel) {
    Query query = entityManager.createQuery("SELECT r FROM ClimateLog r WHERE r.date = :date AND r.parcel = :parcel");
    query.setParameter("date", givenDate);
    query.setParameter("parcel", givenParcel);

    return (ClimateLog) query.getSingleResult();
  }

  /**
   * Comprueba si la parcela dada tiene un registro
   * climatico asociado (en la base de datos) y si lo
   * tiene retorna verdadero, en caso contrario retorna
   * falso
   *
   * @param  date
   * @param  parcel
   * @return verdadero en caso de enccontrar un registro del clima
   * con la fecha y la parcela dadas, en caso contrario retorna falso
   */
  public boolean checkExistenceClimateLog(Calendar date, Parcel parcel) {
    Query query = entityManager.createQuery("SELECT r FROM ClimateLog r WHERE (r.date = :date AND r.parcel = :parcel)");
    query.setParameter("date", date);
    query.setParameter("parcel", parcel);

    boolean result;

    try {
      query.getSingleResult();
      result = true;
    } catch(NoResultException ex) {
      result = false;
    }

    return result;
  }

  /**
   * Establece el agua acumulada del registro climatico
   * del dia de hoy de una parcela dada
   *
   * @param givenDate
   * @param givenParcel
   * @param waterAccumulated [milimetros]
   */
  public void updateWaterAccumulatedToday(Calendar givenDate, Parcel givenParcel, double waterAccumulated) {
    Query query = entityManager.createQuery("UPDATE ClimateLog c SET c.waterAccumulated = :waterAccumulated WHERE (c.date = :givenDate AND c.parcel = :givenParcel)");
    query.setParameter("givenDate", givenDate);
    query.setParameter("givenParcel", givenParcel);
    query.setParameter("waterAccumulated", waterAccumulated);
    query.executeUpdate();
  }

  /**
   * Recupera el registro climatico del dia de ayer haciendo
   * uso de la API climatica subyacente en caso de que dicho
   * registro no exista en la base de datos subyacente, en
   * caso contrario lo recupera de la base de datos
   *
   * @param  parcel
   * @param  yesterdaySolarRadiation
   * @param  yesterdayInsolation
   * @param  cropCoefficient         [kc]
   * @return el registro climatico del dia de ayer de la
   * parcela dada
   */
  public ClimateLog retrieveYesterdayClimateLog(Parcel parcel, double yesterdaySolarRadiation, double yesterdayInsolation, double cropCoefficient) {
    /*
     * Fecha del dia inmediatamente anterior a la fecha
     * actual del sistema
     */
    Calendar yesterdayDate = UtilDate.getYesterdayDate();

    /*
     * Instancia unica (por usar el patron de diseño Singleton)
     * de la clase ClimateLogService que tiene por objetivo
     * recuperar de la API climatica utilizada los registros
     * climaticos
     */
    ClimateLogService climateLogService = ClimateLogService.getInstance();
    ClimateLog yesterdayClimateLog;
    double yesterdayEto;
    double yesterdayEtc;

    /*
     * Si el registro climatico del dia de ayer no existe en la
     * base de datos subyacente se lo tiene que pedir a la API
     * climatica subyacente y se lo tiene que persistir en la
     * base de datos subyacente
     */
    if (!(checkExistenceClimateLog(yesterdayDate, parcel))) {
      /*
       * Con esta sentencia de decision se logra que se haga
       * una peticion a la API climatica Dark Sky solo cuando
       * no existe, en la base de datos, el registro climatico
       * solicitado, lo cual es bueno porque hace que no siempre
       * se use dicha API, y a su vez esto es un beneficio porque
       * la API climatica Dark Sky permite en su uso gratuito
       * 1000 peticiones
       */
      yesterdayClimateLog = climateLogService.getClimateLog(parcel.getLatitude(), parcel.getLongitude(), (yesterdayDate.getTimeInMillis() / 1000));

      /*
       * Evapotranspiracion del cultivo de referencia (ETo) con las
       * condiciones climaticas del registro climatico del dia de ayer,
       * la radiacion solar del dia de ayer y la insolacion maxima del
       * dia de ayer
       */
      yesterdayEto = Eto.getEto(yesterdayClimateLog, yesterdaySolarRadiation, yesterdayInsolation);

      /*
       * Evapotranspiracion del cultivo bajo condiciones estandar (ETc)
       * del cultivo dado con la ETo del dia de ayer
       */
      yesterdayEtc = cropCoefficient * yesterdayEto;

      yesterdayClimateLog.setEto(yesterdayEto);
      yesterdayClimateLog.setEtc(yesterdayEtc);
      yesterdayClimateLog.setParcel(parcel);
      return create(yesterdayClimateLog);
    }

    return find(yesterdayDate, parcel);
  }

}
