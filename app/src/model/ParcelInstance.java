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

  @Column(name="SEED_DATE", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar seedDate; // fecha de siembra de un cultivo

  @Column(name="HARVEST_DATE")
  @Temporal(TemporalType.DATE)
  private Calendar harvestDate; // fecha de cosecha de un cultivo

  @ManyToOne
  @JoinColumn(name="FK_CROP", nullable=false)
  private Cultivo crop;

  @ManyToOne
  @JoinColumn(name="FK_PARCEL", nullable=false)
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

  @Override
  public String toString() {
    return String.format("ID: %s\nCultivo: %s\nFecha de siembra: %s\n", id, crop.getNombre(),
    (seedDate.get(Calendar.DAY_OF_MONTH) + "-" + (seedDate.get(Calendar.MONTH) + 1) + "-" + seedDate.get(Calendar.YEAR)));
  }

}
