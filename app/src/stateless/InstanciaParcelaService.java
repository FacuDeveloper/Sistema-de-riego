package stateless;

import java.util.Collection;

import javax.persistence.EntityManager;

import model.Cultivo;
import model.InstanciaParcela;
import model.Parcel;

public interface InstanciaParcelaService {
  public void setEntityManager(EntityManager em);
  public InstanciaParcela create(InstanciaParcela ins);
  public InstanciaParcela remove(int id);
  public InstanciaParcela change(int id, InstanciaParcela ins);
  public InstanciaParcela find(int id);
  public InstanciaParcela find(Parcel parcela, Cultivo cultivo);
  public Collection<InstanciaParcela> findAll();
  public InstanciaParcela findCurrentParcelInstance(Parcel parcel);
}
