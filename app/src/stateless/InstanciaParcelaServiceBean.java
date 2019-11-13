package stateless;

import java.util.Collection;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import java.util.Calendar;

import model.InstanciaParcela;
import model.Parcel;
import model.Cultivo;

@Stateless
public class InstanciaParcelaServiceBean implements InstanciaParcelaService {

  @PersistenceContext(unitName="SisRiegoDB")
  protected EntityManager entityManager;

  public void setEntityManager(EntityManager emLocal){
    entityManager = emLocal;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Persiste una instancia de parcela en la base datos
   * @param InstanciaParcela ins
   * @return InstanciaParcela se retorna la InstanciaParcela persistida en la base de datos
   */
  public InstanciaParcela create(InstanciaParcela ins){
    getEntityManager().persist(ins);
    return ins;
  }

  public InstanciaParcela remove(Parcel parcela, Cultivo cultivo){
    InstanciaParcela instanciaParcela = find(parcela, cultivo);

    if (instanciaParcela != null) {
      getEntityManager().remove(instanciaParcela);
      return instanciaParcela;
    }

    return null;
  }

  /**
   * Remueva la instancia de parcela con el id solicitado
   * @param int id que indentifica una instancia de parcela
   * @return InstanciaParcela se retorna la instancia de parcela eliminada o null si no se encontro ninguna con el id
   */
  public InstanciaParcela remove(int id){
    InstanciaParcela instanciaParcela = find(id);

    if (instanciaParcela != null) {
      getEntityManager().remove(instanciaParcela);
      return instanciaParcela;
    }

    return null;
  }

  /**
   * Modifica los valores de una instancia de parcela identificada con el id recibido
   * @param int id que identifica la instancia a modificar
   * @param InstanciaParcela
   * @return se retorna la instancia de parcela modificada o null si no se encuentra ninguna con el id recibido
   */
  public InstanciaParcela change(int id, InstanciaParcela ins){
    InstanciaParcela instanciaParcela = find(id);

    if (instanciaParcela != null) {
      if (instanciaParcela.getId() != ins.getId()){
        return null;
      }

      instanciaParcela.setParcel(ins.getParcel());
      instanciaParcela.setCultivo(ins.getCultivo());
      instanciaParcela.setFechaSiembra(ins.getFechaSiembra());
      instanciaParcela.setFechaCosecha(ins.getFechaCosecha());
      return instanciaParcela;
    }

    return null;
  }

  public InstanciaParcela find(Parcel parcela, Cultivo cultivo){
    Query query = entityManager.createQuery("SELECT e FROM InstanciaParcela e where e.parcela = :parcela and e.cultivo = :cultivo");
    query.setParameter("parcela", parcela);
    query.setParameter("cultivo", cultivo);

    InstanciaParcela instanciaParcela = null;

    try {
      instanciaParcela = (InstanciaParcela) query.getSingleResult();
    } catch(NoResultException noresult) {

    }

    return instanciaParcela;
  }

  /**
  * Busca una instancia de parcela en la base de datos con el id recibido
  * @param int id Id que identifica una unica instancia de parcela
  * @return InstanciaParcela se retorna la instancia de parcela encontrada, o null si no existe ninguna instancia con el id recibido
  */
  public InstanciaParcela find(int id){
    return getEntityManager().find(InstanciaParcela.class, id);
  }

  /**
   * @return retorna una coleccion con todas las instancias de parcela de
   * la base de datos subyacente
   */
  public Collection<InstanciaParcela> findAll() {
    Query query = getEntityManager().createQuery("SELECT e FROM InstanciaParcela e ORDER BY e.id");
    return (Collection<InstanciaParcela>) query.getResultList();
  }

  /**
   * @param  parcel
   * @return registro historico, de una parcela dada, que
   * tiene fecha de siembra y que no tiene fecha de cosecha
   * para un cultivo, en caso contrario retorna el valor
   * nulo
   */
  public InstanciaParcela findCurrentParcelInstance(Parcel parcel) {
    /*
     * Selecciona el registro historico de una parcela dada, que
     * tiene fecha de siembra y que no tiene fecha de cosecha
     * del cultivo sembrado, y este registro es el registro
     * historico actual de la parcela dada
     */
    Query query = getEntityManager().createQuery("SELECT r FROM InstanciaParcela r JOIN r.parcel p WHERE (r.seedDate IS NOT NULL AND r.harvestDate IS NULL AND p = :parcel)");
    query.setParameter("parcel", parcel);

    InstanciaParcela resultingParcelInstancce = null;

    try {
      resultingParcelInstancce = (InstanciaParcela) query.getSingleResult();
    } catch(Exception e) {

    }

    return resultingParcelInstancce;
  }

}
