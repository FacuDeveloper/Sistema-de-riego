package stateless;

import java.util.Collection;
import java.util.List;
import java.util.Calendar;

import javax.persistence.EntityManager;

import model.Cultivo;
import model.InstanciaParcela;
import model.InstanceParcelStatus;
import model.Parcel;

public interface InstanciaParcelaService {
  public void setEntityManager(EntityManager em);
  public InstanciaParcela create(InstanciaParcela ins);
  public InstanciaParcela remove(int id);
  public InstanciaParcela modify(int id, InstanciaParcela ins);
  public InstanciaParcela find(int id);
  public InstanciaParcela find(Parcel parcela, Cultivo cultivo);
  public InstanciaParcela find(Parcel givenParcel, int id);
  public Collection<InstanciaParcela> findAll();
  public Collection<InstanciaParcela> findInstancesParcelByParcelName(String parcelName);
  public InstanciaParcela findInDevelopment(Parcel givenParcel);
  public InstanciaParcela findRecentFinished(Parcel givenParcel);
  public boolean dateOverlayInCreation(InstanciaParcela givenInstanceParcel);
  public boolean dateOverlayInModification(InstanciaParcela modifiedInstanceParcel);
}
