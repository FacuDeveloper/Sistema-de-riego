import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import java.util.Calendar;

import util.UtilDate;

import stateless.CultivoService;
import stateless.CultivoServiceBean;
import stateless.InstanceParcelStatusServiceBean;

import model.InstanceParcelStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InstanceParcelStatusTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static CultivoService cultivoService;
  private static InstanceParcelStatusServiceBean statusService;

  @BeforeClass
  public static void preTest() {
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    cultivoService = new CultivoServiceBean();
    cultivoService.setEntityManager(entityManager);

    statusService = new InstanceParcelStatusServiceBean();
    statusService.setEntityManager(entityManager);
  }

  @Test
  public void testStatusFinished() {
    /*
     * Para que el metodo getStatus() de la clase InstanceParcelStatusServiceBean
     * retorne el estado "Finalizado", la fecha de cosecha de una instancia de
     * parcela dada tiene que estar antes de la fecha actual del sistema
     */
    System.out.println("*** Prueba unitaria para obtener el estado 'Finalizado' ***");
    System.out.println();

    System.out.println("Fecha actual del sistema: " + UtilDate.formatDate(Calendar.getInstance()));

    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 7);
    seedDate.set(Calendar.YEAR, 2009);
    System.out.println("Fecha de siembra: " + UtilDate.formatDate(seedDate));

    Calendar harvestDate = cultivoService.calculateHarvestDate(seedDate, cultivoService.find(1));
    System.out.println("Fecha de cosecha: " + UtilDate.formatDate(harvestDate));

    InstanceParcelStatus finishedStatus = statusService.getStatus(seedDate, harvestDate);

    System.out.println("Estado: " + finishedStatus.getName());

    String expectedResult = "Finalizado";
    assertEquals(expectedResult, finishedStatus.getName());
    System.out.println();
  }

  @Test
  public void testStatusInDevelopment() {
    /*
     * Para que el metodo getStatus() de la clase InstanceParcelStatusServiceBean
     * retorne el estado "En desarrollo", la fecha actual del sistema tiene que
     * estar entre las fechas de siembra y de cosecha de una instancia de parcela
     * dada
     */
    System.out.println("*** Prueba unitaria para obtener el estado 'En desarrollo' ***");
    System.out.println();

    System.out.println("Fecha actual del sistema: " + UtilDate.formatDate(Calendar.getInstance()));

    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 7);
    seedDate.set(Calendar.YEAR, 2020);
    System.out.println("Fecha de siembra: " + UtilDate.formatDate(seedDate));

    Calendar harvestDate = cultivoService.calculateHarvestDate(seedDate, cultivoService.find(1));
    System.out.println("Fecha de cosecha: " + UtilDate.formatDate(harvestDate));

    InstanceParcelStatus inDevelopmentStatus = statusService.getStatus(seedDate, harvestDate);

    System.out.println("Estado: " + inDevelopmentStatus.getName());

    String expectedResult = "En desarrollo";
    assertEquals(expectedResult, inDevelopmentStatus.getName());
  }

  @Test
  public void testStatusOnHold() {
    /*
     * Para que el metodo getStatus() de la clase InstanceParcelStatusServiceBean
     * retorne el estado "En espera", la fecha de siembra de una instancia de parcela
     * dada tiene qu estar despues de la fecha actual del sistema
     */
    System.out.println("*** Prueba unitaria para obtener el estado 'En espera' ***");
    System.out.println();

    System.out.println("Fecha actual del sistema: " + UtilDate.formatDate(Calendar.getInstance()));

    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 7);
    seedDate.set(Calendar.YEAR, 2022);
    System.out.println("Fecha de siembra: " + UtilDate.formatDate(seedDate));

    Calendar harvestDate = cultivoService.calculateHarvestDate(seedDate, cultivoService.find(1));
    System.out.println("Fecha de cosecha: " + UtilDate.formatDate(harvestDate));

    InstanceParcelStatus onHoldStatus = statusService.getStatus(seedDate, harvestDate);

    System.out.println("Estado: " + onHoldStatus.getName());

    String expectedResult = "En espera";
    assertEquals(expectedResult, onHoldStatus.getName());
    System.out.println();
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
