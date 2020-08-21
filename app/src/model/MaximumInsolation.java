/*
 * Esta clase permite extraer de la base de datos la insolacion
 * maxima diaria (N) haciendo uso del numero del dia en el año
 * del dia 15 de cada mes y de una latitud en el hemisferio
 * sur desde 0 a -70 grados decimales
 */

package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="INSOLACION_MAXIMA")
public class MaximumInsolation {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="MAXIMUM_INSOLATION_ID")
  private int id;

  @Column(name="MAXIMUM_INSOLATION_VALUE")
  private float maximumInsolationValue;

  @ManyToOne
  @JoinColumn(name="FK_LATITUDE")
  private Latitude decimalLatitude;

  @ManyToOne
  @JoinColumn(name="FK_MONTH")
  private Month month;

  // Constructor method
  public MaximumInsolation() {

  }

  /* Getters and setters */

	/**
	* Returns value of id
	* @return
	*/
	public int getId() {
		return id;
	}

	/**
	* Returns value of decimalLatitude
	* @return decimalLatitude
	*/
	public Latitude getLatitude() {
		return decimalLatitude;
	}

  /**
   * Returns value of maximumInsolationValue
   * @return
   */
  public float getInsolation() {
    return maximumInsolationValue;
  }

  /**
   * Returns value of month
   * @return
   */
  public Month getMonth() {
    return month;
  }

}
