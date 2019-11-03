/*
 * Esta clase representa el registro historico
 * de la entidad parcela
 */

package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.Calendar;

@Entity
@Table(name="PARCEL_INSTANCE")
public class ParcelInstance {

  @Id
  @Column(name="PARCEL_INSTANCE_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name="SEED_DATE")
  @Temporal(TemporalType.DATE)
  private Calendar seedDate; // fecha de siembra

  @Column(name="HARVEST_DATE")
  @Temporal(TemporalType.DATE)
  private Calendar harvestDate; // fecha de cosecha

  @ManyToOne
  @JoinColumn(name="FK_CROP")
  private Cultivo crop;

  @ManyToOne
  @JoinColumn(name="FK_PARCEL")
  private Parcel parcel;

  // Constructor method
  public ParcelInstance() {

  }

  public int getId() {
    return id;
  }

  public Calendar getSeedDate() {
    return seedDate;
  }

  public void setSeedDate(Calendar seedDate) {
    this.seedDate = seedDate;
  }

  public Calendar getHarvestDate() {
    return harvestDate;
  }

  public void setHarvestDate(Calendar harvestDate) {
    this.harvestDate = harvestDate;
  }

  public Cultivo getCrop() {
    return crop;
  }

  public void setCrop(Cultivo crop) {
    this.crop = crop;
  }

  public Parcel getParcel() {
    return parcel;
  }

  public void setParcel(Parcel parcel) {
    this.parcel = parcel;
  }

}
