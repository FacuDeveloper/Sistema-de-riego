import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import stateless.ParcelServiceBean;

import model.Parcel;
import model.MaxCrop;

import java.util.List;
import java.util.ArrayList;

public class MaxCropTest {
  private static EntityManager entityManager;
  private static EntityManagerFactory entityMangerFactory;
  private static ParcelServiceBean parcelService;

  @BeforeClass
  public static void preTest(){
    entityMangerFactory = Persistence.createEntityManagerFactory("SisRiegoDB");
    entityManager = entityMangerFactory.createEntityManager();

    parcelService = new ParcelServiceBean();
    parcelService.setEntityManager(entityManager);
  }

  /*
   * Bloque de codigo fuente de prueba unitaria
   * que tiene como objeto probar la consulta SQL
   * que recupera el cultivo que mas fue sembrado
   * en una parcela dada
   */
  @Test
  public void testMaxCrop() {
    Parcel choosenParcel = parcelService.find(1);

    /*
     * Agrupa por parcela y por cultivo contando la cantidad de
     * veces que aparece un cultivo en cada grupo <parcela, cultivo>
     * lo que hace que uno sepa la cantidad de veces que fue plantado
     * un cultivo en una parcela dada
     *
     * De lo anterior selecciona los cultivos y la cantidad de
     * veces que fue plantado cada uno en una parcela dada
     */
    String countCrop = "SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS CANT_VECES_SEMBRADO FROM INSTANCIA_PARCELA INNER JOIN CULTIVO ON FK_CULTIVO = CULTIVO_ID INNER JOIN PARCEL ON FK_PARCELA = PARCEL_ID WHERE FK_PARCELA = ?1 GROUP BY FK_PARCELA, FK_CULTIVO";

    /*
     * De la consulta anterior selecciona la mayor cantidad de veces
     * que fue plantado un cultivo dado en una parcela dada pero no
     * selecciona el cultivo, ni la parcela en la cual fue plantado
     *
     * Tambien se puede dar que seleccione mas de un cultivo porque
     * puede que existan varios cultivos que fueron sembrados la
     * misma cantidad maxima de veces en sus correspondientes parcelas
     */
    String maxCountCrop = "SELECT MAX(R.CANT_VECES_SEMBRADO) AS MAX_CANT_VECES_SEMBRADO FROM (SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS CANT_VECES_SEMBRADO FROM INSTANCIA_PARCELA INNER JOIN CULTIVO ON FK_CULTIVO = CULTIVO_ID INNER JOIN PARCEL ON FK_PARCELA = PARCEL_ID WHERE FK_PARCELA = ?2 GROUP BY FK_PARCELA, FK_CULTIVO) AS R";

    /*
     * Selecciona el nombre y la cantidad de veces que fueron
     * sembrados de aquellos cultivos que mas fueron sembrados
     * en sus correspondientes parcelas
     */
    String mainQuery = "SELECT NOMBRE, R.CANT_VECES_SEMBRADO FROM CULTIVO INNER JOIN (" + countCrop + ") AS R ON CULTIVO_ID = R.FK_CULTIVO WHERE R.CANT_VECES_SEMBRADO = (" + maxCountCrop + ")";

    Query query = entityManager.createNativeQuery(mainQuery);
    query.setParameter("1", choosenParcel.getId());
    query.setParameter("2", choosenParcel.getId());

    List<Object[]> maxCrops = query.getResultList();
    List<MaxCrop> crops = new ArrayList<>();

    for (Object[] currentCrop : maxCrops) {
      crops.add(new MaxCrop(((String) currentCrop[0]), ((int) currentCrop[1])));
    }

    for (MaxCrop currentMaxCrop : crops) {
      System.out.println(currentMaxCrop);
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
