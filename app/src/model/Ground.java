package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ground {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="GROUND_ID")
  private int id;

  @Column(name="DEPTH", nullable=false)
  private int depth; // profundidad, medida en centimetros

  @Column(name="STONY", nullable=false)
  private int stony; // pedregosidad, medida en porcentaje

  @ManyToOne
  @JoinColumn(name="TYP_GRO_ID", nullable=false)
  private TypeGround typeGround; // tipo de suelo

  // Constructor method
  public Ground() {

  }

  /*
   * Getters and setters
   */
  public int getId() {
    return id;
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public int getStony() {
    return stony;
  }

  public void setStony(int stony) {
    this.stony = stony;
  }

  public TypeGround getTypeGround() {
    return typeGround;
  }

  public void setTypeGround(TypeGround typeGround) {
    this.typeGround = typeGround;
  }

  @Override
  public String toString() {
    return "Ground id: " + id + " depth: " + depth + " stony: " + stony;
  }

}
