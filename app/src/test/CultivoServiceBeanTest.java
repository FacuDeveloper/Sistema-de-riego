/*
 * Esta clase contiene distintos bloques de codigo
 * fuente de prueba unitaria para probar el bloque
 * de codigo fuente que calcula el coeficiente
 * del cultivo dado en funcion del cultivo, la
 * fecha de siembra y la fecha actual
 *
 * Hay un total de cinco pruebas unitarias
 * con distintos cultivos cada una: Tomate,
 * cebada, banana, alfalfa y alcachofa
 */

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import stateless.CultivoService;
import stateless.CultivoServiceBean;

import model.Cultivo;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CultivoServiceBeanTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static CultivoService cropService;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    cropService = new CultivoServiceBean();
    cropService.setEntityManager(entityManager);

    System.out.println("Prueba unitaria del método del cálculo del coeficiente del cultivo (kc) dada una fecha de siembre y una fecha actual");
    System.out.println();
  }

  @Test
  public void testTomatoKc() {
    Cultivo crop = cropService.find(30);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    Calendar seedTime = Calendar.getInstance();
    seedTime.set(Calendar.DAY_OF_MONTH, 1);
    seedTime.set(Calendar.MONTH, 9);
    seedTime.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedTime.get(Calendar.DAY_OF_MONTH) + "-" + (seedTime.get(Calendar.MONTH) + 1) + "-" + seedTime.get(Calendar.YEAR));
    System.out.println();

    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 25);
    currentDate.set(Calendar.MONTH, 9);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + (crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal()) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (currentDate.get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR)) + " días");
    System.out.println("kc: " + cropService.getKc(crop, seedTime, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  @Test
  public void testBananaKc() {
    Cultivo crop = cropService.find(3);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    Calendar seedTime = Calendar.getInstance();
		seedTime.set(Calendar.DAY_OF_MONTH, 1);
		seedTime.set(Calendar.MONTH, 9);
		seedTime.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedTime.get(Calendar.DAY_OF_MONTH) + "-" + (seedTime.get(Calendar.MONTH) + 1) + "-" + seedTime.get(Calendar.YEAR));
    System.out.println();

    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 25);
    currentDate.set(Calendar.MONTH, 9);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + (crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal()) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (currentDate.get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR)) + " días");
    System.out.println("kc: " + cropService.getKc(crop, seedTime, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * de un cultivo en particular haciendo uso
   * del cultivo cebada
   */
  @Test
  public void testBarleyKc() {
    Cultivo crop = cropService.find(4);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    Calendar seedTime = Calendar.getInstance();
    seedTime.set(Calendar.DAY_OF_MONTH, 10);
    seedTime.set(Calendar.MONTH, 9);
    seedTime.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedTime.get(Calendar.DAY_OF_MONTH) + "-" + (seedTime.get(Calendar.MONTH) + 1) + "-" + seedTime.get(Calendar.YEAR));
    System.out.println();

    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 30);
    currentDate.set(Calendar.MONTH, 10);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + (crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal()) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (currentDate.get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR)) + " días");
    System.out.println("kc: " + cropService.getKc(crop, seedTime, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  @Test
  public void testAlfalfaKc() {
    Cultivo crop = cropService.find(1);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    Calendar seedTime = Calendar.getInstance();
    seedTime.set(Calendar.DAY_OF_MONTH, 1);
    seedTime.set(Calendar.MONTH, 0);
    seedTime.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedTime.get(Calendar.DAY_OF_MONTH) + "-" + (seedTime.get(Calendar.MONTH) + 1) + "-" + seedTime.get(Calendar.YEAR));
    System.out.println();

    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 27);
    currentDate.set(Calendar.MONTH, 11);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + (crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal()) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (currentDate.get(Calendar.DAY_OF_YEAR) - seedTime.get(Calendar.DAY_OF_YEAR)) + " días");
    System.out.println("kc: " + cropService.getKc(crop, seedTime, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * del cultivo haciendo uso de un cultivo y
   * una fecha de siembra
   *
   * La fecha actual la utiliza el metodo mencionado
   * de forma automatica y predeterminada
   *
   * En este caso el cultivo que se utiliza para
   * la prueba es la alcachofa
   */
  @Ignore
  public void testArtichokeKc() {
    Cultivo crop = cropService.find(2);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    Calendar seedTime = Calendar.getInstance();
    seedTime.set(Calendar.DAY_OF_MONTH, 1);
    seedTime.set(Calendar.MONTH, 0);
    seedTime.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedTime.get(Calendar.DAY_OF_MONTH) + "-" + (seedTime.get(Calendar.MONTH) + 1) + "-" + seedTime.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + (crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo()) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal()) + " días");
    System.out.println();

    System.out.println("kc: " + cropService.getKc(crop, seedTime));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }


}
