import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;

import java.util.Calendar;

import model.Parcel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClimateLogExistTest {
  private static ClimateLogServiceBean climateLogServiceBean;
  private static ParcelServiceBean parcelService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  // @Ignore
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    climateLogServiceBean = new ClimateLogServiceBean();
    climateLogServiceBean.setEntityManager(entityManager);

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Los siguientes bloques de codigo fuente de
   * prueba unitaria tienen la finalidad de probar
   * el correcto funcionamiento del bloque de codigo
   * fuente llamada exist de la clase ClimateLogServiceBean
   * el cual tiene la responsabilidad de determinar si
   * existe o no un registro historico climatico de una
   * parcela en una fecha dada
   *
   * *** NOTA ***
   * El metodo exist() de la clase ClimateLogServiceBean
   * es necesario para el modulo de obtencion y almacenamiento
   * de datos climaticos para cada parcela existente en el sistema
   */
  @Test
  public void testPositiveExistClimateLog() {
    System.out.println("Prueba unitaria de existencia positiva de un registro climatico dado una fecha y una parcela");
    System.out.println();

    Calendar date = Calendar.getInstance();
    date.set(Calendar.DAY_OF_MONTH, 1);
    date.set(Calendar.MONTH, 9);
    date.set(Calendar.YEAR, 2019);

    Parcel parcel = parcelService.find(1);

    if (climateLogServiceBean.exist(date, parcel)) {
      System.out.println("Registro climatico correspondiente a la fecha " + (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR))
      + " y a la parcela con ID = " + parcel.getId() + ", encontrado");
    } else {
      System.out.println("Registro climatico correspondiente a la fecha " + (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR))
      + " y a la parcela con ID = " + parcel.getId() + ", no encontrado");
    }

    System.out.println("*** Fin de prueba de existencia positiva ***");
    System.out.println();
  }

  @Test
  public void testNegativeExistClimateLog() {
    System.out.println("Prueba unitaria de existencia negativa de un registro climatico dado una fecha y una parcela");
    System.out.println();

    Calendar date = Calendar.getInstance();
    date.set(Calendar.DAY_OF_MONTH, 1);
    date.set(Calendar.MONTH, 10);
    date.set(Calendar.YEAR, 2019);

    Parcel parcel = parcelService.find(1);

    if (climateLogServiceBean.exist(date, parcel)) {
      System.out.println("Registro climatico correspondiente a la fecha " + (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR))
      + " y a la parcela con ID = " + parcel.getId() + ", encontrado");
    } else {
      System.out.println("Registro climatico correspondiente a la fecha " + (date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR))
      + " y a la parcela con ID = " + parcel.getId() + ", no encontrado");
    }

    System.out.println("*** Fin de prueba de existencia negativa ***");
    System.out.println();
  }

  @Test
  public void methodTest() {

  }

  @AfterClass
  // @Ignore
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
