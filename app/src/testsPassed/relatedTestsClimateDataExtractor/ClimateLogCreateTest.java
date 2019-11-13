import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;

import java.util.Calendar;
import java.util.Collection;

import climate.ClimateLogService;

import model.Parcel;
import model.ClimateLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClimateLogCreateTest {
  private static ClimateLogServiceBean climateLogServiceBean;
  private static ParcelServiceBean parcelService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    climateLogServiceBean = new ClimateLogServiceBean();
    climateLogServiceBean.setEntityManager(entityManager);

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Prueba unitaria para la creacion
   * de registros climaticos
   *
   * *** NOTA ***
   * Para ejecutar esta prueba unitaria
   * es necesario que la base de datos
   * este cargada con parcelas y que
   * cada registro del clima tenga
   * establecida una parcela, de lo
   * contrario esta prueba unitaria
   * no funcionara correctamente
   */
  @Test
  public void testCreate() {
    Collection<Parcel> parcels = parcelService.findAll();

    /*
     * La API del clima Dark Sky utiliza como
     * parametros las coordenadas geograficas
     * en grados decimales y es por esto que se
     * utilizan variables de tipo double para
     * almacenar de forma temporal las coordenadas
     * geograficas en grados decimales de cada
     * parcela de la coleccion llamada parcels
     */
    double latitude = 0.0;
    double longitude = 0.0;

    /*
     * Fecha actual
     */
    Calendar currentDate = Calendar.getInstance();

    /*
     * Convierte el tiempo en milisegundos
     * a segundos porque la API del clima Dark
     * Sky utiliza el formato UNIX (el cual
     * esta en segundos) en sus llamadas
     */
    long unixTime = (currentDate.getTimeInMillis() / 1000);

    ClimateLogService climateLogService = ClimateLogService.getInstance();
    ClimateLog climateLog = null;

    for (Parcel currentParcel : parcels) {
      latitude = currentParcel.getLatitude();
      longitude = currentParcel.getLongitude();

      /*
       * Obtiene un registro climatico correspondiente
       * a una ubicacion geografica en una fecha dada
       */
      climateLog = climateLogService.getClimateLog(latitude, longitude, unixTime);

      /*
       * La tbla del registro climatico tiene como no
       * nulo la clave foranea a parcela, por ende, antes
       * de persistir un registro climatico primero tiene
       * que tener establecida una parcela
       */
      climateLog.setParcel(currentParcel);

      entityManager.getTransaction().begin();
      climateLog = climateLogServiceBean.create(climateLog);
      entityManager.getTransaction().commit();

      assertNotNull(climateLog);

      System.out.println(climateLog);
    }

  }

  @Test
  public void methodTest() {

  }

  @AfterClass
  public static void postTest() {
    /*
     * Cierra las conexiones, cosa que hace
     * que se liberen los recursos utilizados
     * por el administrador de entidades y su fabrica
     */
    entityManager.close();
    entityMangerFactory.close();
  }

}
