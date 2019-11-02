/*
 * Esta clase permite extraer de la base de datos la radiacion solar
 * haciendo uso del numero del dia en el año del dia 15 de cada mes
 * y de una latitud en el hemisferio sur desde 0 a -70 grados decimales
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
@Table(name="SOLAR_RADIATION")
public class SolarRadiation {

  /*
   * Instance variables
   */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="SOLAR_RADIATION_ID")
  private int id;

  @Column(name="SOLAR_RADIATION_VALUE")
  private float solarRadiationValue;

  @ManyToOne
  @JoinColumn(name="FK_LATITUDE")
  private Latitude decimalLatitude;

  @ManyToOne
  @JoinColumn(name="FK_MONTH")
  private Month month;

  // Constructor method
  public SolarRadiation() {

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
   * Returns value of solarRadiationValue
   * @return
   */
  public float getRadiation() {
    return solarRadiationValue;
  }

	/**
	* Returns value of decimalLatitude
	* @return decimalLatitude
	*/
	public Latitude getLatitude() {
		return decimalLatitude;
	}

  /**
   * Returns value of month
   * @return
   */
  public Month getMonth() {
    return month;
  }

}
