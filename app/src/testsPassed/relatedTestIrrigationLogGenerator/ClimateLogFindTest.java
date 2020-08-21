import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ClimateLogServiceBean;
import stateless.ParcelServiceBean;

import java.util.Calendar;

import model.Parcel;
import model.ClimateLog;

import util.FormatDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClimateLogFindTest {
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
   * NOTA: Si se hace mas de una prueba, modificar
   * "El siguiente bloque de codigo fuente ... tiene" por
   * "Los siguientes bloques de codigo fuente ... tienen"
   *
   * El siguiente bloque de codigo fuente de
   * prueba unitaria tiene la finalidad de probar
   * el correcto funcionamiento del bloque de codigo
   * fuente llamado find(Calendar givenDate, Parcel givenParcel)
   * de la clase ClimateLogServiceBean, el cual tiene la
   * responsabilidad de retornar el registro historico
   * climatico, si existe, de la parcela y la fecha dadas
   * como parametros
   *
   * *** NOTA ***
   * El metodo find(Calendar givenDate, Parcel givenParcel)
   * de la clase ClimateLogServiceBean es necesario para
   * el modulo de creacion y almacenamiento de registros
   * historicos de riego para cada parcela existente en
   * el sistema
   */
  @Test
  public void testPositiveFindClimateLog() {
    System.out.println("Prueba unitaria positiva de busqueda de un registro climatico dado una fecha y una parcela");
    System.out.println();

    Parcel choosenparcel = parcelService.find(1);

    /*
     * Supongamos que la fecha actual es 20/10/19
     */
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 1);
    currentDate.set(Calendar.MONTH, 0);
    currentDate.set(Calendar.YEAR, 2020);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + FormatDate.formatDate(currentDate));
    System.out.println("Número del día del año: " + currentDate.get(Calendar.DAY_OF_YEAR));
    System.out.println();

    Calendar yesterdayDate = Calendar.getInstance();

    /*
     * Si la fecha actual es el dia numero 1 del año,
     * es decir, el primer dia de Enero, la fecha anterior
     * a la fecha actual, en este caso, es el dia numero 365
     * del año, es decir, el ultimo dia de Diciembre
     *
     * Si la fecha actual no es el dia numero 1 del año, la
     * fecha anterior a la fecha actual es el dia anterior
     * al dia de la fecha actual
     */
    if (currentDate.get(Calendar.DAY_OF_YEAR) == 1) {
      yesterdayDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR) - 1);
      yesterdayDate.set(Calendar.DAY_OF_YEAR, 365);
    } else {
      yesterdayDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
      yesterdayDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) - 1);
    }

    System.out.println("*** Fecha del día de ayer ***");
    System.out.println("Fecha del día de ayer: " + FormatDate.formatDate(yesterdayDate));
    System.out.println("Número del día del año: " + yesterdayDate.get(Calendar.DAY_OF_YEAR));
    System.out.println();

    ClimateLog yesterdayClimateLog = climateLogServiceBean.find(yesterdayDate, choosenparcel);
    assertNotNull(yesterdayClimateLog);

    System.out.println("Registro climático del día de ayer");
    System.out.println(yesterdayClimateLog);
    System.out.println();

    System.out.println("*** Fin de prueba unitaria positiva de busqueda ***");
    System.out.println();
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
