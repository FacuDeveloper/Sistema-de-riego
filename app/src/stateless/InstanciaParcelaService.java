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
  public InstanciaParcela create(InstanciaParcela newInstance);
  public InstanciaParcela remove(int id);
  public InstanciaParcela modify(int id, InstanciaParcela modifiedInstance);
  public InstanciaParcela find(int id);
  public InstanciaParcela find(Parcel parcela, Cultivo cultivo);
  public InstanciaParcela find(Parcel givenParcel, int id);
  public Collection<InstanciaParcela> findAll();
  public Collection<InstanciaParcela> findInstancesParcelByParcelName(String parcelName);
  public InstanciaParcela findInDevelopment(Parcel givenParcel);
  public boolean crossoverDate(Calendar seedTime, Calendar harvestDate);
  public boolean overlapDates(Collection<InstanciaParcela> instances, InstanciaParcela givenInstance);
  public boolean dateOverlayInModification(InstanciaParcela modifiedInstanceParcel);
}
