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
  public Cultivo modify(int id, Cultivo cultivo);
  public Cultivo find(int id);
  public Collection<Cultivo> findAll();
  public Collection<Cultivo> findAllActive();
  public Page<Cultivo> findByPage(Integer page, Integer cantPerPage, Map<String, String> parameters);
  public double getKc(Cultivo crop, Calendar seedTime);

  /*
   * Metodo utilizado para la clase de prueba
   * unitaria llamada KcTest
   */
  public double getKc(Cultivo crop, Calendar seedTime, Calendar currentDate);
  public Cultivo findByName(String cropName);
  public Calendar calculateHarvestDate(Calendar seedDate, Cultivo crop);
}
