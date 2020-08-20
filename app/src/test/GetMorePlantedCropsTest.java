import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Ignore;

import stateless.StatisticalReportServiceBean;
// import stateless.InstanciaParcelaServiceBean;
// import stateless.InstanceParcelStatusServiceBean;
// import stateless.CultivoService;
// import stateless.CultivoServiceBean;
import stateless.ParcelServiceBean;

import java.util.Collection;
import java.util.Iterator;

import util.UtilDate;

import model.Parcel;

// import model.InstanciaParcela;
// import model.Cultivo;
// import model.InstanceParcelStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GetMorePlantedCropsTest {
  private static StatisticalReportServiceBean statisticalReportService;
  private static ParcelServiceBean parcelService;
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;

  @BeforeClass
  public static void preTest() {
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    statisticalReportService = new StatisticalReportServiceBean();
    statisticalReportService.setEntityManager(entityManager);

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  @Ignore
  public void getMorePlantedCropsTest() {
    Parcel choosenParcel = parcelService.find(1);

    Collection<String> result = statisticalReportService.getMorePlantedCrops(choosenParcel.getId());
    Iterator<String> iterator = result.iterator();

    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }

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
