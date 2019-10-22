import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import java.util.Calendar;

import stateless.EtLogServiceBean;

import model.EtLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EtLogServiceBeanTest {
  private static EtLogServiceBean etLogService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  // private static List<EtLog> etLogs;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    etLogService = new EtLogServiceBean();
    etLogService.setEntityManager(entityManager);
  }

  /*
   * Bloque de codigo fuente de prueba
   * unitaria para el metodo create()
   * de la clase EtLogServiceBean
   */
  @Test
  public void testCreate() {
    System.out.println("Prueba unitaria de creación de registro de evapotranspiración");
    System.out.println();

    EtLog etLogLettuce = new EtLog();
    etLogLettuce.setDate(Calendar.getInstance());
    etLogLettuce.setEto(5.0);
    etLogLettuce.setCropCoefficient(2.0);
    etLogLettuce.setEtc(etLogLettuce.getCropCoefficient() * etLogLettuce.getEto());
    etLogLettuce.setIdCrop(1);
    etLogLettuce.setCropName("Lechuga");
    etLogLettuce.setCropStage("Inicial");
    etLogLettuce.setWaterRemaining(4.0);

    EtLog etLogOrange = new EtLog();
    etLogOrange.setDate(Calendar.getInstance());
    etLogOrange.setEto(1.5);
    etLogOrange.setCropCoefficient(2.3);
    etLogOrange.setEtc(etLogOrange.getCropCoefficient() * etLogOrange.getEto());
    etLogOrange.setIdCrop(2);
    etLogOrange.setCropName("Naranja");
    etLogOrange.setCropStage("Media");
    etLogOrange.setWaterRemaining(1.5);

    EtLog etLogPeach = new EtLog();
    etLogPeach.setDate(Calendar.getInstance());
    etLogPeach.setEto(5.0);
    etLogPeach.setCropCoefficient(1.2);
    etLogPeach.setEtc(etLogPeach.getCropCoefficient() * etLogPeach.getEto());
    etLogPeach.setIdCrop(3);
    etLogPeach.setCropName("Durazno");
    etLogPeach.setCropStage("Final");
    etLogPeach.setWaterRemaining(3.2);

    entityManager.getTransaction().begin();
    assertNotNull(etLogService.create(etLogLettuce));
    assertNotNull(etLogService.create(etLogOrange));
    assertNotNull(etLogService.create(etLogPeach));
    entityManager.getTransaction().commit();

    /*
     * Si los objetos creados en esta prueba unitaria
     * fueron almacenados en la base de datos de forma
     * satisfactoria, el metodo findAll() de la clase
     * EtLogServiceBean tiene que retornar la referencia
     * a una coleccion no vacia de referencias a objetos
     * de tipo etLog
     */
    assertFalse(etLogService.findAll().isEmpty());

    System.out.println("Registros de evapotranspiración creados");
    System.out.println();

    for (EtLog currentEtLog : etLogService.findAll()) {
      System.out.println(currentEtLog);
      System.out.println();
    }

    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo find() de la clase
   * EtLogServiceBean
   */
  @Test
  public void testFind() {
    System.out.println("Prueba unitaria de recuperación de registro de evapotranspiración");
    System.out.println();

    EtLog etLogBanana = new EtLog();
    etLogBanana.setDate(Calendar.getInstance());
    etLogBanana.setEto(3.7);
    etLogBanana.setCropCoefficient(3.2);
    etLogBanana.setEtc(etLogBanana.getCropCoefficient() * etLogBanana.getEto());
    etLogBanana.setIdCrop(30);
    etLogBanana.setCropName("Banana");
    etLogBanana.setCropStage("Media");
    etLogBanana.setWaterRemaining(2.6);

    entityManager.getTransaction().begin();
    etLogBanana = etLogService.create(etLogBanana);
    entityManager.getTransaction().commit();

    /*
     * Si se recupera de la base de datos un registro
     * de evapotranspiración de forma satisfactoria
     * esta prueba tiene que ser exitosa porque
     * en caso de recuperar de la base de datos
     * un registro de evapotranspiración el
     * metodo find() de la clase EtLogServiceBean
     * tiene que retornar la referencia a un objeto
     * de tipo etLog, con lo cual no existe el valor
     * nulo en este caso
     */
    assertNotNull(etLogService.find(etLogBanana.getId()));

    System.out.println(etLogService.find(etLogBanana.getId()));
    System.out.println();
  }

  @Test
  public void methodTest() {

  }

}
