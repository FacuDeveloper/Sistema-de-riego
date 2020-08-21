import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import stateless.InstanciaParcelaService;
import stateless.InstanciaParcelaServiceBean;
import stateless.ParcelServiceBean;

import model.Parcel;

public class FindRecentFinishedTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static InstanciaParcelaService service;
  private static ParcelServiceBean parcelService;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    service = new InstanciaParcelaServiceBean();
    service.setEntityManager(entityManager);

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Bloque de codigo fuente de prueba unitaria que
   * tiene como objetivo probar el bloque de codigo
   * fuente que recupera la instancia de parcela
   * (registro historico de parcela actual) mas reciente
   * que esta en el estado "Finalizado"
   */
  @Test
  public void testFindRecentFinished() {
    Parcel givenParcel = parcelService.find(1);
    System.out.println("Resultado");
    System.out.println(service.findRecentFinished(givenParcel));

    assertNotNull(givenParcel);
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
