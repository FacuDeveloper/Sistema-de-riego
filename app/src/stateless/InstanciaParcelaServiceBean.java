package stateless;

import java.util.Collection;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import java.util.Calendar;
import java.util.List;

import model.InstanciaParcela;
import model.InstanceParcelStatus;
import model.Parcel;
import model.Cultivo;
import model.InstanceParcelStatus;

import java.lang.Math;

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
   * @param InstanciaParcela newInstance
   * @return InstanciaParcela se retorna la InstanciaParcela persistida en la base de datos
   */
  public InstanciaParcela create(InstanciaParcela newInstance){
    entityManager.persist(newInstance);
    return newInstance;
  }

  public InstanciaParcela remove(Parcel parcela, Cultivo cultivo){
    InstanciaParcela instanciaParcela = find(parcela, cultivo);

    if (instanciaParcela != null) {
      entityManager.remove(instanciaParcela);
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
      entityManager.remove(instanciaParcela);
      return instanciaParcela;
    }

    return null;
  }

  /**
   * Modifica los valores de una instancia de parcela identificada con el id recibido
   * @param  id
   * @param  modifiedInstance
   * @return la instancia de parcela modificada si se encuentra la instancia
   * de parcela con el identificador dado, en caso contrario retorna nulo
   */
  public InstanciaParcela modify(int id, InstanciaParcela modifiedInstance){
    InstanciaParcela choosenInstance = find(id);

    if (choosenInstance != null) {
      if (choosenInstance.getId() != modifiedInstance.getId()) {
        return null;
      }

      choosenInstance.setFechaSiembra(modifiedInstance.getFechaSiembra());
      choosenInstance.setFechaCosecha(modifiedInstance.getFechaCosecha());
      choosenInstance.setParcel(modifiedInstance.getParcel());
      choosenInstance.setCultivo(modifiedInstance.getCultivo());
      choosenInstance.setStatus(modifiedInstance.getStatus());
      return choosenInstance;
    }

    return null;
  }

  public InstanciaParcela find(Parcel parcela, Cultivo cultivo){
    Query query = entityManager.createQuery("SELECT e FROM InstanciaParcela e WHERE e.parcela = :parcela AND e.cultivo = :cultivo");
    query.setParameter("parcela", parcela);
    query.setParameter("cultivo", cultivo);

    InstanciaParcela instanceParcelFound;

    try {
      instanceParcelFound = (InstanciaParcela) query.getSingleResult();
    } catch(NoResultException noresult) {
      instanceParcelFound = null;
    }

    return instanceParcelFound;
  }

  /**
  * Busca una instancia de parcela en la base de datos con el id recibido
  * @param int id Id que identifica una unica instancia de parcela
  * @return InstanciaParcela se retorna la instancia de parcela encontrada, o null si no existe ninguna instancia con el id recibido
  */
  public InstanciaParcela find(int id){
    return entityManager.find(InstanciaParcela.class, id);
  }

  public InstanciaParcela find(Parcel givenParcel, int id) {
    Query query = entityManager.createQuery("SELECT i FROM InstanciaParcela i WHERE i.parcel = :givenParcel AND i.id = :id");
    query.setParameter("givenParcel", givenParcel);
    query.setParameter("id", id);

    InstanciaParcela instanciaParcela;

    try {
      instanciaParcela = (InstanciaParcela) query.getSingleResult();
    } catch(NoResultException noresult) {
      instanciaParcela = null;
    }

    return instanciaParcela;
  }

  /**
   * @return retorna una coleccion con todas las instancias de parcela de
   * la base de datos subyacente
   */
  public Collection<InstanciaParcela> findAll() {
    Query query = entityManager.createQuery("SELECT e FROM InstanciaParcela e ORDER BY e.id");
    return (Collection<InstanciaParcela>) query.getResultList();
  }

  /**
   * @param  parcelName
   * @return coleccion de instancias de parcelas que conocen
   * a la parcela que tiene el nombre provisto
   */
  public Collection<InstanciaParcela> findInstancesParcelByParcelName(String parcelName) {
    Query query = entityManager.createQuery("SELECT e FROM InstanciaParcela e WHERE e.parcel.name = :parcelName ORDER BY e.fechaSiembra");
    query.setParameter("parcelName", parcelName);

    return (Collection<InstanciaParcela>) query.getResultList();
  }

  /**
   * Busca en la base de datos subyacente cada una de las
   * instancias de parcelas pertenecientes a la parcela
   * que tiene el nombre dado excepto aquella instancia
   * de parcela que tiene el identificador dado
   *
   * En otras palabras, busca en la base de datos subyacente
   * todas las instancias de parcela pertenecientes a una
   * parcela dada, excepto una de esas instancias de parcelas
   *
   * Esta busqueda es para cuando se hace la modificacion de
   * una instancia de parcela, accion en la cual se tiene que
   * hacer la comprobacion de superposicion de fechas de la
   * instancia de parcela modificada con las demas instancias
   * de parcelas pertenecientes a la misma parcela, excepto
   * con aquella instancia de parcela a modificar, ya que
   * si dicha comprobacion se hace tambien comparando con
   * la instancia de parcela a modificar entonces siempre
   * habra superposicion de fechas en la modificacion de
   * una instancia de parcela
   *
   * @param  idInstance
   * @param  parcelName
   * @return coleccion que no contiene la instancia de parcela con el
   * identificador dado y que si las instancias de parcelas pertenecientes a la
   * parcela que tiene el nombre dado, siendo la instancia de parcela
   * con el identificador dado tambien perteneciente a la misma parcela
   */
  public Collection<InstanciaParcela> findInstancesExceptOne(int idInstance, String parcelName) {
    Query query = entityManager.createQuery("SELECT e FROM InstanciaParcela e WHERE (e.id <> :idInstance AND e.parcel.name = :parcelName) ORDER BY e.fechaSiembra");
    query.setParameter("idInstance", idInstance);
    query.setParameter("parcelName", parcelName);

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
    Query query = entityManager.createQuery("SELECT r FROM InstanciaParcela r JOIN r.parcel p JOIN r.status s WHERE (s.name = 'En desarrollo' AND p = :parcel)");
    query.setParameter("parcel", givenParcel);

    InstanciaParcela resultingParcelInstancce;

    try {
      resultingParcelInstancce = (InstanciaParcela) query.getSingleResult();
    } catch(Exception e) {
      resultingParcelInstancce = null;
    }

    return resultingParcelInstancce;
  }

  /**
   * Comprueba si la fecha de siembra y la fecha de cosecha de
   * una instancia de parcela estan superpuestas o cruzadas
   *
   * @param  seedTime    [fecha de siembra]
   * @param  harvestDate [fecha de cosecha]
   * @return verdadero si la fecha de siembra es igual o mayor que
   * la fecha de cosecha de una instancia de parcela, falso en
   * caso contrario
   */
  public boolean crossoverDates(Calendar seedTime, Calendar harvestDate) {
    /*
     * Si la fecha de siembra es mayor o igual a la
     * fecha de cosecha de una instancia de parcela
     * entonces la fecha de siembra y la fecha de
     * cosecha estan cruzadas, es decir, superpuestas
     */
    if (seedTime.compareTo(harvestDate) >= 0) {
      return true;
    }

    return false;
  }

  /**
   * Comprueba si hay superposicion de fechas entre la instancia
   * de parcela dada y las demas instancias de parcela pertenecientes
   * a la misma parcela
   *
   * @param  instances
   * @param  givenInstance
   * @return verdadero si hay superposicion de fechas entre la instancia
   * de parcela dada y las instancias de parcelas pertenecientes a la
   * misma parcela, en caso contrario falso
   */
  public boolean overlapDates(Collection<InstanciaParcela> instances, InstanciaParcela givenInstance) {
    for (InstanciaParcela currentInstance : instances) {
      /*
       * Si la fecha de siembra de la instancia de parcela dada
       * es mayor o igual que la fecha de siembra de la instancia
       * de parcela actual y a su vez es menor o igual que la fecha
       * de cosecha de la instancia de parcela actual entonces hay
       * superposicion de fechas
       */
      if ((givenInstance.getFechaSiembra().compareTo(currentInstance.getFechaSiembra()) >= 0) &&
        (givenInstance.getFechaSiembra().compareTo(currentInstance.getFechaCosecha()) <= 0)) {
        return true;
      }

      /*
       * Si la fecha de cosecha de la instancia de parcela dada
       * es mayor o igual que la fecha de siembra de la instancia
       * de parcela actual y a su vez es menor o igual que la fecha
       * de cosecha de la instancia de parcela actual entonces hay
       * superposicion de fechas
       */
      if ((givenInstance.getFechaCosecha().compareTo(currentInstance.getFechaSiembra()) >= 0)
        && (givenInstance.getFechaCosecha().compareTo(currentInstance.getFechaCosecha()) <= 0)) {
        return true;
      }

      /*
       * Si la fecha de siembra de la instancia de parcela dada
       * es menor o igual que la fecha de siembra de la instancia
       * de parcela actual y la fecha de cosecha de la instancia
       * de parcela dada es mayor o igual que la fecha de cosecha
       * de la instancia de parcela actual entonces hay superposicion
       * de fechas
       */
      if ((givenInstance.getFechaSiembra().compareTo(currentInstance.getFechaSiembra()) <= 0)
        && (givenInstance.getFechaCosecha().compareTo(currentInstance.getFechaCosecha()) >= 0)) {
        return true;
      }

    }

    return false;
  }

}
