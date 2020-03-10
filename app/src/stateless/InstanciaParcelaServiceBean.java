package stateless;

import java.util.Collection;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import model.InstanciaParcela;
import model.InstanceParcelStatus;
import model.Parcel;
import model.Cultivo;

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
  public InstanciaParcela modify(int id, InstanciaParcela ins){
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
    return getEntityManager().find(InstanciaParcela.class, id);
  }

  public InstanciaParcela find(Parcel givenParcel, int id) {
    Query query = getEntityManager().createQuery("SELECT i FROM InstanciaParcela i WHERE i.parcel = :givenParcel AND i.id = :id");
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
    Query query = getEntityManager().createQuery("SELECT e FROM InstanciaParcela e ORDER BY e.id");
    return (Collection<InstanciaParcela>) query.getResultList();
  }

  /**
   * @param  parcelName
   * @return coleccion de instancias de parcelas que conocen
   * a la parcela que tiene el nombre provisto
   */
  public Collection<InstanciaParcela> findInstancesParcelByParcelName(String parcelName) {
    Query query = getEntityManager().createQuery("SELECT e FROM InstanciaParcela e WHERE e.parcel.name = :parcelName ORDER BY e.fechaSiembra");
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
    Query query = getEntityManager().createQuery("SELECT r FROM InstanciaParcela r JOIN r.parcel p JOIN r.status s WHERE (s.name = 'En desarrollo' AND p = :parcel)");
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
   * @param  givenParcel
   * @return la instancia de parcela (registro historico de parcela)
   * mas reciente que esta en el estado "Finalizado", en caso contrario
   * retorna el valor nulo
   */
  public InstanciaParcela findRecentFinished(Parcel givenParcel) {
    Query query = getEntityManager().createQuery("SELECT r FROM InstanciaParcela r WHERE r.id = (SELECT MAX(r.id) FROM InstanciaParcela r JOIN r.parcel p JOIN r.status s WHERE (s.name = 'Finalizado' AND p = :parcel))");
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
   * Comprueba si superposicion de fechas entre las instancias
   * de parcelas y la instancia de parcela dada
   *
   * @param  givenInstanceParcel
   * @return verdadero si hay superposicion de fechas
   * entre las instancias de parcelas y la instancia
   * de parcela dada, falso en caso contrario
   */
  public boolean dateOverlayInCreation(InstanciaParcela givenInstanceParcel) {
    /*
     * Coleccion de instancias de parcelas que conocen a
     * la misma parcela de la instancia de parcela dada
     */
    Collection<InstanciaParcela> instancesParcel = findInstancesParcelByParcelName(givenInstanceParcel.getParcel().getName());
    InstanciaParcela instanceParcelPrevious = null;
    InstanciaParcela instanceParcelNext = null;

    /*
     * Esta variable contendra la distancia en milisegundos
     * que hay entre la fecha de siembra de la instancia
     * de parcela dada y la fecha de cosecha
     * de la instancia de parcela anterior a la
     * instancia de parcela mencionada anteriormente
     */
    long distancePrevious = 90000000000000L;

    /*
     * Esta variable contendra la distancia en milisegundos
     * que hay entre la fecha de cosecha de la instancia
     * de parcela dada y la fecha de siembra de
     * la instancia de parcela siguiente a la
     * instancia de parcela mencionada anteriormente
     */
    long distanceNext = 90000000000000L;

    /*
     * Esta variable contendra la diferencia
     * entre dos fechas dadas
     */
    long result = 0;

    for (InstanciaParcela currentInstanceParcel : instancesParcel) {

      /*
       * Si la fecha de siembra de la instancia de parcela dada
       * es anterior o igual a la fecha de siembra de otra instancia
       * de parcela y si la fecha de cosecha de la instancia de
       * parcela dada es igual o posterior a la fecha de cosecha
       * de otra instancia de parcela, entonces hay superposicion
       * de fechas
       */
      if ((givenInstanceParcel.getFechaCosecha() != null) && (givenInstanceParcel.getFechaSiembra().compareTo(currentInstanceParcel.getFechaSiembra()) <= 0)
      && (givenInstanceParcel.getFechaCosecha().compareTo(currentInstanceParcel.getFechaCosecha()) >= 0)) {
        return true;
      }

      result = Math.abs(currentInstanceParcel.getFechaCosecha().getTimeInMillis() - givenInstanceParcel.getFechaSiembra().getTimeInMillis());

      /*
       * De esta forma se obtiene la instancia
       * de parcela anterior a la instancia
       * de parcela dada, que esta mas cerca,
       * en milisegundos, a la instancia de parcela dada
       */
      if (result < distancePrevious) {
        distancePrevious = result;
        instanceParcelPrevious = currentInstanceParcel;
      }

      /*
       * La fecha de cosecha puede no estar definida
       * cuando se crea una instancia de parcela
       * que va a tener el estado "En desarrollo"
       */
      if (givenInstanceParcel.getFechaCosecha() != null) {
        result = Math.abs(givenInstanceParcel.getFechaCosecha().getTimeInMillis() - currentInstanceParcel.getFechaSiembra().getTimeInMillis());
      }

      /*
       * De esta forma se obtiene la instancia
       * de parcela siguiente a la instancia
       * de parcela dada, que esta mas cerca,
       * en milisegundos, a la instancia de parcela dada
       */
      if ((result < distanceNext) && (givenInstanceParcel.getFechaCosecha() != null)) {
        distanceNext = result;
        instanceParcelNext = currentInstanceParcel;
      }

    } // End for

    /*
     * Si la fecha de siembra de la instancia de parcela
     * dada es igual o mayor a la fecha de siembra de la
     * instancia de parcela previa y si la fecha de siembra
     * de la instancia de parcela dada es menor o igual a
     * la fecha de cosecha de la instancia de parcela previa,
     * entonces hay superposicion de fechas
     */
    if ((instanceParcelPrevious != null) && ((givenInstanceParcel.getFechaSiembra().compareTo(instanceParcelPrevious.getFechaSiembra())) >= 0) &&
    ((givenInstanceParcel.getFechaSiembra().compareTo(instanceParcelPrevious.getFechaCosecha())) <= 0)) {
      return true;
    }

    /*
     * Si la fecha de cosecha de la instancia de parcela
     * dada es igual o mayor que la fecha de siembra de
     * la instancia de parcela siguiente y si la fecha
     * de cosecha de la instancia de parcela dada es
     * menor o igual que la fecha de cosecha de la
     * instancia de parcela siguiente, entonces hay
     * superposicion de fechas
     */
    if ((instanceParcelNext != null) && (givenInstanceParcel.getFechaCosecha() != null) &&
    ((givenInstanceParcel.getFechaCosecha().compareTo(instanceParcelNext.getFechaSiembra())) >= 0) &&
    ((givenInstanceParcel.getFechaCosecha().compareTo(instanceParcelNext.getFechaCosecha())) <= 0)) {
      return true;
    }

    return false;
  }

  /**
   * Comprueba si en la base de datos hay instancias
   * de parcelas de una parcela dada, y si las hay
   * retorna verdadero, en caso contrario retorna
   * falso
   *
   * @return verdadero si la coleccion no tiene
   * ningun elemento, en caso contrario retorna falso
   */
  public boolean isEmpty(String parcelName) {
    Collection<InstanciaParcela> instancesParcel = findInstancesParcelByParcelName(parcelName);
    return instancesParcel.isEmpty();
  }

  /**
   * Comprueba si hay superposicion de fechas entre la instancia
   * de parcela modificada y las demas instancias de parcelas, todas
   * estas y la anterior de la misma parcela
   *
   * @param  modifiedInstanceParcel
   * @return verdadero si hay superposicion de fechas entre
   * la instancia de parcela modificada y las demas instancias
   * de parcelas
   */
  public boolean dateOverlayInModification(InstanciaParcela modifiedInstanceParcel) {
    List<InstanciaParcela> instancesParcel = (List<InstanciaParcela>) findInstancesParcelByParcelName(modifiedInstanceParcel.getParcel().getName());
    InstanciaParcela previuosParcelInstance = null;
    InstanciaParcela nextParcelInstance = null;

    int index = instancesParcel.indexOf(modifiedInstanceParcel);

    if (index > 0) {
      previuosParcelInstance = instancesParcel.get(index - 1);
    }

    if ((index + 1) < instancesParcel.size()) {
      nextParcelInstance = instancesParcel.get(index + 1);
    }

    /*
     * Si existe una instancia de parcela previa a la
     * instancia de parcela modificada y la fecha de
     * siembra de esta ultima esta entre la fecha de siembra
     * y la fecha de cosecha de la instancia de parcela
     * previa, entonces hay superposicion
     */
    if ((previuosParcelInstance != null) && (modifiedInstanceParcel.getFechaCosecha() != null) &&
    (modifiedInstanceParcel.getFechaSiembra().compareTo(previuosParcelInstance.getFechaSiembra()) >= 0) &&
    (modifiedInstanceParcel.getFechaSiembra().compareTo(previuosParcelInstance.getFechaCosecha()) <= 0)) {
      return true;
    }

    /*
     * Si existe una instancia de parcela siguiente a la
     * instancia de parcela modificada y la fecha de
     * cosecha de esta ultima esta entre la fecha de
     * siembra y la fecha de cosecha de la instancia
     * de parcela siguiente, entonces hay superposicion
     */
    if ((nextParcelInstance != null) && (modifiedInstanceParcel.getFechaCosecha() != null) &&
    (modifiedInstanceParcel.getFechaCosecha().compareTo(nextParcelInstance.getFechaSiembra()) >= 0) &&
    (modifiedInstanceParcel.getFechaCosecha().compareTo(nextParcelInstance.getFechaCosecha()) <= 0)) {
      return true;
    }

    /*
     * Si la fecha de siembra de la instancia de parcela
     * modificada es menor o igual que la fecha de siembra
     * de la instancia de parcela previa a la anteriormente
     * mencionada y la fecha de cosecha de la instancia
     * de parcela modificada es mayor o igual a la fecha
     * de cosecha de la instancia de parcela previa a la
     * anteriormente mencionada, entonces hay superposicion
     */
    if ((previuosParcelInstance != null) && (modifiedInstanceParcel.getFechaCosecha() != null) &&
    (modifiedInstanceParcel.getFechaSiembra().compareTo(previuosParcelInstance.getFechaSiembra()) <= 0) &&
    (modifiedInstanceParcel.getFechaCosecha().compareTo(previuosParcelInstance.getFechaCosecha())) >= 0) {
      return true;
    }

    /*
     * Si la fecha de siembra de la instancia de parcela
     * modificada es menor o igual que la fecha de siembra
     * de la instancia de parcela siguiente a la anteriormente
     * mencionada y la fecha de cosecha de la instancia
     * de parcela modificada es mayor o igual a la fecha
     * de cosecha de la instancia de parcela siguiente a
     * a anteriormente mencionada, entonces hay superposicion
     */
    if ((nextParcelInstance != null) && (modifiedInstanceParcel.getFechaCosecha() != null) &&
    (modifiedInstanceParcel.getFechaSiembra().compareTo(nextParcelInstance.getFechaSiembra()) <= 0) &&
    (modifiedInstanceParcel.getFechaCosecha().compareTo(nextParcelInstance.getFechaCosecha())) >= 0) {
      return true;
    }

    return false;
  }


  /**
   * Modifica los estados de las instancias de parcela
   * en base a sus fechas y la fecha actual de sistema
   *
   * @param parcelName
   * @param finalized
   * @param inDevelopment
   * @param waiting
   */
  public void modifyStates(String parcelName, InstanceParcelStatus finalized, InstanceParcelStatus inDevelopment, InstanceParcelStatus waiting) {
    /*
     * Coleccion que tiene todas las instancias de parcela
     * que pertenecen a la misma parcela
     */
    Collection<InstanciaParcela> instances = findInstancesParcelByParcelName(parcelName);

    // Fecha actual del sistema
    Calendar currentDate = Calendar.getInstance();

    for (InstanciaParcela current : instances) {

      /*
       * Si la fecha de cosecha de la instancia de parcela
       * es menor que la fecha actual del sistema, dicha
       * instancia tiene que tener el estado 'Finalizado'
       */
      if ((current.getFechaCosecha().compareTo(currentDate)) < 0) {
        current.setStatus(finalized);
      }

      /*
       * Si la fecha actual del sistema esta entre la fecha de
       * siembra y la fecha de cosecha de la instancia de parcela,
       * esta tiene que tener el estado 'En desarrollo'
       */
      if ((currentDate.compareTo(current.getFechaSiembra()) >= 0) && (currentDate.compareTo(current.getFechaCosecha()) <= 0)) {
        current.setStatus(inDevelopment);
      }

      /*
       * Si la fecha de siembra de la instancia de parcela
       * es mayor que la fecha actual del sistema, dicha
       * instancia tiene que tener el estado 'En espera'
       */
      if (current.getFechaSiembra().compareTo(currentDate) > 0) {
        current.setStatus(waiting);
      }

    } // End for

  }

}
