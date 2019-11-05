import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import model.SolarRadiation;
import model.Latitude;
import model.Month;

import stateless.SolarRadiationServiceBean;
import stateless.LatitudeServiceBean;
import stateless.MonthServiceBean;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.NoResultException;

public class SolarRadiationServiceBeanTest {
  private static SolarRadiationServiceBean solarService;
  private static LatitudeServiceBean latitudeService;
  private static MonthServiceBean monthService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static List<Latitude> latitudes;
  private static List<Month> months;

  // @BeforeClass
  @Ignore
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    solarService = new SolarRadiationServiceBean();
    solarService.setEntityManager(entityManager);

    latitudeService = new LatitudeServiceBean();
    latitudeService.setEntityManager(entityManager);

    monthService = new MonthServiceBean();
    monthService.setEntityManager(entityManager);

    latitudes = new ArrayList<>();
    months = new ArrayList<>();
  }

  @Ignore
  public void testPositiveFind() {
    int numberLatitude = -70;
    int numberMonth = 5;

    System.out.println("Prueba unitaria de recuperación satisfactoria de la radiación solar extraterrestre");
    System.out.println("Recuperación de la radiación solar de la latitud = " + numberLatitude + " en el mes número " + numberMonth);
    System.out.println();

    Latitude latitude = latitudeService.find(numberLatitude);
    assertNotNull(latitude);

    Month month = monthService.find(numberMonth);
    assertNotNull(month);

    SolarRadiation solarRadiation = null;

    try {
      solarRadiation = solarService.find(month, latitude);
    } catch(NoResultException ex) {
    }

    assertNotNull(solarRadiation);

    System.out.println("Radiacion solar");
    System.out.println("Latitud: " + solarRadiation.getLatitude().getLatitude());
    System.out.println("Número del mes: " + month.getId());
    System.out.println("Valor de la radiacion: " + solarRadiation.getRadiation());
    System.out.println("******************************************");
    System.out.println();
  }

  @Ignore
  public void testNegativeFind() {
    int numberLatitude = 7;
    int numberMonth = -40;

    System.out.println("Prueba de recuperación insatisfactoria de la radiación solar extraterrestre");
    System.out.println("Recuperación de radiación solar de la latitud = " + numberLatitude + " en el mes número: " + numberMonth);
    System.out.println();

    Latitude latitude = new Latitude();
    latitude.setLatitude(numberLatitude);

    Month month = new Month();

    /*
     * El identificador del mes tambien funciona
     * como el numero del mes
     */
    // month.setId(numberMonth);

    entityManager.getTransaction().begin();
    latitude = latitudeService.create(latitude);
    month = monthService.create(month);
    entityManager.getTransaction().commit();

    latitudes.add(latitude);
    months.add(month);

    SolarRadiation solarRadiation = null;

    try {
      solarRadiation = solarService.find(month, latitude);
    } catch(NoResultException ex) {
      System.out.println("No existe un valor de radiación solar extraterrestre para el mes y la latitud solicitados");
    }

    System.out.println("******************************************");
  }

  /*
   * Codigo fuente para cuando la latitud de una parcela
   * sea impar
   *
   * En el caso de que la latitud de una parcela
   * sea mayor a 0 se obtendra de la base de
   * datos la radiacion solar correspondiente
   * a la latitud 0 en el mes dado del año
   *
   * En el caso de que la latitud de una parcela
   * sea menor a -70 se obtendra de la base de
   * datos la radiacion solar correspondiente
   * a la latitud -70 en el mes dado del año
   *
   * En el caso de que la latitud de una parcela
   * sea impar se calcula el promedio de las radiaciones
   * de las latitudes opuestas a la impar en el mes
   * dado del año
   *
   * En el caso de que la latitud de una parcela
   * sea par se obtiene la radiacion solar extraterrestre
   * correspondiente a la latitud y el mes dados
   *
   */
  @Ignore
  public void testSolarRadiation() {
    System.out.println("Prueba unitaria de recuperación de radiación solar (Ra) con latitud impar, > 0 y < -70");

    int numberLatitude = -66;
    Latitude previousLatitude = null;
    Latitude nextLatitude = null;
    int numberMonth = 1;
    Month month = monthService.find(numberMonth);
    double solarRadiation = 0.0;

    /*
     * Si la latitud en el mes solicitado es mayor
     * a 0, se recupera la radiacion solar extraterrestre en
     * el mes dado de la latitud 0
     *
     */
    if (numberLatitude > 0) {
      solarRadiation = solarService.find(month, latitudeService.find(0)).getRadiation();
    }

    /*
     * Si la latitud en el mes solicitado es
     * menor a -70, se recupera la radiacion
     * solar extraterrestre en el mes dado de la latitud -70 (Hemisferio sur)
     */
    if (numberLatitude < -70) {
      solarRadiation = solarService.find(month, latitudeService.find(-70)).getRadiation();
    }

    /*
     * Si la latitud en el mes solicitado esta
     * entre 0 y -70 incluidos y es impar, se recuperan
     * las radiaciones solares extraterrestres aledañas
     * a la latitud impar en el mes solicitado, y se
     * calcula el promedio de estas dos radiaciones
     * solares extraterrestres
     */
    if ((numberLatitude >= -70) && (numberLatitude <= 0) && ((numberLatitude % 2) != 0)) {
      previousLatitude = latitudeService.find(numberLatitude - 1);
      nextLatitude = latitudeService.find(numberLatitude + 1);
      solarRadiation = ((solarService.find(month, previousLatitude).getRadiation() + solarService.find(month, nextLatitude).getRadiation()) / 2.0);
      System.out.println("Radiación solar de la latitud: " + previousLatitude.getLatitude() + " es "+ solarService.find(month, previousLatitude).getRadiation());
      System.out.println("Radiación solar de la laitud: " + nextLatitude.getLatitude() + " es " + solarService.find(month, nextLatitude).getRadiation());
    }

    /*
     * Si la latitud en el mes solicitado esta
     * entre 0 y -70 incluidos y es par, se recupera
     * la radiacion solar extraterrestre
     * correspondiente a la latitud y el mes solicitados
     */
    if ((numberLatitude >= -70) && (numberLatitude <= 0) && ((numberLatitude % 2) == 0)) {
      solarRadiation = solarService.find(month, latitudeService.find(numberLatitude)).getRadiation();
    }

    System.out.println("La radiación solar correspondiente a la latitud " + numberLatitude + " y al mes número " + numberMonth + " es: " + solarRadiation);
    System.out.println("******************************************");
    System.out.println();
  }

  @Test
  public void test() {

  }

  // @AfterClass
  @Ignore
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
