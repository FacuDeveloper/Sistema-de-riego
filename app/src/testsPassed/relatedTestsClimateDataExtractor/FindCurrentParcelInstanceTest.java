import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.ParcelInstanceServiceBean;
import stateless.ParcelServiceBean;

import model.Parcel;
import model.ParcelInstance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FindCurrentParcelInstanceTest {
  private static ParcelInstanceServiceBean parcelInstanceService;
  private static ParcelServiceBean parcelService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  // @Ignore
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    parcelInstanceService = new ParcelInstanceServiceBean();
    parcelInstanceService.setEntityManager(entityManager);

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Bloques de codigo fuente de prueba
   * unitaria para el metodo findCurrentParcelInstance()
   * de la clase ParcelInstanceServiceBean
   *
   * Para ejecutar estas pruebas unitarias es
   * necesario que la base de datos este cargada
   * con el contenido del archivo SQL llamado
   * parcelLog, el cual a su vez depende de
   * que los contenidos de los archivos SQL
   * parcels e insertsCultivo esten cargados
   * en la base de datos
   *
   * *** NOTA ***
   * El metodo findCurrentParcelInstance() de la clase
   * ParcelInstanceServiceBean es necesario
   * para el modulo de obtencion y almacenamiento
   * auotmatico de datos climaticos
   */
  @Test
  public void testPositiveCurrentParcelInstance() {
    System.out.println("Prueba unitaria positiva para la obtencion de un registro historico actual de una parcela");
    System.out.println();

    Parcel choosenParcel = parcelService.find(1);
    ParcelInstance parcelInstance = null;

    parcelInstance = parcelInstanceService.findCurrentParcelInstance(choosenParcel);

    if (parcelInstance == null) {
      System.out.println("Registro historico actual de una parcela, no existente");
    } else {
      System.out.println("Registro historico actual de una parcela, existente");
      System.out.println(parcelInstance);
    }

    System.out.println();
  }

  @Test
  public void testNegativeCurrentParcelInstance() {
    System.out.println("Prueba unitaria negativa para la obtencion de un registro historico actual de una parcela");
    System.out.println();

    Parcel choosenParcel = parcelService.find(2);
    ParcelInstance parcelInstance = null;

    parcelInstance = parcelInstanceService.findCurrentParcelInstance(choosenParcel);

    if (parcelInstance == null) {
      System.out.println("Registro historico actual de una parcela, no existente");
    } else {
      System.out.println("Registro historico actual de una parcela, existente");
      System.out.println(parcelInstance);
    }

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
