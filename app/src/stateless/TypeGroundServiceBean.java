package stateless;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.TypeGround;

@Stateless
public  class TypeGroundServiceBean {

  /*
   * Instance variables
   */
  @PersistenceContext(unitName="irrigation")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager localEntityManager){
    entityManager = localEntityManager;
  }

}
