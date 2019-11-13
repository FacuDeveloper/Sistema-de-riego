import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

import stateless.IrrigationLogServiceBean;
import stateless.ParcelServiceBean;

import java.util.Calendar;

import model.Parcel;
import model.ClimateLog;

import util.FormatDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IrrigationLogExistTest {
  private static IrrigationLogServiceBean irrigationLogService;
  private static ParcelServiceBean parcelService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    irrigationLogService = new IrrigationLogServiceBean();
    irrigationLogService.setEntityManager(entityManager);

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
   * fuente llamado exist(Calendar givenDate, Parcel givenParcel)
   * de la clase IrrigationLogServiceBean, el cual tiene la
   * responsabilidad de retornar verdadero si existe un
   * regostro historico de riego asociado a la parcela y
   * a la fecha dada, y falso en el caso de que no
   * exista tal registro historico de riego
   *
   * *** NOTA ***
   * El metodo exist(Calendar givenDate, Parcel givenParcel)
   * de la clase IrrigationLogServiceBean es necesario para
   * el modulo de creacion y almacenamiento de registros
   * historicos de riego para cada parcela existente en
   * el sistema
   */
  @Test
  public void testPositiveExistIrrigationLog() {
    System.out.println("Prueba unitaria positiva de existencia de un registro de riego dado una fecha y una parcela");
    System.out.println();

    Parcel choosenparcel = parcelService.find(1);
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) - 1);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + FormatDate.formatDate(currentDate));
    System.out.println("Número del día del año: " + currentDate.get(Calendar.DAY_OF_YEAR));
    System.out.println();

    System.out.println("El registro historico de riego existe: " + (irrigationLogService.exist(currentDate, choosenparcel) ? "Sí" : "No"));

    System.out.println("*** Fin de prueba positiva de existencia ***");
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
