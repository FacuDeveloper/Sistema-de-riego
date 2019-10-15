import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import model.MaximumInsolation;
import model.Latitude;
import model.Month;

import stateless.MaximumInsolationServiceBean;
import stateless.LatitudeServiceBean;
// import stateless.DayFifteenMonthServiceBean;
import stateless.MonthServiceBean;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.NoResultException;

public class MaximumInsolationServiceBeanTest {
  private static MaximumInsolationServiceBean insolationService;
  private static LatitudeServiceBean latitudeService;
  private static MonthServiceBean monthService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static List<Latitude> latitudes;
  private static List<Month> months;

  @BeforeClass
  // @Ignore
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    insolationService = new MaximumInsolationServiceBean();
    insolationService.setEntityManager(entityManager);

    latitudeService = new LatitudeServiceBean();
    latitudeService.setEntityManager(entityManager);

    monthService = new MonthServiceBean();
    monthService.setEntityManager(entityManager);

    latitudes = new ArrayList<>();
    months = new ArrayList<>();
  }

  @Test
  public void testPositiveFind() {
    int numberLatitude = -70;
    int numberMonth = 1;

    System.out.println("Prueba unitaria de recuperación satisfactoria de la duración máxima de insolación (N)");
    System.out.println("Recuperación de la máxima insolación solar de la latitud " + numberLatitude + " en el mes número " + numberMonth);
    System.out.println();

    Latitude latitude = latitudeService.getLatitude(numberLatitude);
    assertNotNull(latitude);

    Month month = monthService.find(numberMonth);
    assertNotNull(month);

    MaximumInsolation maximumInsolation = null;

    try {
      maximumInsolation = insolationService.findMaximumInsolation(month, latitude);
    } catch(NoResultException ex) {
    }

    assertNotNull(maximumInsolation);

    System.out.println("Máxima insolación");
    System.out.println("Latitud: " + maximumInsolation.getLatitude().getLatitude());
    // System.out.println("Número del dia del año: " + maximumInsolation.getDay().getDayNumber());
    System.out.println("Número del mes del año: " + month.getId());
    System.out.println("Valor de la máxima insolación (N): " + maximumInsolation.getMaximumInsolation());
    System.out.println("*********************************************");
    System.out.println();
  }

  @Ignore
  public void testNegativeFind() {
    int numberLatitude = 7;
    int numberMonth = -40;

    System.out.println("Prueba de recuperación insatisfactoria de la duración máxima de insolación (N)");
    System.out.println("Recuperación de la máxima insolación de la latitud " + numberLatitude + " en el mes número " + numberMonth);
    System.out.println();

    Latitude latitude = new Latitude();
    latitude.setLatitude(numberLatitude);

    Month month = new Month();
    month.setId(numberMonth);

    entityManager.getTransaction().begin();
    latitude = latitudeService.create(latitude);
    month = monthService.create(month);
    entityManager.getTransaction().commit();

    latitudes.add(latitude);
    months.add(month);

    MaximumInsolation maximumInsolation = null;

    try {
      maximumInsolation = insolationService.findMaximumInsolation(month, latitude);
    } catch(NoResultException ex) {
      System.out.println("No existe un valor de máxima insolación para número del mes y la latitud solicitados");
    }

    System.out.println("*********************************************");
    System.out.println();
  }

  /*
   * Codigo fuente para cuando la latitud de una parcela
   * seam impar
   *
   * En el caso de que la latitud de una parcela
   * sea impar se calcula el promedio de las maximas
   * insolaciones de las latitudes opuestas a la
   * latitud impar en el mes dado del año
   *
   * En el caso de que la latitud de una parcela
   * sea mayor a 0 se obtendra de la base de
   * datos la maxima insolación correspondiente
   * a la latitud 0 en el mes dado del año
   *
   * En el caso de que la latitud de una parcela
   * sea menor a -70 se obtendra de la base de
   * datos la maxima insolación correspondiente
   * a la latitud -70 en el mes dado del año
   */
  @Ignore
  public void testMaximumInsolation() {
    System.out.println("Prueba unitaria de recuperación de la insolación máxima (N) con latitud impar, > 0 y < -70");
    System.out.println();

    int numberLatitude = -66;
    Latitude previousLatitude = null;
    Latitude nextLatitude = null;
    int numberMonth = 1;
    Month month = monthService.find(numberMonth);
    double maximumInsolation = 0.0;

    /*
     * Si la latitud en el mes solicitado es mayor
     * a 0, se recupera la máxima insolación en el
     * mes dado de la latitud 0
     */
    if (numberLatitude > 0) {
      maximumInsolation = insolationService.findMaximumInsolation(month, latitudeService.getLatitude(0)).getMaximumInsolation();
    }

    /*
     * Si la latitud en el mes solicitado es
     * menor a -70, se recuperara la máxima
     * insolación en el mes dado de la latitud -70 (Hemisferio sur)
     */
    if (numberLatitude < -70) {
      maximumInsolation = insolationService.findMaximumInsolation(month, latitudeService.getLatitude(-70)).getMaximumInsolation();
    }

    /*
     * Si la latitud en el mes solicitado esta
     * entre 0 y -70 y es impar, se recuperan
     * las máximas insolaciones aledañas a la
     * latitud impar en el mes solicitado, y se
     * calcula el promedio de estas dos máximas
     * insolaciones
     */
    if ((numberLatitude >= -70) && (numberLatitude <= 0) && ((numberLatitude % 2) != 0) ) {
      previousLatitude = latitudeService.getLatitude(numberLatitude - 1);
      nextLatitude = latitudeService.getLatitude(numberLatitude + 1);
      maximumInsolation = ((insolationService.findMaximumInsolation(month, previousLatitude).getMaximumInsolation() + insolationService.findMaximumInsolation(month, nextLatitude).getMaximumInsolation()) / 2.0);
      System.out.println("La insolación máxima de la latitud " + previousLatitude.getLatitude() + " en el mes número " + numberMonth + " es " + insolationService.findMaximumInsolation(month, previousLatitude).getMaximumInsolation());
      System.out.println("La insolación máxima de la latitud " + nextLatitude.getLatitude() + " en el mes número " + numberMonth + " es " + insolationService.findMaximumInsolation(month, nextLatitude).getMaximumInsolation());
    }

    /*
     * Si la latitud en el mes solicitado esta
     * entre 0 y -70 y es par, se recupera la
     * máxima insolación correspondiente a la
     * latitud y el mes solicitados
     */
    if ((numberLatitude >= -70) && (numberLatitude <= 0) && ((numberLatitude % 2) == 0) ) {
      maximumInsolation = insolationService.findMaximumInsolation(month, latitudeService.getLatitude(numberLatitude)).getMaximumInsolation();
    }

    System.out.println("La insolación máxima correspondiente a la latitud " + numberLatitude + " y al mes número " + numberMonth + " es: " + maximumInsolation);
    System.out.println("*********************************************");
    System.out.println();
  }

  @Test
  public void test() {

  }

  @AfterClass
  // @Ignore
  public static void postTest() {
    /*
     * Elimina todos los datos de la base de datos
     * que que hayan sido ingresados y no eliminados de la misma
     * en los metodos preTest() y de @Test
     */
    entityManager.getTransaction().begin();

    for (Latitude currentLatitude : latitudes) {
      entityManager.remove(currentLatitude);
    }

    for (Month currentMonth : months) {
      entityManager.remove(currentMonth);
    }

    entityManager.getTransaction().commit();

    /*
     * Cierra las conexiones, cosa que hace
     * que se liberen los recursos utilizados
     * por el administrador de entidades y su fabrica
     */
    entityManager.close();
    entityMangerFactory.close();
  }

}
