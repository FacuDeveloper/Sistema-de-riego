package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Collection;

/*
 * NOTE: Revisar si va a existir o no el tipo de suelo
 *
 * En base a esta decision se va a crear o no el modelo
 * de datos suelo
 */
@Entity
@Table(name="TYPE_GROUND")
public class TypeGround {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="TYPE_GROUND_ID")
  private int id;

  @Column(name="TEXTURE", unique=true, length=30, nullable=false)
  private String texture;

  @Column(name="DESCRIPTION", unique=true, length=180, nullable=false)
  private String description;

  // Constructor method
  public TypeGround() {

  }

  /*
   * Getters and setters
   */
  public int getId() {
    return id;
  }

  public String getTexture() {
    return texture;
  }

  public void setTexture(String texture) {
    this.texture = texture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Type ground id: " + id + " texture: " + texture;
  }

}
