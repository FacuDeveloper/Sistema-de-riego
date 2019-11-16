/*
 * Esta clase representa el registro historico
 * de la entidad parcela
 */

package model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="INSTANCIA_PARCELA")
public class InstanciaParcela {

  @Id
  @Column(name="INSTANCIA_PARCELA_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name="FECHA_SIEMBRA", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar fechaSiembra;

  @Column(name="FECHA_COSECHA", nullable=false)
  @Temporal(TemporalType.DATE)
  private Calendar fechaCosecha;

  @ManyToOne
  @JoinColumn(name="FK_PARCELA", nullable=false)
  private Parcel parcel;

  @ManyToOne
  @JoinColumn(name="FK_CULTIVO", nullable=false)
  private Cultivo cultivo;

  @ManyToOne
  @JoinColumn(name="FK_ESTADO", nullable=false)
  private InstanceParcelStatus instanceParcelStatus;

  // Constructor method
  public InstanciaParcela() {

  }

  public int getId() {
    return this.id;
  }

  public Calendar getFechaSiembra() {
    return fechaSiembra;
  }

  public void setFechaSiembra(Calendar fechaSiembra) {
    this.fechaSiembra = fechaSiembra;
  }

  public Calendar getFechaCosecha() {
    return fechaCosecha;
  }

  public void setFechaCosecha(Calendar fechaCosecha) {
    this.fechaCosecha = fechaCosecha;
  }

  public Cultivo getCultivo() {
    return cultivo;
  }

  public void setCultivo(Cultivo cultivo) {
    this.cultivo = cultivo;
  }

  public Parcel getParcel() {
    return parcel;
  }

  public void setParcel(Parcel parcel) {
    this.parcel = parcel;
  }

  public InstanceParcelStatus getStatus() {
    return instanceParcelStatus;
  }

  public void setStatus(InstanceParcelStatus instanceParcelStatus) {
    this.instanceParcelStatus = instanceParcelStatus;
  }

}
