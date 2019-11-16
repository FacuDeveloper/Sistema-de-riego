/*
 * Esta clase representa el estado del cultivo
 * que aparece como "sembrado" en la clase entidad
 * instancia parcela (registro historico de parcela)
 */

package model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="INSTANCIA_PARCELA_ESTADO")
public class InstanceParcelStatus {

  @Id
  @Column(name="INSTANCIA_PARCELA_ESTADO_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(name="NOMBRE", nullable=false)
  private String name;

  @Column(name="DESCRIPCION", nullable=false)
  private String description;

  // Constructor method
  public InstanceParcelStatus() {

  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
