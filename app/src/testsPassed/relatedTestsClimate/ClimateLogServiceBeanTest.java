import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;

import climate.ClimateLogService;

import model.ClimateLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClimateLogServiceBeanTest {
  private static ClimateLogServiceBean climateLogServiceBean;
  private static ParcelServiceBean parcelServiceBean;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    climateLogServiceBean = new ClimateLogServiceBean();
    climateLogServiceBean.setEntityManager(entityManager);

    parcelServiceBean = new ParcelServiceBean();
    parcelServiceBean.setEntityManager(entityManager);
  }

  /*
   * Bloque de codigo fuente para la prueba
   * unitaria del metodo de creacion de la
   * clase de servicio de la base de datos
   * ClimateLogServiceBean
   */
  @Test
  public void testCreate() {
    ClimateLogService climateLogService = ClimateLogService.getInstance();

    /*
     * Estos datos son para la obtencion de
     * los datos climaticos de la ciudad Puerto
     * Madryn para la fecha 31/10/19
     */
    double latitude = -42.7683337;
    double longitude = -65.060855;

    /*
     * La API del clima Dark Sky brinda los datos climaticos
     * de la fecha anterior a la que se le pasa por parametros
     * con lo cual para obtener los datos climaticos de la fecha
     * 31/10/19 se le tiene que pasar como parametro la fecha
     * 1/11/19 en formato UNIX TIMESTAMP, la cual en dicho formato
     * es 1572566400
     */
    long dateUnixTimeStamp = 1572566400;

    ClimateLog climateLog = climateLogService.getClimateLog(latitude, longitude, dateUnixTimeStamp);
    climateLog.setParcel(parcelServiceBean.find(1));

    entityManager.getTransaction().begin();
    climateLogServiceBean.create(climateLog);
    entityManager.getTransaction().commit();
  }

  @Test
  public void methodTest() {

  }

  @AfterClass
  public static void postTest() {
    // Cierra las conexiones
    entityManager.close();
    entityMangerFactory.close();
  }

}
