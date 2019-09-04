package stateless;

import java.util.Collection;
// import java.util.Calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Ground;

@Stateless
public  class GroundServiceBean {

  /*
   * Instance variables
   */
  @PersistenceContext(unitName="irrigation")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager localEntityManager){
    entityManager = localEntityManager;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Ground create(Ground ground) {
    getEntityManager().persist(ground);
    return ground;
  }

  /**
   * Elimina un suelo mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado el suelo, en caso contrario nulo
   */
  public Ground remove(int id) {
    Ground ground = find(id);

    if (ground != null) {
      getEntityManager().remove(ground);
      return ground;
    }

    return null;
  }

  /**
   * Actualiza o modifica la entidad asociada al id dado
   *
   * @param  id
   * @param  modifiedGround
   * @return un valor no nulo en caso de modificar la entidad solicitada
   * mediante el id, en caso contrario retorna un valor nulo
   */
  public Ground modify(int id, Ground modifiedGround) {
    Ground choosenGround = find(id);

    if (choosenGround != null) {
      choosenGround.setDepth(modifiedGround.getDepth());
      choosenGround.setStony(modifiedGround.getStony());
      choosenGround.setTypeGround(modifiedGround.getTypeGround());
      return choosenGround;
    }

    return null;
  }

  public Ground find(int id) {
    return getEntityManager().find(Ground.class, id);
  }

  public Collection<Ground> findAll() {
    Query query = getEntityManager().createQuery("SELECT g FROM Ground g ORDER BY g.id");
    return (Collection<Ground>) query.getResultList();
  }

}
