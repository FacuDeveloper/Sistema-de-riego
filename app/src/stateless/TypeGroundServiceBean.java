package stateless;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;

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

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public TypeGround create(TypeGround typeGround) {
    getEntityManager().persist(typeGround);
    return typeGround;
  }

  /**
   * Elimina un suelo mediante su id
   *
   * @param  id
   * @return No nulo en caso de haber eliminado el suelo, en caso contrario nulo
   */
  public TypeGround remove(int id) {
    TypeGround typeGround = find(id);

    if (typeGround != null) {
      getEntityManager().remove(typeGround);
      return typeGround;
    }

    return null;
  }

  /**
   * Actualiza o modifica la entidad asociada al id dado
   *
   * @param  id
   * @param  modifiedTypeGround
   * @return un valor no nulo en caso de modificar la entidad solicitada
   * mediante el id, en caso contrario retorna un valor nulo
   */
  public TypeGround modify(int id, TypeGround modifiedTypeGround) {
    TypeGround choosenTypeGround = find(id);

    if (choosenTypeGround != null) {
      choosenTypeGround.setTexture(modifiedTypeGround.getTexture());
      choosenTypeGround.setDescription(modifiedTypeGround.getDescription());
      return choosenTypeGround;
    }

    return null;
  }

  public TypeGround find(int id) {
    return getEntityManager().find(TypeGround.class, id);
  }

  public Collection<TypeGround> findAll() {
    Query query = getEntityManager().createQuery("SELECT t FROM TypeGround t ORDER BY t.id");
    return (Collection<TypeGround>) query.getResultList();
  }

  /**
   * Busca los tipos de suelo que coincidan con el nombre dado
   *
   * Esto es necesario para la busqueda que se hace cuando se ingresan caracteres
   *
   * @param  textureName
   * @return coleccion de tipos de suelo que coinciden con el nombre dado
   */
  public Collection<TypeGround> findByTextureName(String textureName) {
    StringBuffer queryStr = new StringBuffer("SELECT t FROM TypeGround t");

    if (textureName != null) {
      queryStr.append(" WHERE UPPER(t.texture) LIKE :texture ");
    }

    Query query = entityManager.createQuery(queryStr.toString());

    if (textureName != null) {
      query.setParameter("texture", "%" + textureName.toUpperCase() + "%");
    }

    Collection<TypeGround> types = (Collection<TypeGround>) query.getResultList();
    return types;
  }

}
