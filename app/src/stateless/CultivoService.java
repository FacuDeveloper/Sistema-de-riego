package stateless;

import java.util.Collection;
import java.util.Calendar;
import java.util.Map;

import javax.persistence.EntityManager;

import model.Cultivo;
//import model.TipoCultivo;

public interface CultivoService {
  public void setEntityManager(EntityManager em);
  public Cultivo create(Cultivo cultivo);
  public Cultivo remove(int id);
  public Cultivo change(int id, Cultivo cultivo);
  public Cultivo find(int id);
  public Collection<Cultivo> findAll();
  public Page<Cultivo> findByPage(Integer page, Integer cantPerPage, Map<String, String> parameters);
  public double getKc(Cultivo crop, Calendar seedTime);
}
