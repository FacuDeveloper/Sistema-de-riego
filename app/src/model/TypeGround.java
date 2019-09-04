package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Collection;

@Entity
public class TypeGround {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="TYP_GRO_ID")
  private int id;

  @Column(name="TEXTURE", unique=true, length=90, nullable=false)
  private String texture;

  @Column(name="DESCRIPTION", unique=true, length=90, nullable=false)
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
