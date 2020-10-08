/*
 * Prueba unitaria que corresponde a la tarea 82 del documento de tareas
 * y a la tarea 79 en GitHub.
 *
 * Para ejecutar esta prueba primero se necesita tener cargada la base
 * de datos subyacente con los datos contenidos en el archivo SQL
 * llamado stage-t82, y para hacer esta carga se debe ejecutar el
 * comando "ant stage-t82" (sin comillas)
 */

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ReportServiceBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GetMorePlantedCropTest {
  private static ReportServiceBean reportService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  private final String CROP_NOT_FOUND = "Cultivo no encontrado";

  @BeforeClass
  public static void preTest() {
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    reportService = new ReportServiceBean();
    reportService.setEntityManager(entityManager);

    System.out.println("***** Prueba unitaria de la tarea 82 del Product Backlog *****");
    System.out.println();
  }

  @Test
  public void getMorePlantedCropTest() {
    int idParcel = 1;

    System.out.println("* Prueba unitaria para el caso en el que sí existe el cultivo más plantado de una parcela dada.");
    System.out.println("La parcela de prueba es la que tiene ID = " + idParcel + ".");
    System.out.println();

    String expectedResult = "ALFALFA";
    String result = reportService.getMorePlantedCrop(idParcel);

    /*
     * El resultado esperado es "ALFALFA"
     */
    assertEquals(expectedResult, result);

    System.out.println("Cultivo más plantado en la parcela con ID = " + idParcel + ": " + result);
    System.out.println();
  }

  @Test
  public void getMoreThanOnePlantedCropTest() {
    int idParcel = 2;

    System.out.println("* Prueba unitaria para el caso en el que no existe el cultivo que más fue plantado de una parcela dada");
    System.out.println("porque dicha parcela tiene un historial en el cual tiene igual cantidad de registros de plantacion");
    System.out.println("para cada cultivo.");
    System.out.println("La parcela de prueba es la que tiene ID = " + idParcel + ".");
    System.out.println();

    String result = reportService.getMorePlantedCrop(idParcel);

    /*
     * El resultado esperado es "Cultivo no encontrado"
     */
    assertEquals(CROP_NOT_FOUND, result);

    System.out.println("Cultivo más plantado en la parcela con ID = " + idParcel + ": " + result);
    System.out.println();
  }

  @Test
  public void getZeroCropMostPlantedTest() {
    int idParcel = 3;

    System.out.println("* Prueba unitaria para el caso en el que no existe el cultivo que más fue plantado en una parcela dada");
    System.out.println("porque dicha parcela aún no tiene registros historicos de plantacion de cultivos.");
    System.out.println("La parcela de prueba es la que tiene ID = " + idParcel + ".");
    System.out.println();

    String result = reportService.getMorePlantedCrop(idParcel);

    /*
     * El resultado esperado es "Cultivo no encontrado"
     */
    assertEquals(CROP_NOT_FOUND, result);

    System.out.println("Cultivo más plantado en la parcela con ID = " + idParcel + ": " + result);
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

    System.out.println();
    System.out.println("***** Fin de la prueba unitaria de la tarea 82 del Product Backlog *****");
  }

}
