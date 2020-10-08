/*
 * Esta clase contiene distintos bloques de codigo
 * fuente de prueba unitaria para probar el bloque
 * de codigo fuente que calcula el coeficiente
 * del cultivo dado en funcion del cultivo, la
 * fecha de siembra y la fecha actual
 *
 * Dicho bloque de codigo es el metodo llamado
 * getKc() de la clase CultivoServiceBean
 *
 * Hay un total de cinco pruebas unitarias
 * con distintos cultivos cada una: Tomate,
 * cebada, banana, alfalfa y alcachofa
 */

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import stateless.CultivoService;
import stateless.CultivoServiceBean;

import model.Cultivo;

import java.util.Calendar;

import java.lang.Math;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class KcTest {
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

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * de un cultivo en particular haciendo uso
   * del cultivo TOMATE
   */
  @Test
  public void testTomatoKc() {
    Cultivo crop = cropService.find(30);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    // Fecha de siembra
    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 27);
    seedDate.set(Calendar.MONTH, 11);
    seedDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR));
    System.out.println("Número del día del año: " + seedDate.get(Calendar.DAY_OF_YEAR));
    System.out.println();

    // Fecha actual
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 5);
    currentDate.set(Calendar.MONTH, 0);
    currentDate.set(Calendar.YEAR, 2020);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println("Número del día del año: " + currentDate.get(Calendar.DAY_OF_YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + getDaysInitialStage(crop) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + getDaysMiddleStage(crop) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + getDaysFinalStage(crop) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (getDaysLife(seedDate, currentDate)) + " días aproximadamente");
    System.out.println("kc: " + cropService.getKc(crop, seedDate, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * de un cultivo en particular haciendo uso
   * del cultivo BANANA
   */
  @Ignore
  public void testBananaKc() {
    Cultivo crop = cropService.find(3);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    // Fecha de siembra
    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 9);
    seedDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR));
    System.out.println();

    // Fecha actual
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 25);
    currentDate.set(Calendar.MONTH, 9);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + getDaysInitialStage(crop) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + getDaysMiddleStage(crop) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + getDaysFinalStage(crop) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (getDaysLife(seedDate, currentDate)) + " días aproximadamente");
    System.out.println("kc: " + cropService.getKc(crop, seedDate, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * de un cultivo en particular haciendo uso
   * del cultivo CEBADA
   */
  @Ignore
  public void testBarleyKc() {
    Cultivo crop = cropService.find(4);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    // Fecha de siembra
    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 10);
    seedDate.set(Calendar.MONTH, 9);
    seedDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR));
    System.out.println();

    // Fecha actual
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 30);
    currentDate.set(Calendar.MONTH, 10);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + getDaysInitialStage(crop) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + getDaysMiddleStage(crop) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + getDaysFinalStage(crop) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (getDaysLife(seedDate, currentDate)) + " días aproximadamente");
    System.out.println("kc: " + cropService.getKc(crop, seedDate, currentDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * para el metodo que calcula el coeficiente
   * de un cultivo en particular haciendo uso
   * del cultivo ALFALFA
   */
  @Ignore
  public void testAlfalfaKc() {
    Cultivo crop = cropService.find(1);

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    // Fecha de siembra
    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 0);
    seedDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR));
    System.out.println();

    // Fecha actual
    Calendar currentDate = Calendar.getInstance();
    currentDate.set(Calendar.DAY_OF_MONTH, 29);
    currentDate.set(Calendar.MONTH, 11);
    currentDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha actual ***");
    System.out.println("Fecha actual: " + currentDate.get(Calendar.DAY_OF_MONTH) + "-" + (currentDate.get(Calendar.MONTH) + 1) + "-" + currentDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + getDaysInitialStage(crop) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + getDaysMiddleStage(crop) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + getDaysFinalStage(crop) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (getDaysLife(seedDate, currentDate)) + " días aproximadamente");
    System.out.println("kc: " + cropService.getKc(crop, seedDate, currentDate));
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
   * la prueba es la ALCACHOFA
   */
  @Ignore
  public void testArtichokeKc() {
    Cultivo crop = cropService.find(2);

    // Fecha actual
    Calendar currentDate = Calendar.getInstance();

    System.out.println("*** Cultivo de prueba ***");
    System.out.println(crop);

    // Fecha de siembra
    Calendar seedDate = Calendar.getInstance();
    seedDate.set(Calendar.DAY_OF_MONTH, 1);
    seedDate.set(Calendar.MONTH, 0);
    seedDate.set(Calendar.YEAR, 2019);

    System.out.println("*** Fecha de siembra ***");
    System.out.println("Fecha de siembra: " + seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR));
    System.out.println();

    System.out.println("*** Período de cada etapa ***");
    System.out.println("Etapa inicial: Entre 1 y " + getDaysInitialStage(crop) + " días");
    System.out.println("Etapa media: Entre " + (crop.getEtInicial() + crop.getEtDesarrollo() + 1)  + " y " + getDaysMiddleStage(crop) + " días");
    System.out.println("Etapa final: Entre " + (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + 1) + " y " + getDaysFinalStage(crop) + " días");
    System.out.println();

    System.out.println("KC correspondiente a la diferencia de dias entre la fecha actual y la fecha de siembra: " + (getDaysLife(seedDate, currentDate)) + " días aproximadamente");
    System.out.println("kc: " + cropService.getKc(crop, seedDate));
    System.out.println("*** Fin de prueba del cultivo " + crop.getNombre() + " ***");
    System.out.println();
  }

  /**
   * @param  seedDate
   * @param  currentDate
   * @return la cantidad de dias que hay entre la fecha
   * actual y la fecha de siembra
   */
  private int getDaysLife(Calendar seedDate, Calendar currentDate) {
    int daysLife = 0;

    if (seedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
      daysLife = (currentDate.get(Calendar.DAY_OF_YEAR) - seedDate.get(Calendar.DAY_OF_YEAR));
    }

    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) == 1) {
      daysLife = (currentDate.get(Calendar.DAY_OF_YEAR) + (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1));
    }

    /*
     * NOTE: Este calculo esta mal pero no tan mal
     */
    if (Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)) > 1) {
      daysLife = ((Math.abs(seedDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR))) * 365) - (365 - seedDate.get(Calendar.DAY_OF_YEAR) + 1) - (365 - currentDate.get(Calendar.DAY_OF_YEAR));
    }

    return daysLife;
  }

  /**
   * @param  crop
   * @return la cantidad de dias que dura la etapa inicial
   * del cultivo dado
   */
  private int getDaysInitialStage(Cultivo crop) {
    return (crop.getEtInicial() + crop.getEtDesarrollo());
  }

  /**
   * @param  crop
   * @return la cantidad de dias que dura la etapa media
   * del cultivo dado
   */
  private int getDaysMiddleStage(Cultivo crop) {
    return (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo());
  }

  /**
   * @param  crop
   * @return la cantidad de dias que dura la etapa final
   * del cultivo dado
   */
  private int getDaysFinalStage(Cultivo crop) {
    return (crop.getEtMedia() + crop.getEtInicial() + crop.getEtDesarrollo() + crop.getEtFinal());
  }

  @AfterClass
  public static void postTest() {
    // Cierra las conexiones
    entityManager.close();
    entityMangerFactory.close();
  }

}
