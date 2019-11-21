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
      instanciaParcela.setStatus(ins.getStatus());
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

  public InstanciaParcela find(Parcel givenParcel, int id) {
    Query query = getEntityManager().createQuery("SELECT i FROM InstanciaParcela i WHERE i.parcel = :givenParcel AND i.id = :id");
    query.setParameter("givenParcel", givenParcel);
    query.setParameter("id", id);

    InstanciaParcela instanciaParcela = null;

    try {
      instanciaParcela = (InstanciaParcela) query.getSingleResult();
    } catch(NoResultException noresult) {

    }

    return instanciaParcela;
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
   * Se considera registro historico actual de parcela a
   * aquel que esta tiene su cultivo en el estado "En desarrollo"
   *
   * Solo puede haber un unico registro historico de parcela
   * en el estado mencionado y esto es para cada parcela
   * existente en el sistema, con lo cual siempre deberia
   * haber un unico registro historico actual de parcela
   * para cada parcela existente en el sistema
   *
   * @param  givenParcel
   * @return registro historico de parcela actual, si hay uno
   * actual, en caso contrario retorna falso
   */
  public InstanciaParcela findInDevelopment(Parcel givenParcel) {
    Query query = getEntityManager().createQuery("SELECT r FROM InstanciaParcela r JOIN r.parcel p JOIN r.status s WHERE (s.name = 'En desarrollo' AND p = :parcel)");
    query.setParameter("parcel", givenParcel);

    InstanciaParcela resultingParcelInstancce = null;

    try {
      resultingParcelInstancce = (InstanciaParcela) query.getSingleResult();
    } catch(Exception e) {

    }

    return resultingParcelInstancce;
  }

  /**
   * @param  givenParcel
   * @return la instancia de parcela (registro historico de parcela)
   * mas reciente que esta en el estado "Finalizado", en caso contrario
   * retorna el valor nulo
   */
  public InstanciaParcela findRecentFinished(Parcel givenParcel) {
    Query query = getEntityManager().createQuery("SELECT r FROM InstanciaParcela r WHERE r.id = (SELECT MAX(r.id) FROM InstanciaParcela r JOIN r.parcel p JOIN r.status s WHERE (s.name = 'Finalizado' AND p = :parcel))");
    query.setParameter("parcel", givenParcel);

    InstanciaParcela resultingParcelInstancce = null;

    try {
      resultingParcelInstancce = (InstanciaParcela) query.getSingleResult();
    } catch(Exception e) {

    }

    return resultingParcelInstancce;
  }

}
